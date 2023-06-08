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
	decode := gob.NewDecoder(inFile)
	err = decode.Decode(&myMap)
	if err != nil {
		log.Fatal("gob Decode:", err)
	}

	// Find something interesting.
	fmt.Printf("com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class: {%s}\n", 
                    myMap["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"])
	
}
