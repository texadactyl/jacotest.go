/*

cleansrc utility

Valid source code character criteria: The character must be printable, \n, \r, or \t.

Given a source code file, clean it by either replacing characters that are not valid or discard them.

*/

package main

import (
	"encoding/hex"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"unicode"
	"utilities/helpers"
)

// Show help and then exit to the O/S
func showHelp() {
	suffix := filepath.Base(os.Args[0])
	fmt.Printf("\nUsage:  %s  [-h]  [-c <one-character> ]  [-r]\n\nwhere\n", suffix)
	fmt.Printf("\t-h : This display.\n")
	fmt.Printf("\t-c : Character used as a substitution for invalid source characters, specified as a 1-character string.\n")
	fmt.Printf("\t     Examples:\n")
	fmt.Printf("\t\t-c A --> The letter A is the substitute.\n")
	fmt.Printf("\t\t-c space --> The space character (' ') is the substitute.\n")
	fmt.Printf("\t\t-c tab --> The tab character ('\\t') is the substitute.\n")
	fmt.Printf("\t\t-c nl --> The newline character ('\\n') is the substitute.\n")
	fmt.Printf("\t\t-c cr --> The carriage return character ('\\r') is the substitute.\n")
	fmt.Printf("\t\t-c 0x23 --> The pound sign character ('#') is the substitute.\n")
	fmt.Printf("\t     If -c is not specified, then invalid source characters are discarded (squeezed out).\n")
	fmt.Printf("\t-r : Rewrite the contents of the input source code file. BE CAREFUL !!!\n")
	fmt.Printf("\nExit codes:\n")
	fmt.Printf("\t0\tNormal completion.\n")
	fmt.Printf("\t1\tNormal completion. The input source code file could be fixed but no input file rewrite was requested.\n")
	fmt.Printf("\t2\tHelp was requested or something went wrong during execution.\n")
	os.Exit(2)
}

func isSrcByte(bite byte) bool {
	if unicode.IsPrint(rune(bite)) {
		return true
	}
	if bite == '\n' || bite == '\r' || bite == '\t' {
		return true
	}
	return false
}

func main() {

	var Args []string
	var flagReplacement = false
	var inPath = ""
	var nilByte = byte(0x00)
	var replByte = nilByte

	// Initialise Args to the command-line arguments.
	for _, singleVar := range os.Args[1:] {
		Args = append(Args, singleVar)
	}

	// Make sure that at least one request was made.
	if len(Args) < 1 {
		showHelp()
	}

	// Parse command line arguments.
	for ii := 0; ii < len(Args); ii++ {
		var err error
		if !strings.HasPrefix(Args[ii], "-") {
			inPath, err = filepath.Abs(Args[ii])
			if err != nil {
				helpers.LogError(fmt.Sprintf("Input file (%s) cannot be accessed: %s", Args[ii], err.Error()))
				showHelp()
			}
			break
		}
		switch Args[ii] {
		case "-h":
			showHelp()
		case "-r":
			flagReplacement = true
		case "-c":
			ii += 1
			if Args[ii] == "space" {
				replByte = ' '
				break
			}
			if Args[ii] == "tab" {
				replByte = '\t'
				break
			}
			if Args[ii] == "nl" {
				replByte = '\n'
				break
			}
			if Args[ii] == "cr" {
				replByte = '\r'
				break
			}
			if strings.HasPrefix(Args[ii], "0x") {
				// Hexstring for one character: 0xn or 0xnn
				if len(Args[ii]) > 4 {
					helpers.LogError(fmt.Sprintf("-c %s value too large for one character", Args[ii]))
					showHelp()
				}
				decoded, err := hex.DecodeString(Args[ii][2:])
				if err != nil {
					helpers.LogError(fmt.Sprintf("-c %s is not hexidecimal (0xH or 0xHH where H is from 0123456789ABCDEFabcdef)", Args[ii]))
					showHelp()
				}
				replByte = decoded[0]
				if replByte == nilByte {
					helpers.LogWarning("-c 0x00 ---> invalid source characters will not be replaced but discarded (squeezed out)")
				}
				break
			}
			if len(Args[ii]) == 1 {
				replByte = Args[ii][0]
				break
			}
			helpers.LogError("Use -c to specify only ONE character")
			showHelp()

		default:
			helpers.LogError(fmt.Sprintf("Unrecognizable command option: %s", Args[ii]))
			showHelp()
		}
	}

	// Check for input file path specification.
	if inPath == "" {
		helpers.LogError("Input file specification required")
		showHelp()
	}

	// Read entire input file contents.
	inBytes, err := os.ReadFile(inPath)
	if err != nil {
		helpers.FmtFatal("os.ReadFile failed:", inPath, err)
	}
	msg := fmt.Sprintf("%d input characters", len(inBytes))
	helpers.Logger(msg)

	// Copy inBytes to outBytes.
	// Check outBytes for source code validity.
	// If a byte deviates, replace or discard it.
	var outBytes = inBytes
	numBytes := len(inBytes)
	numRepls := 0
	lineNum := 1
	offset := 0
	for ii := 0; ii < numBytes; ii++ {
		if outBytes[ii] == '\n' {
			lineNum += 1
			offset = 0
			continue
		}
		if !isSrcByte(outBytes[ii]) {
			// Not a valid source code byte.
			nastyOne := outBytes[ii]
			numRepls += 1
			if replByte == nilByte {
				// Discard byte.
				tempBytes := inBytes[:ii]
				outBytes = append(tempBytes, outBytes[ii+1:]...)
				numBytes -= 1
				fmt.Printf("\tLine %d, offset %d: discarded 0x%x\n", lineNum, offset, nastyOne)
			} else {
				// Replace byte.
				outBytes[ii] = replByte
				fmt.Printf("\tLine %d, offset %d: replaced 0x%x with 0x%x\n", lineNum, offset, nastyOne, replByte)
			}
		}
		offset += 1
	}

	// If clean input file, exit now.
	if numRepls < 1 {
		helpers.Logger("Clean input source code file")
		os.Exit(0)
	}

	// Report how many invalid source file characters were detected.
	msg = fmt.Sprintf("Detected %d invalid source file character(s)", numRepls)
	helpers.Logger(msg)

	// If not rewriting the input file, exit now.
	if !flagReplacement {
		helpers.Logger("The input source code file could be fixed but no input file rewrite was requested")
		os.Exit(1)
	}

	// Replace input file contents with outBytes.
	err = os.WriteFile(inPath, outBytes, 0666)
	if err != nil {
		helpers.FmtFatal("os.WriteFile failed", inPath, err)
	}
	msg = fmt.Sprintf("%d output characters", len(outBytes))
	helpers.Logger(msg)

}
