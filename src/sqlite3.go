package main

import (
	"database/sql"
	"encoding/csv"
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"time"

	_ "modernc.org/sqlite"
)

var sqlTracing = false

// Wait before the single retry of a failed INSERT.
const msecsSleep = 1000

// History table
const tableHistory = "history"

// History table columns
const colTestCase = "test_case"
const colJvm = "jvm"
const colDate = "date_utc"
const colTime = "time_utc"
const colResult = "result"
const colFailText = "fail_text"
const colETmsecs = "elapsed_msecs"
const colAllocMB = "allocated_mb"

// Secondary indexes
const ixTestCaseName = "ix_test_case"

// fail_text value for a test case that passed
const failTextNone = "n/a"

// Values for columns that are not known: failed tests, and rows that predate the column.
const etUnknown = -1    // elapsed_msecs
const allocUnknown = -1 // allocated_mb

// File system and SQL constants
const dirDatabase = "database"
const fileDatabase = "jacotest.db"
const driverDatabase = "sqlite"
const dirTests = "tests"

// Assigned and used at run-time
var pathDatabase string
var dbHandle *sql.DB
var dbIsOpen = false

// historyRow is one row of the history table.
type historyRow struct {
	testCase string
	jvm      string
	dateUTC  string
	timeUTC  string
	result   string
	failText string
	etMsecs  int
	allocMB  int
}

// SQL statements that do not change at run-time.
var (
	// Select the list for a complete history row, in the order that scanHistoryRow expects.
	// Nullable columns are given default values so that Scan will never see NULL.
	fmtSqlSelectColumns = fmt.Sprintf(
		"%s, %s, %s, %s, %s, COALESCE(%s, '%s') AS %s, COALESCE(%s, %d) AS %s, COALESCE(%s, %d) AS %s",
		colTestCase, colJvm, colDate, colTime, colResult,
		colFailText, failTextNone, colFailText,
		colETmsecs, etUnknown, colETmsecs,
		colAllocMB, allocUnknown, colAllocMB)

	// The newest N rows of one test case (N < 0 means all), again ordered newest first.
	// The inner ORDER BY decides which rows survive the LIMIT. The outer ORDER BY
	// fixes the order of the output, which SQLite does not promise to keep from the inner one.
	fmtSqlSelectLastN = fmt.Sprintf(
		"SELECT * FROM (SELECT %s FROM %s WHERE %s = %%s ORDER BY %s DESC, %s DESC LIMIT %%s) ORDER BY %s DESC, %s DESC",
		fmtSqlSelectColumns, tableHistory, colTestCase, colDate, colTime, colDate, colTime)
)

/*
Internal function to run an SQL statement and handle any errors.
*/
func sqlFunc(text string) error {

	var err error
	var statement *sql.Stmt

	if sqlTracing {
		logger("sqlFunc: SQL: %s", text)
	}

	// Prepare SQL statement.
	statement, err = dbHandle.Prepare(text)
	if err != nil {
		fatalErr(err, "sqlFunc: statement.Prepare (%s) failed, err: \n%s", text, err.Error())
	}

	// Execute SQL statement.
	_, err = statement.Exec()
	if err != nil {
		fatalErr(err, "sqlFunc: statement.Exec (%s) failed, err: \n%s", text, err.Error())
	}

	// No errors
	err = statement.Close()
	if err != nil {
		fatalErr(err, "sqlFunc: statement.Close() failed, err: \n%s", err.Error())
	}

	return nil

}

/*
Internal function to run an SQL select query and handle any errors. The output is returned to the caller.
*/
func sqlQuery(text string) (*sql.Rows, bool) {

	if sqlTracing {
		logger("sqlQuery: SQL: %s", text)
	}

	rows, err := dbHandle.Query(text)
	if err != nil {
		logError("sqlQuery: dbHandle.Query(%s) failed, err: %s", text, err.Error())
		return nil, false
	}

	return rows, true

}

/*
scanHistoryRow reads the current row of a result set whose select list is fmtSqlSelectColumns.
*/
func scanHistoryRow(rows *sql.Rows, caller string) historyRow {

	var row historyRow
	err := rows.Scan(&row.testCase, &row.jvm, &row.dateUTC, &row.timeUTC, &row.result,
		&row.failText, &row.etMsecs, &row.allocMB)
	if err != nil {
		fatalErr(err, "%s: rows.Scan failed", caller)
	}
	return row

}

/*
closeRows closes a result set and reports any error that ended the iteration early.
rows.Next returns false both at the end of the data and on an error, so Err must be checked.
*/
func closeRows(rows *sql.Rows, caller string) {

	iterErr := rows.Err()
	closeErr := rows.Close()
	if iterErr != nil {
		fatalErr(iterErr, "%s: rows iteration error", caller)
	}
	if closeErr != nil {
		fatalErr(closeErr, "%s: rows.Close failed", caller)
	}

}

/*
historyTestCaseDirExists reports whether the test case still has a directory under the 'tests' directory.
*/
func historyTestCaseDirExists(testsDir, testCase, caller string) bool {

	path := filepath.Join(testsDir, testCase)
	ok, err := isDirectory(path)
	if err != nil {
		fatalErr(err, "%s: isDirectory(%s) failed", caller, path)
	}
	return ok

}

/*
getTestsDir returns the absolute path of the 'tests' directory.
*/
func getTestsDir(caller string) string {

	curDir, err := os.Getwd()
	if err != nil {
		fatalErr(err, "%s: os.Getwd failed", caller)
	}
	return filepath.Join(curDir, dirTests)

}

/*
Internal function to initialize a Jacotest database.

* Create the database (includes file creation/re-creation).
* Create the history table and all of its columns, a combination of which is the primary index.
* Create secondary indexes.
*/
func initDB() {

	if sqlTracing {
		logger("initDB: Begin")
	}

	sqlText := "CREATE TABLE " + tableHistory + " ("
	sqlText += colTestCase + " VARCHAR NOT NULL, "
	sqlText += colJvm + " VARCHAR NOT NULL, "
	sqlText += colDate + " VARCHAR NOT NULL, "
	sqlText += colTime + " VARCHAR NOT NULL, "
	sqlText += colResult + " VARCHAR NOT NULL, "
	sqlText += colFailText + " VARCHAR, "
	sqlText += colETmsecs + " INTEGER, "
	sqlText += colAllocMB + " INTEGER, "
	sqlText += "PRIMARY KEY (" + colTestCase + ", " + colDate + ", " + colTime + ") )"
	err := sqlFunc(sqlText)
	if err != nil {
		fatalErr(err, "initDB: unrecoverable SQL error: %s", sqlText)
	}

	sqlText = "CREATE INDEX " + ixTestCaseName + " ON " + tableHistory + " (" + colTestCase + ")"
	err = sqlFunc(sqlText)
	if err != nil {
		fatalErr(err, "initDB: unrecoverable SQL error: %s", sqlText)
	}

	if sqlTracing {
		logger("%s", "initDB: End")
	}

}

/*
DBOpen - Database Open

* If the database directory is not present, create it.
* If the history.db file in the database directory is not present, call initDB.
* Connect to DB.
* Validate DB.
*/
func DBOpen() {
	global := GetGlobalRef()

	sqlTracing = global.FlagVerbose
	if sqlTracing {
		logger("DBOpen: Begin")
	}

	// Database directory
	_, err := os.ReadDir(dirDatabase)
	if err != nil {
		err := os.Mkdir(dirDatabase, 0755)
		if err != nil {
			fatalErr(err, "DBOpen: Mkdir(%s) failed", dirDatabase)
		}
	}

	// Database file
	pathDatabase = dirDatabase + "/" + fileDatabase
	_, err = os.Stat(pathDatabase)
	if err != nil {
		// O/S error while trying to stat the database file.
		if sqlTracing {
			logger("DBOpen: database file(%s) inaccessible", pathDatabase)
		}

		// Try to create the database file.
		dbHandle, err = sql.Open(driverDatabase, pathDatabase)
		if err != nil {
			fatalErr(err, "DBOpen: sql.Open(new database %s) failed", pathDatabase)
		}

		// Database successfully opened - now, initialize the tabled and column metadata.
		initDB()

		if sqlTracing {
			logger("%s", "DBOpen: End, database created")
		}
		return
	}

	// Connect to pre-existing database
	if sqlTracing {
		logger("%s", "DBOpen database file exists")
	}
	dbHandle, err = sql.Open(driverDatabase, pathDatabase)
	if err != nil {
		fatalErr(err, "DBOpen: sql.Open(existing database %s) failed", pathDatabase)
	}

	// The database is open and ready for business.
	dbIsOpen = true

	// If column "elapsed_msecs" does not yet exist, populate it.
	query := fmt.Sprintf("SELECT COUNT(1) FROM pragma_table_info('%s') WHERE name = '%s'", tableHistory, colETmsecs)
	rows, success := sqlQuery(query)
	if !success {
		fatalErr(errors.New("cannot continue"), "DBOpen: sqlQuery(%s) failed", query)
	}
	var count int
	if rows.Next() {
		if err := rows.Scan(&count); err != nil {
			fatalErr(err, "%s", "DBOpen: failed to scan count")
		}
	}
	if err := rows.Err(); err != nil {
		_ = rows.Close()
		fatalErr(err, "%s", "DBOpen: rows iteration error")
	}

	// If the row-count < 1, alter the history table.
	// The default value for the new column is NULL.
	if count < 1 {
		updater := fmt.Sprintf("ALTER TABLE '%s' ADD COLUMN '%s' INTEGER DEFAULT -1", tableHistory, colETmsecs)
		err := sqlFunc(updater)
		if err != nil {
			fatalErr(err, "DBOpen: '%s' failed", updater)
		}
		backfill := fmt.Sprintf("UPDATE '%s' SET '%s' = -1", tableHistory, colETmsecs)
		err = sqlFunc(backfill)
		if err != nil {
			fatalErr(err, "DBOpen: '%s' failed", backfill)
		}
		if sqlTracing {
			logger("%s", "DBOpen: alter table and backfill succeeded")
		}
	}

	err = rows.Close()
	if err != nil {
		fatalErr(err, "%s", "DBOpen: rows.Close() failed")
	}

	if sqlTracing {
		logger("%s", "DBOpen: End")
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
		fatalErr(err, "%s", "DBClose: sql.Close failed")
	}
	fmt.Println("DBClose: End")

}

/*
DBStorePassed - Store a PASSED jacotest test case result.
*/
func DBStorePassed(testCaseName string, etMsecs int, allocMB int) {

	global := GetGlobalRef()

	jvm := "'" + global.JvmName + "'"
	tcn := "'" + testCaseName + "'"
	dateUTC := "'" + getUtcDate() + "'"
	timeUTC := "'" + getUtcTime() + "'"
	etMsecsStr := strconv.Itoa(etMsecs)
	allocMBStr := strconv.Itoa(allocMB)
	sqlText := "INSERT INTO " + tableHistory + " VALUES("
	sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'passed', 'n/a', " + etMsecsStr + ", " + allocMBStr + ")"

	err := sqlFunc(sqlText)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		err := sqlFunc(sqlText)
		if err != nil {
			errMsg := fmt.Sprintf("DBStorePassed: 2nd try did not work - Giving up!\n%s\n%s", sqlText, err.Error())
			fatalErr(err, "%s", errMsg)
		}
		logger("DBStorePassed: 2nd try is a success: %s", sqlText)
	}
}

/*
DBStoreFailed - Store a FAILED jacotest test case result.
*/
func DBStoreFailed(testCaseName, failText string) {

	// Clean failText
	runes := []rune(failText)
	for ii := range runes {
		if runes[ii] == '\'' || runes[ii] == '"' {
			runes[ii] = '*'
		}
	}
	failText = string(runes)

	// Form string parameters for the SQL text.
	qFailText := "'" + cleanerText(failText, true) + "'"
	jvm := "'" + global.JvmName + "'"
	tcn := "'" + testCaseName + "'"
	dateUTC := "'" + getUtcDate() + "'"
	timeUTC := "'" + getUtcTime() + "'"
	sqlText := "INSERT INTO " + tableHistory + " VALUES("
	sqlText += tcn + ", " + jvm + ", " + dateUTC + ", " + timeUTC + ", 'failed', " + qFailText + ", -1, -1)"

	err := sqlFunc(sqlText)
	if err != nil {
		time.Sleep(msecsSleep * time.Millisecond)
		err := sqlFunc(sqlText)
		if err != nil {
			fatalErr(err, "DBStoreFailed: 2nd try did not work - Giving up!\n%s\n%s", sqlText, err.Error())
		}
		logger("DBStoreFailed: 2nd try is a success: %s", sqlText)
	}

}

/*
DBPrtChanges - Print result records that have changed since the previous run.
Note that history rows are ordered by test case name, date descending, and time descending.
*/
func DBPrtChanges() {

	// Query descending test case, date, and time.
	sqlText := "SELECT " + colTestCase + ", " + colJvm + ", " + colDate + ", " + colTime +
		", " + colResult + ", COALESCE(" + colFailText + ", 'n/a'), " + colETmsecs + " FROM " + tableHistory +
		" ORDER BY " + colTestCase + ", " + colDate + " DESC, " + colTime + " DESC"

	// Previous result record w.r.t. the test case, date, and time
	var prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText = "", "", "", "", "", ""
	var prvETMsecs int

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText = "", "", "", "", "", ""
	var curEtMsecs int

	// Get all the history table rows.
	var msg string
	counter := 0
	rows, ok := sqlQuery(sqlText)
	defer closeRows(rows, "DBPrtChanges")
	if !ok {
		return
	}
	logger("Looking for test cases with changed results .....")

	// High-level scan.
	for rows.Next() {
		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText, &prvETMsecs)
		if err != nil {
			fatalErr(err, "%s", "DBPrtChanges: rows.Scan failed")
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, force a match.
		if curTestCase != prvTestCase {
			// Make the previous version the current version and continue.
			curTestCase = prvTestCase
			curJvm = prvJvm
			curDateUTC = prvDateUTC
			curTimeUTC = prvTimeUTC
			curResult = prvResult
			curFailText = prvFailText
			curEtMsecs = prvETMsecs
			continue
		}

		// Same test case as the last one.
		// Same result?
		if prvResult != curResult || prvFailText != curFailText {
			// New result. Show the changes.
			counter += 1
			fmt.Printf("\n\tcur  >>  %-s  %-8s  %-10s  %-8s  %-6s  %d  %-s\n", curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curEtMsecs, curFailText)
			fmt.Printf("\tprv  >>  %-s  %-8s  %-10s  %-8s  %-6s  %d  %-s\n", prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvETMsecs, prvFailText)
		}

		// Skip to a new test case.
		for rows.Next() {
			// Get next history row back in time.
			err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText, &prvETMsecs)
			if err != nil {
				fatalErr(err, "%s", "DBPrtChanges: rows.Scan/skipping failed")
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
				curEtMsecs = prvETMsecs
				break // Escape from skipping. Resume high-level scan.
			}
		}
	}

	// Done. How many changed results?
	if counter == 0 {
		msg = "No test cases with changed results"
		logger("%s", msg)
	} else {
		msg = fmt.Sprintf("Number of test cases with changed results = %d", counter)
		loggerSkip("%s", msg)
	}

}

/*
DBPrtLastPass - Print result records where the test case currently fails but passed sometime in the past.
Note that history rows are ordered by test case name, date descending, and time descending.
*/
func DBPrtLastPass() {

	// Construct Query.
	sqlText := "SELECT " + colTestCase + ", " + colJvm + ", " + colDate + ", " + colTime +
		", " + colResult + ", COALESCE(" + colFailText + ", 'n/a'), " + colETmsecs + " FROM " + tableHistory +
		" ORDER BY " + colTestCase + ", " + colDate + " DESC, " + colTime + " DESC"

	// Previous result record w.r.t. the test case, date, and time
	var prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult, prvFailText = "", "", "", "", "", ""
	var prvETMsecs int

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curFailText = "", "", "", "", "", ""
	var curETMsecs int

	// Get all the history table rows.
	var msg string
	counter := 0
	rows, ok := sqlQuery(sqlText)
	defer closeRows(rows, "DBPrtLastPass")
	if !ok {
		return
	}
	logger("DBPrtLastPass: Looking for test cases that currently fail but passed sometime previously .....")

	// High-level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText, &prvETMsecs)
		if err != nil {
			fatalErr(err, "%s", "DDBPrtLastPass: rows.Scan failed")
		}

		// Same test case as last test case? The first time, the current fields are spaces.
		// So, the next test always fails on the very first row.
		if curTestCase != prvTestCase {
			// No, this is a new test case.
			// Make it current and continue.
			curTestCase = prvTestCase
			curJvm = prvJvm
			curDateUTC = prvDateUTC
			curTimeUTC = prvTimeUTC
			curResult = prvResult
			curFailText = prvFailText
			curETMsecs = prvETMsecs
			continue
		}

		// Same test case as the last one.
		// Does the first record for the test case indicate failure?
		if curResult == "failed" {
			if prvResult == "passed" {
				// Show the changes.
				counter += 1
				fmt.Printf("\tfailed  >>  %-s  %-8s  %-10s  %-8s  %-6s  %d  %-s\n",
					curTestCase, curJvm, curDateUTC, curTimeUTC, curResult, curETMsecs, curFailText)
				fmt.Printf("\tpassed  >>  %-s  %-8s  %-10s  %-8s  %-6s\n",
					prvTestCase, prvJvm, prvDateUTC, prvTimeUTC, prvResult)
				// Will skip to the next test case.
			} else { // prevResult is a fail
				continue // So, keep scanning within the current test case.
			}
		}

		// Skip to a new test case.
		for rows.Next() {
			// Get next history row back in time.
			err := rows.Scan(&prvTestCase, &prvJvm, &prvDateUTC, &prvTimeUTC, &prvResult, &prvFailText, &prvETMsecs)
			if err != nil {
				fatalErr(err, "%s", "DBPrtLastPass: rows.Scan/skipping failed")
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
				curETMsecs = prvETMsecs
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
	logger("%s", msg)

}

/*
DBDeleteMostRecent - Delete the most recent logged pass/fail record for each test case.
*/
func DBDeleteMostRecent() {

	// Query descending test case, date, and time.
	sqlSelect := "SELECT " + colTestCase + ", " + colJvm + ", " + colDate + " desc, " + colTime + " FROM " + tableHistory + " NOLOCK ORDER BY " + colTestCase + ", " + colDate + " DESC, " + colTime + " DESC"

	// Set up for DELETE operaations.
	deleteFormat := "DELETE FROM " + tableHistory + " WHERE " + colTestCase + " = '%s' AND " + colJvm + "='%s' AND " + colDate + " = '%s' AND " + colTime + " = '%s'"
	var deleteList []string

	// Most current result record w.r.t. date and time
	var prvTestCase = ""
	var curTestCase, curJvm, curDateUTC, curTimeUTC string

	// Get all the history table rows.
	rows, ok := sqlQuery(sqlSelect)
	defer closeRows(rows, "DBDeleteMostRecent")
	if !ok {
		return
	}

	// High-level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC)
		if err != nil {
			fatalErr(err, "%s", "DBDeleteMostRecent: rows.Scan failed")
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
		err := sqlFunc(sqlStmt)
		if err != nil {
			fatalErr(err, "%s", "DBDeleteMostRecent: DELETE failed")
		}
		counter += 1
	}

	// Close and re-open database.
	DBClose()
	DBOpen()

	logger("Removed %d test case result records", counter)

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

	// Open the passfail file for output/replace.
	global := GetGlobalRef()
	file, err := os.Create(global.PassFailFilePath)
	if err != nil {
		fatalErr(err, "os.Open(%s) failed", global.PassFailFilePath)
	}
	deferredCloseFile(file)

	// Query descending test case, date, and time.
	sqlSelect := "SELECT " + colTestCase + ", " + colJvm + ", " + colDate + ", " + colTime +
		", " + colResult + ", COALESCE(" + colFailText + ", 'n/a'), " + colETmsecs + " FROM " + tableHistory +
		" ORDER BY " + colTestCase + ", " + colDate + " DESC, " + colTime + " DESC"

	// Most current result record w.r.t. date and time
	var prvTestCase = ""
	var curTestCase, curJvm, curDateUTC, curTimeUTC, curResult string
	var curFailText any
	var curEtMsecs int

	// Get all the history table rows.
	rows, ok = sqlQuery(sqlSelect)
	defer closeRows(rows, "DBPrintMostRecent")
	if !ok {
		return
	}

	curDir, err := os.Getwd()
	if err != nil {
		fatalErr(err, "%s", "DBPrintMostRecent: os.Getwd failed")
	}
	testsDir := curDir + "/tests/"

	// High-level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC, &curResult, &curFailText, &curEtMsecs)
		if err != nil {
			fatalErr(err, "%s", "DBPrintMostRecent: rows.Scan failed")
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
				fatalErr(err, "%s", errMsg)
			}
			if !ok {
				continue
			}

			// Show the database information.
			fmt.Printf("[%d]  %-s  %-8s  %-6s  %-s\n", counter+1, curTestCase, curJvm, curResult, curFailText)
			_, err := fmt.Fprintf(file, "%s\t%s\n", curTestCase, curResult)
			if err != nil {
				fatalErr(err, "DBPrintMostRecent: writing %s failed", global.PassFailFilePath)
			}
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
DBReportOrDeleteObsolete - Delete database records of obsolete test cases.
*/
func DBReportOrDeleteObsolete(flagDelete bool) {

	var err error
	var ok bool
	var rows *sql.Rows
	deleteFormat := "DELETE FROM " + tableHistory + " WHERE " + colTestCase + " = '%s' AND " + colJvm + "='%s' AND " + colDate + " = '%s' AND " + colTime + " = '%s'"
	var deleteList []string
	counter := 0

	// Query descending test case, date, and time.
	sqlSelect := "SELECT " + colTestCase + ", " + colJvm + ", " + colDate + " desc, " + colTime + " FROM " + tableHistory

	// Most current result record w.r.t. date and time
	var curTestCase, curJvm, curDateUTC, curTimeUTC string

	// Get all the history table rows.
	rows, ok = sqlQuery(sqlSelect)
	defer closeRows(rows, "DBReportOrDeleteObsolete")
	if !ok {
		return
	}

	curDir, err := os.Getwd()
	if err != nil {
		fatalErr(err, "%s", "DBReportOrDeleteObsolete: os.Getwd failed")
	}
	testsDir := curDir + "/tests/"

	// High-level scan.
	for rows.Next() {

		// Get next history row by test case and going back in time.
		err := rows.Scan(&curTestCase, &curJvm, &curDateUTC, &curTimeUTC)
		if err != nil {
			fatalErr(err, "%s", "DBReportOrDeleteObsolete: rows.Scan failed")
		}

		// New test case.
		// Does the curTestCase still exist?
		testCase := testsDir + curTestCase
		ok, err = isDirectory(testCase)
		//fmt.Printf("DEBUG path %s --> %v, %v\n", testCase, ok, err)
		if err != nil {
			errMsg := fmt.Sprintf("DBReportOrDeleteObsolete: isDirectory(%s) failed, err: %v", testCase, err)
			fatalErr(err, "%s", errMsg)
		}
		if !ok {
			counter += 1
			if flagDelete {
				logger("Removing database record for obsolete test case: %s %s %s %s",
					curTestCase, curJvm, curDateUTC, curTimeUTC)
				sqlDelete := fmt.Sprintf(deleteFormat, curTestCase, curJvm, curDateUTC, curTimeUTC)
				deleteList = append(deleteList, sqlDelete)
			} else {
				logger("Orphan database record: %s %s %s %s",
					curTestCase, curJvm, curDateUTC, curTimeUTC)
			}
		}

	}

	if flagDelete {
		// For each delete statement, execute it.
		for _, sqlStmt := range deleteList {
			err := sqlFunc(sqlStmt)
			if err != nil {
				fatalErr(err, "%s", "DBDeleteMostRecent: DELETE failed")
			}
		}
		fmt.Printf("Removed %d database records\n", counter)
	} else {
		fmt.Printf("Found %d orphaned database records\n", counter)
	}

}

/*
DBUpdateTestCaseName - Update test case name.
*/
// DBUpdateTestCaseName updates all records in the history table
// where test_case == oldName, changing it to newName.
func DBUpdateTestCaseName(oldName, newName string) {

	sqlSelect := fmt.Sprintf("UPDATE "+tableHistory+" SET "+colTestCase+" = '%s' WHERE "+colTestCase+" = '%s'", newName, oldName)
	err := sqlFunc(sqlSelect)
	if err != nil {
		fatalErr(err, "DBUpdateTestCaseName: sqlFunc(%s) failed", sqlSelect)
	}

	sqlSelect = fmt.Sprintf("SELECT "+colTestCase+" FROM "+tableHistory+" WHERE "+colTestCase+" = '%s'", newName)
	rows, ok := sqlQuery(sqlSelect)
	defer closeRows(rows, "DBUpdateTestCaseName")
	if !ok {
		fatalText("DBUpdateTestCaseName: sqlQuery(%s) failed", sqlSelect)
	}
	count := 0
	for rows.Next() {
		count++
	}
	fmt.Printf("DBUpdateTestCaseName: %d row(s) updated test case name from %s to %s\n", count, oldName, newName)

}

/*
DBGenerateTestCaseCsv - Write the last count results of a test case to <testCaseName>.csv in the current directory.
If the count is 0, all recorded results of the test case are written.
The first line is a header with the history table column names. The data lines are in
reverse chronological order (newest first): date descending, then time descending.
An existing file of the same name is replaced.
*/
func DBGenerateTestCaseCsv(count int, testCaseName string) {

	if count < 0 {
		fatalText("DBGenerateTestCaseCsv: count must not be negative, got %d", count)
	}

	// In SQLite, a negative LIMIT means no limit.
	limit := count
	if count == 0 {
		limit = -1
	}

	text := fmt.Sprintf(fmtSqlSelectLastN, testCaseName, limit)
	rows, ok := sqlQuery(text)
	defer closeRows(rows, "DBGenerateTestCaseCsv")

	if !ok {
		return
	}

	var history []historyRow
	for rows.Next() {
		history = append(history, scanHistoryRow(rows, "DBGenerateTestCaseCsv"))
	}

	// Do not create an empty file for a test case that has no history.
	if len(history) == 0 {
		logger("DBGenerateTestCaseCsv: no results found for test case %s", testCaseName)
		return
	}

	// Form csvPath.
	curDir, err := os.Getwd()
	if err != nil {
		fatalErr(err, "%s", "DBGenerateTestCaseCsv: os.Getwd() failed")
	}
	csvPath := filepath.Join(curDir, testCaseName+".csv")
	file, err := os.Create(csvPath)
	if err != nil {
		fatalErr(err, "DBGenerateTestCaseCsv: os.Create(%s) failed", csvPath)
	}
	deferredCloseFile(file)

	// encoding/csv quotes fields that contain commas, quotes, or newlines (fail_text may).
	writer := csv.NewWriter(file)
	records := [][]string{{colTestCase, colJvm, colDate, colTime, colResult, colFailText, colETmsecs, colAllocMB}}
	for _, row := range history {
		records = append(records, []string{row.testCase, row.jvm, row.dateUTC, row.timeUTC,
			row.result, row.failText, strconv.Itoa(row.etMsecs), strconv.Itoa(row.allocMB)})
	}
	err = writer.WriteAll(records) // includes Flush
	if err != nil {
		fatalErr(err, "DBGenerateTestCaseCsv: writing %s failed", csvPath)
	}
	err = file.Close()
	if err != nil {
		fatalErr(err, "DBGenerateTestCaseCsv: closing %s failed", csvPath)
	}

	fmt.Printf("DBGenerateTestCaseCsv: wrote %d result(s) to %s\n", len(history), csvPath)

}
