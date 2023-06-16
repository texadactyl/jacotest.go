package main

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"strconv"
	"strings"
	"time"
)

const MyName = "Jacotest"

// Show help and then exit to the O/S
func showHelp() {
	_ = InitGlobals("dummy", "dummy", 60, false)
	suffix := filepath.Base(os.Args[0])
	fmt.Printf("\nUsage:  %s  [-h]  [-c]  [-x]  [-v]  [-t NSECS]  [ -j { openjdk | jacobin } ]\n\nwhere\n", suffix)
	fmt.Printf("\t-h : This display\n")
	fmt.Printf("\t-c : Clean all of the .class files and .log files\n\n")
	fmt.Printf("\t-x : Compile and execute all of the tests\n")
	fmt.Printf("\t-v : Verbose logging\n")
	fmt.Printf("\t-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60\n")
	fmt.Printf("\t-j : This is the JVM to use in executing all test cases.  Default: openjdk\n")
	ShowExecInfo()
	os.Exit(0)
}

// Show results
func showResults(category string, arrayNames []string) {
	suffix := "test case"
	errorCount := len(arrayNames)
	if errorCount > 0 {
		if errorCount > 1 {
			suffix = suffix + "s"
		}
		Logger(fmt.Sprintf("%s errors in %d %s", category, errorCount, suffix))
		for _, name := range arrayNames {
			Logger(fmt.Sprintf("	 %s", name))
		}
	} else {
		Logger(fmt.Sprintf("No %s errors", category))
	}
}

// Command line interface
func main() {
	tStart := time.Now()
	var Args []string
	var wString string
	flagClean := false
	flagExecute := false
	jvmName := "jacobin" // default virtual machine name
	jvmExe := "jacobin"  // default virtual machine executable
	var deadlineSecs int = 60
	now := time.Now()
	nowStamp := now.Format("2006-01-02 15:04:05")
	timeZone, _ := now.Zone()

	// Positioned in the tree top directory?
	handle, err := os.Open("VERSION.txt")
	if err != nil {
		Fatal("You are not positioned in the jacotest tree top directory")
	}
	handle.Close()

	// Initialise Args to the command-line arguments
	for _, singleVar := range os.Args[1:] {
		Args = append(Args, singleVar)
	}

	// Make sure that at least one request was made
	if len(Args) < 1 {
		showHelp()
	}

	// Parse command line arguments
	for ii := 0; ii < len(Args); ii++ {
		switch Args[ii] {

		case "-h":
			showHelp()

		case "-x":
			flagExecute = true
			flagClean = true // Force a pre-clean when executing tests

		case "-c":
			flagClean = true

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

		default:
			LogError(fmt.Sprintf("Unrecognizable argument: %s", Args[ii]))
			showHelp()
		}
	}

	// Make sure that at least one of -c or -x was specified
	if !flagClean && !flagExecute {
		LogError("Must specify -h, -c, or -x")
		showHelp()
	}

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

	// Process clean request
	if flagClean {
		// Clean up .class files
		err := filepath.WalkDir(global.DirTests, CleanOneTest)
		if err != nil {
			Fatal(fmt.Sprintf("Cleaner returned an error\nerror=%s", err.Error()))
		}

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
		for index := range names {
			name := names[index]
			fullPath := filepath.Join(global.DirLogs, name)
			err := os.Remove(fullPath)
			if err != nil {
				Fatal(fmt.Sprintf("os.Remove(%s) returned an error\nerror=%s", fullPath, err.Error()))
			}
			if flagVerbose {
				Logger(fmt.Sprintf("Removed: %s", fullPath))
			}
		}
	}

	// Process execute request
	if flagExecute {
		var successNames []string
		var errCompileNames []string
		var errRunnerNames []string
		var timeoutRunnerNames []string

		// Initialise report file
		rptHandle, err := os.Create(global.ReportFilePath)
		if err != nil {
			FmtFatal("os.Create failed", global.ReportFilePath, err)
		}
		defer rptHandle.Close()
		fmt.Fprintf(rptHandle, "%s version %s\n", MyName, global.Version)
		fmt.Fprintf(rptHandle, "Run report using JVM %s<br>Case deadline = %d seconds<br>Date/Time %s %s<br><br>\n",
			jvmName, deadlineSecs, nowStamp, timeZone)
		fmt.Fprintf(rptHandle, "| Test Case | Result | Console Output |\n")
		fmt.Fprintf(rptHandle, "| :--- | :---: | :--- |\n")

		// Get all of the subdirectories (test cases) under tests
		entries, err := os.ReadDir(global.DirTests)
		if err != nil {
			FmtFatal("Error in accessing directory", global.DirTests, err)
		}

		// For each test case, execute it
		Logger(fmt.Sprintf("Test case deadline: %d seconds", deadlineSecs))
		for _, entry := range entries {
			if entry.IsDir() {
				testCaseName := entry.Name()
				fullPath := filepath.Join(global.DirTests, testCaseName)
				resultCode, outlog := ExecuteOneTest(fullPath)
				outlog = strings.ReplaceAll(outlog, "\n", "\n|||") // some outlog strings have multiple embedded \n characters
				switch resultCode {
				case RC_NORMAL:
					successNames = append(successNames, testCaseName)
					fmt.Fprintf(rptHandle, "| %s | PASSED | n/a |\n", testCaseName)
				case RC_COMP_ERROR:
					errCompileNames = append(errCompileNames, testCaseName)
					fmt.Fprintf(rptHandle, "| %s | COMP-ERROR | compilation error(s)\n | | | See logs/FAILED-*-javac.log files |\n", testCaseName)
				case RC_EXEC_ERROR:
					errRunnerNames = append(errRunnerNames, testCaseName)
					fmt.Fprintf(rptHandle, "| %s | FAILED | %s |\n", testCaseName, outlog)
				case RC_EXEC_TIMEOUT:
					timeoutRunnerNames = append(timeoutRunnerNames, testCaseName)
					fmt.Fprintf(rptHandle, "| %s | TIMEOUT | %s |\n", testCaseName, outlog)
				}
			}
		}

		// Show successes
		Logger(fmt.Sprintf("Success in %d test cases", len(successNames)))
		for _, name := range successNames {
			Logger(fmt.Sprintf("	 %s", name))
		}

		// Show compilation errors
		showResults("compilation", errCompileNames)

		// Show runner errors
		showResults("runner failure", errRunnerNames)

		// Show timeout errors
		showResults("runner timeout", timeoutRunnerNames)

		// Grapes
		outPath := global.FSumFilePath
		outHandle := OutGrapeOpen(outPath, false)
		OutGrapeText(outHandle, "Failed Test Case Summary - "+nowStamp+" "+timeZone)

		OutGrapeText(outHandle, "\n===========================")
		OutGrapeText(outHandle, "panic: interface conversion")
		OutGrapeText(outHandle, "===========================")
		ExecGrape("logs", ".log", "panic: interface conversion", outHandle)

		OutGrapeText(outHandle, "\n=================================")
		OutGrapeText(outHandle, "runtime error: index out of range")
		OutGrapeText(outHandle, "=================================")
		ExecGrape("logs", ".log", "runtime error: index out of range", outHandle)

		OutGrapeText(outHandle, "\n================")
		OutGrapeText(outHandle, "AASTORE")
		OutGrapeText(outHandle, "================")
		ExecGrape("logs", ".log", "AASTORE", outHandle)

		OutGrapeText(outHandle, "\n================")
		OutGrapeText(outHandle, "invalid bytecode")
		OutGrapeText(outHandle, "================")
		ExecGrape("logs", ".log", "nvalid bytecode", outHandle)

		OutGrapeText(outHandle, "\n================")
		OutGrapeText(outHandle, "WaitClassStatus")
		OutGrapeText(outHandle, "================")
		ExecGrape("logs", ".log", "WaitClassStatus", outHandle)

		OutGrapeText(outHandle, "\n============================")
		OutGrapeText(outHandle, "could not find or load class")
		OutGrapeText(outHandle, "============================")
		ExecGrape("logs", ".log", "could not find or load class", outHandle)

		OutGrapeText(outHandle, "\n=====================================")
		OutGrapeText(outHandle, "runtime error: invalid memory address")
		OutGrapeText(outHandle, "=====================================")
		ExecGrape("logs", ".log", "runtime error: invalid memory address", outHandle)

		OutGrapeText(outHandle, "\n=====================================")
		OutGrapeText(outHandle, "MethAreaFetch")
		OutGrapeText(outHandle, "=====================================")
		ExecGrape("logs", ".log", "MethAreaFetch", outHandle)

		OutGrapeText(outHandle, "\n================")
		OutGrapeText(outHandle, "runtime.sigpanic")
		OutGrapeText(outHandle, "================")
		ExecGrape("logs", ".log", "runtime.sigpanic", outHandle)

		err = outHandle.Close()
		if err != nil {
			FmtFatal("outHandle.Close failed:", outPath, err)
		}
		Logger(fmt.Sprintf("Wrote failed test case summary to: %s", outPath))

		// Done. Show elapsed time and exit normally to the O/S.
		tStop := time.Now()
		elapsed := tStop.Sub(tStart)
		Logger(fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String()))
	}

	Logger("End")
}
