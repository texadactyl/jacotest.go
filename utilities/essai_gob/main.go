package main

import (
	"encoding/gob"
	"fmt"
	"log"
	"os"
)

const GobFile = "xx.gob"

func main() {

	// Build input map
	m1 := make(map[string]string)
	m1["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"] = "java.base.jmod"
	m1["com/sun/crypto/provider/AESCipher$AES128_CFB_NoPadding.class"] = "java.base.jmod"
	m1["com/sun/crypto/provider/AESCipher$AES128_ECB_NoPadding.class"] = "java.base.jmod"

	// Open output file
	outFile, err := os.Create(GobFile)
	if err != nil {
		log.Fatal("encoding os.Create:", err)
	}
	inky := gob.NewEncoder(outFile)

	// Create an encoder and send a value.
	err = inky.Encode(m1)
	if err != nil {
		log.Fatal("gob Encode:", err)
	}

	// Close the output file
	err = outFile.Close()
	if err != nil {
		log.Fatal("encoding os.Close:", err)
	}

	// Initialise input map
	m2 := make(map[string]string)

	// Open input file
	inFile, err := os.Open(GobFile)
	if err != nil {
		log.Fatal("decoding os.Open:", err)
	}

	// Create a decoder and receive a value.
	dinky := gob.NewDecoder(inFile)
	err = dinky.Decode(&m2)
	if err != nil {
		log.Fatal("gob Decode:", err)
	}

	// Show the 2 maps
	fmt.Println("m1 = ", m1)
	fmt.Println("m1 com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class >>> ", m1["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"])
	fmt.Println("m2 = ", m2)
	fmt.Println("m2 com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class >>> ", m2["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"])

	// Success?
	if len(m1) != len(m2) {
		log.Fatal("len(m1) != len(m2)") // Sadly, no.
	}
	if m1["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"] != m2["com/sun/crypto/provider/AESCipher$AES128_CBC_NoPadding.class"] {
		log.Fatal("m1 != m2") // Sadly, no.
	}

	// Success!
	fmt.Println("End")

}
