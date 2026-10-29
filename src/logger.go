package main

import (
	"fmt"
	"os"
	"time"
)

const logTimeFormat = "15:04:05"

// logger - Time-stamp log function.
func logger(format string, arg ...any) {
	now := time.Now()
	msg := fmt.Sprintf(format, arg...)
	_, _ = fmt.Fprintf(os.Stdout, "%s %s\n", now.Format(logTimeFormat), msg)
	_ = os.Stdout.Sync()
}

// loggerSkip - Time-stamp log function with a preceding newline for readability.
func loggerSkip(format string, arg ...any) {
	logger("\n%s", fmt.Sprintf(format, arg...))
}

// logWarning - Log a warning.
func logWarning(format string, arg ...any) {
	logger("*** WARNING :: "+format, arg...)
}

// logError - Log an error.
func logError(format string, arg ...any) {
	logger("*** ERROR :: "+format, arg...)
}

// logTimeout - Log a timeout.
func logTimeout(format string, arg ...any) {
	logger("*** TIMEOUT :: "+format, arg...)
}

// logSkip - Skip a line for report readability.
func logSkip() {
	logger("")
}

// fatalText - Log a fatal error message and exit.
func fatalText(format string, arg ...any) {
	logger("*** FATAL :: "+format, arg...)
	DBClose()
	os.Exit(1)
}

// fatalErr - Log a fatal error message with error text and exit.
func fatalErr(err error, format string, arg ...any) {
	msg := fmt.Sprintf(format, arg...)
	logger("*** FATAL :: %s\n\terr: %s", msg, err.Error())
	DBClose()
	os.Exit(1)
}
