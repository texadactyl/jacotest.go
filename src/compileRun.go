package main

import (
	"context"
	"fmt"
	"io/fs"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
)

// runner - Run a subprocess and return result + output log as a string.
// (internal function)
// 0 : success
// 1 : failure
// 2 : timeout
func runner(cmdName string, cmdExec string, dirName string, argOpts string, argFile string) (int, string) {
	globals := GetGlobalRef()
	if globals.FlagVerbose {
		here, _ := os.Getwd()
		Logger(fmt.Sprintf("runner: on entry cmdName=%s, cmdExec=%s, dirName=%s, here=%s, argFile=%s", cmdName, cmdExec, dirName, here, argFile))
	}

	// Set up a command context with a timeout
	ctx, cancel := context.WithTimeout(context.Background(), globals.Deadline)
	defer cancel()

	// Construct a command with the given parameters
	sliceOpts := strings.Split(argOpts, " ")
	sliceOpts = append(sliceOpts, argFile)
	cmd := exec.CommandContext(ctx, cmdExec, sliceOpts[:]...)

	// Form a log file name infix
	infix := dirName + "." + cmdName

	// Run the command.
	// Get the combined stdout and stderr text
	outBytes, err := cmd.CombinedOutput()
	outString := string(outBytes)

	// Error occurred?
	if err != nil { // YES

		// Timeout?
		if ctx.Err() == context.DeadlineExceeded { // YES
			LogTimeout(fmt.Sprintf("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outString))
			StoreText(globals.DirLogs, "TIMEOUT."+infix+".log", outString)
			return RC_EXEC_TIMEOUT, outString
		}

		// Not a timeout error but something else bad happened
		outString = CleanerText(outString, true)
		LogError(fmt.Sprintf("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outString))
		StoreText(globals.DirLogs, "FAILED."+infix+".log", outString)
		if cmdExec == "javac" {
			return RC_COMP_ERROR, outString
		}
		return RC_EXEC_ERROR, outString

	}

	// No errors occurred.
	// If not a compile run, store outString.
	if cmdExec != "javac" {
		StoreText(globals.DirLogs, "PASSED."+infix+".log", outString)
	}

	// Return outString and a normal status code to caller
	return RC_NORMAL, outString
}

// compileOneTree - Compile all .java files in the directory tree rooted at pathTreeTop.
// Then, javap all .class files in the directory tree rooted at pathTreeTop.
func compileOneTree(pathTreeTop string) int {
	var statusCode int

	// Get all of the directory entries from fullPathDir
	entries, err := os.ReadDir(pathTreeTop)
	if err != nil {
		FatalErr(fmt.Sprintf("compileOneTree: os.ReadDir(%s) failed", pathTreeTop), err)
	}

	// Compile every .java file at the top level.
	// If there are subdirectories (package), they will automatically be compiled as well.
	errorCount := 0
	for _, dirEntry := range entries {
		fileName := dirEntry.Name()

		// Get full path of file name
		fullPathFile := filepath.Join(pathTreeTop, fileName)

		// If a directory, we will skip it
		fileInfo, err := os.Stat(fullPathFile)
		if err != nil {
			FatalErr(fmt.Sprintf("compileOneTree: os.Stat(%s) failed", fullPathFile), err)
		}
		if fileInfo.IsDir() {
			continue
		}

		// Not a directory.
		// If not a .java file, skip it
		if filepath.Ext(fileName) != ".java" {
			continue
		}

		// We have a simple file with extension .java in the tree top.
		Logger(fmt.Sprintf("Compiling %s / %s", filepath.Base(pathTreeTop), fileName))

		// Run compilation
		options := global.JavacOptPrefix + " -cp " + pathTreeTop + string(os.PathListSeparator) + global.DirHelpers
		statusCode, _ = runner("javac", "javac", filepath.Base(pathTreeTop), options, fileName)
		errorCount += statusCode
	}

	// If any compilation errors, return now.
	if errorCount > 0 {
		return errorCount
	}

	// Compiled every .java file in the tree without errors.
	// For each .class file produced, generate javap output.
	err2 := filepath.WalkDir(pathTreeTop, func(absFilePath string, dirent fs.DirEntry, err error) error {
		if dirent.IsDir() {
			return nil
		}
		if !strings.HasSuffix(dirent.Name(), ".class") {
			return nil
		}
		output, errInner := exec.Command("javap", "-v", absFilePath).Output()
		if errInner != nil {
			FatalErr("compileOneTree WalkDir: exec.Command(javap "+absFilePath+") failed.", errInner)
		}
		dirPath := filepath.Dir(absFilePath)
		outFile := "javap_" + dirent.Name() + ".log"
		StoreText(dirPath, outFile, string(output))
		return nil
	})

	if err2 != nil {
		FatalErr("filepath.WalkDir failed", err2)
	}

	// Return compilation error count to caller
	return errorCount
}

// ExecuteOneTest - Execute the requested test case.
// (internal function)
// 1. Compile all source files of one test case.
// 2. Run the test case.
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
		FatalErr("ExecuteOneTest os.Getwd failed", err)
	}

	// Position to fullPathDir as the new working dir
	err = os.Chdir(fullPathDir)
	if err != nil {
		FatalErr(fmt.Sprintf("ExecuteOneTest os.Chdir(%s) failed", fullPathDir), err)
	}

	if global.FlagCompile {
		// Compile every .java file in the tree
		errorCount := compileOneTree(fullPathDir)

		// If there was at least one compilation error, go no further
		if errorCount > 0 {
			// Go back to the original working directory.
			err2 := os.Chdir(here)
			if err2 != nil {
				FatalErr(fmt.Sprintf("ExecuteOneTest os.Chdir(%s) failed", here), err2)
			}
			return RC_COMP_ERROR, ""
		}
	}

	if !global.FlagExecute {
		return RC_NORMAL, ""
	}

	// At this point, we are sitting in the test case directory.
	// Execute test "main.class".
	testName := filepath.Base(fullPathDir)
	Logger(fmt.Sprintf("Executing %s using jvm=%s", testName, global.JvmName))
	var outString string
	options := global.JvmOptPrefix + " -cp " + fullPathDir + string(os.PathListSeparator) + global.DirHelpers
	if global.JvmName == "jacobin" {
		stcode, outString = runner(global.JvmName, global.JvmExe, testName, options, "main.class")
	} else {
		stcode, outString = runner(global.JvmName, global.JvmExe, testName, options, "main")
	}

	// Go back to the original working directory.
	err = os.Chdir(here)
	if err != nil {
		FatalErr(fmt.Sprintf("ExecuteOneTest os.Chdir(%s) failed.  Was trying to return here:", here), err)
	}

	// Return runner execution result
	return stcode, outString // test case success
}
