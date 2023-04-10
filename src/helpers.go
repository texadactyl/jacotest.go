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
	text := fmt.Sprintf("*** ERROR, %s", msg)
	Logger(text)
}

//
// Log an error and croak
func Fatal(msg string) {
	text := fmt.Sprintf("*** FATAL, %s", msg)
	Logger(text)
	os.Exit(1)
}

// Log an error in a special format and croak
func FmtFatal(msg string, name string, err error) {
	text := fmt.Sprintf("*** FATAL, %s\n%s\n%s", msg, name, err.Error())
	Logger(text)
	os.Exit(1)
}

//
// If the specified directory does not yet exist, create it
func MakeDir(dirPath string) {
    info, err := os.Stat(dirPath)
    if err == nil { // found it
        if ! info.IsDir() { // expected a directory, not a simple file !!
            Fatal(fmt.Sprintf("Observed a simple file: %s (expected a directory)", dirPath))
        }
    } else { // not found or an error occured
        if os.IsNotExist(err) {
            // Create directory
            err = os.Mkdir(dirPath, 0755)
            if err != nil {
                FmtFatal("os.MkDir failed", dirPath, err)
            }
        } else { // some type of error
            FmtFatal("os.Stat failed", dirPath, err)
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
// Run a subprocess and return result
// nil : success
// error : failure
func runner(cmdexec string, testName string, argFile string) error {
	global := GetGlobalRef()
	
	// Set up a command context with a timeout
    ctx, cancel := context.WithTimeout(context.Background(), global.Deadline)
    defer cancel()
    
    // Execute command with the given parameters
    cmd := exec.CommandContext(ctx, cmdexec, argFile)
    
    // Form a message prefix
    fn := filepath.Base(argFile)
    fn_without_ext := strings.TrimSuffix(fn, path.Ext(fn))
    prefix := testName + "-" + fn_without_ext + "-" + cmdexec
    
    // Get the combined stdout and stderr text
    outbytes, err := cmd.CombinedOutput()
    outlog := string(outbytes)
    
    // Error occured?
    if err != nil { // YES
        LogError(fmt.Sprintf("cmd.Run(%s %s) returned: %s", cmdexec, argFile, outlog))
        // Timeout?
        if (ctx.Err() == context.DeadlineExceeded) { // YES
            storeText(global.DirLogs, "TIMEOUT-" + prefix  + ".log", outlog)
        } else { // Not a time out error but something else bad happened
            storeText(global.DirLogs, "FAILED-" + prefix  + ".log", outlog)
        }
        return err
    }
    
    // No errors occured. Just store outlog.
    if cmdexec != "javac" {
        storeText(global.DirLogs, "PASSED-" + prefix + ".log", outlog)
    }
    return nil
}
//
// Clean the .class file of one test
func CleanOneTest(fullPath string, dirEntry fs.DirEntry, err error) error {
    // If not a directory, skip it
    if dirEntry.IsDir() {
        return nil
    }
    
    // Anything but .class or .jar will be skipped
    switch(filepath.Ext(fullPath)) {
        case ".class":
        case ".jar":
        default:
            return nil // skip this directory entry
    }
    
    // Its a candidate for removal
    err = os.Remove(fullPath)
    if err != nil {
        FmtFatal("CleanOneTest: os.Remove failed", fullPath, err)
    }
    if flagVerbose {
        Logger(fmt.Sprintf("Removed: %s", fullPath))
    }
    return nil
}

//
// Compile all .java files in a single directory
func compileOneDir(fullPath string, dirEntry fs.DirEntry, err error) error {
    // If not a directory, skip it
    if dirEntry.IsDir() {
        return nil
    }
    
    // If not a .java file, skip it
    if filepath.Ext(fullPath) != ".java" {
        return nil
    }
    
    // We have a simple file with extension .java
    fullPathDir := filepath.Dir(fullPath)
    testName := filepath.Base(fullPathDir)
    Logger(fmt.Sprintf("Compiling %s / %s", testName, filepath.Base(fullPath)))
    
    // Set the classpath to be the same as the test case directory
    os.Setenv("CLASSPATH", fullPathDir)
    return runner("javac", testName, fullPath)
}

//
// 1. Compile all source files of one test
// 2. Run the test
//
// Return:
// 0 : success
// 1 : compilation errors
// 2 : run errors
func ExecuteOneTest(fullPathDir string) int {
	global := GetGlobalRef()

    // Compile the whole test case directory tree and compile every .java file
    err := filepath.WalkDir(fullPathDir, compileOneDir)
    if err != nil {
        return 1
    }
    
    // Save the current working dir and position to fullPathDir as the new working dir
    here, err2 := os.Getwd()
    if err2 != nil {
        FmtFatal("ExecuteOneTest os.Getwd failed.  Was targeting:", fullPathDir, err2)
    }
    err2 = os.Chdir(fullPathDir)
    if err2 != nil {
        FmtFatal("ExecuteOneTest os.Chdir failed.  Was targeting:", fullPathDir, err2)
    }

    // At this point, we are sitting in the test case directory
    // and the classpath is the test case directory.
    // Execute test "main.class".
    testName := filepath.Base(fullPathDir)
    Logger(fmt.Sprintf("Executing %s using jvm=%s", testName, global.Jvm))
    if global.Jvm == "jacobin" {
        err = runner(global.Jvm, testName, "main.class")
    } else {
        err = runner(global.Jvm, testName, "main")
    }
    
    // Go back to the original working dir
    err2 = os.Chdir(here)
    if err2 != nil {
        FmtFatal("ExecuteOneTest os.Chdir failed.  Was trying to return here:", here, err2)
    }
    
    // Examine runner execution result
    if err != nil { // test case failure or timeout
        return 2
    }
    return 0 // test case success
}

