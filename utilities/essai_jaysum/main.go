package main

import (
	"bufio"
	"fmt"
	"hash/crc32"
	"log"
	"os"
)

const GobFile = "class_prefix_map.gob"

func getAllTheBytes(filePath string) []byte {

	// Open file
	file, err := os.Open(filePath)
	if err != nil {
		msg := fmt.Sprintf("os.Open(%s) failed", filePath)
		log.Fatal(msg)
	}
	defer file.Close()

	// Get size of file in bytes
	stats, statsErr := file.Stat()
	if statsErr != nil {
		msg := fmt.Sprintf("file.Stat(%s) failed", filePath)
		log.Fatal(msg)
	}
	var size = stats.Size()

	// Make a big enough byte-array
	bytes := make([]byte, size)

	// Read all the bytes
	buffer := bufio.NewReader(file)
	_, err = buffer.Read(bytes)

	return bytes
}

func main() {

	// Form the full path to the jmod directory
	javaHome := os.Getenv("JAVA_HOME")
	if javaHome == "" {
		log.Fatal("os.GetEnv failed to find JAVA_HOME")
	}
	fullPath := javaHome + string(os.PathSeparator) + "release"

	// Get bytes from file
	bites := getAllTheBytes(fullPath)

	// Compte uint32 checksum
	checkSum := crc32.ChecksumIEEE(bites)

	// Show checksum
	log.Printf("Checksum (32 bits) of %s: %x\n", fullPath, checkSum)

}
