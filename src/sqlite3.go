package main

/*
Downloads + installs:
* go get github.com/mattn/go-sqlite3
* go install -v ./...
* https://www.sqlite.org/download.html (API for the Browser)
* https://sqlitebrowser.org/dl/        (Browser)
*/

import (
	"database/sql"
	"fmt"
	_ "modernc.org/sqlite"
	"os"
	"time"
)

var sqlTracing = false

const msecsSleep = 1000

// History table
const tableHistory = "history"

// History table columns
const colTestCase = "test_case"
const colJvm = "jvm"
const colDate = "date_utc"
const colTime = "time_utc"
const colResult = "result"
const colFailText = "fail_text" // similar to jacotest failure report categories

// Secondary indexes
const ixTestCaseName = "ix_test_case"

// File system and SQL constants
const dirDatabase = "database"
const fileDatabase = "jacotest.db"
const driverDatabase = "sqlite"

// Assigned and used at run-time
var pathDatabase string
var dbHandle *sql.DB
var dbIsOpen = false

/*
Internal function to run an SQL statement and handle any errors.
*/
func sqlFunc(text string, commit bool) error {

	if sqlTracing {
		msg := fmt.Sprintf("sqlFunc: SQL: %s", text)
		Logger(msg)
	}

	// Begin transaction if commit true.
	if commit {
		dbHandle.Begin()
		textBegin := "BEGIN TRANSACTION;"
		statement, err := dbHandle.Prepare(textBegin) // Prepare COMMIT SQL Statement
		if err != nil {
			msg := fmt.Sprintf("sqlFunc: Prepare (%s) (%s) failed, err: \n%s", text, textBegin, err.Error())
			LogError(msg)
			return err
		}

		_, err = statement.Exec()
		if err != nil {
			msg := fmt.Sprintf("sqlFunc: Exec (%s) (%s) failed, err: %s\n%s", text, textBegin, err.Error())
			LogError(msg)
			return err
		}
	}

	// Prepare SQL statement.
	statement, err := dbHandle.Prepare(text)
	if err != nil {
		msg := fmt.Sprintf("sqlFunc: Prepare (%s) failed, err: \n%s", text, err.Error())
		LogError(msg)
		return err
	}

	// Execute SQL statement.
	_, err = statement.Exec()
	if err != nil {
		msg := fmt.Sprintf("sqlFunc: Exec (%s) failed, err: %s\n%s", text, err.Error())
		LogError(msg)
		return err
	}

	// End transaction if commit true.
	if commit {
		textCommit := "COMMIT;"
		statement, err := dbHandle.Prepare(textCommit) // Prepare COMMIT SQL Statement
		if err != nil {
			msg := fmt.Sprintf("sqlFunc: Prepare (%s) (%s) failed, err: \n%s", text, textCommit, err.Error())
			LogError(msg)
			return err
		}

		_, err = statement.Exec() // Execute SQL Statements
		if err != nil {
			msg := fmt.Sprintf("sqlFunc: Exec (%s) (%s) failed, err: %s\n%s", text, textCommit, err.Error())
			LogError(msg)
			return err
		}
	}

	// No errors
	return nil

}

/*
Internal function to run an SQL select query and handle any errors. The output is returned to caller.
*/
func sqlQuery(text string) (*sql.Rows, bool) {

	if sqlTracing {
		msg := fmt.Sprintf("sqlQuery: SQL: %s", text)
		Logger(msg)
	}

	rows, err := dbHandle.Query(text)
	if err != nil {
		errMsg := fmt.Sprintf("sqlQuery: dbHandle.Query(%s) failed, err: %s", text, err.Error())
		LogError(errMsg)
		return nil, false
	}

	return rows, true

}

/*
Internal function to initialise a jacotest database.

* Create database (includes file creation/re-creation).
* Create history table and all of its columns, a combination of which is the primary index.
* Create secondary indexes.
*/
func initDB() {

	if sqlTracing {
		Logger("initDB: Begin")
	}

	sqlText := "CREATE TABLE " + tableHistory + " ("
	sqlText += colTestCase + " VARCHAR NOT NULL, "
	sqlText += colJvm + " VARCHAR NOT NULL, "
	sqlText += colDate + " VARCHAR NOT NULL, "
	sqlText += colTime + " VARCHAR NOT NULL, "
	sqlText += colResult + " VARCHAR NOT NULL, "
	sqlText += colFailText + " VARCHAR, "
	sqlText += "PRIMARY KEY (" + colTestCase + ", " + colDate + ", " + colTime + ") )"
	err := sqlFunc(sqlText, true)
	if err != nil {
		FatalErr(fmt.Sprintf("initDB: unrecoverable SQL create-table error", sqlText), err)
	}

	sqlText = "CREATE INDEX " + ixTestCaseName + " ON " + tableHistory + " (" + colTestCase + ")"
	err = sqlFunc(sqlText, true)
	if err != nil {
		FatalErr(fmt.Sprintf("initDB: unrecoverable SQL create-index error", sqlText), err)
	}

	if sqlTracing {
		Logger("initDB: End")
	}

}

/*
DBOpen - Database Open

* If the database directory is not present, create it.
* If the history.db file in the database directory is not present, call initDB.
* Connect to DB.
* Validate DB.
*/
func DBOpen(flagVerbose bool) {

	sqlTracing = flagVerbose
	if sqlTracing {
		Logger("DBOpen: Begin")
	}

	// Database directory
	_, err := os.ReadDir(dirDatabase)
	if err != nil {
		err := os.Mkdir(dirDatabase, 0755)
		if err != nil {
			errMsg := fmt.Sprintf("DBOpen: Mkdir(%s) failed", dirDatabase)
			FatalErr(errMsg, err)
		}
	}

	// Database file
	pathDatabase = dirDatabase + "/" + fileDatabase
	_, err = os.Stat(pathDatabase)
	if err != nil {
		if sqlTracing {
			infoMsg := fmt.Sprintf("DBOpen: database file(%s) inaccessible", pathDatabase)
			Logger(infoMsg)
		}
		dbHandle, err = sql.Open(driverDatabase, pathDatabase)
		if err != nil {
			errMsg := fmt.Sprintf("DBOpen: sql.Open(new database %s) failed", pathDatabase)
			FatalErr(errMsg, err)
		}
		initDB()

		if sqlTracing {
			Logger("DBOpen: End, database created")
		}
		return
	}

	// Connect to pre-existing database
	if sqlTracing {
		Logger("DBOpen database file exists")
	}
	dbHandle, err = sql.Open(driverDatabase, pathDatabase)
	if err != nil {
		FatalErr(fmt.Sprintf("DBOpen: sql.Open(existing database %s) failed", pathDatabase), err)
	}

	dbIsOpen = true

	if sqlTracing {
		Logger("DBOpen: End")
	}

}

/*
DBClose - Close the database.
*/
func DBClose() {

	// Quick return if database is closed.
	if !dbIsOpen {
		return
	}

	// Make sure that we do not come through here again.
	dbIsOpen = false

	// Close the database.
	fmt.Println("DBClose: Begin")
	err := dbHandle.Close()
	if err != nil {
		FatalErr("DBClose: sql.Close failed", err)
	}
	fmt.Println("DBClose: End")

}

/*
DBStorePassed - Store a PASSED jacotest test case resjvmult.
*/
func DBStorePassed(testCaseName string) {

	global := GetGlobalRef()

	jvm := "'" + global.JvmName + "'"
	tcn := "'" + testCaseName + "'"
	dateUTC := "'" + GetUtcDate() + "'"
	timeUTC := "'" + GetUtcTime() + "'"
	sqlText := "INSERT INTO " + tableHistory + " VALUES("
	sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'passed', NULL)"

	err := sqlFunc(sqlText, true)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		dateUTC = "'" + GetUtcDate() + "'"
		timeUTC = "'" + GetUtcTime() + "'"
		sqlText = "INSERT INTO " + tableHistory + " VALUES("
		sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'passed', NULL)"
		err := sqlFunc(sqlText, true)
		if err != nil {
			errMsg := fmt.Sprintf("DBStorePassed: 2nd try did not work - Giving up!\n%s\n%s", sqlText, err.Error())
			LogError(errMsg)
			return
		}
		msg := fmt.Sprintf("DBStorePassed: 2nd try is a success: %s", sqlText)
		Logger(msg)
	}
}

/*
DBStoreFailed - Store a FAILED jacotest test case result.
*/
func DBStoreFailed(testCaseName, failText string) {

	global := GetGlobalRef()

	// Clean failText
	runes := []rune(failText)
	for ii := 0; ii < len(runes); ii++ {
		if runes[ii] == '\'' || runes[ii] == '"' {
			runes[ii] = '*'
		}
	}
	failText = string(runes)

	// Form string parameters for the SQL text.
	qFailText := "'" + CleanerText(failText, true) + "'"
	jvm := "'" + global.JvmName + "'"
	tcn := "'" + testCaseName + "'"
	dateUTC := "'" + GetUtcDate() + "'"
	timeUTC := "'" + GetUtcTime() + "'"
	sqlText := "INSERT INTO " + tableHistory + " VALUES("
	sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'failed', " + qFailText + ")"

	err := sqlFunc(sqlText, true)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		dateUTC = "'" + GetUtcDate() + "'"
		timeUTC = "'" + GetUtcTime() + "'"
		sqlText = "INSERT INTO " + tableHistory + " VALUES("
		sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'failed', " + qFailText + ")"
		err := sqlFunc(sqlText, true)
		if err != nil {
			errMsg := fmt.Sprintf("DBStoreFailed: 2nd try did not work - Giving up!\n%s\n%s", sqlText, err.Error())
			LogError(errMsg)
			return
		}
		msg := fmt.Sprintf("DBStoreFailed: 2nd try is a success: %s", sqlText)
		Logger(msg)
	}

}

/*
DBPrtChanges - Print result records that have changed since previous run.
Note that history rows are ordered by test case name, date descending, and time descending.
*/
func DBPrtChanges() {

	// Query descending test case, date, and time.
	sqlText := "SELECT test_case, jvm, date_utc desc, time_utc, result, COALESCE(fail_text, 'n/a') FROM history ORDER BY test_case, date_utc DESC, time_utc DESC"

	// Previous result record w.r.t. case, date, and time
	var prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText = "", "", "", "", "", ""

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText = "", "", "", "", "", ""

	// Get all the history table rows.
	var msg string
	counter := 0
	rows, ok := sqlQuery(sqlText)
	if !ok {
		return
	}
	Logger("Looking for test cases with changed results .....")

	// High level scan.
	for rows.Next() {
		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
		if err != nil {
			FatalErr("DBPrtChanges: rows.Scan failed", err)
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, the next test always fails on the very first row.
		if curTestCase != prvTestCase {
			// No, this is a new one.
			// Make it current and continue.
			curTestCase = prvTestCase
			curJvm = prvJvm
			curDateUTC = prvDateUTC
			curTimeUTC = prvTimeUTC
			curResult = prvResult
			curFailText = prvFailText
			continue
		}

		// Same test case as the last one.
		// Same result?
		if prvResult != curResult || prvFailText != curFailText {
			// Not the same result. Show the changes.
			counter += 1
			fmt.Printf("\n\tcur  >>  %-s  %-8s  %-10s  %-8s  %-6s  %-s\n", curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText)
			fmt.Printf("\tprv  >>  %-s  %-8s  %-10s  %-8s  %-6s  %-s\n", prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText)
		}

		// Skip to a new test case.
		for rows.Next() {
			// Get next history row back in time.
			err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
			if err != nil {
				FatalErr("DBPrtChanges: rows.Scan/skipping failed", err)
			}

			// Same as last test case?
			if curTestCase == prvTestCase {
				continue // Same: Skip this history record for same test case.
			} else {
				// Reached a new test case.
				// Make it current and continue.
				curTestCase = prvTestCase
				curJvm = prvJvm
				curDateUTC = prvDateUTC
				curTimeUTC = prvTimeUTC
				curResult = prvResult
				curFailText = prvFailText
				break // Escape from skipping. Resume high-level scan.
			}
		}
	}

	// Done. How many changed results?
	if counter == 0 {
		msg = "No test cases with changed results"
		Logger(msg)
	} else {
		msg = fmt.Sprintf("Number of test cases with changed results = %d", counter)
		LoggerSkip(msg)
	}
}

/*
DBPrtLastPass - Print result records where the test case currently fails but passed sometime in the past.
Note that history rows are ordered by test case name, date descending, and time descending.
*/
func DBPrtLastPass() {

	// Query descending test case, date, and time.
	sqlText := "SELECT test_case, jvm, date_utc desc, time_utc, result, COALESCE(fail_text, 'n/a') FROM history ORDER BY test_case, date_utc DESC, time_utc DESC"

	// Previous result record w.r.t. case, date, and time
	var prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText = "", "", "", "", "", ""

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText = "", "", "", "", "", ""

	// Get all the history table rows.
	var msg string
	counter := 0
	rows, ok := sqlQuery(sqlText)
	if !ok {
		return
	}
	Logger("Looking for test cases that currently fail but passed sometime previously .....")

	// High level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
		if err != nil {
			FatalErr("DBPrtChanges: rows.Scan failed", err)
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, the next test always fails on the very first row.
		if curTestCase != prvTestCase {
			// No, this is a new one.
			// Make it current and continue.
			curTestCase = prvTestCase
			curJvm = prvJvm
			curDateUTC = prvDateUTC
			curTimeUTC = prvTimeUTC
			curResult = prvResult
			curFailText = prvFailText
			continue
		}

		// Same test case as the last one.
		// First record for the test case indicate failure?
		if curResult == "failed" {
			if prvResult == "passed" {
				// Show the changes.
				counter += 1
				fmt.Printf("\tfailed  >>  %-s  %-8s  %-10s  %-8s  %-6s  %-s\n", curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText)
				fmt.Printf("\tpassed  >>  %-s  %-8s  %-10s  %-8s  %-6s\n", prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult)
				// Will skip to the next test case.
			} else { // prevResult is a fail
				continue // So, keep scanning within the current test case.
			}
		}

		// Skip to a new test case.
		for rows.Next() {
			// Get next history row back in time.
			err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
			if err != nil {
				FatalErr("DBPrtChanges: rows.Scan/skipping failed", err)
			}

			// Same as last test case?
			if curTestCase == prvTestCase {
				continue // Same: Skip this history record for same test case.
			} else {
				// Reached a new test case.
				// Make it current and continue.
				curTestCase = prvTestCase
				curJvm = prvJvm
				curDateUTC = prvDateUTC
				curTimeUTC = prvTimeUTC
				curResult = prvResult
				curFailText = prvFailText
				break // Escape from skipping. Resume high-level scan.
			}
		}
	}

	// Done. How many changed results?
	if counter == 0 {
		msg = "No test cases with changed results"
	} else {
		msg = fmt.Sprintf("Number of test cases with changed results = %d", counter)
	}
	Logger(msg)
}

/*
DBDeleteMostRecent - Delete the most recent logged pass/fail record for each test case.
*/
func DBDeleteMostRecent() {

	// Query descending test case, date, and time.
	sqlSelect := "SELECT test_case, jvm, date_utc desc, time_utc FROM " + tableHistory + " NOLOCK ORDER BY test_case, date_utc DESC, time_utc DESC"

	// Set up for DELETE operaations.
	deleteFormat := "DELETE FROM " + tableHistory + " WHERE test_case = '%s' AND jvm='%s' AND date_utc = '%s' AND time_utc = '%s'"
	var deleteList []string

	// Most current result record w.r.t. date and time
	var prvTestCase = ""
	var curTestCase, curJvm, curDateUTC, curTimeUTC string

	// Get all the history table rows.
	rows, ok := sqlQuery(sqlSelect)
	if !ok {
		return
	}

	// High level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC)
		if err != nil {
			FatalErr("DBDeleteMostRecent: rows.Scan failed", err)
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, the next test always fails on the very first row.
		if curTestCase != prvTestCase {
			// No, this is a new one.
			// TODO: Delete DB record.
			sqlDelete := fmt.Sprintf(deleteFormat, curTestCase, curJvm, curDateUTC, curTimeUTC)
			deleteList = append(deleteList, sqlDelete)
			// Make it the previous and continue.
			prvTestCase = curTestCase
		}

	}

	// For each delete statement, execute it.
	counter := 0
	for _, sqlStmt := range deleteList {
		err := sqlFunc(sqlStmt, true)
		if err != nil {
			FatalErr("DBDeleteMostRecent: DELETE failed", err)
		}
		counter += 1
	}

	// Close and re-open database.
	DBClose()
	DBOpen(sqlTracing)

	msg := fmt.Sprintf("Removed %d test case result records", counter)
	Logger(msg)

}

/*
DBPrintMostRecent - Print the most recent logged pass/fail record for each test case.
*/
func DBPrintMostRecent() {

	counter := 0
	passes := 0
	fails := 0
	var err error
	var ok bool
	var rows *sql.Rows

	// Query descending test case, date, and time.
	sqlSelect := "SELECT test_case, jvm, date_utc desc, time_utc, result, fail_text FROM " +
		tableHistory + " ORDER BY test_case, date_utc DESC, time_utc DESC"

	// Most current result record w.r.t. date and time
	var prvTestCase = ""
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult string
	var curFailText any

	// Get all the history table rows.
	rows, ok = sqlQuery(sqlSelect)
	if !ok {
		return
	}

	curDir, err := os.Getwd()
	if err != nil {
		FatalErr("DBPrintMostRecent: os.Getwd failed", err)
	}
	testsDir := curDir + "/tests/"

	// High level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC, &curResult, &curFailText)
		if err != nil {
			FatalErr("DBPrintMostRecent: rows.Scan failed", err)
		}
		if curFailText == nil {
			curFailText = " "
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, the next test always fails on the very first row.
		if curTestCase != prvTestCase {
			// New test case.
			// Does the curTestCase still exist?
			testCase := testsDir + curTestCase
			ok, err = isDirectory(testCase)
			//fmt.Printf("DEBUG path %s --> %v, %v\n", testCase, ok, err)
			if err != nil {
				errMsg := fmt.Sprintf("DBPrintMostRecent: isDirectory(%s) failed, err: %v", testCase, err)
				FatalErr(errMsg, err)
			}
			if !ok {
				//errMsg := fmt.Sprintf("DBPrintMostRecent: %s is not a directory", testCase)
				//LogWarning(errMsg)
				continue
			}

			// Show the database information.
			fmt.Printf("[%d]  %-s  %-8s  %-6s  %-s\n", counter+1, curTestCase, curJvm, curResult, curFailText)
			counter += 1
			if curResult == "passed" {
				passes += 1
			} else {
				if curResult == "failed" {
					fails += 1
				}
			}
			// Make it the previous and continue.
			prvTestCase = curTestCase
		}

	}

	fmt.Printf("Printed %d records\n", counter)
	fmt.Printf("Passed test cases: %d\n", passes)
	fmt.Printf("Failed test cases: %d\n", fails)

}

/*
DBDeleteObsolete - Delete database records of obsolete test cases.
*/
func DBDeleteObsolete() {

	var err error
	var ok bool
	var rows *sql.Rows
	deleteFormat := "DELETE FROM " + tableHistory + " WHERE test_case = '%s' AND jvm='%s' AND date_utc = '%s' AND time_utc = '%s'"
	var deleteList []string

	// Query descending test case, date, and time.
	sqlSelect := "SELECT test_case, jvm, date_utc desc, time_utc FROM " + tableHistory

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC string

	// Get all the history table rows.
	rows, ok = sqlQuery(sqlSelect)
	if !ok {
		return
	}

	curDir, err := os.Getwd()
	if err != nil {
		FatalErr("DBDeleteObsolete: os.Getwd failed", err)
	}
	testsDir := curDir + "/tests/"

	// High level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC)
		if err != nil {
			FatalErr("DBDeleteObsolete: rows.Scan failed", err)
		}

		// New test case.
		// Does the curTestCase still exist?
		testCase := testsDir + curTestCase
		ok, err = isDirectory(testCase)
		//fmt.Printf("DEBUG path %s --> %v, %v\n", testCase, ok, err)
		if err != nil {
			errMsg := fmt.Sprintf("DBDeleteObsolete: isDirectory(%s) failed, err: %v", testCase, err)
			FatalErr(errMsg, err)
		}
		if !ok {
			errMsg := fmt.Sprintf("Removing database record for obsolete test case: %s %s %s %s",
				curTestCase, curJvm, curDateUTC, curTimeUTC)
			Logger(errMsg)
			sqlDelete := fmt.Sprintf(deleteFormat, curTestCase, curJvm, curDateUTC, curTimeUTC)
			deleteList = append(deleteList, sqlDelete)
		}

	}

	// For each delete statement, execute it.
	counter := 0
	for _, sqlStmt := range deleteList {
		err := sqlFunc(sqlStmt, true)
		if err != nil {
			FatalErr("DBDeleteMostRecent: DELETE failed", err)
		}
		counter += 1
	}

	fmt.Printf("Removed %d database records\n", counter)

}
