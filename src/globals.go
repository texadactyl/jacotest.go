package main

import (
	"path/filepath"
	"runtime/debug"
	"strings"
	"fmt"
	"time"
)

type GlobalsStruct struct {
	// ---- jacobin version number ----
	// note: all references to version number must come from this literal
	Version string          // Software version string
	DirTests string         // Full path of tests directory
	DirLogs string          // Full path of logs directory
	FlagVerbose bool        // Verbose logging? true/false
	Jvm string              // "java" or "jacobin"
	Deadline time.Duration  // Run deadline in seconds (type time.Duration)
}

var global GlobalsStruct

func ShowExecInfo() {
    fmt.Printf("Version: %s\n", global.Version)
	info, _ := debug.ReadBuildInfo()
	for ii := 0; ii < len(info.Settings); ii++ {
	    wkey := info.Settings[ii].Key
	    if strings.HasPrefix(wkey, "vcs.") {
		    fmt.Printf("BuildData %s: %s\n", wkey, info.Settings[ii].Value)
		}
	}
}

func InitGlobals(jvm string, deadline_secs int, flagVerbose bool) GlobalsStruct {
    absTests, err1 := filepath.Abs("./tests")
    if err1 != nil {
        FmtFatal("InitGlobalsStruct error in accessing directory", "./tests", err1)
    }
    absLogs, err2 := filepath.Abs("./logs")
    if err2 != nil {
        FmtFatal("InitGlobalsStruct error in accessing directory", "./logs", err2)
    }
    duration, err := time.ParseDuration(fmt.Sprintf("%ds", deadline_secs))
    if err != nil {
        FmtFatal("time.ParseDuration failed", string(deadline_secs), err)
    }
	global = GlobalsStruct{
		Version:        "1.0",
		DirTests:       absTests,
		DirLogs:        absLogs,
		FlagVerbose:    flagVerbose,
		Jvm:            jvm,
		Deadline:       duration,           
	}
	return global
}

// GetGlobalRef returns a pointer to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}

