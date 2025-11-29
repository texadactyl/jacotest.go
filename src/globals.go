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
const PATH_DIR_HELPERS = "./tests/HELPERS"
const PATH_VERSION = "./VERSION.txt"
const PATH_ERROR_CATEGORIES = "./ERROR_CATEGORIES.txt"

// Definition of the singleton global
type GlobalsStruct struct {
	Version              string        // Software version string
	DirTests             string        // Full path of tests directory
	DirHelpers           string        // Full path of tests/HELPERS directory
	DirLogs              string        // Full path of logs directory
	LogFileExt           string        // .log
	DirReports           string        // Full path of reports directory
	ReportFilePath       string        // Full path of the detailed report file
	SumFilePath          string        // Full path of the Summary file
	DirGA                string        // Full path of the GithubActions directory
	PassfailFilePath     string        // Full path of the passfail file
	ErrCatFilePath       string        // Full path of the error categories file
	FlagVerbose          bool          // Verbose logging? true/false
	FlagGalt             bool          // JVM Jacobin is run in G-alternate mode
	FlagCompile          bool          // -c option
	FlagDeleteObsolete   bool          // -s option
	FlagMdReport         bool          // -M option
	FlagTwoMostRecent    bool          // -r 1 option
	FlagFailedPassed     bool          // -r 2 option
	FlagPrintMostRecent  bool          // -r 3 option
	FlagExecute          bool          // -x option
	FlagDeleteMostRecent bool          // -z option
	FlagReportOrphans    bool          // -N option (report orphans)
	JvmName              string        // JVM name: "openjdk" or "jacobin"
	JvmExe               string        // JVM executable file: "java" or "jacobin"
	Deadline             time.Duration // Run deadline in seconds (type time.Duration)
	JvmOptPrefix         string        // JVM execution options prefix
	JavacOptPrefix       string        // Java compiler options prefix
	UserXopts            string        // User-defined options when -x is specified
}

// Here's the singleton
var global GlobalsStruct

// GetGlobalRef returns a reference to the singleton instance of GlobalsStruct
func GetGlobalRef() *GlobalsStruct {
	return &global
}

func ckPath(argPath string) string {
	absPath, err := filepath.Abs(argPath)
	if err != nil {

		FatalErr(fmt.Sprintf("InitGlobals:ckPath: filepath.Abs(%s) failed", argPath), err)
	}
	info, err := os.Stat(absPath)
	if err != nil {
		if os.IsNotExist(err) {
			FatalText(fmt.Sprintf("InitGlobals:ckPath: Cannot find path (%s)", absPath))
		} else {
			FatalErr(fmt.Sprintf("InitGlobals:ckPath: os.Stat (%s) failed", absPath), err)
		}
	}
	if !info.IsDir() {
		FatalText(fmt.Sprintf("InitGlobals:ckPath: Path (%s) exists but is not a directory", absPath))
	}

	//Logger(fmt.Sprintf("InitGlobals:ckPath: argPath=%s, absPath=%s", argPath, absPath))
	return absPath
}

// Initialise the singleton global
func InitGlobals(jvmName, jvmExe string, deadline_secs int, userXopts string) *GlobalsStruct {

	versionBytes, err := os.ReadFile(PATH_VERSION)
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: ReadFile(%s) failed", PATH_VERSION), err)
	}

	versionString := string(versionBytes[:])
	versionString = strings.Trim(versionString, " ")

	absTests := ckPath(PATH_DIR_TESTS)
	absHelpers := ckPath(PATH_DIR_HELPERS)

	absLogs, err := filepath.Abs(PATH_DIR_LOGS)
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", PATH_DIR_LOGS), err)
	}
	MakeDir(absLogs)

	absReports, err := filepath.Abs(PATH_DIR_REPORTS)
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", PATH_DIR_REPORTS), err)
	}
	MakeDir(absReports)

	nowString := time.Now().Format("2006-01-02_15.04.05")

	absReportFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_RUN_REPORT, nowString, jvmName))
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", TEMPLATE_RUN_REPORT), err)
	}

	absSummaryFile, err := filepath.Abs(fmt.Sprintf(TEMPLATE_SUMMARY_REPORT, nowString, jvmName))
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", TEMPLATE_RUN_REPORT), err)
	}

	global.PassfailFilePath = absLogs + string(os.PathSeparator) + "passfail.txt"
	absPassfailFile, err := filepath.Abs(global.PassfailFilePath)
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", global.PassfailFilePath), err)
	}

	absErrCatFile, err := filepath.Abs(PATH_ERROR_CATEGORIES)
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: filepath.Abs(%s) failed", PATH_ERROR_CATEGORIES), err)
	}

	duration, err := time.ParseDuration(fmt.Sprintf("%ds", deadline_secs))
	if err != nil {
		FatalErr(fmt.Sprintf("InitGlobals: time.ParseDuration(%d seconds) failed", deadline_secs), err)
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
		PassfailFilePath:     absPassfailFile,
		ErrCatFilePath:       absErrCatFile,
		FlagVerbose:          false,
		FlagGalt:             false,
		FlagExecute:          false,
		FlagTwoMostRecent:    false,
		FlagPrintMostRecent:  false,
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
	}

	global.JavacOptPrefix = "-Xlint:all -Werror"

	if global.JvmName == "jacobin" {
		global.JvmOptPrefix = "-ea"
		if global.FlagGalt {
			global.JvmOptPrefix += " -JJ:galt"
		}
	} else {
		global.JvmOptPrefix = "-ea -server"
	}

	return &global
}

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
