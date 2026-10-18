package main

import (
	"crypto/sha256"
	"encoding/csv"
	"fmt"
	"io"
	"io/fs"
	"os"
	"path/filepath"
	"strings"
)

// csvWriter is used to emit CSV records.
var csvWriter *csv.Writer

// counter - count of all class files processed.
var counter = 0

// ramTableKey_type - key type for ramTable.
type ramTableKey_type struct {
	DirSuffix, ClassName string
}

// ramTable - temporary table of class checksums until they can be sorted.
var ramTable = make(map[ramTableKey_type]string)

// ClassChecksum walks testsTreeTop and, for every file with extension ".class",
// writes one CSV record to csvFullPath with 3 fields:
//
//	directory suffix (relative to testsTreeTop, "/" separators, "." for the top itself),
//	class name (file name without ".class"),
//	SHA-256 of the class file's bytes (lowercase hex).
//
// Unreadable files and directories are reported on stderr and skipped.

func ClassChecksum(csvFullPath string, testsTreeTop string) {

	Logger(fmt.Sprintf("ClassChecksum: output file: %s", csvFullPath))

	csvFile, err := os.Create(csvFullPath)
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: cannot create %s: %v\n", csvFullPath, err)
		FatalText(errMsg)
	}
	csvWriter = csv.NewWriter(csvFile)

	err = csvWriter.Write([]string{"$ Jacotest", "version", global.Version})
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: csvWriter.Write(%s) failed, err: %v\n", csvFullPath, err)
		FatalText(errMsg)
	}

	err = filepath.WalkDir(testsTreeTop, innerFunc)
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: WalkDir(%s) failed: %v\n", testsTreeTop, err)
		FatalText(errMsg)
	}

	// Create a slice of keys.
	keys := make([]string, 0, len(ramTable))
	for key := range ramTable {
		keys = append(keys, key.DirSuffix+"/"+key.ClassName)
	}

	// Sort the keys, case-insensitive.
	SortCaseInsensitive(&keys)

	// Process the key slice, depending on selection value.
	for _, concatted := range keys {
		strArray := strings.Split(concatted, "/")
		key := ramTableKey_type{
			DirSuffix: strArray[0],
			ClassName: strArray[1],
		}
		checksum := ramTable[key]
		err = csvWriter.Write([]string{strArray[0], strArray[1], checksum})
		if err != nil {
			errMsg := fmt.Sprintf("ClassChecksum: csvWriter.Write(%s) failed, err: %v\n", csvFullPath, err)
			FatalText(errMsg)
		}

	}
	csvWriter.Flush()

	_ = csvFile.Close()
	if err = csvWriter.Error(); err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: csvFile.Close(%s) failed, err: %v\n", testsTreeTop, err)
		FatalText(errMsg)
	}

	Logger(fmt.Sprintf("ClassChecksum: processed %d class files", counter))

}

// innerFunc is the inner function called by filepath.WalkDir.
func innerFunc(path string, entry fs.DirEntry, err error) error {

	// If an entry cannot be accessed, that is a fatal error.
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: cannot access path: %s, entry: %s, err: %v\n",
			path, entry.Name(), err)
		FatalText(errMsg)
	}

	// If a directory entry, skip it.
	if entry != nil && entry.IsDir() {
		return nil
	}

	if !entry.Type().IsRegular() || !strings.HasSuffix(entry.Name(), ".class") {
		return nil
	}

	checksum, err := sha256Hex(path)
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: sha256Hex(%s) returned err: %v\n", path, err)
		FatalText(errMsg)
	}

	// Directory suffix = parent directory relative to the tree top, not the full path.
	// ToSlash keeps the field identical on Windows, Linux, and macOS, so CSVs from
	// different machines can be compared directly.
	dirSuffix, err := filepath.Rel(testsTreeTop, filepath.Dir(path))
	if err != nil {
		errMsg := fmt.Sprintf("ClassChecksum: filepath.Rel(%s) returned: %v\n", path, err)
		FatalText(errMsg)
	}
	dirSuffix = filepath.ToSlash(dirSuffix)

	className := strings.TrimSuffix(entry.Name(), ".class")

	key := ramTableKey_type{
		DirSuffix: dirSuffix,
		ClassName: className,
	}
	ramTable[key] = checksum

	counter += 1

	return nil

}

// sha256Hex returns the SHA-256 of the file's raw bytes as lowercase hex.
func sha256Hex(path string) (string, error) {
	// Open the input file given by path.
	file, err := os.Open(path)
	if err != nil {
		errMsg := fmt.Sprintf("sha256Hex, os.Open(%s) failed, err: %v\n", path, err)
		FatalText(errMsg)
	}
	defer DeferredCloseFile(file)

	// Hash the file's contents.
	hasher := sha256.New()
	_, err = io.Copy(hasher, file)
	if err != nil {
		errMsg := fmt.Sprintf("sha256Hex, io.Copy(%s) to hasher failed, err: %v\n", path, err)
		FatalText(errMsg)
	}

	// Return the hex string representing the hash.
	return fmt.Sprintf("%x", hasher.Sum(nil)), nil
}
