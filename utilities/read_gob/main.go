package main

import (
	"encoding/gob"
	"fmt"
	"log"
	"os"
)

func main() {

	// Initialise input map
	myMap := make(map[string]string)

	// Open input file
	gobFilePath := os.Args[1]
	inFile, err := os.Open(gobFilePath)
	if err != nil {
		log.Fatal("decoding os.Open:", err)
	}

	// Create a decoder and receive a value.
	dinky := gob.NewDecoder(inFile)
	err = dinky.Decode(&myMap)
	if err != nil {
		log.Fatal("gob Decode:", err)
	}

	// Show the 2 maps
	fmt.Println("Map:\n", myMap)

}
