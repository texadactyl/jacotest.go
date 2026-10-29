package main

import (
	"errors"
	"os"
	"os/exec"
	"regexp"
	"strings"
	"testing"
	"time"
)

func TestLogger(t *testing.T) {
	out := captureOutput(t, func() {
		logger("hello %s %d", "world", 123)
	})

	// Expected format: "15:04:05 hello world 123\n"
	matched, err := regexp.MatchString(`^\d{2}:\d{2}:\d{2} hello world 123\n$`, out)
	if err != nil {
		t.Fatalf("regex error: %v", err)
	}
	if !matched {
		t.Errorf("logger output did not match expected pattern, got: %q", out)
	}

	// Verify timestamp is parseable as logTimeFormat
	parts := strings.SplitN(strings.TrimSuffix(out, "\n"), " ", 2)
	if len(parts) < 2 {
		t.Fatalf("expected at least 2 parts in output, got: %q", out)
	}
	if _, err := time.Parse(logTimeFormat, parts[0]); err != nil {
		t.Errorf("failed to parse log timestamp %q: %v", parts[0], err)
	}
	if parts[1] != "hello world 123" {
		t.Errorf("expected message 'hello world 123', got %q", parts[1])
	}
}

func TestLoggerSkip(t *testing.T) {
	out := captureOutput(t, func() {
		loggerSkip("skip message %d", 42)
	})

	// Expected format: "15:04:05 \nskip message 42\n"
	matched, err := regexp.MatchString(`^\d{2}:\d{2}:\d{2} \nskip message 42\n$`, out)
	if err != nil {
		t.Fatalf("regex error: %v", err)
	}
	if !matched {
		t.Errorf("loggerSkip output did not match expected pattern, got: %q", out)
	}
}

func TestLogWarning(t *testing.T) {
	out := captureOutput(t, func() {
		logWarning("warning message %s", "disk full")
	})

	if !strings.Contains(out, "*** WARNING :: ") {
		t.Errorf("expected output to contain warning prefix, got: %q", out)
	}
	if !strings.Contains(out, "disk full") {
		t.Errorf("expected output to contain message content, got: %q", out)
	}
}

func TestLogError(t *testing.T) {
	out := captureOutput(t, func() {
		logError("error message %d", 404)
	})

	if !strings.Contains(out, "*** ERROR :: ") {
		t.Errorf("expected output to contain error prefix, got: %q", out)
	}
	if !strings.Contains(out, "404") {
		t.Errorf("expected output to contain error code, got: %q", out)
	}
}

func TestLogTimeout(t *testing.T) {
	out := captureOutput(t, func() {
		logTimeout("timed out after %d seconds", 30)
	})

	if !strings.Contains(out, "*** TIMEOUT :: ") {
		t.Errorf("expected output to contain timeout prefix, got: %q", out)
	}
	if !strings.Contains(out, "30 seconds") {
		t.Errorf("expected output to contain timeout details, got: %q", out)
	}
}

func TestLogSkip(t *testing.T) {
	out := captureOutput(t, func() {
		logSkip()
	})

	// Expected format: "15:04:05 \n"
	matched, err := regexp.MatchString(`^\d{2}:\d{2}:\d{2} \n$`, out)
	if err != nil {
		t.Fatalf("regex error: %v", err)
	}
	if !matched {
		t.Errorf("logSkip output did not match expected pattern, got: %q", out)
	}
}

func TestFatalProcessHelper(t *testing.T) {
	switch os.Getenv("TEST_CRASHER") {
	case "fatalText":
		fatalText("fatal failure %s", "aborting")
	case "fatalErr":
		fatalErr(errors.New("connection reset"), "fatal err context %d", 100)
	default:
		return
	}
}

func TestFatalText(t *testing.T) {
	cmd := exec.Command(os.Args[0], "-test.run=TestFatalProcessHelper")
	cmd.Env = append(os.Environ(), "TEST_CRASHER=fatalText")
	out, err := cmd.CombinedOutput()

	exitErr, ok := errors.AsType[*exec.ExitError](err)
	if !ok || exitErr.ExitCode() != 1 {
		t.Fatalf("expected exit code 1, got error: %v, output: %s", err, string(out))
	}

	outStr := string(out)
	if !strings.Contains(outStr, "*** FATAL :: ") {
		t.Errorf("expected output to contain fatal prefix, got: %q", outStr)
	}
	if !strings.Contains(outStr, "fatal failure aborting") {
		t.Errorf("expected output to contain fatal message, got: %q", outStr)
	}
}

func TestFatalErr(t *testing.T) {
	cmd := exec.Command(os.Args[0], "-test.run=TestFatalProcessHelper")
	cmd.Env = append(os.Environ(), "TEST_CRASHER=fatalErr")
	out, err := cmd.CombinedOutput()

	exitErr, ok := errors.AsType[*exec.ExitError](err)
	if !ok || exitErr.ExitCode() != 1 {
		t.Fatalf("expected exit code 1, got error: %v, output: %s", err, string(out))
	}

	outStr := string(out)
	if !strings.Contains(outStr, "*** FATAL :: fatal err context 100") {
		t.Errorf("expected output to contain formatted message, got: %q", outStr)
	}
	if !strings.Contains(outStr, "\terr: connection reset") {
		t.Errorf("expected output to contain error detail, got: %q", outStr)
	}
}
