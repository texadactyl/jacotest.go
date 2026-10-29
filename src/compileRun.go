package main

import (
	"context"
	"errors"
	"io/fs"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
)

// runner - Run a subprocess and return a result plus output log as a string.
// (internal function)
// 0: success
// 1: failure
// 2: timeout
func runner(cmdName string, cmdExec string, dirName string, argOpts string, argFile string) (int, string) {
	globals := GetGlobalRef()
	if globals.FlagVerbose {
		here, _ := os.Getwd()
		logger("runner: on entry cmdName=%s, cmdExec=%s, dirName=%s, argOpts=%s, here=%s, argFile=%s",
			cmdName, cmdExec, dirName, argOpts, here, argFile)
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
		if errors.Is(ctx.Err(), context.DeadlineExceeded) { // YES
			logTimeout("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outString)
			storeText(globals.DirLogs, "TIMEOUT."+infix+".log", outString)
			return RC_EXEC_TIMEOUT, outString
		}

		// Not a timeout error, but something else bad happened
		outString = cleanerText(outString, true)
		logError("runner: cmd.Run(%s %s) returned: %s", cmdName, argFile, outString)
		storeText(globals.DirLogs, "FAILED."+infix+".log", outString)
		if cmdExec == "javac" {
			return RC_COMP_ERROR, outString
		}
		return RC_EXEC_ERROR, outString

	}

	// No errors occurred.
	// If not a compile-run, store outString.
	if cmdExec != "javac" {
		storeText(globals.DirLogs, "PASSED."+infix+".log", outString)
	}

	// Return outString and a normal status code to caller
	return RC_NORMAL, outString
}

// compileOneTree - Compile all .java files in the directory tree rooted at pathTreeTop.
// Then, javap all .class files in the directory tree rooted at pathTreeTop.
func compileOneTree(pathTreeTop string) int {
	var statusCode int

	// Get all the directory entries from fullPathDir
	entries, err := os.ReadDir(pathTreeTop)
	if err != nil {
		fatalErr(err, "compileOneTree: os.ReadDir(%s) failed", pathTreeTop)
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
			fatalErr(err, "compileOneTree: os.Stat(%s) failed", fullPathFile)
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
		logger("Compiling %s / %s", filepath.Base(pathTreeTop), fileName)

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
	err2 := filepath.WalkDir(pathTreeTop, func(absFilePath string, dirEntry fs.DirEntry, err error) error {
		if dirEntry.IsDir() {
			return nil
		}
		if !strings.HasSuffix(dirEntry.Name(), ".class") {
			return nil
		}
		output, errInner := exec.Command("javap", "-v", absFilePath).Output()
		if errInner != nil {
			fatalErr(errInner, "compileOneTree WalkDir: exec.Command(javap %s) failed.", absFilePath)
		}
		dirPath := filepath.Dir(absFilePath)
		outFile := "javap_" + dirEntry.Name() + ".log"
		storeText(dirPath, outFile, string(output))
		return nil
	})

	if err2 != nil {
		fatalErr(err2, "filepath.WalkDir failed")
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
// 0: success
// 1: compilation errors
// 2: run errors
func ExecuteOneTest(fullPathDir string) (int, string) {
	var stCode int
	global := GetGlobalRef()

	// Save the path of the current working dir
	here, err := os.Getwd()
	if err != nil {
		fatalErr(err, "ExecuteOneTest os.Getwd failed")
	}

	// Position to fullPathDir as the new working dir
	err = os.Chdir(fullPathDir)
	if err != nil {
		fatalErr(err, "ExecuteOneTest os.Chdir(%s) failed", fullPathDir)
	}

	if global.FlagCompile {
		// Compile every .java file in the tree
		errorCount := compileOneTree(fullPathDir)

		// If there was at least one compilation error, go no further
		if errorCount > 0 {
			// Go back to the original working directory.
			err2 := os.Chdir(here)
			if err2 != nil {
				fatalErr(err2, "ExecuteOneTest os.Chdir(%s) failed", here)
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
	logger("Executing %s using jvm=%s", testName, global.JvmName)
	var outString string
	options := global.JvmOptPrefix + " -cp " + fullPathDir + string(os.PathListSeparator) + global.DirHelpers
	if global.UserXopts != "" {
		options = options + " " + global.UserXopts
	}
	if global.JvmName == "jacobin" {
		stCode, outString = runner(global.JvmName, global.JvmExe, testName, options, "main.class")
	} else {
		stCode, outString = runner(global.JvmName, global.JvmExe, testName, options, "main")
	}

	// Go back to the original working directory.
	err = os.Chdir(here)
	if err != nil {
		fatalErr(err, "ExecuteOneTest os.Chdir(%s) failed.  Was trying to return here:", here)
	}

	// Return runner execution result
	return stCode, outString // test case success
}
