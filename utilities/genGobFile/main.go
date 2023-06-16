package main

import (
	"archive/zip"
	"bytes"
	"encoding/binary"
	"encoding/gob"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"utilities/helpers"
)

const ExpectedMagicNumber = 0x4A4D
const JmodFileName = "java.base.jmod"
const GobFile = "xx.gob"

var xrefMap map[string]string

// Walk the base jmod file and count the bytes for all classes/*.class entries
func main() {

	helpers.Logger("Begin")

	// Initialize the cross-reference map
	xrefMap = make(map[string]string)

	// Form the full path to the jmod directory
	javaHome := os.Getenv("JAVA_HOME")
	if javaHome == "" {
		helpers.Fatal("os.GetEnv failed to find JAVA_HOME")
	}
	dirPath := javaHome + string(os.PathSeparator) + "jmods"
	fullPath := filepath.Join(dirPath, JmodFileName)
	countClasses := processJmodsFile(JmodFileName, fullPath)

	msg := fmt.Sprintf("Total classes added for all jmod files = %d", countClasses)
	helpers.Logger(msg)

	// Open output gob file
	outFile, err := os.Create(GobFile)
	if err != nil {
		helpers.FmtFatal("encoding os.Create failed", GobFile, err)
	}

	// Create a gob encoder and encode the cross-reference map
	inky := gob.NewEncoder(outFile)
	err = inky.Encode(xrefMap)
	if err != nil {
		helpers.FmtFatal("gob Encode failed", "", err)
	}

	// Close the output file
	err = outFile.Close()
	if err != nil {
		helpers.FmtFatal("encoding os.Close failed", GobFile, err)
	}

	helpers.Logger("End")

}

func processJmodsFile(jmodFileName string, fullPath string) int {

	// Open the jmods file
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

	// Skip over the jmod header so that it is recognized as a ZIP file
	offsetReader := bytes.NewReader(jmodBytes[4:])

	// Prepare the reader for the zip archive
	zipReader, err := zip.NewReader(offsetReader, int64(len(jmodBytes)-4))
	if err != nil {
		helpers.FmtFatal("zip.NewReader failed:", fullPath, err)
	}

	// For each file entry within the zip reader, process it
	countClasses := 0
	for _, fileEntry := range zipReader.File {

		// Has the right prefix and suffix?
		if !strings.HasPrefix(fileEntry.Name, "classes") {
			continue
		}
		if !strings.HasSuffix(fileEntry.Name, ".class") {
			continue
		}

		// Remove the "classes" + string(os.PathSeparator) prefix.
		classFileName := strings.Replace(fileEntry.Name, "classes"+string(os.PathSeparator), "", 1)

		// Add to map
		xrefMap[classFileName] = jmodFileName
		fmt.Println(classFileName)

		// Add to count of classes
		countClasses += 1
	}

	return countClasses

}
