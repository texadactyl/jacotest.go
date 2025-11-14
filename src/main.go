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

// Show help and then exit to the O/S
func showHelp() {
	_ = InitGlobals("dummy", "dummy", 0, "")
	name := filepath.Base(os.Args[0])
	ShowExecInfo(name)
	fmt.Printf("\nUsage:  %s  [ options ]\n\nwhere\n", name)
	fmt.Printf("\t-h : This display.\n")
	fmt.Printf("\t-c : Compile all the test cases.\n")
	fmt.Printf("\t-j name : This is the JVM to use in executing all test cases. Default: jacobin.\n")
	fmt.Printf("\t     Other JVM names recognized:\n")
	fmt.Printf("\t     * openjdk : OpenJDK (aka Hotspot) JVM\n")
	fmt.Printf("\t     * galt : Run jacobin in G-alternate mode\n")
	fmt.Printf("\t     Note that specifying -j implies parameters -x and -r 1.\n")
	fmt.Printf("\t-M : Generate a run report suitable for viewing on github (normally, not produced).\n")
	fmt.Printf("\t-N : Report all database history records whose test case name is obsolete (orphans).\n")
	fmt.Printf("\t-n old new : Rename test case name from old to new.\n")
	fmt.Printf("\t-r 1 : For each test case, print the last two results if there was a changed result.\n")
	fmt.Printf("\t-r 2 : For each failed test case, print this result if it passed sometime previously.\n")
	fmt.Printf("\t-r 3 : For each test case, print the last result.\n")
	fmt.Printf("\t-s : Delete database records of non-existing test cases (they were deleted).\n")
	fmt.Printf("\t-t num : This is the timeout value of num seconds in executing each test case.  Default: 60.\n")
	fmt.Printf("\t-u : User-defined options to pass to the JVM when -x is specified.\n")
	fmt.Printf("\t-v : Verbose logging.\n")
	fmt.Printf("\t-x : Execute all test cases.\n")
	fmt.Printf("\t     Specifying -x implies parameter -r 1.\n")
	fmt.Printf("\t-z : Remove the most recent result for all test cases.\n")
	fmt.Print("\n")
	os.Exit(0)
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
		msg = fmt.Sprintf("%s errors in %d %s", category, arrayCount, suffix)
		Logger(msg)
		WriteOutputText(outHandle, "\n"+msg)
		if showList {
			for _, name := range arrayNames {
				msg = fmt.Sprintf("	 %s", name)
				Logger(msg)
				WriteOutputText(outHandle, msg)
			}
		}
	} else {
		msg = fmt.Sprintf("No %s errors", category)
		Logger(msg)
		WriteOutputText(outHandle, "\n"+msg)
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

	// Temporary flags before setting them in global.
	flagVerbose := false          // Verbose logging? true/false
	flagGalt := false             // JVM Jacobin is run in G-alternate mode
	flagCompile := false          // -c option
	flagDeleteObsolete := false   // -s option
	flagMdReport := false         // -M option
	flagTwoMostRecent := false    // -r 1 option
	flagFailedPassed := false     // -r 2 option
	flagPrintMostRecent := false  // -r 3 option
	flagExecute := false          // -x option
	flagDeleteMostRecent := false // -z option
	flagReportOrphans := false    // -N option
	flagRenameTestCase := false   // -n oldname newname
	oldTestCaseName := ""
	newTestCaseName := ""

	// Positioned in the tree top directory?
	_, err = os.Open("VERSION.txt")
	if err != nil {
		FatalText("You are not positioned in the jacotest tree top directory")
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
			case "openjdk":
				jvmExe = "java" // openjdk JVM executable name
			case "jacobin":
				jvmExe = "jacobin" // jacobin JVM executable name
			case "galt":
				jvmExe = "jacobin" // jacobin JVM executable name
				flagGalt = true
			default:
				LogError(fmt.Sprintf("Unrecognizable JVM name: %s", jvmName))
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
				LogError("Missing -n argument for old test case name")
				showHelp()
			}
			oldTestCaseName = Args[ii]
			ii++
			if ii >= len(Args) {
				LogError("Missing -n argument for new test case name")
				showHelp()
			}
			newTestCaseName = Args[ii]
			flagRenameTestCase = true

		case "-r":
			ii++
			if ii >= len(Args) {
				LogError("Missing -r argument")
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
			default:
				LogError(fmt.Sprintf("Unrecognizable -r argument: %s", Args[ii]))
				showHelp()
			}

		case "-s":
			flagDeleteObsolete = true

		case "-t": // Deadline in seconds requested
			ii += 1
			deadlineSecs, err = strconv.Atoi(Args[ii])
			if err != nil {
				LogError(fmt.Sprintf("Parameter -t requires an integer value, saw: %s", Args[ii]))
				showHelp()
			}

		case "-u":
			ii += 1
			userXopts = Args[ii]

		case "-v":
			flagVerbose = true

		case "-x":
			flagExecute = true
			flagTwoMostRecent = true

		case "-z": // Delete the most recent test result for each test case.
			flagDeleteMostRecent = true

		default:
			LogError(fmt.Sprintf("Unrecognizable argument: %s", Args[ii]))
			showHelp()
		}
	}

	// Initialise global flags and get a handle.
	global := InitGlobals(jvmName, jvmExe, deadlineSecs, userXopts)
	global.FlagVerbose = flagVerbose
	global.FlagGalt = flagGalt
	global.FlagCompile = flagCompile
	global.FlagDeleteObsolete = flagDeleteObsolete
	global.FlagMdReport = flagMdReport
	global.FlagTwoMostRecent = flagTwoMostRecent
	global.FlagFailedPassed = flagFailedPassed
	global.FlagPrintMostRecent = flagPrintMostRecent
	global.FlagExecute = flagExecute
	global.FlagDeleteMostRecent = flagDeleteMostRecent
	global.FlagReportOrphans = flagReportOrphans

	// Start up signal listener.
	wg.Add(1)
	go func() {
		defer wg.Done()
		sig := <-shutdownChan
		msg := fmt.Sprintf("Shutdown signal received: %v. Cleaning up", sig)
		FatalText(msg)
	}()

	// Show the program version.
	if global.FlagPrintMostRecent {
		fmt.Printf("%s version %s", MyName, global.Version)
	} else {
		Logger(fmt.Sprintf("%s version %s", MyName, global.Version))
	}

	// Open database.
	defer DBClose()
	DBOpen()

	// Make sure that the jvmExe file can be found in the O/S PATH.
	wString, err = exec.LookPath(jvmExe)
	if err != nil {
		FatalErr(fmt.Sprintf("exec.LookPath(%s) failed", jvmExe), err)
	}
	if global.FlagVerbose {
		Logger(fmt.Sprintf("Found JVM %s", wString))
	}

	// Open logs directory.
	fileOpened, err := os.Open(global.DirLogs)
	if err != nil {
		FatalErr(fmt.Sprintf("os.Open(%s) failed", global.DirLogs), err)
	}

	// Get all the file entries in the logs directory.
	names, err := fileOpened.Readdirnames(0) // get all entries
	if err != nil {
		FatalErr(fmt.Sprintf("Readdirnames(%s) failed", global.DirLogs), err)
	}

	// For each log file entry, remove it.
	if global.FlagCompile || global.FlagExecute {
		for index := range names {
			name := names[index]
			fullPath := filepath.Join(global.DirLogs, name)
			err := os.Remove(fullPath)
			if err != nil {
				FatalErr(fmt.Sprintf("os.Remove(%s) failed", fullPath), err)
			}
		}
	}

	// Process execute request.
	if global.FlagExecute || global.FlagCompile {
		var successNames []string
		var errCompileNames []string
		var errExecutionNames []string
		tblErrCases := make(map[string]int)
		var timeoutExecutionNames []string
		var rptHandle *os.File
		counterErrCases := 0

		if global.FlagMdReport {
			// Initialise detailed report file
			rptHandle, err = os.Create(global.ReportFilePath)
			if err != nil {
				FatalErr(fmt.Sprintf("os.Create(%s) failed", global.ReportFilePath), err)
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

		// Initialise summary report file.
		outPath := global.SumFilePath
		outHandle, err := os.OpenFile(outPath, FlagsOpen, ModeOutputFile)
		if err != nil {
			FatalText(fmt.Sprintf("os.OpenFile(%s) failed, err=%s", outPath, err))
		}
		msg := fmt.Sprintf("%s version %s", MyName, global.Version)
		WriteOutputText(outHandle, msg)
		msg = fmt.Sprintf("O/S %s, arch %s", runtime.GOOS, runtime.GOARCH)
		WriteOutputText(outHandle, msg)
		outLines, err := exec.Command(jvmExe, "--version").Output()
		if err != nil {
			FatalErr("Error in getting the JVM version", err)
		}
		WriteOutputText(outHandle, "Using this JVM:")
		WriteOutputText(outHandle, string(outLines))

		// Get all the subdirectories (test cases) under tests
		entries, err := os.ReadDir(global.DirTests)
		if err != nil {
			FatalErr(fmt.Sprintf("os.ReadDir(%s) failed", global.DirTests), err)
		}

		// Phase 1 - For each test case subdirectory of tests, run it.
		Logger(fmt.Sprintf("Test case deadline: %d seconds", deadlineSecs))
		for _, entry := range entries {

			if entry.IsDir() {
				testCaseName := entry.Name()
				fullPath := filepath.Join(global.DirTests, testCaseName)
				mainFile := fullPath + string(os.PathSeparator) + "main.java"
				_, err := os.Stat(mainFile)
				if err != nil {
					msg := fmt.Sprintf("File %s does not exist - skipping directory", mainFile)
					LogWarning(msg)
					continue
				}
				resultCode, outlog := ExecuteOneTest(fullPath)
				outlog = strings.ReplaceAll(outlog, "\n", "\n|||") // some outlog strings have multiple embedded \n characters
				switch resultCode {
				case RC_NORMAL:
					successNames = append(successNames, testCaseName)
					if global.FlagMdReport {
						_, _ = fmt.Fprintf(rptHandle, "| %s | PASSED | n/a |\n", testCaseName)
					}
					if global.FlagExecute {
						DBStorePassed(testCaseName)
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
						_, _ = fmt.Fprintf(rptHandle, "| %s | FAILED | %s |\n", testCaseName, outlog)
					}
					// DBStoreFailed calls will be made in the SQL database source file.
				case RC_EXEC_TIMEOUT:
					exitStatus = 1
					timeoutExecutionNames = append(timeoutExecutionNames, testCaseName)
					if global.FlagMdReport {
						_, _ = fmt.Fprintf(rptHandle, "| %s | TIMEOUT | %s |\n", testCaseName, outlog)
					}
					if global.FlagExecute {
						DBStoreFailed(testCaseName, "Timeout")
					}
				}
			}
		}

		// Show successes.
		msg = fmt.Sprintf("Success in %d test cases", len(successNames))
		Logger(msg)
		WriteOutputText(outHandle, msg)
		for _, name := range successNames {
			msg = fmt.Sprintf("	 %s", name)
			WriteOutputText(outHandle, msg)
		}

		// Show compilation errors.
		if global.FlagCompile {
			showResults("Compilation", errCompileNames, outHandle, true)
		}

		// Exit now if compilation only.
		if !global.FlagExecute {
			msg := fmt.Sprintf("Ended with exit status %d", exitStatus)
			Logger(msg)
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
			LogSkip()
			WriteOutputText(outHandle, " ")
			infoMsg := fmt.Sprintf("*** WARNING: Number of error cases = %d but total from fail-categories = %d",
				len(errExecutionNames), counterErrCases)
			Logger(infoMsg)
			WriteOutputText(outHandle, infoMsg)
			infoMsg = "Test cases not falling into a summary report error category:"
			Logger(infoMsg)
			WriteOutputText(outHandle, infoMsg)
			for testCase, counter := range tblErrCases {
				if counter > 0 { // ignore if this test case was grepped at least once
					continue
				}
				fmt.Printf("\t%s\n", testCase)
				WriteOutputText(outHandle, "\t"+testCase)
			}
			LogSkip()
			WriteOutputText(outHandle, " ")
		}

		// Show elapsed time.
		tStop := time.Now()
		elapsed := tStop.Sub(tStart)
		etMsg := fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String())
		Logger(etMsg)
		WriteOutputText(outHandle, "\n"+etMsg)

		// Close summary report handle.
		err = outHandle.Close()
		if err != nil {
			FatalErr(fmt.Sprintf("report.Close(%s) failed:", outPath), err)
		}
		Logger(fmt.Sprintf("Wrote test case summary to: %s", outPath))

	} // if global.FlagExecute || global.FlagCompile

	// Print the last-2-results report?
	if global.FlagTwoMostRecent {
		DBPrtChanges()
	}

	// Print the last result of every test case?
	if global.FlagPrintMostRecent {
		DBPrintMostRecent()
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
	msg := fmt.Sprintf("Ended with exit status %d", exitStatus)
	fmt.Println(msg)
	os.Exit(exitStatus)

}
