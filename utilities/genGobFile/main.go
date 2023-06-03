package main

import (
	"archive/zip"
	"bytes"
	"encoding/binary"
	"fmt"
	"encoding/gob"
	"os"
	"path/filepath"
	"strings"
	"utilities/helpers"
)

const ExpectedMagicNumber = 0x4A4D
const GobFile = "class_prefix_map.gob" // TODO: This will move somewhere else, no doubt!
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
	countFiles := 0
	countClasses := 0
	for index := range names {
		name := names[index]
		fullPath := filepath.Join(dirPath, name)
		countClasses += processJmodsFile(name, fullPath)
		countFiles += 1
	}
	
	msg := fmt.Sprintf("Total jmod files processed= %d", countFiles)
	helpers.Logger(msg)
	msg = fmt.Sprintf("Total classes added for all jmod files = %d", countClasses)
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

func processJmodsFile(baseName string, fullPath string) int {

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
		if ! strings.HasPrefix(fileEntry.Name, "classes/") { continue }
		if ! strings.HasSuffix(fileEntry.Name, ".class") { continue }

		// Remove the "classes/" prefix.
		classFileName := strings.Replace(fileEntry.Name, "classes/", "", 1)
		
		// Form array splut = [ "a", "b", etc ] based on a/b/etc
		splut := strings.Split(classFileName, string(os.PathSeparator))
		lenSplut := len(splut)
		if lenSplut < 2 { continue }
		
		// Compute class name prefix.
		// Note: Right now, we are taking the whole class name.
		// Yes, we could have just used the classFileName instead of computing a prefix.
		// That could change in the future if we need to reduce the gob size.
		// TODO: Is using a prefix vs the whole name worth it?
		//classNamePrefix := splut[0]
		//for ii := 1; ii < lenSplut; ii++ {
		//	classNamePrefix += string(os.PathSeparator) + splut[ii]
		//}
		
		// Add to map
		// TODO: do this? xrefMap[classNamePrefix] = baseName
		xrefMap[classFileName] = baseName

		// Add to count of classes
		countClasses += 1
	}

	msg := fmt.Sprintf("Total classes added for %s = %d", baseName, countClasses)
	helpers.Logger(msg)
	
	return countClasses

}

