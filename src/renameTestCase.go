package main

import (
	"fmt"
	"os"
	"path/filepath"
)

func testCaseExists(curDir, name string) bool {
	path := curDir + string(os.PathSeparator) + "tests" + string(os.PathSeparator) + name

	// Exists?
	info, err := os.Stat(path)
	if err != nil {
		if os.IsNotExist(err) {
			return false
		} else {
			FatalErr(fmt.Sprintf("testCaseExists: os.Stat(%s) failed", path), err)
		}
	}

	// Is it a directory?
	if !info.IsDir() {
		return false
	}

	return true
}

func renameTestCaseDir(curDir, oldName, newName string) {
	oldPath := curDir + string(os.PathSeparator) + "tests" + string(os.PathSeparator) + oldName
	newLeaf := newName

	// Split into directory and leaf components.
	parentDir := filepath.Dir(oldPath)
	newPath := filepath.Join(parentDir, newLeaf)

	// Perform the rename.
	err := os.Rename(oldPath, newPath)
	if err != nil {
		FatalErr(fmt.Sprintf("renameTestCaseDir: os.Rename(%s --> %s) failed", oldPath, newPath), err)
	}
}

func renameTestCase(oldName, newName string) {
	curDir, err := os.Getwd()
	if err != nil {
		FatalErr("renameTestCase: os.Getwd failed", err)
	}

	if !testCaseExists(curDir, newName) {
		if !testCaseExists(curDir, oldName) {
			FatalText(fmt.Sprintf("renameTestCase: old test case name (%s) and new test case Name (%s) do not exist",
				oldName, newName))
		}
		// New name does not yet exist.
		// Old name does exist.
		// Perform rename.
		renameTestCaseDir(curDir, oldName, newName)
	}

	DBUpdateTestCaseName(oldName, newName)
}
