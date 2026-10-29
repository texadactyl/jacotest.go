package main

import (
	"bytes"
	"crypto/sha256"
	"database/sql"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"slices"
	"strings"
	"testing"
	"time"
	"unicode"
)

const (
	ModeOutputFile = 0644
	FlagsOpen      = os.O_CREATE | os.O_WRONLY
	DirPermissions = 0755
)

// getUtcDate - Get UTC date string, YYYY-MM-DD.
func getUtcDate() string {
	return time.Now().UTC().Format("2006-01-02")
}

// getUtcTime - Get UTC time string in the format of hh:mm:ss.ddd.
func getUtcTime() string {
	return time.Now().UTC().Format("15:04:05.000")
}

// writeOutputText - Write a text line to the given output file handle.
func writeOutputText(outHandle *os.File, textLine string) {
	if _, err := fmt.Fprintln(outHandle, textLine); err != nil {
		outPath, _ := filepath.Abs(filepath.Dir(outHandle.Name()))
		fatalErr(err, "fmt.Fprintln(%s) failed", outPath)
	}
}

// makeDir - If the specified directory does not yet exist, create it.
func makeDir(pathDir string) {
	info, err := os.Stat(pathDir)
	if err == nil {
		if !info.IsDir() {
			fatalText("makeDir: Observed a simple file: %s (expected a directory)", pathDir)
		}
	} else if os.IsNotExist(err) {
		if err := os.Mkdir(pathDir, DirPermissions); err != nil {
			fatalErr(err, "makeDir: os.Mkdir(%s) failed", pathDir)
		}
	} else {
		fatalErr(err, "makeDir: os.Stat(%s) failed", pathDir)
	}
}

// storeText - Store a text file in the specified directory.
func storeText(targetDir, argFile, text string) {
	var err error
	fullPath := filepath.Join(targetDir, argFile)
	outHandle, err := os.Create(fullPath)
	if err != nil {
		fatalErr(err, "storeText: os.Create(%s) failed", fullPath)
	}
	_, err = fmt.Fprintln(outHandle, text)
	if err != nil {
		fatalErr(err, "storeText: fmt.Fprintln(%s) failed", fullPath)
	}
	err = outHandle.Close()
	if err != nil {
		fatalErr(err, "storeText: fmt.Close(%s) failed", fullPath)
	}
}

// cleanerText - Replace or remove non-printable characters in a string.
func cleanerText(inString string, newlines bool) string {
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

/*
    formatElapsedTime - Given the elapsed time in milliseconds, format as HH:MM:SS.ddd

	Usage:

	fmt.Println(formatElapsed(3_723_456)) // "01:02:03.456"
	fmt.Println(formatElapsed(63456)) // "00:01:03.456"
	fmt.Println(formatElapsed(3456)) // "3.456 sec"
	fmt.Println(formatElapsed(456)) // "456 msec"
*/

func formatElapsedTime(etMsecs int) string {
	hours := etMsecs / 3600000
	minutes := (etMsecs % 3600000) / 60000
	seconds := (etMsecs % 60000) / 1000
	millis := etMsecs % 1000

	switch {
	case hours == 0 && minutes == 0 && seconds == 0:
		return fmt.Sprintf("%d msec", millis)
	case hours == 0 && minutes == 0:
		return fmt.Sprintf("%d.%03d sec", seconds, millis)
	default:
		return fmt.Sprintf("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis)
	}
}

// deferredRemoveFile - support deferred file removal
func deferredRemoveFile(name string) {
	err := os.Remove(name)
	if err == nil {
		return
	}
	_, _ = fmt.Printf("\n*** ERROR, deferredRemoveFile: os.Remove(%s) failed, err: %v\n", name, err)
}

// deferredCloseFile - support deferred file close
func deferredCloseFile(file *os.File) {
	err := file.Close()
	if err == nil {
		return
	}
	name := file.Name()
	fmt.Printf("\n*** ERROR, deferredCloseFile: os.Remove(%s) failed, err: %v\n", name, err)
}

// sortCaseInsensitive - Case-insensitive sort.
func sortCaseInsensitive(ptrSlice *[]string) {
	slices.SortFunc(*ptrSlice, func(a, b string) int {
		return strings.Compare(strings.ToLower(a), strings.ToLower(b))
	})
}

// captureOutput captures stdout output generated while executing fn.
func captureOutput(t *testing.T, fn func()) string {
	t.Helper()

	oldStdout := os.Stdout
	rdr, wrt, err := os.Pipe()
	if err != nil {
		t.Fatalf("os.Pipe failed: %v", err)
	}
	os.Stdout = wrt

	outChan := make(chan string)
	go func() {
		var buf bytes.Buffer
		_, _ = io.Copy(&buf, rdr)
		outChan <- buf.String()
	}()

	fn()

	_ = wrt.Close()
	os.Stdout = oldStdout
	out := <-outChan
	_ = rdr.Close()

	return out
}

// sha256Hex returns the SHA-256 of the file's raw bytes as a lowercase hexadecimal-coded string.
func sha256Hex(path string) (string, error) {
	// Open the input file given by path.
	file, err := os.Open(path)
	if err != nil {
		fatalText("sha256Hex, os.Open(%s) failed, err: %v\n", path, err)
	}
	defer deferredCloseFile(file)

	// Hash the file's contents.
	hasher := sha256.New()
	_, err = io.Copy(hasher, file)
	if err != nil {
		fatalText("sha256Hex, io.Copy(%s) to hasher failed, err: %v\n", path, err)
	}

	// Return the hex string representing the hash.
	return fmt.Sprintf("%x", hasher.Sum(nil)), nil
}

// UTCTimeStr2LocalTimeStr - Convert a UTC time string into a local one
func UTCTimeStr2LocalTimeStr(utcString string) string {
	timeStamp, err := time.Parse("2006-01-02T15:04:05Z07:00", utcString)
	if err != nil {
		return fmt.Sprintf("time.Parse error: %s", err.Error())
	}
	zone, _ := time.Now().Zone()
	return fmt.Sprintf("%s %s", timeStamp.Local().Format("2006-01-02 15:04:05"), zone)
}

// setupUnitTest sets up a temporary environment for unit tests, including a temp directory and a database connection.
func setupUnitTest(t *testing.T) {
	t.Helper()

	// tempDir = absolute path to the temp directory.
	tempDir, err := filepath.Abs("./tempDir")
	if err != nil {
		t.Fatalf("setupUnitTest: failed to get absolute path for temp directory (./tempDir): %v", err)
	}

	// Ensure it does not yet exist.
	err = os.RemoveAll(tempDir)
	if err != nil && !os.IsNotExist(err) {
		t.Fatalf("setupUnitTest: failed to remove temp directory (%s): %v", tempDir, err)
	}

	// Create the temp directory.
	err = os.Mkdir(tempDir, 0755)
	if err != nil {
		t.Fatalf("setupUnitTest os.Mkdir(%s) failed: %v", tempDir, err)
	}

	dbPath := filepath.Join(tempDir, "test.db")
	absDbPath, err := filepath.Abs(dbPath)
	if err != nil {
		t.Fatalf("setupUnitTest filepath.Abs failed for %s: %v", dbPath, err)
	}

	dsn := fmt.Sprintf("%s?_pragma=busy_timeout(5000)", absDbPath)
	handle, err := sql.Open(driverDatabase, dsn)
	if err != nil {
		t.Fatalf("setupUnitTest: sql.Open failed: %v", err)
	}

	prevHandle := dbHandle
	prevIsOpen := dbIsOpen
	t.Cleanup(func() {
		DBClose()
		dbHandle = prevHandle
		dbIsOpen = prevIsOpen
		err := os.RemoveAll(tempDir)
		if err != nil && !os.IsNotExist(err) {
			t.Errorf("setupUnitTest: failed to remove temp directory (%s): %v", tempDir, err)
		}
	})

	dbHandle = handle
	dbIsOpen = true
}
