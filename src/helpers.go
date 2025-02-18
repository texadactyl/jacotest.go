package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"
	"unicode"
)

const ModeOutputFile = 0644
const FlagsOpen = os.O_CREATE | os.O_WRONLY

// Logger - Time-stamp log function.
func Logger(msg string) {
	now := time.Now()
	fmt.Printf("%s %s\n", now.Format("15:04:05"), msg)
}

// LogWarning - Log a warning.
func LogWarning(msg string) {
	text := fmt.Sprintf("*** Warning :: %s", msg)
	Logger(text)
}

// LogError - Log an error.
func LogError(msg string) {
	text := fmt.Sprintf("*** ERROR :: %s", msg)
	Logger(text)
}

// LogTimeout - Log a timeout.
func LogTimeout(msg string) {
	text := fmt.Sprintf("*** TIMEOUT :: %s", msg)
	Logger(text)
}

// LogSkip - skip a line for report readability.
func LogSkip() {
	fmt.Printf("\n")
}

// FatalText - Log a fatal error message and croak.
func FatalText(text string) {
	errMsg := fmt.Sprintf("*** FATAL :: %s", text)
	Logger(errMsg)
	DBClose()
	os.Exit(1)
}

// FatalErr - Log a fatal error message with error.Error() text and croak.
func FatalErr(msg string, err error) {
	var text string
	text = fmt.Sprintf("*** FATAL :: %s\n\terr: %s", msg, err.Error())
	Logger(text)
	DBClose()
	os.Exit(1)
}

// GetUtcDate - Get UTC date string, YYYY-MM-DD.
func GetUtcDate() string {
	now := time.Now().UTC()
	return now.Format("2006-01-02")
}

// GetUtcTime - Get UTC time string in the format of hh:mm:ss.ddd.
func GetUtcTime() string {
	now := time.Now().UTC()
	return now.Format("15:04:05.000")
}

// WriteOutputText - Write a text line to the given output file handle.
func WriteOutputText(outHandle *os.File, textLine string) {

	_, err := fmt.Fprintln(outHandle, textLine)
	if err != nil {
		outPath, _ := filepath.Abs(filepath.Dir(outHandle.Name()))
		FatalErr(fmt.Sprintf("fmt.Fprintln(%s) failed", outPath), err)
	}

}

// MakeDir - If the specified directory does not yet exist, create it.
func MakeDir(pathDir string) {
	info, err := os.Stat(pathDir)
	if err == nil { // found it
		if !info.IsDir() { // expected a directory, not a simple file !!
			FatalText(fmt.Sprintf("MakeDir: Observed a simple file: %s (expected a directory)", pathDir))
		}
	} else { // not found or an error occurred
		if os.IsNotExist(err) {
			// Create directory
			err = os.Mkdir(pathDir, 0755)
			if err != nil {
				FatalErr(fmt.Sprintf("MakeDir: os.MkDir(%s) failed", pathDir), err)
			}
		} else { // some type of error
			FatalErr(fmt.Sprintf("MakeDir: os.Stat(%s) failed", pathDir), err)
		}
	}
}

// StoreText - Store a text file in the specified directory.
func StoreText(targetDir string, argFile string, text string) {
	// Create the log file
	fullPath := filepath.Join(targetDir, argFile)
	outHandle, err := os.Create(fullPath)
	if err != nil {
		FatalErr(fmt.Sprintf("StoreText: os.Create(%s) failed, err=%s", fullPath), err)
	}
	defer outHandle.Close()

	// Store the given text
	_, err = fmt.Fprintln(outHandle, text)
	if err != nil {
		FatalErr(fmt.Sprintf("StoreText: fmt.Fprintln(%s) failed", fullPath), err)
	}
}

// CleanerText replaces all non-printable characters in a string with a space.
func CleanerText(arg string) string {
	runes := []rune(arg)
	for ix, roon := range runes {
		if !unicode.IsPrint(roon) && roon != '\n' && roon != '\r' {
			runes[ix] = ' '
		}
	}
	return string(runes)
}
