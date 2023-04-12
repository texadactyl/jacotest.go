package main

import (
	"path/filepath"
	"runtime/debug"
	"strings"
	"fmt"
	"time"
)

// Software version
const VERSION = "1.0"

// Test Case Return Codes
const RC_NORMAL = 0
const RC_COMP_ERROR = 1
const RC_EXEC_ERROR = 2
const RC_EXEC_TIMEOUT = 3

// Path constants
const PATH_RUN_REPORT = "./RUN-REPORT-%s.md" // %s = JVM name
const PATH_LOGS = "./logs"
const PATH_TESTS = "./tests"

// Definition of the singleton global
type GlobalsStruct struct {
	// ---- jacobin version number ----
	// note: all references to version number must come from this literal
	Version string          // Software version string
	DirTests string         // Full path of tests directory
	DirLogs string          // Full path of logs directory
	FlagVerbose bool        // Verbose logging? true/false
	Jvm string              // "java" or "jacobin"
	Deadline time.Duration  // Run deadline in seconds (type time.Duration)
	ReportFilePath string  // Full path of the summary file
}

// Here's the singleton!
var global GlobalsStruct

// Show executable binary information relevant to "vcs"
func ShowExecInfo() {
    // Only interested in the "vcs." information
    fmt.Printf("Version: %s\n", global.Version)
	info, _ := debug.ReadBuildInfo()
	for ii := 0; ii < len(info.Settings); ii++ {
	    wkey := info.Settings[ii].Key
	    if strings.HasPrefix(wkey, "vcs.") {
		    fmt.Printf("BuildData %s: %s\n", wkey, info.Settings[ii].Value)
		}
	}
}

// Initialise the singleton global
func InitGlobals(jvm string, deadline_secs int, flagVerbose bool) GlobalsStruct {
    absTests, err1 := filepath.Abs(PATH_TESTS)
    if err1 != nil {
        FmtFatal("InitGlobals: filepath.Abs failed", PATH_TESTS, err1)
    }
    absLogs, err2 := filepath.Abs(PATH_LOGS)
    if err2 != nil {
        FmtFatal("InitGlobals: filepath.Abs failed", PATH_LOGS, err2)
    }
    absSummary, err3 := filepath.Abs(fmt.Sprintf(PATH_RUN_REPORT, jvm))
    if err3 != nil {
        FmtFatal("InitGlobals: filepath.Abs failed", PATH_RUN_REPORT, err2)
    }
    duration, err := time.ParseDuration(fmt.Sprintf("%ds", deadline_secs))
    if err != nil {
        FmtFatal("InitGlobals: time.ParseDuration failed", string(deadline_secs), err)
    }
	global = GlobalsStruct{
		Version:            VERSION,
		DirTests:           absTests,
		DirLogs:            absLogs,
		FlagVerbose:        flagVerbose,
		Jvm:                jvm,
		Deadline:           duration,
		ReportFilePath:    absSummary,           
	}
	return global
}

// GetGlobalRef returns a pointer to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}

