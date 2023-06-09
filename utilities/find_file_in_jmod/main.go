package main

import (
	"archive/zip"
	"bytes"
	"encoding/binary"
	"fmt"
	"io"
	"log"
	"os"
	"path/filepath"
)

const ExpectedMagicNumber = 0x4A4D

func showHelp() {
	fmt.Printf("\nUsage:  %s  <jmod name>  <file name>\n\n", filepath.Base(os.Args[0]))
	os.Exit(1)
}

func main() {

	if len(os.Args) != 3 {
		showHelp()
	}
	jmodName := os.Args[1]
	fileName :=  "classes/" + os.Args[2]

	// Form the full path to the jmod file name
	javaHome := os.Getenv("JAVA_HOME")
	if javaHome == "" {
		log.Fatal("os.GetEnv failed to find JAVA_HOME")
	}
        jmodPath := javaHome + string(os.PathSeparator) + "jmods" + string(os.PathSeparator) + jmodName
    
        // Read entire jmod file contents
	jmodBytes, err := os.ReadFile(jmodPath)
	if err != nil {
		log.Fatalf("os.ReadFile(%s) failed:\n%s\n", jmodPath, err.Error())
	}

	// Validate the file's magic number
	fileMagicNumber := binary.BigEndian.Uint16(jmodBytes[:2])
	if fileMagicNumber != ExpectedMagicNumber {
		log.Fatalf("fileMagicNumber != ExpectedMagicNumber in jmod file %s\n", jmodPath)
	}

	// Skip over the jmod header so that it is recognized as a ZIP file
	ioReader := bytes.NewReader(jmodBytes[4:])
	
	// Prepare the reader for the zip archive
	zipReader, err := zip.NewReader(ioReader, int64(len(jmodBytes)-4))
	if err != nil {
		log.Fatalf("zip.NewReader(%s) failed:\n%s\n", jmodPath, err.Error())
	}

	// Open the file within the zip archive
	fileHandle, err := zipReader.Open(fileName)
	if err != nil {
		log.Fatalf("zipReader.Open(%s in %s) failed:\n%s\n", fileName, jmodPath, err.Error())
	}

	// Read entire class file contents
	classBytes, err := io.ReadAll(fileHandle)
	if err != nil {
		log.Fatalf("os.ReadAll(%s in %s) failed:\n%s\n", fileName, jmodPath, err.Error())
	}

	byteCount := len(classBytes)
	fmt.Printf("Total bye count of %s in %s = %d\n", fileName, jmodPath, byteCount)

}

