package main

import (
	"archive/zip"
	"bytes"
	"encoding/binary"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"utilities/helpers"
)

const ExpectedMagicNumber = 0x4A4D

// Walk the base JMOD file and count the bytes for all classes/*.class entries
func main() {

	// Form the full path to the base JMOD
	javaHome := os.Getenv("JAVA_HOME")
	if javaHome == "" {
		helpers.Fatal("os.GetEnv failed to find JAVA_HOME")
	}
    dirPath := javaHome + string(os.PathSeparator) + "jmods"
    
	// Open jmods directory
	dirOpened, err := os.Open(dirPath)
	if err != nil {
		helpers.FmtFatal("os.Open(jmods directory) failed", dirPath, err)
	}

	// Get all the file entries in the logs directory
	names, err := dirOpened.Readdirnames(0) // get all entries
	if err != nil {
		helpers.FmtFatal("Readdirnames(jmods directory) failed", dirPath, err)
	}

	// For each jmods file, process it
	for index := range names {
		name := names[index]
		fullPath := filepath.Join(dirPath, name)
		processJmodsFile(name, fullPath)
	}
	
}

func processJmodsFile(baseName string, fullPath string) {
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
	countFiles := 0
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
		
		// Form a.b.c
		splut := strings.Split(classFileName, string(os.PathSeparator))
		var a_b_c string
		switch len(splut) {
			case 1:
				a_b_c = splut[0]
			case 2:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1]
			case 3:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2]
			case 4:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2] + string(os.PathSeparator) + splut[3]
			case 5:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2] + string(os.PathSeparator) + splut[3] + string(os.PathSeparator) + splut[4]
			case 6:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2] + string(os.PathSeparator) + splut[3] + 
				        string(os.PathSeparator) + splut[4] + string(os.PathSeparator) + splut[5]
			case 7:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2] + string(os.PathSeparator) + splut[3] + 
				        string(os.PathSeparator) + splut[4] + string(os.PathSeparator) + splut[5] + string(os.PathSeparator) + splut[6]
			default:
				a_b_c = splut[0] + string(os.PathSeparator) + splut[1] + string(os.PathSeparator) + splut[2] + string(os.PathSeparator) + splut[3] + 
				        string(os.PathSeparator) + splut[4] + string(os.PathSeparator) + splut[5] + string(os.PathSeparator) + splut[6] + string(os.PathSeparator) + splut[7]
		}	
		
		// Log this a.b.c
		fmt.Printf("prefix , %s , %s\n", baseName, a_b_c)

		// Add byte count to total
		countFiles += 1
	}

	fmt.Printf("TOTAL , %s , %d\n", baseName, countFiles)

}

