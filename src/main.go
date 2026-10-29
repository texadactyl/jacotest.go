package main

import (
	"fmt"
	"os"
	"os/exec"
	"os/signal"
	"path/filepath"
	"runtime"
	"strconv"
	"strings"
	"sync"
	"syscall"
	"time"
)

const MyName = "Jacotest"
const csvFullPath = "./new.class.checksums.csv"
const testsTreeTop = "./tests"

// Show help and then exit to the O/S
func showHelp() {
	_ = InitGlobals("dummy", "dummy", 0, "")
	name := filepath.Base(os.Args[0])
	ShowExecInfo(name)
	fmt.Printf("\nUsage:  %s  [ options ]\n\nwhere\n", name)
	fmt.Printf("\t-h : This display.\n")
	fmt.Printf("\t-c : Compile all the test cases.\n")
	fmt.Printf("\t-f N TC : Create an output CSV file from the last N results for test case TC. N=0 means all results\n")
	fmt.Printf("\t-j name : This is the JVM to use in executing all test cases. Default: jacobin.\n")
	fmt.Printf("\t     Other JVM names recognized:\n")
	fmt.Printf("\t     * hotspot, openjdk : Hotspot JVM (aka Open JDK JVM)\n")
	fmt.Printf("\t     Note that specifying -j implies parameters -x and -r 1.\n")
	fmt.Printf("\t-M : Generate a run report suitable for viewing on github (normally, not produced).\n")
	fmt.Printf("\t-N : Report all database history records whose test case name is obsolete (orphans).\n")
	fmt.Printf("\t-n old new : Rename test case name from old to new.\n")
	fmt.Printf("\t-r 1 : For each test case, print the last two results if there was a changed result.\n")
	fmt.Printf("\t-r 2 : For each failed test case, print this result if it passed sometime previously.\n")
	fmt.Printf("\t-r 3 : For each test case, print the last result.\n")
	fmt.Printf("\t-r 4 : Generate a test case class file checksum CSV file.\n")
	fmt.Printf("\t-s : Delete database records of non-existing test cases (test cases that were previously renamed or removed).\n")
	fmt.Printf("\t-t num : This is the timeout value of num seconds in executing each test case.  Default: 60.\n")
	fmt.Printf("\t-u : JVM options when -x is specified.\n")
	fmt.Printf("\t-v : Verbose logging.\n")
	fmt.Printf("\t-X num : Execute the first num test cases.\n")
	fmt.Printf("\t-x : Execute all test cases.\n")
	fmt.Printf("\t     Specifying -x implies parameter -r 1.\n")
	fmt.Printf("\t-z : Remove the most recent result for all test cases.\n")
	fmt.Print("\n")
	os.Exit(0)
}

// createLatest creates "latest" in the same directory as specified in the path argument,
// pointing to the file. Works on macOS, Linux, and Windows.
func createLatest(path string) error {
	dir := filepath.Dir(path)
	link := filepath.Join(dir, "latest.txt")
	target := filepath.Base(path)

	// Remove existing link/file if present
	_ = os.Remove(link)

	// Prefer a relative symlink (best semantics)
	if err := os.Symlink(target, link); err == nil {
		return nil
	}

	// Fallback: hard link (works on Windows without admin)
	return os.Link(path, link)
}

// Show results
func showResults(category string, arrayNames []string, outHandle *os.File, showList bool) {
	var msg string
	suffix := "test case"
	arrayCount := len(arrayNames)
	if arrayCount > 0 {
		if arrayCount > 1 {
			suffix = suffix + "s"
		}
		logger("%s errors in %d %s", category, arrayCount, suffix)
		writeOutputText(outHandle, "\n"+msg)
		if showList {
			for _, name := range arrayNames {
				msg = fmt.Sprintf("	 %s", name)
				logger("%s", msg)
				writeOutputText(outHandle, msg)
			}
		}
	} else {
		msg = fmt.Sprintf("No %s errors", category)
		logger("%s", msg)
		writeOutputText(outHandle, "\n"+msg)
	}
}

// Command line interface
func main() {
	tStart := time.Now()
	var err error
	exitStatus := 0 // optimistic
	_ = os.Setenv("java.awt.headless", "true")
	var Args []string
	var wString string
	jvmName := "jacobin" // default virtual machine name
	jvmExe := "jacobin"  // default virtual machine executable
	deadlineSecs := 60   // default deadline in seconds
	userXopts := ""
	numTestCases := 0

	// Temporary flags before setting them in global.
	flagVerbose := false          // Verbose logging? true/false
	flagCompile := false          // -c option
	flagDeleteObsolete := false   // -s option
	flagMdReport := false         // -M option
	flagTwoMostRecent := false    // -r 1 option
	flagFailedPassed := false     // -r 2 option
	flagPrintMostRecent := false  // -r 3 option
	flagClassChecksum := false    // -r 4 option
	flagExecute := false          // -x option
	flagDeleteMostRecent := false // -z option
	flagReportOrphans := false    // -N option
	flagRenameTestCase := false   // -n oldName newName

	oldTestCaseName := ""
	newTestCaseName := ""

	// Positioned in the tree top directory?
	_, err = os.Open("VERSION.cfg")
	if err != nil {
		fatalText("You are not positioned in the jacotest tree top directory")
	}

	// Get current timestamp and zone.
	now := time.Now() // Get the current time.
	nowStamp := now.Format("2006-01-02 15:04:05")
	timeZone, _ := now.Zone()

	// Handle signal interruptions.
	var wg sync.WaitGroup
	shutdownChan := make(chan os.Signal, 1)
	signal.Notify(shutdownChan, os.Interrupt, syscall.SIGTERM)

	// Initialise Args to the command-line arguments.
	Args = append(Args, os.Args[1:]...)

	// If no options specified, just show help.
	if len(Args) < 1 {
		showHelp()
	}

	// Parse command line arguments.
	for ii := 0; ii < len(Args); ii++ {
		switch Args[ii] {

		case "-c":
			flagCompile = true

		case "-h":
			showHelp()

		case "-j": // JVM requested
			ii += 1
			jvmName = Args[ii]
			// Validate JVM
			switch jvmName {
			case "hotspot", "openjdk":
				jvmExe = "java" // Hotspot JVM executable name
			case "jacobin":
				jvmExe = "jacobin" // jacobin JVM executable name
			default:
				logError("Unrecognizable JVM name: %s", jvmName)
				showHelp()
			}

		case "-M":
			flagMdReport = true
			flagExecute = true
			flagTwoMostRecent = true

		case "-N":
			flagReportOrphans = true

		case "-n":
			ii++
			if ii >= len(Args) {
				logError("Missing -n argument for old test case name")
				showHelp()
			}
			oldTestCaseName = Args[ii]
			ii++
			if ii >= len(Args) {
				logError("Missing -n argument for new test case name")
				showHelp()
			}
			newTestCaseName = Args[ii]
			flagRenameTestCase = true

		case "-r":
			ii++
			if ii >= len(Args) {
				logError("Missing -r argument")
				showHelp()
			}
			arg := Args[ii]
			switch arg {
			case "1":
				flagTwoMostRecent = true
			case "2":
				flagFailedPassed = true
			case "3":
				flagPrintMostRecent = true
			case "4":
				flagClassChecksum = true
			default:
				logError("Unrecognizable -r argument: %s", Args[ii])
				showHelp()
			}

		case "-f":
			ii++
			if ii >= len(Args) {
				logError("Missing -f arguments")
				showHelp()
			}
			stringCount := Args[ii]
			count, err := strconv.Atoi(stringCount)
			if err != nil || count < 0 {
				logError("Option -f count must be a non-negative integer")
				showHelp()
			}
			ii++
			if ii >= len(Args) {
				logError("Missing -f test case name")
				showHelp()
			}
			testCaseName := Args[ii]
			DBOpen()
			DBGenerateTestCaseCsv(count, testCaseName)
			os.Exit(0)

		case "-s":
			flagDeleteObsolete = true

		case "-t": // Deadline in seconds requested
			ii += 1
			deadlineSecs, err = strconv.Atoi(Args[ii])
			if err != nil {
				logError("Parameter -t requires an integer value, saw: %s", Args[ii])
				showHelp()
			}

		case "-u":
			ii += 1
			userXopts = Args[ii]

		case "-v":
			flagVerbose = true

		case "-X":
			ii += 1
			numTestCases, err = strconv.Atoi(Args[ii])
			if err != nil {
				logError("Parameter -X requires an integer value, saw: %s", Args[ii])
				showHelp()
			}
			if numTestCases < 1 {
				logError("Parameter -X requires an integer value > 0, saw: %s", Args[ii])
				showHelp()
			}
			flagExecute = true
			flagTwoMostRecent = true

		case "-x":
			flagExecute = true
			flagTwoMostRecent = true

		case "-z": // Delete the most recent test result for each test case.
			flagDeleteMostRecent = true

		default:
			logError("Unrecognizable argument: %s", Args[ii])
			showHelp()
		}
	}

	// Initialize global flags and get a handle.
	global := InitGlobals(jvmName, jvmExe, deadlineSecs, userXopts)
	defer deferredRemoveFile(global.TempFile.Name())
	global.FlagVerbose = flagVerbose
	global.FlagCompile = flagCompile
	global.FlagDeleteObsolete = flagDeleteObsolete
	global.FlagMdReport = flagMdReport
	global.FlagTwoMostRecent = flagTwoMostRecent
	global.FlagFailedPassed = flagFailedPassed
	global.FlagPrintMostRecent = flagPrintMostRecent
	global.FlagClassChecksum = flagClassChecksum
	global.FlagExecute = flagExecute
	global.FlagDeleteMostRecent = flagDeleteMostRecent
	global.FlagReportOrphans = flagReportOrphans

	// Start up signal listener.
	wg.Add(1)
	go func() {
		defer wg.Done()
		sig := <-shutdownChan
		fatalText("Shutdown signal received: %v. Cleaning up", sig)
	}()

	// Show the program version.
	if global.FlagPrintMostRecent {
		fmt.Printf("%s version %s", MyName, global.Version)
	} else {
		logger("%s version %s", MyName, global.Version)
	}

	// Open database.
	defer DBClose()
	DBOpen()

	// Make sure that the jvmExe file can be found in the O/S PATH.
	wString, err = exec.LookPath(jvmExe)
	if err != nil {
		fatalErr(err, "exec.LookPath(%s) failed", jvmExe)
	}
	if global.FlagVerbose {
		logger("Found JVM %s", wString)
	}

	// Open the 'logs' directory.
	fileOpened, err := os.Open(global.DirLogs)
	if err != nil {
		fatalErr(err, "os.Open(%s) failed", global.DirLogs)
	}

	// Get all the file entries in the logs directory.
	names, err := fileOpened.Readdirnames(0) // get all entries
	if err != nil {
		fatalErr(err, "fileOpened.Readdirnames(%s) failed", global.DirLogs)
	}

	// For each log file entry, remove it.
	if global.FlagCompile || global.FlagExecute {
		for index := range names {
			name := names[index]
			fullPath := filepath.Join(global.DirLogs, name)
			err := os.Remove(fullPath)
			if err != nil {
				fatalErr(err, "os.Remove(%s) failed", fullPath)
			}
		}
	}

	// Process execute request.
	if global.FlagExecute || global.FlagCompile {
		var successNames []string
		var successETs []int
		var errCompileNames []string
		var errExecutionNames []string
		tblErrCases := make(map[string]int)
		var timeoutExecutionNames []string
		var rptHandle *os.File
		counterErrCases := 0

		if global.FlagMdReport {
			// Initialize detailed report file
			rptHandle, err = os.Create(global.ReportFilePath)
			if err != nil {
				fatalErr(err, "os.Create(%s) failed", global.ReportFilePath)
			}
			defer func() {
				_ = rptHandle.Close()
			}()
			_, _ = fmt.Fprintf(rptHandle, "%s version %s\n", MyName, global.Version)
			_, _ = fmt.Fprintf(rptHandle, "Run report using JVM %s<br>Case deadline = %d seconds<br>Date/Time %s %s<br><br>\n",
				jvmName, deadlineSecs, nowStamp, timeZone)
			_, _ = fmt.Fprintf(rptHandle, "| Test Case | Result | Console Output |\n")
			_, _ = fmt.Fprintf(rptHandle, "| :--- | :---: | :--- |\n")
		}

		// Initialize summary report file.
		outPath := global.SumFilePath
		outHandle, err := os.OpenFile(outPath, FlagsOpen, ModeOutputFile)
		if err != nil {
			fatalText("os.OpenFile(%s) failed, err=%s", outPath, err)
		}
		msg := fmt.Sprintf("%s version %s", MyName, global.Version)
		writeOutputText(outHandle, msg)
		msg = fmt.Sprintf("O/S %s, arch %s", runtime.GOOS, runtime.GOARCH)
		writeOutputText(outHandle, msg)
		outLines, err := exec.Command(jvmExe, "--version").Output()
		if err != nil {
			fatalErr(err, "Error in getting the JVM version")
		}
		writeOutputText(outHandle, "Using this JVM:")
		writeOutputText(outHandle, string(outLines))

		// Get all the subdirectories (test cases) under directory tests,
		entries, err := os.ReadDir(global.DirTests)
		if err != nil {
			fatalErr(err, "os.ReadDir(%s) failed", global.DirTests)
		}

		// Phase 1 - For each test case subdirectory of tests, run it.
		logger("Test case deadline: %d seconds", deadlineSecs)

		// Begin the test case directory loop.
		entryCount := 0
		for _, entry := range entries {

			// If not an O/S directory, skip it.
			if !entry.IsDir() {
				continue
			}

			// It is (hopefully) a test case directory.
			entryCount += 1

			// TODO: True? Check if we have reached the number of test cases to run.
			if numTestCases != 0 && numTestCases < entryCount {
				break
			}

			// Get the test case name and the full path of the test case directory.
			testCaseName := entry.Name()
			fullPath := filepath.Join(global.DirTests, testCaseName)

			// Get the full path of the "main.java" file within the test case directory.
			mainJavaFile := fullPath + string(os.PathSeparator) + "main.java"

			// Check for the existence of main.java.
			_, err := os.Stat(mainJavaFile)
			if err != nil {
				logWarning("File %s does not exist - skipping directory", mainJavaFile)
				continue
			}

			// Take the initial time of this test case.
			// Execute the test case.
			// Take the final time of this test case.
			ti := time.Now()
			resultCode, outLog := ExecuteOneTest(fullPath)
			tf := time.Now()

			// Calculate elapsed time in milliseconds.
			elapsed := tf.Sub(ti)
			etMsecs := int(elapsed.Milliseconds())

			// Clean the test case log file.
			// Some outLog strings have multiple embedded \n characters.
			outLog = strings.ReplaceAll(outLog, "\n", "\n|||")

			// Switch on test case result.
			switch resultCode {
			case RC_NORMAL:
				successNames = append(successNames, testCaseName)
				successETs = append(successETs, etMsecs)
				if global.FlagMdReport {
					_, _ = fmt.Fprintf(rptHandle, "| %s | PASSED | n/a |\n", testCaseName)
				}
				if global.FlagExecute {
					allocMB := -1
					data, err := os.ReadFile(global.TempFile.Name())
					if err == nil {
						allocMB, err = strconv.Atoi(strings.TrimSpace(string(data)))
						if err != nil {
							fatalErr(err, "Format error in memory allocation file %s:", global.TempFile.Name())
						}
					}
					DBStorePassed(testCaseName, etMsecs, allocMB)
				}
			case RC_COMP_ERROR:
				exitStatus = 1
				errCompileNames = append(errCompileNames, testCaseName)
				if global.FlagMdReport {
					_, _ = fmt.Fprintf(rptHandle, "| %s | COMP-ERROR | compilation error(s)\n | | | See logs/FAILED-*-javac.log files |\n", testCaseName)
				}
				if global.FlagExecute {
					DBStoreFailed(testCaseName, "Compile error")
				}
			case RC_EXEC_ERROR:
				exitStatus = 1
				errExecutionNames = append(errExecutionNames, testCaseName)
				tblErrCases[testCaseName] = 0
				if global.FlagMdReport {
					_, _ = fmt.Fprintf(rptHandle, "| %s | FAILED | %s |\n", testCaseName, outLog)
				}
				// DBStoreFailed calls will be made in the SQL database source file.
			case RC_EXEC_TIMEOUT:
				exitStatus = 1
				timeoutExecutionNames = append(timeoutExecutionNames, testCaseName)
				if global.FlagMdReport {
					_, _ = fmt.Fprintf(rptHandle, "| %s | TIMEOUT | %s |\n", testCaseName, outLog)
				}
				if global.FlagExecute {
					DBStoreFailed(testCaseName, "Timeout")
				}
			}
			// End of result switch
		}
		// End test case directory loop.
		// At this point, we have executed all the requested test cases.

		// Show successes.
		msg = fmt.Sprintf("Success in %d test cases", len(successNames))
		logger("%s", msg)
		writeOutputText(outHandle, msg)
		for ix := range successNames {
			msg = fmt.Sprintf("	 %s ... %s", successNames[ix], formatElapsedTime(successETs[ix]))
			writeOutputText(outHandle, msg)
		}

		// Show compilation errors.
		if global.FlagCompile {
			showResults("Compilation", errCompileNames, outHandle, true)
		}

		// Exit now if compilation only.
		if !global.FlagExecute {
			logger("Ended with exit status %d", exitStatus)
			os.Exit(exitStatus)
		}

		// Show timeout errors.
		showResults("Execution timeout", timeoutExecutionNames, outHandle, true)

		// Show execution failures.
		showResults("Execution failure", errExecutionNames, outHandle, false)

		// Phases 2 and 3 - Show failures grouped by error category.
		counterErrCases += Phases2And3(tblErrCases, outHandle)

		// Discrepancies in error total?
		if len(errExecutionNames) != counterErrCases {
			logSkip()
			writeOutputText(outHandle, " ")
			infoMsg := fmt.Sprintf("*** WARNING: Number of error cases = %d but total from fail-categories = %d",
				len(errExecutionNames), counterErrCases)
			logger("%s", infoMsg)
			writeOutputText(outHandle, infoMsg)
			infoMsg = "Test cases not falling into a summary report error category:"
			logger("%s", infoMsg)
			writeOutputText(outHandle, infoMsg)
			for testCase, counter := range tblErrCases {
				if counter > 0 { // ignore if this test case was grepped at least once
					continue
				}
				fmt.Printf("\t%s\n", testCase)
				writeOutputText(outHandle, "\t"+testCase)
			}
			logSkip()
			writeOutputText(outHandle, " ")
		}

		// Show elapsed time for all test cases executed.
		tStop := time.Now()
		elapsed := tStop.Sub(tStart)
		etMsg := fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String())
		logger("%s", etMsg)
		writeOutputText(outHandle, "\n"+etMsg)

		// Close summary report handle.
		err = outHandle.Close()
		if err != nil {
			fatalErr(err, "report.Close(%s) failed:", outPath)
		}
		_ = createLatest(global.SumFilePath)
		logger("Wrote test case summary to: %s", outPath)

	} // if global.FlagExecute || global.FlagCompile

	// Print the last-2-results report?
	if global.FlagTwoMostRecent {
		DBPrtChanges()
	}

	// Print the last result of every test case?
	if global.FlagPrintMostRecent {
		DBPrintMostRecent()
	}

	// Print the checksums of all the class files?
	if global.FlagClassChecksum {
		classChecksum(csvFullPath, testsTreeTop)
	}

	// Delete the most recent test result for each test case?
	if global.FlagDeleteMostRecent {
		DBDeleteMostRecent()
	}

	// Print the failed-passed report?
	if global.FlagFailedPassed {
		DBPrtLastPass()
	}

	// Delete database records of obsolete test cases?
	if global.FlagDeleteObsolete {
		DBReportOrDeleteObsolete(true)
	}

	// Delete database records of obsolete test cases?
	if global.FlagReportOrphans {
		DBReportOrDeleteObsolete(false)
	}

	if flagRenameTestCase {
		renameTestCase(oldTestCaseName, newTestCaseName)
	}

	// Close database.
	DBClose()

	// Bye bye.
	logger("Ended with exit status %d", exitStatus)
	os.Exit(exitStatus)

}
