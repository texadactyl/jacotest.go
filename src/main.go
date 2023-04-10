package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"
	"strconv"
)

//
// Command line interface for runner
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
	os.Exit(1)
}

//
// Report results
func reportResults(label string, arrayX []string) {
    testTests := "test"
    nerr := len(arrayX)
    if nerr > 0 {
        if nerr > 1 {
            testTests = testTests + "s"
        }
        Logger(fmt.Sprintf("%s errors in %d %s", label, nerr, testTests))
        for _, name := range arrayX {
            Logger(fmt.Sprintf("     %s", name))
        }
    } else {
        Logger(fmt.Sprintf("No %s errors", label))
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
        Fatal("You are npt positioned in the tree top directory")
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
		// if it's a JVM option (so, it begins with a hyphen)
		// break the option into the option and any embedded arg values, if any
		switch Args[ii] {
		    case "-x":
                flagExecute = true
                flagClean = true
		    case "-c":
		        flagClean = true
		    case "-v":
		        flagVerbose = true
		    case "-h":
		        showHelp()
		    case "-j":
		        ii += 1
		        jvm = Args[ii]
		    case "-t":
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
	
	// Validate jvm
	switch jvm {
	    case "java":
	    case "jacobin":
	    default:
	        LogError(fmt.Sprintf("Unrecognizable JVM parameter: %s", jvm))
	        showHelp()
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
	    
	    // Clean up log files
        fileOpened, err := os.Open(global.DirLogs)
        if err != nil {
            FmtFatal("os.Open failed", global.DirLogs, err)
        }
        dirNames, err := fileOpened.Readdirnames(0) // get all entries
        if err != nil {
            FmtFatal("Readdirnames failed", global.DirLogs, err)
        }
        for index := range(dirNames) {
            name := dirNames[index]
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
	    var successNames [] string
	    var errCompileNames [] string
	    var errRunnerNames [] string
	    entries, err := os.ReadDir(global.DirTests)
        if err != nil {
            FmtFatal("Error in accessing directory", global.DirTests, err)
        }
    	Logger(fmt.Sprintf("Deadline: %d seconds", deadline_secs))
        for _, entry := range entries {
            if entry.IsDir() {
                fullPath := filepath.Join(global.DirTests, entry.Name())
                switch ExecuteOneTest(fullPath) {
	                case 0:
	                    successNames = append(successNames, entry.Name())
	                case 1:
	                    errCompileNames = append(errCompileNames, entry.Name())
	                case 2:
	                    errRunnerNames = append(errRunnerNames, entry.Name())
	            }
            }
        }

        // Report successes
        Logger(fmt.Sprintf("Success in %d tests", len(successNames)))
        for _, name := range successNames {
            Logger(fmt.Sprintf("     %s", name))
        }
        
        // Report compilation results
        reportResults("compilation", errCompileNames)
        
        // Report runner results
        reportResults("runner", errRunnerNames)

	}
	if flagExecute {
	    t_stop := time.Now()
	    elapsed := t_stop.Sub(t_start)
        Logger(fmt.Sprintf("Elapsed time = %s", elapsed.Round(time.Second).String()))
    }
}

