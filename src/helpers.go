package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"
	"unicode"
)

const (
	ModeOutputFile = 0644
	FlagsOpen      = os.O_CREATE | os.O_WRONLY
	DirPermissions = 0755
)

// Logger - Time-stamp log function.
func Logger(msg string) {
	now := time.Now()
	fmt.Printf("%s %s\n", now.Format("15:04:05"), msg)
}

// LoggerSkip - Time-stamp log function with a preceding newline for readability.
func LoggerSkip(msg string) {
	now := time.Now()
	fmt.Printf("\n%s %s\n", now.Format("15:04:05"), msg)
}

// LogWarning - Log a warning.
func LogWarning(msg string) {
	Logger(fmt.Sprintf("*** Warning :: %s", msg))
}

// LogError - Log an error.
func LogError(msg string) {
	Logger(fmt.Sprintf("*** ERROR :: %s", msg))
}

// LogTimeout - Log a timeout.
func LogTimeout(msg string) {
	Logger(fmt.Sprintf("*** TIMEOUT :: %s", msg))
}

// LogSkip - Skip a line for report readability.
func LogSkip() {
	fmt.Println()
}

// FatalText - Log a fatal error message and exit.
func FatalText(text string) {
	Logger(fmt.Sprintf("*** FATAL :: %s", text))
	DBClose()
	os.Exit(1)
}

// FatalErr - Log a fatal error message with error text and exit.
func FatalErr(msg string, err error) {
	Logger(fmt.Sprintf("*** FATAL :: %s\n\terr: %s", msg, err.Error()))
	DBClose()
	os.Exit(1)
}

// GetUtcDate - Get UTC date string, YYYY-MM-DD.
func GetUtcDate() string {
	return time.Now().UTC().Format("2006-01-02")
}

// GetUtcTime - Get UTC time string in the format of hh:mm:ss.ddd.
func GetUtcTime() string {
	return time.Now().UTC().Format("15:04:05.000")
}

// WriteOutputText - Write a text line to the given output file handle.
func WriteOutputText(outHandle *os.File, textLine string) {
	if _, err := fmt.Fprintln(outHandle, textLine); err != nil {
		outPath, _ := filepath.Abs(filepath.Dir(outHandle.Name()))
		FatalErr(fmt.Sprintf("fmt.Fprintln(%s) failed", outPath), err)
	}
}

// MakeDir - If the specified directory does not yet exist, create it.
func MakeDir(pathDir string) {
	info, err := os.Stat(pathDir)
	if err == nil {
		if !info.IsDir() {
			FatalText(fmt.Sprintf("MakeDir: Observed a simple file: %s (expected a directory)", pathDir))
		}
	} else if os.IsNotExist(err) {
		if err := os.Mkdir(pathDir, DirPermissions); err != nil {
			FatalErr(fmt.Sprintf("MakeDir: os.Mkdir(%s) failed", pathDir), err)
		}
	} else {
		FatalErr(fmt.Sprintf("MakeDir: os.Stat(%s) failed", pathDir), err)
	}
}

// StoreText - Store a text file in the specified directory.
func StoreText(targetDir, argFile, text string) {
	fullPath := filepath.Join(targetDir, argFile)
	outHandle, err := os.Create(fullPath)
	if err != nil {
		FatalErr(fmt.Sprintf("StoreText: os.Create(%s) failed", fullPath), err)
	}
	defer outHandle.Close()

	if _, err := fmt.Fprintln(outHandle, text); err != nil {
		FatalErr(fmt.Sprintf("StoreText: fmt.Fprintln(%s) failed", fullPath), err)
	}
}

// CleanerText - Replace or remove non-printable characters in a string.
func CleanerText(inString string, newlines bool) string {
	inRunes := []rune(inString)
	var outRunes []rune
	for _, rr := range inRunes {
		if newlines {
			if !unicode.IsPrint(rr) && rr != '\n' && rr != '\r' {
				outRunes = append(outRunes, ' ')
			} else {
				outRunes = append(outRunes, rr)
			}
		} else if unicode.IsPrint(rr) {
			outRunes = append(outRunes, rr)
		}
	}
	return string(outRunes)
}

// isDirectory - Check if the given path is a directory.
func isDirectory(path string) (bool, error) {
	info, err := os.Stat(path)
	if err != nil {
		if os.IsNotExist(err) {
			return false, nil // Path does not exist
		}
		return false, err // Other errors (e.g., permission issues)
	}
	return info.IsDir(), nil
}
