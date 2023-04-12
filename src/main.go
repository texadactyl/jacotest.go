package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"
	"strconv"
	"strings"
)

//
// Show help and then exit to the O/S
func showHelp() {
	_ = InitGlobals("dummy", 60, false)
    suffix := filepath.Base(os.Args[0])
    fmt.Printf("\nUsage:  %s  [-c]  [-x]  [-v]  [ -j { java | jacobin } ]\n\nwhere\n", suffix)
    fmt.Printf("\t-x : Compile and execute all of the tests\n")
    fmt.Printf("\t-v : Verbose logging\n")
    fmt.Printf("\t-j : This is the JVM to use in executing all test cases.  Default: java\n")
    fmt.Printf("\t-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60\n")
    fmt.Printf("\t-c : Clean all of the .class files and .log files\n\n")
    ShowExecInfo()
    os.Exit(0)
}

//
// Show results
func showResults(category string, arrayNames []string) {
    testTests := "test"
    nerr := len(arrayNames)
    if nerr > 0 {
        if nerr > 1 {
            testTests = testTests + "s"
        }
        Logger(fmt.Sprintf("%s errors in %d %s", category, nerr, testTests))
        for _, name := range arrayNames {
            Logger(fmt.Sprintf("     %s", name))
        }
    } else {
        Logger(fmt.Sprintf("No %s errors", category))
    }
}

//
// Command line interface
func main() {
    t_start := time.Now()
	var Args []string
    flagClean := false
    flagExecute := false
    jvm := "java" // default virtual machine
    var deadline_secs int = 60
    
    // Positioned in the tree top directory?
    handle, err := os.Open("README.md")
    if err != nil {
        Fatal("You are not positioned in the tree top directory")
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
		        jvm = Args[ii]
	            // Validate jvm
	            switch jvm {
	                case "java":
	                case "jacobin":
	                default:
	                    LogError(fmt.Sprintf("Unrecognizable JVM parameter: %s", jvm))
	                    showHelp()
	            }
	            
		    case "-t": // Deadline in seconds requested
		        ii += 1
		        wint, err := strconv.Atoi(Args[ii])
		        if err != nil {
		            LogError(fmt.Sprintf("Parameter -t requires an integer value, saw: %s", Args[ii]))
		            showHelp()
		        }
		        deadline_secs = wint
		        
		    default:
		        LogError(fmt.Sprintf("Unrecognizable argument: %s", Args[ii]))
		        showHelp()
		}
	}
	
	
	// Initialise globals and get a handle to it
	global := InitGlobals(jvm, deadline_secs, flagVerbose)
	ShowExecInfo()
	
    // If log directory does not yet exist, create it
    MakeDir(global.DirLogs)
    
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
            FmtFatal("main: os.Open failed", global.DirLogs, err)
        }

        // Get all the file entries in the logs directory
        names, err := fileOpened.Readdirnames(0) // get all entries
        if err != nil {
            FmtFatal("main: Readdirnames failed", global.DirLogs, err)
        }

        // For each logs entry, remove it
        for index := range(names) {
            name := names[index]
            fullPath := filepath.Join(global.DirLogs, name)
            err := os.Remove(fullPath)
	        if err != nil {
	            Fatal(fmt.Sprintf("main: os.Remove(%s) returned an error\nerror=%s", fullPath, err.Error()))
	        }
            if flagVerbose {
                Logger(fmt.Sprintf("main: Removed: %s", fullPath))
            }
        }
	}
	
	// Process execute request
	if flagExecute {
	    var successNames [] string
	    var errCompileNames [] string
	    var errRunnerNames [] string
	    var timeoutRunnerNames [] string
	    
	    // Initialise report file
	    rptHandle, err := os.Create(global.ReportFilePath)
	    if err != nil {
	        FmtFatal("main: os.Create failed", global.ReportFilePath, err)
	    }
	    defer rptHandle.Close()
        zone, _ := time.Now().Zone()
	    fmt.Fprintf(rptHandle, "Run report using JVM %s\n<br>Date/Time %s %s\n<br>\n<br>\n", jvm, time.Now().Format("2006-01-02 15:04:05"), zone)
	    fmt.Fprintf(rptHandle, "| Test Case | Result | Error Information |\n")
	    fmt.Fprintf(rptHandle, "| :--- | :---: | :--- |\n")
	    
	    // Get all of the subdirectories (test cases) under tests
	    entries, err := os.ReadDir(global.DirTests)
        if err != nil {
            FmtFatal("main: Error in accessing directory", global.DirTests, err)
        }

        // For each test case, execute it
    	Logger(fmt.Sprintf("Deadline: %d seconds", deadline_secs))
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
	                    fmt.Fprintf(rptHandle, "| %s | COMP-ERROR | compilation error(s) |\n", testCaseName)
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
        Logger(fmt.Sprintf("Success in %d tests", len(successNames)))
        for _, name := range successNames {
            Logger(fmt.Sprintf("     %s", name))
        }
        
        // Show compilation errors
        showResults("compilation", errCompileNames)
        
        // Show runner errors
        showResults("runner failure", errRunnerNames)

        // Show timeout errors
        showResults("runner timeout", timeoutRunnerNames)

        // Done. Show elapsed time and exit normally to the O/S.
	    t_stop := time.Now()
	    elapsed := t_stop.Sub(t_start)
        Logger(fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String()))
    }
}

