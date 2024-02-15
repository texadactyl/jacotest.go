package main

import (
	"bufio"
	"fmt"
	"os"
	"os/exec"
	"strings"
	"syscall"
	"utilities/helpers"
)

const PATH_VERSION = "./VERSION.txt"

// Runs in a separate Go thread.
// Reads every line until EOF or an error.
func outputLineReader(reader *bufio.Reader) {
	for {
		str, err := reader.ReadString('\n')
		if err != nil {
			if len(str) > 0 {
				fmt.Print(str)
			}
			break
		}
		fmt.Print(str)
	}
}

// runner - Run a subprocess.
func runner(newdir string, cmdString string) {

	curwd, err := os.Getwd()
	if err != nil {
		helpers.LogError(fmt.Sprintf("runner: os.Getwd failed. Output: %s", err.Error()))
		os.Exit(1)
	}

	// Change directory before execution?
	if newdir == "" { // No
		helpers.Logger(fmt.Sprintf("runner cmd: %s", cmdString))
	} else { // Yes
		helpers.Logger(fmt.Sprintf("runner dir: %s, cmd: %s", newdir, cmdString))
		err := os.Chdir(newdir)
		if err != nil {
			helpers.LogError(fmt.Sprintf("runner: os.Chdir(newdir) failed. Output: %s", err.Error()))
			os.Exit(1)
		}
	}

	// Split command string into a named command and its arguments.
	cmdArr := strings.Split(cmdString, " ")
	name := cmdArr[0]
	args := []string{}
	if len(cmdArr) > 1 {
		args = cmdArr[1:]
	}

	// Construct command.
	command := exec.Command(name, args...)
	command.Env = os.Environ()

	// Attach to command's stdout.
	stdout, err := command.StdoutPipe()
	if err != nil {
		helpers.LogError(fmt.Sprintf("command.StdoutPipe failed, err: %s", err.Error()))
		os.Exit(1)
	}
	defer stdout.Close()
	stdoutReader := bufio.NewReader(stdout)

	// Attach to command's stderr.
	stderr, err := command.StderrPipe()
	if err != nil {
		helpers.LogError(fmt.Sprintf("command.StderrPipe failed, err: %s", err.Error()))
		os.Exit(1)
	}
	defer stderr.Close()
	stderrReader := bufio.NewReader(stderr)

	// Start command.
	if err := command.Start(); err != nil {
		helpers.LogError(fmt.Sprintf("command.Start failed, err: %s", err.Error()))
		os.Exit(1)
	}

	// Start stdout and stderr readers as separate threads.
	go outputLineReader(stdoutReader)
	go outputLineReader(stderrReader)

	// Wait for finish.
	if err := command.Wait(); err != nil {
		if exiterr, ok := err.(*exec.ExitError); ok {
			if status, ok := exiterr.Sys().(syscall.WaitStatus); ok {
				helpers.LogError(fmt.Sprintf("Exit Status: %d", status.ExitStatus()))
				os.Exit(1)
			}
		}
	}

	// Go back original working directory.
	err = os.Chdir(curwd)
	if err != nil {
		helpers.LogError(fmt.Sprintf("runner: os.Chdir(newdir) failed. Output: %s", err.Error()))
		os.Exit(1)
	}

}

// Main function.
func main() {

	// Make sure that we are positioned in the right directory.
	_, err := os.ReadFile(PATH_VERSION)
	if err != nil {
		helpers.LogError("ReadFile(VERSION.txt) failed. Wrong directory?")
		os.Exit(1)
	}

	// Make sure that there are no command-line arguments.
	var Args []string
	for _, singleVar := range os.Args[1:] {
		Args = append(Args, singleVar)
	}
	if len(Args) > 0 {
		fmt.Println("No arguments!")
		os.Exit(1)
	}

	runner("", "git pull")
	runner("src", "go install -v ./...")
	runner("utilities", "go install -v ./...")
	runner("", "jacotest -c")

}
