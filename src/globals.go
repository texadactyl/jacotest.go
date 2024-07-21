package main

import (
	"fmt"
	"os"
	"path/filepath"
	"runtime"
	"runtime/debug"
	"strings"
	"time"
)

// Test Case Return Codes
const RC_NORMAL = 0
const RC_COMP_ERROR = 1
const RC_EXEC_ERROR = 2
const RC_EXEC_TIMEOUT = 3

// Path constants
const PATH_DIR_REPORTS = "./reports"
const TEMPLATE_RUN_REPORT = PATH_DIR_REPORTS + "/RUN_REPORT_%s_%s.md"
const TEMPLATE_SUMMARY_REPORT = PATH_DIR_REPORTS + "/Summary_%s_%s.txt"
const PATH_DIR_LOGS = "./logs"
const PATH_DIR_TESTS = "./tests"
const PATH_VERSION = "./VERSION.txt"
const PATH_ERROR_CATEGORIES = "./ERROR_CATEGORIES.txt"

// Definition of the singleton global
type GlobalsStruct struct {
	Version        string        // Software version string
	DirTests       string        // Full path of tests directory
	DirLogs        string        // Full path of logs directory
	LogFileExt     string        // .log
	DirReports     string        // Full path of reports directory
	ReportFilePath string        // Full path of the detailed report file
	SumFilePath    string        // Full path of the Summary file
	ErrCatFilePath string        // Full path of the error categories file
	FlagVerbose    bool          // Verbose logging? true/false
	JvmName        string        // JVM name: "openjdk" or "jacobin"
	JvmExe         string        // JVM executable file: "java" or "jacobin"
	Deadline       time.Duration // Run deadline in seconds (type time.Duration)
}

// Here's the singleton
var global GlobalsStruct

// Convert a UTC time string into a local one
func UTCTimeStr2LocalTimeStr(utcString string) string {
	timeStamp, err := time.Parse("2006-01-02T15:04:05Z07:00", utcString)
	if err != nil {
		return fmt.Sprintf("time.Parse error: %s", err.Error())
	}
	zone, _ := time.Now().Zone()
	return fmt.Sprintf("%s %s", timeStamp.Local().Format("2006-01-02 15:04:05"), zone)
}

// Show executable binary information relevant to "vcs"
func ShowExecInfo() {
	fmt.Printf("jacotest version: %s\n", global.Version)
	fmt.Printf("Built with: %s\n", runtime.Version())
	// Only interested in the "vcs." information
	info, _ := debug.ReadBuildInfo()
	for ii := 0; ii < len(info.Settings); ii++ {
		biKey := info.Settings[ii].Key
		biValue := info.Settings[ii].Value
		if strings.HasPrefix(biKey, "vcs.") {
			if biKey == "vcs.time" {
				biValue = UTCTimeStr2LocalTimeStr(biValue)
			}
			fmt.Printf("BuildData %s: %s\n", biKey, biValue)
		}
	}
}

// Initialise the singleton global
func InitGlobals(jvmName, jvmExe string, deadline_secs int, flagVerbose bool) GlobalsStruct {

	versionBytes, err := os.ReadFile(PATH_VERSION)
	if err != nil {
		FmtFatal("InitGlobals: ReadFile(PATH_VERSION) failed", PATH_VERSION, err)
	}

	versionString := string(versionBytes[:])
	versionString = strings.Trim(versionString, " ")
	absTests, err := filepath.Abs(PATH_DIR_TESTS)
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", PATH_DIR_TESTS, err)
	}

	absLogs, err := filepath.Abs(PATH_DIR_LOGS)
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", PATH_DIR_LOGS, err)
	}
	MakeDir(absLogs)

	absReports, err := filepath.Abs(PATH_DIR_REPORTS)
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", PATH_DIR_REPORTS, err)
	}
	MakeDir(absReports)

	nowString := time.Now().Format("2006-01-02_15.04.05")

	absReportFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_RUN_REPORT, nowString, jvmName))
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", TEMPLATE_RUN_REPORT, err)
	}

	absSummaryFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_SUMMARY_REPORT, nowString, jvmName))
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", TEMPLATE_RUN_REPORT, err)
	}

	absErrCatFile, err := filepath.Abs(PATH_ERROR_CATEGORIES)
	if err != nil {
		FmtFatal("InitGlobals: filepath.Abs failed", PATH_ERROR_CATEGORIES, err)
	}

	duration, err := time.ParseDuration(fmt.Sprintf("%ds", deadline_secs))
	if err != nil {
		FmtFatal("InitGlobals: time.ParseDuration failed", fmt.Sprintf("%d", deadline_secs), err)
	}
	global = GlobalsStruct{
		Version:        versionString,
		DirTests:       absTests,
		DirLogs:        absLogs,
		LogFileExt:     ".log",
		DirReports:     absReports,
		ReportFilePath: absReportFile,
		SumFilePath:    absSummaryFile,
		ErrCatFilePath: absErrCatFile,
		FlagVerbose:    flagVerbose,
		JvmExe:         jvmExe,
		JvmName:        jvmName,
		Deadline:       duration,
	}
	return global
}

// GetGlobalRef returns a pointer to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}
