package helpers

import (
	"fmt"
	"os"
	"time"
)

// Time-stamp log function
func Logger(msg string) {
	now := time.Now()
	fmt.Printf("%s %s\n", now.Format("15:04:05"), msg)
}

// Log an error
func LogError(msg string) {
	text := fmt.Sprintf("*** ERROR :: %s", msg)
	Logger(text)
}

// Log a timeout
func LogTimeout(msg string) {
	text := fmt.Sprintf("*** TIMEOUT :: %s", msg)
	Logger(text)
}

// Log an error and croak
func Fatal(msg string) {
	text := fmt.Sprintf("*** FATAL :: %s", msg)
	Logger(text)
	os.Exit(1)
}

// Log an error in a special format and croak
// The name parameter is optional
func FmtFatal(msg string, name string, err error) {
	var text string
	if name == "" {
		text = fmt.Sprintf("*** FATAL, %s\n\t\t%s", msg, err.Error())
	} else {
		text = fmt.Sprintf("*** FATAL, %s\n\t\t%s\n\t\t%s", msg, name, err.Error())
	}
	Logger(text)
	os.Exit(1)
}

