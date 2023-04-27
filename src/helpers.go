package main

import (
	"fmt"
	"path"
    "path/filepath"
    "io/fs"
    "context"
	"os"
	"os/exec"
	"strings"
	"time"
)

var flagVerbose = false

//
// Time-stamp log function
func Logger(msg string) {
	now := time.Now()
	fmt.Printf("%s %s\n", now.Format("15:04:05"), msg)
}

//
// Log an error
func LogError(msg string) {
	text := fmt.Sprintf("*** ERROR :: %s", msg)
	Logger(text)
}

//
// Log a timeout
func LogTimeout(msg string) {
	text := fmt.Sprintf("*** TIMEOUT :: %s", msg)
	Logger(text)
}

//
// Log an error and croak
func Fatal(msg string) {
	text := fmt.Sprintf("*** FATAL :: %s", msg)
	Logger(text)
	os.Exit(1)
}

// Log an error in a special format and croak
func FmtFatal(msg string, name string, err error) {
	text := fmt.Sprintf("*** FATAL, %s\n\t\t%s\n\t\t%s", msg, name, err.Error())
	Logger(text)
	os.Exit(1)
}

//
// If the specified directory does not yet exist, create it
func MakeDir(dirPath string) {
    info, err := os.Stat(dirPath)
    if err == nil { // found it
        if ! info.IsDir() { // expected a directory, not a simple file !!
            Fatal(fmt.Sprintf("MakeDir: Observed a simple file: %s (expected a directory)", dirPath))
        }
    } else { // not found or an error occured
        if os.IsNotExist(err) {
            // Create directory
            err = os.Mkdir(dirPath, 0755)
            if err != nil {
                FmtFatal("MakeDir: os.MkDir failed", dirPath, err)
            }
        } else { // some type of error
            FmtFatal("MakeDir: os.Stat failed", dirPath, err)
        }
    }
}

//
// Store a log
func storeText(targetDir string, argFile string, text string) {
    // Create the log file
    fullPath := filepath.Join(targetDir, argFile)
    handle, err := os.Create(fullPath) 
    if err != nil {
        Fatal(fmt.Sprintf("storeText: os.Create(%s) failed, err=%s", fullPath, err))
    }
    defer handle.Close()

    // Store the given text
    _, err = fmt.Fprintln(handle, text)
    if err != nil {
        FmtFatal("storeText: fmt.Fprintln failed", fullPath, err)
    }
}

//
// Run a subprocess and return result + output log as a string
// 0 : success
// 1 : failure
// 2 : timeout
func runner(cmdName string, cmdExec string, dirName string, argFile string) (int, string) {
	global := GetGlobalRef()
	if global.FlagVerbose {
		here, _ := os.Getwd()
	    Logger(fmt.Sprintf("runner: on entry cmdName=%s, cmdExec=%s, dirName=%s, here=%s, argFile=%s", cmdName, cmdExec, dirName, here, argFile))
	}
	
	// Set up a command context with a timeout
    ctx, cancel := context.WithTimeout(context.Background(), global.Deadline)
    defer cancel()
    
    // Execute command with the given parameters
    cmd := exec.CommandContext(ctx, cmdExec, argFile)
    
    // Form a message prefix
    fn := filepath.Base(argFile)
    fn_without_ext := strings.TrimSuffix(fn, path.Ext(fn))
    prefix := dirName + "-" + fn_without_ext + "-" + cmdName
    
    // Get the combined stdout and stderr text
    outbytes, err := cmd.CombinedOutput()
    outlog := string(outbytes)
    
    // Error occured?
    if err != nil { // YES
        // Timeout?
        if (ctx.Err() == context.DeadlineExceeded) { // YES
            LogTimeout(fmt.Sprintf("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outlog))
            storeText(global.DirLogs, "TIMEOUT-" + prefix  + ".log", outlog)
            return RC_EXEC_TIMEOUT, outlog
        }
        // Not a time out error but something else bad happened
        LogError(fmt.Sprintf("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outlog))
        storeText(global.DirLogs, "FAILED-" + prefix  + ".log", outlog)
        if cmdExec == "javac" {
            return RC_COMP_ERROR, outlog
        }
        return RC_EXEC_ERROR, outlog
    }
    
    // No errors occured.
    // If not a compile run, store outlog.
    if cmdExec != "javac" {
        storeText(global.DirLogs, "PASSED-" + prefix + ".log", outlog)
    }
    
    // Return outlog and a normaal status code to caller
    return RC_NORMAL, outlog
}
//
// Clean the .class file of one test
// The caller is walking a tree.
func CleanOneTest(fullPath string, dirEntry fs.DirEntry, err error) error {
    // If not a directory, skip it
    if dirEntry.IsDir() {
        return nil
    }
    
    // Anything but .class or .jar will be skipped
    switch(filepath.Ext(fullPath)) {
        case ".class":
        default:
            return nil // skip this directory entry
    }
    
    // Its a candidate for removal
    err = os.Remove(fullPath)
    if err != nil {
        FmtFatal("CleanOneTest: os.Remove failed", fullPath, err)
    }
    if flagVerbose {
        Logger(fmt.Sprintf("CleanOneTest: Removed: %s", fullPath))
    }
    return nil
}

//
// Compile all .java files in the directory tree rooted at fullPathDir
func compileOneTree(fullPathDir string) int {
    var stcode int
    
    // Get all of the directory entries from fullPathDir
    entries, err := os.ReadDir(fullPathDir)
    if err != nil {
        FmtFatal("compileOneTree: os.ReadDir failed", fullPathDir, err)
    }
    // Process each directory entry
    errorCount := 0
    for _, dirEntry := range entries {
        fileName := dirEntry.Name()
        
        // Get full path of file name
        fullPathFile := filepath.Join(fullPathDir, fileName)

        // If a directory, recur
	    fileInfo, err := os.Stat(fullPathFile)
	    if err != nil {
		    FmtFatal("compileOneTree: os.Stat failed", fullPathFile, err)
	    }
        if fileInfo.IsDir() { // === RECURSION ===
        
			// Save the path of the current working dir
			here, err := os.Getwd()
			if err != nil {
				FmtFatal("compileOneTree os.Getwd failed", "", err)
			}
			
			// Position to fullPathDir as the new working dir  (!!!!!!!!!!!!!!!!!!!!)
			err = os.Chdir(fullPathFile)
			if err != nil {
				FmtFatal("compileOneTree os.Chdir failed.  Was targeting:", fullPathDir, err)
			}

        	errorCount += compileOneTree(fullPathFile)

			// Go back to the original working dir  (!!!!!!!!!!!!!!!!!!!!)
			err2 := os.Chdir(here)
			if err2 != nil {
				FmtFatal("compileOneTree os.Chdir failed.  Was trying to return here:", here, err2)
			}
    
            continue
            
        }
    
        // If not a .java file, skip it
        if filepath.Ext(fileName) != ".java" {
            continue
        }
    
        // We have a simple file with extension .java
        Logger(fmt.Sprintf("Compiling %s / %s", filepath.Base(fullPathDir), fileName))
        
        // Run compilation
        stcode, _ = runner("javac", "javac", filepath.Base(fullPathDir), fileName)
        errorCount += stcode
    }
    
    // Return compilation error count to caller
    return errorCount
}

//
// 1. Compile all source files of one test
// 2. Run the test
//
// Return:
// 0 : success
// 1 : compilation errors
// 2 : run errors
func ExecuteOneTest(fullPathDir string) (int, string) {
    var stcode int
    global := GetGlobalRef()

    // Save the path of the current working dir
    here, err := os.Getwd()
    if err != nil {
        FmtFatal("ExecuteOneTest os.Getwd failed", "", err)
    }
    
    // Position to fullPathDir as the new working dir  (!!!!!!!!!!!!!!!!!!!!)
    err = os.Chdir(fullPathDir)
    if err != nil {
        FmtFatal("ExecuteOneTest os.Chdir failed.  Was targeting:", fullPathDir, err)
    }

    // Compile every .java file in the tree
    errorCount := compileOneTree(fullPathDir)
    
    // If there was at least one compilation error, go no further
    if errorCount > 0 {
        // Go back to the original working dir  (!!!!!!!!!!!!!!!!!!!!)
        err2 := os.Chdir(here)
        if err2 != nil {
            FmtFatal("ExecuteOneTest os.Chdir failed.  Was trying to return here:", here, err2)
        }
        return RC_COMP_ERROR, ""
    }

    // At this point, we are sitting in the test case directory
    // and the classpath is the test case directory.
    // Execute test "main.class".
    testName := filepath.Base(fullPathDir)
    Logger(fmt.Sprintf("Executing %s using jvm=%s", testName, global.JvmName))
    var outlog string
    if global.JvmName == "jacobin" {
        stcode, outlog = runner(global.JvmName, global.JvmExe, testName, "main.class")
    } else {
        stcode, outlog = runner(global.JvmName, global.JvmExe, testName, "main")
    }
    
    // Go back to the original working dir  (!!!!!!!!!!!!!!!!!!!!)
    err2 := os.Chdir(here)
    if err2 != nil {
        FmtFatal("ExecuteOneTest os.Chdir failed.  Was trying to return here:", here, err2)
    }
    
    // Return runner execution result
    return stcode, outlog // test case success
}

