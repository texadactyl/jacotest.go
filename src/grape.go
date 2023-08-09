package main

import (
	"fmt"
	"os"
	"path"
	"path/filepath"
	"strings"
)

// Open a file for create or append
func OutGrapeOpen(outPath string, append bool) *os.File {

	var openFlags int

	if append {
		openFlags = os.O_APPEND | os.O_WRONLY
	} else {
		openFlags = os.O_CREATE | os.O_WRONLY
		_ = os.Remove(outPath)
	}

	outHandle, err := os.OpenFile(outPath, openFlags, MODE_OUTPUT_FILE)
	if err != nil {
		Fatal(fmt.Sprintf("OutGrapeOpen: os.Create(%s) failed, err=%s", outPath, err))
	}

	return outHandle

}

// Write a text line to the given output file handle
func OutGrapeText(outHandle *os.File, textLine string) {

	_, err := fmt.Fprintln(outHandle, textLine)
	if err != nil {
		outPath, _ := filepath.Abs(filepath.Dir(outHandle.Name()))
		FmtFatal("OutGrapeText: fmt.Fprintln failed", outPath, err)
	}

}

// Poor Man's `grep`
func ExecGrape(pathDir string, fileExt string, searchArg string, outHandle *os.File) {

	// Get the list of files in the directory
	fileList, err := os.ReadDir(pathDir)
	if err != nil {
		FmtFatal("ExecGrape: ioutil.ReadDir failed:", pathDir, err)
	}

	// For each .log file in the list, scan it for the search argument
	counter := 0
	for _, file := range fileList {

		fileName := file.Name()

		// Skip subdirectories
		if file.IsDir() {
			continue
		}

		// Skip files with extensions that do not match criteria
		if fileExt != "" {
			if path.Ext(fileName) != fileExt {
				continue
			}
		}

		// Get all the bytes of the current selected file
		filePath := filepath.Join(pathDir, fileName)
		dataBytes, err := os.ReadFile(filePath)
		if err != nil {
			FmtFatal("ExecGrape: ioutil.ReadFile failed:", fileName, err)
		}

		// Convert bytes into an array of strings
		dataStrings := strings.Split(string(dataBytes), "\n")

		// For each string in the file, see if the search argument is present
		for _, line := range dataStrings {
			if strings.Index(line, searchArg) > -1 {
				line = strings.ReplaceAll(line, "\n", "")
				fnameTokens := strings.Split(fileName, ".")
				DBStoreFailed(fnameTokens[1], line)
				line := fmt.Sprintf("%s: %s", fileName, line)
				OutGrapeText(outHandle, line)
				counter += 1
			}
		}

	}

	if counter > 0 {
		wstr := fmt.Sprintf("--- Total: %d", counter)
		OutGrapeText(outHandle, wstr)
	}

}
