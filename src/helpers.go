package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"
)

const ModeOutputFile = 0644
const FlagsOpen = os.O_CREATE | os.O_WRONLY

// Logger - Time-stamp log function
func Logger(msg string) {
	now := time.Now()
	fmt.Printf("%s %s\n", now.Format("15:04:05"), msg)
}

// LogWarning - Log a warning
func LogWarning(msg string) {
	text := fmt.Sprintf("*** Warning :: %s", msg)
	Logger(text)
}

// LogError - Log an error
func LogError(msg string) {
	text := fmt.Sprintf("*** ERROR :: %s", msg)
	Logger(text)
}

// LogTimeout - Log a timeout
func LogTimeout(msg string) {
	text := fmt.Sprintf("*** TIMEOUT :: %s", msg)
	Logger(text)
}

// Fatal - Log an error and croak
func Fatal(msg string) {
	text := fmt.Sprintf("*** FATAL :: %s", msg)
	Logger(text)
	sqlTracing = true
	DBClose()
	os.Exit(1)
}

// FmtFatal - Log an error in a special format and croak.
func FmtFatal(msg string, name string, err error) {
	var text string
	if name != "" {
		text = fmt.Sprintf("*** FATAL, %s\n\t\t%s\n\t\t%s", msg, name, err.Error())
	} else {
		text = fmt.Sprintf("*** FATAL, %s\n\t\t%s", msg, err.Error())
	}
	Logger(text)
	sqlTracing = true
	DBClose()
	os.Exit(1)
}

// GetUtcDate - Get UTC date string, YYYY-MM-DD
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
		FmtFatal("WriteOutputText: fmt.Fprintln failed", outPath, err)
	}

}

// MakeDir - If the specified directory does not yet exist, create it.
func MakeDir(pathDir string) {
	info, err := os.Stat(pathDir)
	if err == nil { // found it
		if !info.IsDir() { // expected a directory, not a simple file !!
			Fatal(fmt.Sprintf("MakeDir: Observed a simple file: %s (expected a directory)", pathDir))
		}
	} else { // not found or an error occurred
		if os.IsNotExist(err) {
			// Create directory
			err = os.Mkdir(pathDir, 0755)
			if err != nil {
				FmtFatal("MakeDir: os.MkDir failed", pathDir, err)
			}
		} else { // some type of error
			FmtFatal("MakeDir: os.Stat failed", pathDir, err)
		}
	}
}

// StoreText - Store a text file in the specified directory.
func StoreText(targetDir string, argFile string, text string) {
	// Create the log file
	fullPath := filepath.Join(targetDir, argFile)
	outHandle, err := os.Create(fullPath)
	if err != nil {
		Fatal(fmt.Sprintf("storeText: os.Create(%s) failed, err=%s", fullPath, err))
	}
	defer outHandle.Close()

	// Store the given text
	_, err = fmt.Fprintln(outHandle, text)
	if err != nil {
		FmtFatal("storeText: fmt.Fprintln failed", fullPath, err)
	}
}

// CleanerText - Replace nongraphics with '?'.
func CleanerText(argText string) string {
	rr := []rune(argText)
	for ii := 0; ii < len(rr); ii++ {
		if rr[ii] == '\n' || rr[ii] == '\r' || rr[ii] == '\t' {
			continue
		}
		if rr[ii] > 126 || rr[ii] < 32 {
			// Less than ASCII Space or greater than ASCII ~
			rr[ii] = 63 // ='?'
		}
	}
	return string(rr)
}
