package main

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"strconv"
	"strings"
	"time"
)

const MyName = "Jacotest"

// Show help and then exit to the O/S
func showHelp() {
	_ = InitGlobals("dummy", "dummy", 0, false)
	suffix := filepath.Base(os.Args[0])
	fmt.Printf("\nUsage:  %s  [-h]  [-c]  [-x]  [-r {1,2}]  [-M]  [-v]  [-t NSECS]  [ -j { openjdk | jacobin } ]\n\nwhere\n", suffix)
	fmt.Printf("\t-h : This display.\n")
	fmt.Printf("\t-c : Compile the test cases.\n")
	fmt.Printf("\t-r 1 : Print the last two test case results if there was a change (pass/fail).\n")
	fmt.Printf("\t-r 2 : Print the test case results where current failures passed sometime previously.\n")
	fmt.Printf("\t-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60.\n")
	fmt.Printf("\t-j : This is the JVM to use in executing all test cases. Default: jacobin.\n")
	fmt.Printf("\t     Specifying -j implies parameters -x and -r 1.\n")
	fmt.Printf("\t-v : Verbose logging.\n")
	fmt.Printf("\t-x : Execute all test cases.\n")
	fmt.Printf("\t     Specifying -x implies parameter -r 1.\n")
	fmt.Printf("\t-z : Remove the most recent test case result.\n")
	fmt.Printf("\t-M : Generate a run report suitable for viewing on github (normally, not produced).\n\n")
	ShowExecInfo()
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
	os.Setenv("java.awt.headless", "true")
	var Args []string
	var wString string
	flagVerbose := false
	flagExecute := false
	flagTwoMostRecent := false
	flagDeleteMostRecent := false
	flagFailedPassed := false
	flagCompile := false
	flagMdReport := false
	jvmName := "jacobin" // default virtual machine name
	jvmExe := "jacobin"  // default virtual machine executable
	deadlineSecs := 60   // default deadline in seconds
	now := time.Now()    // Get the current time.
	nowStamp := now.Format("2006-01-02 15:04:05")
	timeZone, _ := now.Zone()
	exitStatus := 0 // optimistic

	// Positioned in the tree top directory?
	handle, err := os.Open("VERSION.txt")
	if err != nil {
		Fatal("You are not positioned in the jacotest tree top directory")
	}
	_ = handle.Close()

	// Initialise Args to the command-line arguments.
	Args = append(Args, os.Args[1:]...)

	// If no options specified, just show help.
	if len(Args) < 1 {
		showHelp()
	}

	// Parse command line arguments.
	for ii := 0; ii < len(Args); ii++ {
		switch Args[ii] {

		case "-h":
			showHelp()

		case "-x":
			flagExecute = true
			flagTwoMostRecent = true

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
			default:
				LogError(fmt.Sprintf("Unrecognizable -r argument: %s", Args[ii]))
				showHelp()
			}

		case "-c":
			flagCompile = true

		case "-M":
			flagMdReport = true
			flagExecute = true
			flagTwoMostRecent = true

		case "-v":
			flagVerbose = true

		case "-j": // JVM requested
			ii += 1
			jvmName = Args[ii]
			// Validate JVM
			switch jvmName {
			case "openjdk":
				jvmExe = "java" // openjdk JVM executable name
			case "jacobin":
				jvmExe = "jacobin" // jacobin JVM executable name
			default:
				LogError(fmt.Sprintf("Unrecognizable JVM name: %s", jvmName))
				showHelp()
			}

		case "-t": // Deadline in seconds requested
			ii += 1
			deadlineSecs, err = strconv.Atoi(Args[ii])
			if err != nil {
				LogError(fmt.Sprintf("Parameter -t requires an integer value, saw: %s", Args[ii]))
				showHelp()
			}

		case "-z": // Delete the most recent test result for each test case.
			flagDeleteMostRecent = true

		default:
			LogError(fmt.Sprintf("Unrecognizable argument: %s", Args[ii]))
			showHelp()
		}
	}

	// Open database
	DBOpen(flagVerbose)

	// Make sure that the jvmExe file can be found in the O/S PATH
	wString, err = exec.LookPath(jvmExe)
	if err != nil {
		FmtFatal("exec.LookPath failed to find:", jvmExe, err)
	}
	if flagVerbose {
		Logger(fmt.Sprintf("Found JVM %s", wString))
	}

	// Initialise globals and get a handle to it
	global := InitGlobals(jvmName, jvmExe, deadlineSecs, flagVerbose)
	Logger(fmt.Sprintf("%s version %s", MyName, global.Version))

	// Open logs directory
	fileOpened, err := os.Open(global.DirLogs)
	if err != nil {
		FmtFatal("os.Open failed", global.DirLogs, err)
	}

	// Get all the file entries in the logs directory
	names, err := fileOpened.Readdirnames(0) // get all entries
	if err != nil {
		FmtFatal("Readdirnames failed", global.DirLogs, err)
	}

	// For each logs entry, remove it
	if flagCompile || flagExecute {
		for index := range names {
			name := names[index]
			fullPath := filepath.Join(global.DirLogs, name)
			err := os.Remove(fullPath)
			if err != nil {
				Fatal(fmt.Sprintf("os.Remove(%s) returned an error\nerror=%s", fullPath, err.Error()))
			}
		}
	}

	// Process execute request
	if flagExecute || flagCompile {
		var successNames []string
		var errCompileNames []string
		var errExecutionNames []string
		tblErrCases := make(map[string]int)
		var timeoutExecutionNames []string
		var rptHandle *os.File
		counterErrCases := 0

		if flagMdReport {
			// Initialise detailed report file
			rptHandle, err = os.Create(global.ReportFilePath)
			if err != nil {
				FmtFatal("os.Create failed", global.ReportFilePath, err)
			}
			defer rptHandle.Close()
			fmt.Fprintf(rptHandle, "%s version %s\n", MyName, global.Version)
			fmt.Fprintf(rptHandle, "Run report using JVM %s<br>Case deadline = %d seconds<br>Date/Time %s %s<br><br>\n",
				jvmName, deadlineSecs, nowStamp, timeZone)
			fmt.Fprintf(rptHandle, "| Test Case | Result | Console Output |\n")
			fmt.Fprintf(rptHandle, "| :--- | :---: | :--- |\n")
		}

		// Initialise summary report file
		outPath := global.SumFilePath
		outHandle, err := os.OpenFile(outPath, FlagsOpen, ModeOutputFile)
		if err != nil {
			Fatal(fmt.Sprintf("os.OpenFile(%s) failed, err=%s", outPath, err))
		}
		msg := fmt.Sprintf("%s version %s", MyName, global.Version)
		WriteOutputText(outHandle, msg)
		msg = fmt.Sprintf("O/S %s, arch %s", runtime.GOOS, runtime.GOARCH)
		WriteOutputText(outHandle, msg)
		WriteOutputText(outHandle, msg)
		outLines, err := exec.Command(jvmExe, "--version").Output()
		if err != nil {
			FmtFatal("Error in getting the JVM version", global.DirTests, err)
		}
		WriteOutputText(outHandle, "Using this JVM:")
		WriteOutputText(outHandle, string(outLines))

		// Get all the subdirectories (test cases) under tests
		entries, err := os.ReadDir(global.DirTests)
		if err != nil {
			FmtFatal("Error in accessing directory", global.DirTests, err)
		}

		// Phase 1
		// For each test case subdirectory of tests, run it.
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
				resultCode, outlog := ExecuteOneTest(fullPath, flagCompile, flagExecute, global)
				outlog = strings.ReplaceAll(outlog, "\n", "\n|||") // some outlog strings have multiple embedded \n characters
				switch resultCode {
				case RC_NORMAL:
					successNames = append(successNames, testCaseName)
					if flagMdReport {
						fmt.Fprintf(rptHandle, "| %s | PASSED | n/a |\n", testCaseName)
					}
					if flagExecute {
						DBStorePassed(testCaseName)
					}
				case RC_COMP_ERROR:
					exitStatus = 1
					errCompileNames = append(errCompileNames, testCaseName)
					if flagMdReport {
						fmt.Fprintf(rptHandle, "| %s | COMP-ERROR | compilation error(s)\n | | | See logs/FAILED-*-javac.log files |\n", testCaseName)
					}
					if flagExecute {
						DBStoreFailed(testCaseName, "Compile error")
					}
				case RC_EXEC_ERROR:
					exitStatus = 1
					errExecutionNames = append(errExecutionNames, testCaseName)
					tblErrCases[testCaseName] = 0
					if flagMdReport {
						fmt.Fprintf(rptHandle, "| %s | FAILED | %s |\n", testCaseName, outlog)
					}
					// DBStoreFailed calls will be made in the SQL database source file.
				case RC_EXEC_TIMEOUT:
					exitStatus = 1
					timeoutExecutionNames = append(timeoutExecutionNames, testCaseName)
					if flagMdReport {
						fmt.Fprintf(rptHandle, "| %s | TIMEOUT | %s |\n", testCaseName, outlog)
					}
					if flagExecute {
						DBStoreFailed(testCaseName, "Timeout")
					}
				}
			}
		}

		// Show successes
		msg = fmt.Sprintf("Success in %d test cases", len(successNames))
		Logger(msg)
		WriteOutputText(outHandle, msg)
		for _, name := range successNames {
			msg = fmt.Sprintf("	 %s", name)
			WriteOutputText(outHandle, msg)
		}

		// Show compilation errors
		if flagCompile {
			showResults("Compilation", errCompileNames, outHandle, true)
		}

		// Exit now if compilation only.
		if !flagExecute {
			msg := fmt.Sprintf("Ended with exit status %d", exitStatus)
			Logger(msg)
			os.Exit(exitStatus)
		}

		// Show timeout errors
		showResults("Execution timeout", timeoutExecutionNames, outHandle, true)

		// Show execution failures
		showResults("Execution failure", errExecutionNames, outHandle, false)

		// Phases 2 and 3
		counterErrCases += Phases2And3(tblErrCases, outHandle)

		// Phases 1/2/3 are done.
		// Close summary reprt handle.
		err = outHandle.Close()
		if err != nil {
			FmtFatal("outHandle.Close failed:", outPath, err)
		}
		Logger(fmt.Sprintf("Wrote test case summary to: %s", outPath))

		// Discrepancies in error total?
		if len(errExecutionNames) != counterErrCases {
			LogWarning(fmt.Sprintf("Number of error cases = %d but total from fail-categories = %d", len(errExecutionNames), counterErrCases))
			Logger("Test cases not falling into a summary report error category:")
			for testCase, counter := range tblErrCases {
				if counter > 0 { // ignore if this test case was grepped at least once
					continue
				}
				fmt.Printf("\t%s\n", testCase)
			}
		}

		// Show elapsed time.
		tStop := time.Now()
		elapsed := tStop.Sub(tStart)
		Logger(fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String()))

	} // if flagExecute || flagCompile

	// Print the last 2 report?
	if flagTwoMostRecent {
		DBPrtChanges()
	}

	// Delete the most recent test result for each test case?
	if flagDeleteMostRecent {
		DBDeleteMostRecent()
	}

	// Print the failed-passed report?
	if flagFailedPassed {
		DBPrtLastPass()
	}

	// Close database
	DBClose()

	// Bye bye
	msg := fmt.Sprintf("Ended with exit status %d", exitStatus)
	Logger(msg)
	os.Exit(exitStatus)

}
