package main

import (
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"utilities/helpers"
)

// Show help and then exit to the O/S
func showHelp() {
	suffix := filepath.Base(os.Args[0])
	fmt.Printf("\nUsage:  %s  [-h]  [N  input-table-file  output-CSV-file]\n\nwhere\n", suffix)
	fmt.Printf("\t-h : This display.\n")
	fmt.Printf("\tN = count of columns in the output CSV file = number of related whole lines in the input table file\n")
	fmt.Printf("\tinput-table-file: Every N lines correspond to N CSV columns.\n")
	fmt.Printf("\toutput-CSV-file: Each written line contains N CSV columns.\n")
	fmt.Printf("\nExit codes:\n")
	fmt.Printf("\t0\tNormal completion.\n")
	fmt.Printf("\t1\tSomething went wrong during execution.\n\n")
	os.Exit(1)
}

// Walk the base JMOD file and count the bytes for all classes/*.class entries
func main() {

	var separator = "\t"
	var Args []string
	var ncolumns int
	var inPath string
	var outPath string
	var inLines []string
	var outLines []string
	var msg string

	// Parse command line arguments.
	for _, singleVar := range os.Args[1:] {
		Args = append(Args, singleVar)
	}
	if len(Args) < 1 || Args[0] == "-h" {
		showHelp()
	}
	if len(Args) != 3 {
		helpers.LogError("Wrong number of command line parameters")
		showHelp()
	}
	ncolumns, err := strconv.Atoi(Args[0])
	if err != nil {
		helpers.LogError("The first command line parameter (number of columns) must be numeric")
		showHelp()
	}
	inPath = Args[1]
	outPath = Args[2]

	// Read entire file contents
	inBytes, err := os.ReadFile(inPath)
	if err != nil {
		helpers.FmtFatal("os.ReadFile failed:", inPath, err)
	}
	inLines = strings.Split(string(inBytes[:]), "\n")
	numInLines := len(inLines)
	msg = fmt.Sprintf("Read %d input lines from %s", numInLines, inPath)
	helpers.Logger(msg)

	// If numInLines is not a multiple of the number of columns,
	// then adjust the inLines array so that numInLines modulo the number of columns = 0.
	modder := numInLines % ncolumns
	if modder != 0 {
		for ii := 0; ii < ncolumns-modder; ii++ {
			inLines = append(inLines, " ")
		}
	}
	numInLines = len(inLines)

	// For each ncolumns inLines, append 1 outLines row.
	for ii := 0; ii < numInLines; ii += ncolumns {
		oneLine := ""
		for jj := 0; jj < ncolumns; jj++ {
			oneLine += inLines[ii+jj] + separator
		}
		oneLine = oneLine[:len(oneLine)] // truncate extraneous separator character
		outLines = append(outLines, oneLine)
	}

	// Write the output file.
	numOutLines := 0
	outFile, err := os.Create(outPath)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer outFile.Close()
	for ii := 0; ii < len(outLines); ii++ {
		_, err := outFile.WriteString(outLines[ii] + "\n")
		if err != nil {
			helpers.FmtFatal("WriteString failed", outPath, err)
		}
		numOutLines++
	}
	msg = fmt.Sprintf("Wrote %d output lines to %s", numOutLines, inPath)
	helpers.Logger(msg)

}
