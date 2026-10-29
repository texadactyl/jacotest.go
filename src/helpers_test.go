package main

import (
	"crypto/sha256"
	"errors"
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"regexp"
	"strings"
	"testing"
	"time"
)

func TestGetUtcDate(t *testing.T) {
	dateStr := getUtcDate()
	matched, err := regexp.MatchString(`^\d{4}-\d{2}-\d{2}$`, dateStr)
	if err != nil {
		t.Fatalf("regex match failed: %v", err)
	}
	if !matched {
		t.Errorf("getUtcDate() returned invalid format: %q", dateStr)
	}
	if _, err := time.Parse("2006-01-02", dateStr); err != nil {
		t.Errorf("failed to parse getUtcDate() result %q: %v", dateStr, err)
	}
}

func TestGetUtcTime(t *testing.T) {
	timeStr := getUtcTime()
	matched, err := regexp.MatchString(`^\d{2}:\d{2}:\d{2}\.\d{3}$`, timeStr)
	if err != nil {
		t.Fatalf("regex match failed: %v", err)
	}
	if !matched {
		t.Errorf("getUtcTime() returned invalid format: %q", timeStr)
	}
	if _, err := time.Parse("15:04:05.000", timeStr); err != nil {
		t.Errorf("failed to parse getUtcTime() result %q: %v", timeStr, err)
	}
}

func TestWriteOutputText(t *testing.T) {
	tmpDir := t.TempDir()
	filePath := filepath.Join(tmpDir, "output.txt")
	f, err := os.OpenFile(filePath, FlagsOpen, ModeOutputFile)
	if err != nil {
		t.Fatalf("os.OpenFile failed: %v", err)
	}

	writeOutputText(f, "test output line")
	_ = f.Close()

	content, err := os.ReadFile(filePath)
	if err != nil {
		t.Fatalf("os.ReadFile failed: %v", err)
	}
	expected := "test output line\n"
	if string(content) != expected {
		t.Errorf("expected %q, got %q", expected, string(content))
	}
}

func TestMakeDir(t *testing.T) {
	tmpDir := t.TempDir()
	newDir := filepath.Join(tmpDir, "subdir")

	// 1. Create new directory
	makeDir(newDir)
	isDir, err := isDirectory(newDir)
	if err != nil {
		t.Fatalf("isDirectory failed: %v", err)
	}
	if !isDir {
		t.Errorf("expected directory to be created: %s", newDir)
	}

	// 2. Call makeDir again on existing directory
	makeDir(newDir)
	isDir, err = isDirectory(newDir)
	if err != nil || !isDir {
		t.Errorf("directory should still exist: %s", newDir)
	}
}

func TestHelpersProcessHelper(t *testing.T) {
	switch os.Getenv("TEST_HELPERS_CRASHER") {
	case "makeDir_existingFile":
		filePath := os.Getenv("TEST_CRASH_TARGET")
		makeDir(filePath)
	case "sha256Hex_nonExistent":
		filePath := os.Getenv("TEST_CRASH_TARGET")
		_, _ = sha256Hex(filePath)
	default:
		return
	}
}

func TestMakeDirExistingFile(t *testing.T) {
	tmpDir := t.TempDir()
	filePath := filepath.Join(tmpDir, "plain_file.txt")
	if err := os.WriteFile(filePath, []byte("some content"), 0644); err != nil {
		t.Fatalf("os.WriteFile failed: %v", err)
	}

	cmd := exec.Command(os.Args[0], "-test.run=TestHelpersProcessHelper")
	cmd.Env = append(os.Environ(), "TEST_HELPERS_CRASHER=makeDir_existingFile", "TEST_CRASH_TARGET="+filePath)
	out, err := cmd.CombinedOutput()

	exitErr, ok := errors.AsType[*exec.ExitError](err)
	if !ok || exitErr.ExitCode() != 1 {
		t.Fatalf("expected exit code 1, got error: %v, output: %s", err, string(out))
	}

	outStr := string(out)
	if !strings.Contains(outStr, "Observed a simple file") {
		t.Errorf("expected fatal output to mention observed simple file, got: %q", outStr)
	}
}

func TestSha256Hex(t *testing.T) {
	tests := []struct {
		name     string
		content  []byte
		expected string
	}{
		{
			name:     "empty file",
			content:  []byte(""),
			expected: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
		},
		{
			name:     "simple text",
			content:  []byte("hello world"),
			expected: "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9",
		},
		{
			name:     "multi-line content",
			content:  []byte("line 1\nline 2\nline 3\n"),
			expected: fmt.Sprintf("%x", sha256.Sum256([]byte("line 1\nline 2\nline 3\n"))),
		},
		{
			name:     "binary bytes",
			content:  []byte{0x00, 0xff, 0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc, 0xde, 0xf0},
			expected: fmt.Sprintf("%x", sha256.Sum256([]byte{0x00, 0xff, 0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc, 0xde, 0xf0})),
		},
	}

	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			tmpDir := t.TempDir()
			filePath := filepath.Join(tmpDir, "test_file.bin")
			if err := os.WriteFile(filePath, tc.content, 0644); err != nil {
				t.Fatalf("os.WriteFile failed: %v", err)
			}

			hexSum, err := sha256Hex(filePath)
			if err != nil {
				t.Fatalf("sha256Hex returned unexpected error: %v", err)
			}
			if hexSum != tc.expected {
				t.Errorf("sha256Hex(%s) = %q, want %q", tc.name, hexSum, tc.expected)
			}
		})
	}
}

func TestSha256HexNonExistentFile(t *testing.T) {
	tmpDir := t.TempDir()
	filePath := filepath.Join(tmpDir, "non_existent_file.bin")

	cmd := exec.Command(os.Args[0], "-test.run=TestHelpersProcessHelper")
	cmd.Env = append(os.Environ(), "TEST_HELPERS_CRASHER=sha256Hex_nonExistent", "TEST_CRASH_TARGET="+filePath)
	out, err := cmd.CombinedOutput()

	exitErr, ok := errors.AsType[*exec.ExitError](err)
	if !ok || exitErr.ExitCode() != 1 {
		t.Fatalf("expected exit code 1, got error: %v, output: %s", err, string(out))
	}

	outStr := string(out)
	if !strings.Contains(outStr, "sha256Hex, os.Open") {
		t.Errorf("expected fatal output to mention os.Open failure, got: %q", outStr)
	}
}

func TestStoreText(t *testing.T) {
	tmpDir := t.TempDir()
	fileName := "saved_text.txt"
	textContent := "Hello from storeText!"

	storeText(tmpDir, fileName, textContent)

	fullPath := filepath.Join(tmpDir, fileName)
	data, err := os.ReadFile(fullPath)
	if err != nil {
		t.Fatalf("os.ReadFile failed: %v", err)
	}

	expected := textContent + "\n"
	if string(data) != expected {
		t.Errorf("expected %q, got %q", expected, string(data))
	}
}

func TestCleanerText(t *testing.T) {
	tests := []struct {
		name     string
		input    string
		newlines bool
		expected string
	}{
		{
			name:     "newlines true with non-printable chars",
			input:    "Hello\x00World\x07!\nSecond\rLine",
			newlines: true,
			expected: "Hello World !\nSecond\rLine",
		},
		{
			name:     "newlines false removes non-printable and newlines",
			input:    "Hello\x00World\x07!\nSecond\rLine",
			newlines: false,
			expected: "HelloWorld!SecondLine",
		},
		{
			name:     "all printable text remains unchanged",
			input:    "Normal printable text 123.",
			newlines: true,
			expected: "Normal printable text 123.",
		},
		{
			name:     "empty string",
			input:    "",
			newlines: true,
			expected: "",
		},
	}

	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			result := cleanerText(tc.input, tc.newlines)
			if result != tc.expected {
				t.Errorf("cleanerText(%q, %v) = %q; want %q", tc.input, tc.newlines, result, tc.expected)
			}
		})
	}
}

func TestIsDirectory(t *testing.T) {
	tmpDir := t.TempDir()

	// 1. Directory exists
	isDir, err := isDirectory(tmpDir)
	if err != nil {
		t.Fatalf("unexpected error for directory: %v", err)
	}
	if !isDir {
		t.Errorf("expected true for existing directory %s", tmpDir)
	}

	// 2. Regular file exists
	filePath := filepath.Join(tmpDir, "file.txt")
	if err := os.WriteFile(filePath, []byte("data"), 0644); err != nil {
		t.Fatalf("os.WriteFile failed: %v", err)
	}
	isDir, err = isDirectory(filePath)
	if err != nil {
		t.Fatalf("unexpected error for file: %v", err)
	}
	if isDir {
		t.Errorf("expected false for regular file %s", filePath)
	}

	// 3. Path does not exist
	nonExistent := filepath.Join(tmpDir, "does_not_exist")
	isDir, err = isDirectory(nonExistent)
	if err != nil {
		t.Fatalf("unexpected error for non-existent path: %v", err)
	}
	if isDir {
		t.Errorf("expected false for non-existent path %s", nonExistent)
	}
}

func TestFormatElapsedTime(t *testing.T) {
	tests := []struct {
		msecs    int
		expected string
	}{
		{msecs: 0, expected: "0 msec"},
		{msecs: 456, expected: "456 msec"},
		{msecs: 999, expected: "999 msec"},
		{msecs: 1000, expected: "1.000 sec"},
		{msecs: 3456, expected: "3.456 sec"},
		{msecs: 59999, expected: "59.999 sec"},
		{msecs: 60000, expected: "00:01:00.000"},
		{msecs: 63456, expected: "00:01:03.456"},
		{msecs: 3723456, expected: "01:02:03.456"},
		{msecs: 86400000, expected: "24:00:00.000"},
	}

	for _, tc := range tests {
		t.Run(tc.expected, func(t *testing.T) {
			result := formatElapsedTime(tc.msecs)
			if result != tc.expected {
				t.Errorf("formatElapsedTime(%d) = %q; want %q", tc.msecs, result, tc.expected)
			}
		})
	}
}

func TestDeferredRemoveFile(t *testing.T) {
	tmpDir := t.TempDir()
	filePath := filepath.Join(tmpDir, "remove_me.txt")
	if err := os.WriteFile(filePath, []byte("temp"), 0644); err != nil {
		t.Fatalf("os.WriteFile failed: %v", err)
	}

	// Successful removal should produce no error output
	out := captureOutput(t, func() {
		deferredRemoveFile(filePath)
	})
	if out != "" {
		t.Errorf("expected no output on successful remove, got: %q", out)
	}
	if _, err := os.Stat(filePath); !os.IsNotExist(err) {
		t.Errorf("file should have been removed: %s", filePath)
	}

	// Removing non-existent file should log error
	out = captureOutput(t, func() {
		deferredRemoveFile(filePath)
	})
	if !strings.Contains(out, "*** ERROR, deferredRemoveFile: os.Remove(") {
		t.Errorf("expected error output on removing missing file, got: %q", out)
	}
}

func TestDeferredCloseFile(t *testing.T) {
	tmpDir := t.TempDir()
	filePath := filepath.Join(tmpDir, "close_me.txt")
	f, err := os.Create(filePath)
	if err != nil {
		t.Fatalf("os.Create failed: %v", err)
	}

	// First close succeeds with no output
	out := captureOutput(t, func() {
		deferredCloseFile(f)
	})
	if out != "" {
		t.Errorf("expected no output on successful close, got: %q", out)
	}

	// Second close on closed file should log error
	out = captureOutput(t, func() {
		deferredCloseFile(f)
	})
	if !strings.Contains(out, "*** ERROR, deferredCloseFile:") {
		t.Errorf("expected error output on closing already closed file, got: %q", out)
	}
}

func TestSortCaseInsensitive(t *testing.T) {
	slice := []string{"banana", "Apple", "cherry", "APPLE", "Banana"}
	sortCaseInsensitive(&slice)

	expected := []string{"Apple", "APPLE", "banana", "Banana", "cherry"}
	for i := range slice {
		if strings.ToLower(slice[i]) != strings.ToLower(expected[i]) {
			t.Errorf("sortCaseInsensitive mismatch at index %d: got %q, want case-insensitive %q", i, slice[i], expected[i])
		}
	}
}

func TestCaptureOutput(t *testing.T) {
	captured := captureOutput(t, func() {
		fmt.Print("captured test output")
	})
	if captured != "captured test output" {
		t.Errorf("captureOutput failed: got %q, want %q", captured, "captured test output")
	}
}

func TestSetupUnitTest(t *testing.T) {
	setupUnitTest(t)

	if !dbIsOpen {
		t.Error("expected dbIsOpen to be true after setupUnitTest")
	}
	if dbHandle == nil {
		t.Error("expected dbHandle to not be nil after setupUnitTest")
	}
}
