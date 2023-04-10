package main

import (
	"path/filepath"
	"runtime/debug"
	"strings"
	"fmt"
)

type GlobalsStruct struct {
	// ---- jacobin version number ----
	// note: all references to version number must come from this literal
	Version string
	DirTests string
	DirLogs string
	FlagStdout bool
	FlagVerbose bool
	Jvm string
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

func InitGlobals(jvm string, flagStdout bool, flagVerbose bool) GlobalsStruct {
    absdt, err1 := filepath.Abs("./tests")
    if err1 != nil {
        FmtFatal("InitGlobalsStruct error in accessing directory", "./tests", err1)
    }
    absdl, err2 := filepath.Abs("./logs")
    if err2 != nil {
        FmtFatal("InitGlobalsStruct error in accessing directory", "./logs", err2)
    }
	global = GlobalsStruct{
		Version:           "1.0",
		DirTests:          absdt,
		DirLogs:           absdl,
		FlagStdout:        flagStdout,
		FlagVerbose:       flagVerbose,
		Jvm:               jvm,
	}
	return global
}

// GetGlobalRef returns a pointer to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}

