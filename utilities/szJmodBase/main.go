package main

import (
	"archive/zip"
	"bytes"
	"encoding/binary"
	"fmt"
	"io"
	"os"
	"strings"
	"utilities/helpers"
)

const BaseJmod = "java.base.jmod"
const ExpectedMagicNumber = 0x4A4D

// Walk the base JMOD file and count the bytes for all classes/*.class entries
func main() {

	// Form the full path to the base JMOD
	javaHome := os.Getenv("JAVA_HOME")
    fullPath := javaHome + string(os.PathSeparator) + "jmods" + string(os.PathSeparator) + BaseJmod

	// Open the JMOD file
    _, err := os.Open(fullPath)
    if err != nil {
        helpers.FmtFatal("os.Open failed:", fullPath, err)
    }
    
    // Read entire file contents
	jmodBytes, err := os.ReadFile(fullPath)
	if err != nil {
		helpers.FmtFatal("os.ReadFile failed:", fullPath, err)
	}

	// Validate the file's magic number
	fileMagicNumber := binary.BigEndian.Uint16(jmodBytes[:2])
	if fileMagicNumber != ExpectedMagicNumber {
		helpers.FmtFatal("fileMagicNumber != ExpectedMagicNumber:", fullPath, err)
	}

	// Skip over the JMOD header so that it is recognized as a ZIP file
	offsetReader := bytes.NewReader(jmodBytes[4:])

	// Prepare the reader for the zip archive
	zipReader, err := zip.NewReader(offsetReader, int64(len(jmodBytes)-4))
	if err != nil {
		helpers.FmtFatal("zip.NewReader failed:", fullPath, err)
	}

	// For each file entry within the zip reader, process it
	totalCount := 0
	for _, fileEntry := range zipReader.File {

		// Has the right prefix and suffix?
		if !strings.HasPrefix(fileEntry.Name, "classes") {
			continue // No, skip this entry
		}
		if !strings.HasSuffix(fileEntry.Name, ".class") {
			continue
		}

		// Remove the "classes/" prefix.
		classFileName := strings.Replace(fileEntry.Name, "classes/", "", 1)

		// Open this file entry
		classHandle, err := fileEntry.Open()
		if err != nil {
			helpers.FmtFatal("Cannot access embedded file:", classFileName, err)
		}

		bytesClassFile, err := io.ReadAll(classHandle)
		if err != nil {
			helpers.FmtFatal("io.readAll failed for embedded file:", classFileName, err)
		}

		// Add byte count to total
		totalCount += len(bytesClassFile)

		_ = classHandle.Close()
	}

	megaBytes := float64(totalCount) / 1000000.0
	fmt.Printf("Byte count for all class files: %f MB\n", megaBytes)

}

