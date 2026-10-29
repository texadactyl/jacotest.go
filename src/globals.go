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

// RC_* - Test Case Return Codes:
const RC_NORMAL = 0
const RC_COMP_ERROR = 1
const RC_EXEC_ERROR = 2
const RC_EXEC_TIMEOUT = 3

// The path constants follow:
const PATH_DIR_REPORTS = "./reports"
const TEMPLATE_RUN_REPORT = PATH_DIR_REPORTS + "/RUN_REPORT_%s_%s.md"
const TEMPLATE_SUMMARY_REPORT = PATH_DIR_REPORTS + "/Summary_%s_%s.txt"
const PATH_DIR_LOGS = "./logs"
const PATH_DIR_TESTS = "./tests"
const PATH_DIR_HELPERS = "./tests/HELPERS"
const PATH_VERSION = "./VERSION.cfg"
const PATH_ERROR_CATEGORIES = "./ERROR_CATEGORIES.cfg"

// GlobalsStruct - Definition of the singleton global
type GlobalsStruct struct {
	Version              string        // Software version string
	DirTests             string        // Full path of tests directory
	DirHelpers           string        // Full path of tests/HELPERS directory
	DirLogs              string        // Full path of the logs directory
	LogFileExt           string        // .log
	DirReports           string        // Full path of the reports directory
	ReportFilePath       string        // Full path of the detailed report file
	SumFilePath          string        // Full path of the Summary file
	DirGA                string        // Full path of the Github Actions directory
	PassFailFilePath     string        // Full path of the pass/fail file
	ErrCatFilePath       string        // Full path of the error categories file
	FlagVerbose          bool          // Verbose logging? true/false
	FlagCompile          bool          // -c option
	FlagDeleteObsolete   bool          // -s option
	FlagMdReport         bool          // -M option
	FlagTwoMostRecent    bool          // -r 1 option
	FlagFailedPassed     bool          // -r 2 option
	FlagPrintMostRecent  bool          // -r 3 option
	FlagClassChecksum    bool          // -r 4 option
	FlagExecute          bool          // -x option
	FlagDeleteMostRecent bool          // -z option
	FlagReportOrphans    bool          // -N option (report orphans)
	JvmName              string        // JVM name: "openjdk" or "jacobin"
	JvmExe               string        // JVM executable file: "java" or "jacobin"
	Deadline             time.Duration // Run deadline in seconds (type time.Duration)
	JvmOptPrefix         string        // JVM execution options prefix
	JavacOptPrefix       string        // Java compiler options prefix
	UserXopts            string        // User-defined options when -x is specified
	TempFile             *os.File      // Temp file for capturing allocated memory
	EnvTempFile          string        // Environment variable with temp file name
}

// Here's the GlobalsStruct singleton.
var global GlobalsStruct

// GetGlobalRef returns a reference to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}

func ckPath(argPath string) string {
	absPath, err := filepath.Abs(argPath)
	if err != nil {
		fatalErr(err, "InitGlobals:ckPath: filepath.Abs(%s) failed", argPath)
	}
	info, err := os.Stat(absPath)
	if err != nil {
		if os.IsNotExist(err) {
			fatalText("InitGlobals:ckPath: Cannot find path (%s)", absPath)
		} else {
			fatalErr(err, "InitGlobals:ckPath: os.Stat (%s) failed", absPath)
		}
	}
	if !info.IsDir() {
		fatalText("InitGlobals:ckPath: Path (%s) exists but is not a directory", absPath)
	}

	//logger("InitGlobals:ckPath: argPath=%s, absPath=%s", argPath, absPath)
	return absPath
}

// InitGlobals - Initialize the singleton global
func InitGlobals(jvmName, jvmExe string, deadline_secs int, userXopts string) *GlobalsStruct {

	versionBytes, err := os.ReadFile(PATH_VERSION)
	if err != nil {
		fatalErr(err, "InitGlobals: ReadFile(%s) failed", PATH_VERSION)
	}

	versionString := string(versionBytes[:len(versionBytes)-1])
	versionString = strings.Trim(versionString, " ")

	absTests := ckPath(PATH_DIR_TESTS)
	absHelpers := ckPath(PATH_DIR_HELPERS)

	absLogs, err := filepath.Abs(PATH_DIR_LOGS)
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", PATH_DIR_LOGS)
	}
	makeDir(absLogs)

	absReports, err := filepath.Abs(PATH_DIR_REPORTS)
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", PATH_DIR_REPORTS)
	}
	makeDir(absReports)

	nowString := time.Now().Format("2006-01-02_15.04.05")

	absReportFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_RUN_REPORT, nowString, jvmName))
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", TEMPLATE_RUN_REPORT)
	}

	absSummaryFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_SUMMARY_REPORT, nowString, jvmName))
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", TEMPLATE_RUN_REPORT)
	}

	global.PassFailFilePath = absLogs + string(os.PathSeparator) + "passfail.txt"
	absPassfailFile, err := filepath.Abs(global.PassFailFilePath)
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", global.PassFailFilePath)
	}

	absErrCatFile, err := filepath.Abs(PATH_ERROR_CATEGORIES)
	if err != nil {
		fatalErr(err, "InitGlobals: filepath.Abs(%s) failed", PATH_ERROR_CATEGORIES)
	}

	duration, err := time.ParseDuration(fmt.Sprintf("%ds", deadline_secs))
	if err != nil {
		fatalErr(err, "InitGlobals: time.ParseDuration(%d seconds) failed", deadline_secs)
	}
	global = GlobalsStruct{
		Version:              versionString,
		DirTests:             absTests,
		DirHelpers:           absHelpers,
		DirLogs:              absLogs,
		LogFileExt:           ".log",
		DirReports:           absReports,
		ReportFilePath:       absReportFile,
		SumFilePath:          absSummaryFile,
		PassFailFilePath:     absPassfailFile,
		ErrCatFilePath:       absErrCatFile,
		FlagVerbose:          false,
		FlagExecute:          false,
		FlagTwoMostRecent:    false,
		FlagPrintMostRecent:  false,
		FlagClassChecksum:    false,
		FlagDeleteMostRecent: false,
		FlagDeleteObsolete:   false,
		FlagFailedPassed:     false,
		FlagCompile:          false,
		FlagMdReport:         false,
		FlagReportOrphans:    false,
		JvmExe:               jvmExe,
		JvmName:              jvmName,
		Deadline:             duration,
		UserXopts:            userXopts,
		EnvTempFile:          "JACOTEST_ALLOC_FILE",
	}

	global.JavacOptPrefix = "-Xlint:all -Werror"

	if global.JvmName == "jacobin" {
		global.JvmOptPrefix = "-ea"
	} else {
		global.JvmOptPrefix = "-ea -server"
	}

	// Create the temp file for capturing memory allocation and then close it.
	global.TempFile, err = os.CreateTemp("", "jacotest-alloc-*")
	if err != nil {
		fatalErr(err, "InitGlobals: cannot create jacotest-alloc file")
	}
	err = os.Setenv(global.EnvTempFile, global.TempFile.Name())
	if err != nil {
		fatalErr(err, "InitGlobals: cannot set environment variable for jacotest-alloc file")
	}
	err = global.TempFile.Close()
	if err != nil {
		fatalErr(err, "InitGlobals: cannot close jacotest-alloc file")
	}
	return &global
}

// ShowExecInfo - Show executable binary information relevant to "vcs"
func ShowExecInfo(name string) {
	fmt.Printf("%s version: %s\n", name, global.Version)
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
