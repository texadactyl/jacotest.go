package main

import (
	"fmt"
	"os"
	"path"
	"path/filepath"
	"strings"
)

var ph23Tracing = false

// Table tblHitByTC
// key: test case name
// value: matched fragment
var tblHitByTC = make(map[string]string)

// Table TblHitByCat
// key: matched fragment
// values: test case name, whole text line
type TblHitByCat struct {
	fragment string
	tcName   string
	textLine string
}

var tblHitByCat []TblHitByCat
var allTestCases = make(map[string]string)

// Phase 2 - Build tblHitByTC and tblHitByCat.
func phase2(tblErrCatDefs []string, logFileExt string) {
	globals := GetGlobalRef()

	// Get the list of files in the directory
	logFileList, err := os.ReadDir(globals.DirLogs)
	if err != nil {
		FatalErr(fmt.Sprintf("phase2: os.ReadDir(%s) failed:", globals.DirLogs), err)
	}

	// For each failure category entry, process all the "FAILED" log files.
	for _, errCatFragment := range tblErrCatDefs {

		// For each .log file in the list that represents a failed test case,
		// scan it for the search argument
		for _, logFile := range logFileList {

			fileName := logFile.Name()

			// Skip subdirectories
			if logFile.IsDir() {
				continue
			}

			// Skip file names that are not prefixed by "FAILED".
			if !strings.HasPrefix(fileName, "FAILED") {
				continue
			}

			// Skip files with extensions that do not match criteria
			if logFileExt != "" {
				if path.Ext(fileName) != logFileExt {
					continue
				}
			}

			// Extract test case name.
			fnameTokens := strings.Split(fileName, ".")
			testCaseName := fnameTokens[1] // Collect test case name.

			// If the test case was not already recorded, add it to allTestCases having no error text line.
			_, ok := allTestCases[testCaseName]
			if !ok {
				allTestCases[testCaseName] = "" // Assume that we won't find a category for this test case.
			}

			if ph23Tracing {
				fmt.Printf("DEBUG phase2 logFile=%s, testCaseName=%s\n", fileName, testCaseName)
			}

			// Skip if the corresponding test case is already in tblHitByTC.
			// Keeping just the first error message entry for a given test case.
			_, found := tblHitByTC[testCaseName]
			if found {
				if ph23Tracing {
					fmt.Printf("DEBUG phase2 skipping duplicate: testCaseName=%s\n", testCaseName)
				}
				continue
			}

			// Get all the bytes of the current selected file.
			fullLogFilePath := filepath.Join(globals.DirLogs, fileName)
			logDataBytes, err := os.ReadFile(fullLogFilePath)
			if err != nil {
				FatalErr(fmt.Sprintf("ExecGrape: os.ReadFile(%s) failed", fileName), err)
			}

			// Convert bytes into an array of text lines.
			logTextLines := strings.Split(string(logDataBytes), "\n")

			// For each text line in the file, see if the search argument is present
			for _, textLine := range logTextLines {
				if strings.Contains(textLine, errCatFragment) {
					// Found a substring in current line that matches errCatExpected.
					tblHitByTC[testCaseName] = textLine
					tblHitByCat = append(tblHitByCat, TblHitByCat{errCatFragment, testCaseName, textLine})
					allTestCases[testCaseName] = textLine
					if ph23Tracing {
						fmt.Printf("DEBUG phase2 added hit: fragment=%s, testCaseName=%s, textLine=%s\n",
							errCatFragment, testCaseName, textLine)
						fmt.Println(tblHitByTC)
					}
					break
				} else { // no hit
					if ph23Tracing {
						fmt.Printf("DEBUG phase2 missed: fragment=%s, testCaseName=%s, textLine=%s\n",
							errCatFragment, testCaseName, textLine)
					}
				}
			}

		} // for _, logFile := range logFileList

	} // for _, searchArg := range tblErrCatDefs

}

// Phase 3 - Report from tblHitByCat
// which is already sequentially grouped by error category.
func phase3(tblCheckList map[string]int, outHandle *os.File) int {
	lastCat := ""
	hitCounter := 0
	catCounter := 0

	// For each failure category entry, process all the tblHitByCat entries.
	for _, tblEntry := range tblHitByCat {

		// tblEntry holds the current hit by error category. Does it match the last error category?
		if tblEntry.fragment != lastCat {

			// New error category.
			if hitCounter > 0 {
				// Need to emit group total pending.
				wstr := fmt.Sprintf("--- Total: %d", catCounter)
				WriteOutputText(outHandle, wstr)

				// Zero out the error category counter.
				catCounter = 0
			} // else this is the first error category.

			// Write heading for new error category.
			WriteOutputText(outHandle, "")
			wstr := fmt.Sprintf("===== %s =====", tblEntry.fragment)
			WriteOutputText(outHandle, wstr)

			// Make this the last category for group totaling.
			lastCat = tblEntry.fragment
		}

		// Note that this test case was processed.
		tblCheckList[tblEntry.tcName] += 1

		// Insert a "failed" test case record into the database.
		DBStoreFailed(tblEntry.tcName, tblEntry.textLine)

		// Write a summary line for this test case failure.
		line := fmt.Sprintf("%s: %s", tblEntry.tcName, tblEntry.textLine)
		WriteOutputText(outHandle, line)

		// Bump total hits.
		hitCounter += 1

		// Bump error category hits.
		catCounter += 1
	}

	// Need to emit total for last group.
	wstr := fmt.Sprintf("--- Total: %d", catCounter)
	WriteOutputText(outHandle, wstr)

	// Take care of uncategorized errors.
	for name, errorText := range allTestCases {
		if errorText == "" {
			// Insert an uncategorized fail test case record into the database.
			DBStoreFailed(name, "UNCATEGORIZED")
		}
	}

	// Return the total number of hits.
	return hitCounter
}

// Phases 2 and 3
func Phases2And3(tblCheckList map[string]int, outHandle *os.File) int {

	// Table of error categories (string fragments).
	var tblErrCatDefs []string
	var tempTable []string

	// Get a handle on the jacotest globals.
	globals := GetGlobalRef()

	// Get the error categories as a slice of strings.
	fileBytes, err := os.ReadFile(globals.ErrCatFilePath)
	if err != nil {
		FatalErr(fmt.Sprintf("Phases2And3: os.ReadFile(%s) failed", globals.ErrCatFilePath), err)
	}
	giantString := string(fileBytes)
	tempTable = strings.Split(giantString, "\n")

	// Throw out the comment lines which begin with '#'.
	for _, fragment := range tempTable {
		fragment = strings.Trim(fragment, " ")
		if len(fragment) < 1 || strings.HasPrefix(fragment, "#") {
			continue
		}
		tblErrCatDefs = append(tblErrCatDefs, fragment)
		if ph23Tracing {
			fmt.Printf("DEBUG phases2And3: tblErrCatDefs fragment=%s\n", fragment)
		}
	}

	// Run Phase 2.
	phase2(tblErrCatDefs, globals.LogFileExt)

	// Run Phase 3.
	counter := phase3(tblCheckList, outHandle)

	// Return with a count of all the error hits.
	return counter

}
