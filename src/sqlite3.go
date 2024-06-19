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
var sqliteDatabase *sql.DB

/*
Internal function to run an SQL statement and handle any errors.
*/
func sqlFunc(text string) error {

	if sqlTracing {
		msg := fmt.Sprintf("sqlFunc: %s", text)
		Logger(msg)
	}

	statement, err := sqliteDatabase.Prepare(text) // Prepare SQL Statement
	if err != nil {
		FmtFatal("sqlFunc: sqliteDatabase.Prepare failed", text, err)
	}

	_, err = statement.Exec() // Execute SQL Statements
	if err != nil {
		msg := fmt.Sprintf("sqlFunc: statement.Exec failed: %s\n%s", text, err.Error())
		LogError(msg)
		return err
	}

	// No errors
	return nil

}

/*
Internal function to run an SQL select query and handle any errors. The output is returned to caller.
*/
func sqlQuery(text string) *sql.Rows {

	if sqlTracing {
		msg := fmt.Sprintf("sqlQuery: %s", text)
		Logger(msg)
	}

	rows, err := sqliteDatabase.Query(text)
	if err != nil {
		FmtFatal("sqlQuery: sqliteDatabase.Query failed", text, err)
	}

	return rows

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
	err := sqlFunc(sqlText)
	if err != nil {
		FmtFatal("initDB: unrecoverable SQL create-table error", sqlText, err)
	}

	sqlText = "CREATE INDEX " + ixTestCaseName + " ON " + tableHistory + " (" + colTestCase + ")"
	err = sqlFunc(sqlText)
	if err != nil {
		FmtFatal("initDB: unrecoverable SQL create-index error", sqlText, err)
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
			FmtFatal("DBOpen: Cannot create database directory", dirDatabase, err)
		}
	}

	// Database file
	pathDatabase = dirDatabase + "/" + fileDatabase
	_, err = os.Stat(pathDatabase)
	if err != nil {
		if sqlTracing {
			Logger("DBOpen: database file inaccessible: " + err.Error())
		}
		sqliteDatabase, err = sql.Open(driverDatabase, pathDatabase)
		if err != nil {
			FmtFatal("DBOpen: sql.Open(create) failed", pathDatabase, err)
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
	sqliteDatabase, err = sql.Open(driverDatabase, pathDatabase)
	if err != nil {
		FmtFatal("DBOpen: sql.Open(pre-existing) failed", pathDatabase, err)
	}

	// sqliteDatabase stays open until process exit

	// TODO: Validate database

	if sqlTracing {
		Logger("DBOpen: End")
	}

}

/*
DBClose - Store a PASSED jacotest test case result.Close the database
*/
func DBClose() {

	if sqlTracing {
		Logger("DBClose: Begin")
	}

	err := sqliteDatabase.Close()
	if err != nil {
		FmtFatal("DBClose: sql.Close failed", pathDatabase, err)
	}

	if sqlTracing {
		Logger("DBClose: End")
	}

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

	err := sqlFunc(sqlText)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		dateUTC = "'" + GetUtcDate() + "'"
		timeUTC = "'" + GetUtcTime() + "'"
		sqlText = "INSERT INTO " + tableHistory + " VALUES("
		sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'passed', NULL)"
		err := sqlFunc(sqlText)
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
	qFailText := "'" + CleanerText(failText) + "'"
	jvm := "'" + global.JvmName + "'"
	tcn := "'" + testCaseName + "'"
	dateUTC := "'" + GetUtcDate() + "'"
	timeUTC := "'" + GetUtcTime() + "'"
	sqlText := "INSERT INTO " + tableHistory + " VALUES("
	sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'failed', " + qFailText + ")"

	err := sqlFunc(sqlText)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		dateUTC = "'" + GetUtcDate() + "'"
		timeUTC = "'" + GetUtcTime() + "'"
		sqlText = "INSERT INTO " + tableHistory + " VALUES("
		sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'failed', " + qFailText + ")"
		err := sqlFunc(sqlText)
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
	rows := sqlQuery(sqlText)
	Logger("Looking for test cases with changed results .....")

	// High level scan.
	for rows.Next() {
		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
		if err != nil {
			FmtFatal("DBPrtChanges: rows.Scan failed", pathDatabase, err)
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
			fmt.Printf("\tcur  >>  %-s  %-8s  %-10s  %-8s  %-6s  %-s\n", curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText)
			fmt.Printf("\tprv  >>  %-s  %-8s  %-10s  %-8s  %-6s  %-s\n", prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText)
		}

		// Skip to a new test case.
		for rows.Next() {
			// Get next history row back in time.
			err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
			if err != nil {
				FmtFatal("DBPrtChanges: rows.Scan/skipping failed", pathDatabase, err)
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
	rows := sqlQuery(sqlText)
	Logger("Looking for test cases that currently fail but passed sometime previously .....")

	// High level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText)
		if err != nil {
			FmtFatal("DBPrtChanges: rows.Scan failed", pathDatabase, err)
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
				FmtFatal("DBPrtChanges: rows.Scan/skipping failed", pathDatabase, err)
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
