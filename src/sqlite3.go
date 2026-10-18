package main

/*
Database access for jacotest. Every SQLite operation is in this file.

Downloads + installs:
* go get modernc.org/sqlite (pure Go driver, no cgo)
* go install -v ./...
* https://www.sqlite.org/download.html (API for the Browser)
* https://sqlitebrowser.org/dl/ (Browser)

The history table has one row per test case execution. Its primary key is
(test_case, date_utc, time_utc). Rows for a test case are read newest first.
*/

import (
	"bufio"
	"database/sql"
	"encoding/csv"
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"time"

	_ "modernc.org/sqlite"
)

var sqlTracing = false

// Wait before the single retry of a failed INSERT.
const msecsSleep = 1000

// How long SQLite waits on a lock held by another process before returning SQLITE_BUSY.
const busyTimeoutMsecs = 5000

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

// Values stored in the result column
const resultPassed = "passed"
const resultFailed = "failed"

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

// SQL statements that do not change at run-time.
var (
	// Select the list for a complete history row, in the order that scanHistoryRow expects.
	// Nullable columns are given default values so that Scan will never see NULL.
	sqlSelectColumns = fmt.Sprintf(
		"%s, %s, %s, %s, %s, COALESCE(%s, '%s') AS %s, COALESCE(%s, %d) AS %s, COALESCE(%s, %d) AS %s",
		colTestCase, colJvm, colDate, colTime, colResult,
		colFailText, failTextNone, colFailText,
		colETmsecs, etUnknown, colETmsecs,
		colAllocMB, allocUnknown, colAllocMB)

	// All history rows, grouped by test case, newest first within each test case.
	sqlSelectHistory = fmt.Sprintf(
		"SELECT %s FROM %s ORDER BY %s, %s DESC, %s DESC",
		sqlSelectColumns, tableHistory, colTestCase, colDate, colTime)

	// The newest N rows of one test case (N < 0 means all), again ordered newest first.
	// The inner ORDER BY decides which rows survive the LIMIT. The outer ORDER BY
	// fixes the order of the output, which SQLite does not promise to keep from the inner one.
	sqlSelectLastN = fmt.Sprintf(
		"SELECT * FROM (SELECT %s FROM %s WHERE %s = ? ORDER BY %s DESC, %s DESC LIMIT ?) ORDER BY %s DESC, %s DESC",
		sqlSelectColumns, tableHistory, colTestCase, colDate, colTime, colDate, colTime)

	sqlInsertHistory = fmt.Sprintf(
		"INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
		tableHistory, colTestCase, colJvm, colDate, colTime, colResult, colFailText, colETmsecs, colAllocMB)

	sqlCreateHistory = fmt.Sprintf(
		"CREATE TABLE %s (%s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, "+
			"%s VARCHAR NOT NULL, %s VARCHAR, %s INTEGER DEFAULT %d, %s INTEGER DEFAULT %d, PRIMARY KEY (%s, %s, %s))",
		tableHistory, colTestCase, colJvm, colDate, colTime, colResult, colFailText,
		colETmsecs, etUnknown, colAllocMB, allocUnknown,
		colTestCase, colDate, colTime)

	sqlCreateIndex = fmt.Sprintf("CREATE INDEX %s ON %s (%s)", ixTestCaseName, tableHistory, colTestCase)

	sqlColumnExists = "SELECT COUNT(1) FROM pragma_table_info(?) WHERE name = ?"

	// Number each test case's rows from newest (1) to oldest, then delete the rows numbered 1.
	sqlDeleteMostRecent = fmt.Sprintf(
		"DELETE FROM %s WHERE rowid IN (SELECT rowid FROM (SELECT rowid, ROW_NUMBER() OVER "+
			"(PARTITION BY %s ORDER BY %s DESC, %s DESC) AS rn FROM %s) WHERE rn = 1)",
		tableHistory, colTestCase, colDate, colTime, tableHistory)

	sqlDeleteByTestCase = fmt.Sprintf("DELETE FROM %s WHERE %s = ?", tableHistory, colTestCase)

	sqlRenameTestCase = fmt.Sprintf("UPDATE %s SET %s = ? WHERE %s = ?", tableHistory, colTestCase, colTestCase)
)

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

// execer is satisfied by both *sql.DB and *sql.Tx.
type execer interface {
	Exec(query string, args ...any) (sql.Result, error)
}

/*
execSQL runs one statement that returns no rows, using bound parameters.
It returns the number of rows affected. Errors are returned to the caller, not treated as fatal.
*/
func execSQL(db execer, text string, args ...any) (int64, error) {

	if sqlTracing {
		Logger(fmt.Sprintf("execSQL: SQL: %s", text))
	}

	result, err := db.Exec(text, args...)
	if err != nil {
		return 0, fmt.Errorf("execSQL: Exec(%s) failed: %w", text, err)
	}

	count, err := result.RowsAffected()
	if err != nil {
		return 0, fmt.Errorf("execSQL: RowsAffected(%s) failed: %w", text, err)
	}
	return count, nil

}

/*
sqlExec runs one statement outside an explicit transaction.
SQLite makes a single statement atomic by itself.
*/
func sqlExec(text string, args ...any) (int64, error) {
	return execSQL(dbHandle, text, args...)
}

/*
sqlTx runs fn inside a transaction. If fn returns an error, the transaction is rolled back.
All statements of the transaction must go through tx. The pool is limited to one connection,
so any *sql.Rows still open when sqlTx is called would block it forever.
*/
func sqlTx(fn func(tx *sql.Tx) error) error {

	tx, err := dbHandle.Begin()
	if err != nil {
		return fmt.Errorf("sqlTx: Begin failed: %w", err)
	}

	err = fn(tx)
	if err != nil {
		if rbErr := tx.Rollback(); rbErr != nil {
			return fmt.Errorf("sqlTx: Rollback failed (%v) after: %w", rbErr, err)
		}
		return err
	}

	err = tx.Commit()
	if err != nil {
		return fmt.Errorf("sqlTx: Commit failed: %w", err)
	}
	return nil

}

/*
sqlQuery runs a select query. On failure the error is logged and the second return value is false.
The caller must close the returned rows before issuing any other statement.
*/
func sqlQuery(text string, args ...any) (*sql.Rows, bool) {

	if sqlTracing {
		Logger(fmt.Sprintf("sqlQuery: SQL: %s", text))
	}

	rows, err := dbHandle.Query(text, args...)
	if err != nil {
		LogError(fmt.Sprintf("sqlQuery: dbHandle.Query(%s) failed, err: %s", text, err.Error()))
		return nil, false
	}

	return rows, true

}

/*
scanHistoryRow reads the current row of a result set whose select list is sqlSelectColumns.
*/
func scanHistoryRow(rows *sql.Rows, caller string) historyRow {

	var row historyRow
	err := rows.Scan(&row.testCase, &row.jvm, &row.dateUTC, &row.timeUTC, &row.result,
		&row.failText, &row.etMsecs, &row.allocMB)
	if err != nil {
		FatalErr(fmt.Sprintf("%s: rows.Scan failed", caller), err)
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
		FatalErr(fmt.Sprintf("%s: rows iteration error", caller), iterErr)
	}
	if closeErr != nil {
		FatalErr(fmt.Sprintf("%s: rows.Close failed", caller), closeErr)
	}

}

/*
historyTestCaseDirExists reports whether the test case still has a directory under the 'tests' directory.
*/
func historyTestCaseDirExists(testsDir, testCase, caller string) bool {

	path := filepath.Join(testsDir, testCase)
	ok, err := isDirectory(path)
	if err != nil {
		FatalErr(fmt.Sprintf("%s: isDirectory(%s) failed, err: %v", caller, path, err), err)
	}
	return ok

}

/*
getTestsDir returns the absolute path of the 'tests' directory.
*/
func getTestsDir(caller string) string {

	curDir, err := os.Getwd()
	if err != nil {
		FatalErr(fmt.Sprintf("%s: os.Getwd failed", caller), err)
	}
	return filepath.Join(curDir, dirTests)

}

/*
Internal function to initialize a new jacotest database.

* Create the history table. The primary key is (test_case, date_utc, time_utc).
* Create the secondary index.
Both statements are in one transaction so that a failure does not leave a half-built database.
*/
func initDB() {

	if sqlTracing {
		Logger("initDB: Begin")
	}

	err := sqlTx(func(tx *sql.Tx) error {
		for _, text := range []string{sqlCreateHistory, sqlCreateIndex} {
			if _, err := execSQL(tx, text); err != nil {
				return err
			}
		}
		return nil
	})
	if err != nil {
		FatalErr("initDB: unrecoverable SQL error", err)
	}

	if sqlTracing {
		Logger("initDB: End")
	}

}

/*
addIntColumnIfMissing upgrades a database created before the column existed.
Adding a column with a DEFAULT makes the existing rows read as that default; no UPDATE is needed.
*/
func addIntColumnIfMissing(column string, defaultValue int) {

	if sqlTracing {
		Logger(fmt.Sprintf("addIntColumnIfMissing: column: %s, defaultValue: %d", column, defaultValue))
	}

	var count int
	err := dbHandle.QueryRow(sqlColumnExists, tableHistory, column).Scan(&count)
	if err != nil {
		FatalErr(fmt.Sprintf("addIntColumnIfMissing: check for column %s failed", column), err)
	}
	if count > 0 {
		return
	}

	sqlText := fmt.Sprintf("ALTER TABLE %s ADD COLUMN %s INTEGER DEFAULT %d", tableHistory, column, defaultValue)
	_, err = sqlExec(sqlText)
	if err != nil {
		FatalErr(fmt.Sprintf("addIntColumnIfMissing: '%s' failed", sqlText), err)
	}
	if sqlTracing {
		Logger(fmt.Sprintf("addIntColumnIfMissing: column %s added", column))
	}

}

/*
upgradeSchema brings a pre-existing database up to date with the current schema.
*/
func upgradeSchema() {
	addIntColumnIfMissing(colETmsecs, etUnknown)
	addIntColumnIfMissing(colAllocMB, allocUnknown)
}

/*
DBOpen - Database Open

* If the database directory is not present, create it.
* If the database file in the database directory is not present, create it and call initDB.
* Otherwise, upgrade the schema if it is out of date.
*/
func DBOpen() {

	global := GetGlobalRef()

	sqlTracing = global.FlagVerbose
	if sqlTracing {
		Logger("DBOpen: Begin")
	}

	// Already open.
	if dbIsOpen {
		return
	}

	// Database directory
	err := os.MkdirAll(dirDatabase, 0755)
	if err != nil {
		FatalErr(fmt.Sprintf("DBOpen: MkdirAll(%s) failed", dirDatabase), err)
	}

	// Database file. Only "does not exist" means that a new database is needed.
	pathDatabase = filepath.Join(dirDatabase, fileDatabase)
	_, err = os.Stat(pathDatabase)
	isNew := errors.Is(err, os.ErrNotExist)
	if err != nil && !isNew {
		FatalErr(fmt.Sprintf("DBOpen: Stat(%s) failed", pathDatabase), err)
	}

	// sql.Open does not touch the file; Ping does (creating it if it is new).
	dsn := fmt.Sprintf("%s?_pragma=busy_timeout(%d)", pathDatabase, busyTimeoutMsecs)
	dbHandle, err = sql.Open(driverDatabase, dsn)
	if err != nil {
		FatalErr(fmt.Sprintf("DBOpen: sql.Open(%s) failed", pathDatabase), err)
	}

	// One connection only: avoids SQLITE_BUSY between connections of this process.
	// Consequently, result sets must be closed before the next statement is issued.
	dbHandle.SetMaxOpenConns(1)

	err = dbHandle.Ping()
	if err != nil {
		FatalErr(fmt.Sprintf("DBOpen: cannot connect to %s", pathDatabase), err)
	}
	dbIsOpen = true

	if isNew {
		initDB()
		if sqlTracing {
			Logger("DBOpen: End, database created")
		}
		return
	}

	upgradeSchema()
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
	if sqlTracing {
		Logger("DBClose: Begin")
	}
	err := dbHandle.Close()
	if err != nil {
		FatalErr("DBClose: sql.Close failed", err)
	}
	if sqlTracing {

		Logger("DBClose: End")
	}

}

/*
storeResult inserts one result record for the current JVM, date, and time.
If the insert fails, it is tried once more after a pause (for example, another
process may be holding a lock). A second failure is fatal.
*/
func storeResult(caller, testCaseName, result, failText string, etMsecs, allocMB int) {

	global := GetGlobalRef()

	// The date and time are taken once so that both attempts store the same primary key.
	// The order must match the column list of sqlInsertHistory.
	args := []any{testCaseName, global.JvmName, GetUtcDate(), GetUtcTime(), result, failText, etMsecs, allocMB}

	_, err := sqlExec(sqlInsertHistory, args...)
	if err == nil {
		return
	}

	time.Sleep(msecsSleep * time.Millisecond)
	_, err = sqlExec(sqlInsertHistory, args...)
	if err != nil {
		FatalErr(fmt.Sprintf("%s: 2nd try did not work - Giving up!\n%s", caller, err.Error()), err)
	}
	Logger(fmt.Sprintf("%s: 2nd try is a success: %s", caller, testCaseName))

}

/*
DBStorePassed - Store a PASSED jacotest test case result.
*/
func DBStorePassed(testCaseName string, etMsecs int, allocMB int) {
	storeResult("DBStorePassed", testCaseName, resultPassed, failTextNone, etMsecs, allocMB)
}

/*
DBStoreFailed - Store a FAILED jacotest test case result.
*/
func DBStoreFailed(testCaseName, failText string) {

	// Quotes are replaced (not escaped) so that failure texts stay comparable with
	// the ones already stored in existing databases. See DBPrtChanges.
	failText = strings.Map(func(r rune) rune {
		if r == '\'' || r == '"' {
			return '*'
		}
		return r
	}, failText)

	storeResult("DBStoreFailed", testCaseName, resultFailed, CleanerText(failText, true), etUnknown, allocUnknown)

}

/*
DBPrtChanges - Print result records that have changed since a previous run.
Rows arrive ordered by test case, date descending, and time descending. So, for each
test case, the first row is the latest result and the second row is the previous result.
*/
func DBPrtChanges() {

	rows, ok := sqlQuery(sqlSelectHistory)
	defer closeRows(rows, "DBPrtChanges")
	if !ok {
		return
	}
	Logger("Looking for test cases with changed results .....")

	var cur historyRow // latest result of the test case being scanned
	compared := false  // true once cur has been compared with its previous result
	counter := 0

	for rows.Next() {
		row := scanHistoryRow(rows, "DBPrtChanges")

		// First row of a new test case: it becomes the current result.
		if row.testCase != cur.testCase {
			cur = row
			compared = false
			continue
		}

		// Only the second row of each test case is of interest.
		if compared {
			continue
		}
		compared = true

		if row.result != cur.result || row.failText != cur.failText {
			counter++
			fmt.Printf("\n\tcur  >>  %s  %-8s  %-10s  %-8s  %-6s  %dms  %dMB  %s\n",
				cur.testCase, cur.jvm, cur.dateUTC, cur.timeUTC, cur.result, cur.etMsecs, cur.allocMB, cur.failText)
			fmt.Printf("\tprv  >>  %s  %-8s  %-10s  %-8s  %-6s  %dms  %dMB  %s\n",
				row.testCase, row.jvm, row.dateUTC, row.timeUTC, row.result, row.etMsecs, row.allocMB, row.failText)
		}
	}

	if counter == 0 {
		Logger("No test cases with changed results")
	} else {
		LoggerSkip(fmt.Sprintf("Number of test cases with changed results = %d", counter))
	}

}

/*
DBPrtLastPass - Print result records where the test case currently fails but passed sometime in the past.
For each such test case, the most recent passing record is shown.
*/
func DBPrtLastPass() {

	rows, ok := sqlQuery(sqlSelectHistory)
	defer closeRows(rows, "DBPrtLastPass")

	if !ok {
		return
	}
	Logger("DBPrtLastPass: Looking for test cases that currently fail but passed sometime previously .....")

	var cur historyRow // latest result of the test case being scanned
	reported := false  // true once this test case has been printed
	counter := 0

	for rows.Next() {
		row := scanHistoryRow(rows, "DBPrtLastPass")

		// First row of a new test case: it becomes the current result.
		if row.testCase != cur.testCase {
			cur = row
			reported = false
			continue
		}

		// Older record of the same test case. Only a test case that currently fails
		// and has an earlier pass is reported, and only once.
		if reported || cur.result != resultFailed || row.result != resultPassed {
			continue
		}
		reported = true
		counter++
		fmt.Printf("\tfailed  >>  %s  %-8s  %-10s  %-8s  %-6s  %dms  %dMB  %s\n",
			cur.testCase, cur.jvm, cur.dateUTC, cur.timeUTC, cur.result, cur.etMsecs, cur.allocMB, cur.failText)
		fmt.Printf("\tpassed  >>  %s  %-8s  %-10s  %-8s  %-6s  %dms  %dMB\n",
			row.testCase, row.jvm, row.dateUTC, row.timeUTC, row.result, row.etMsecs, row.allocMB)
	}

	if counter == 0 {
		Logger("No test cases that currently fail but passed previously")
	} else {
		Logger(fmt.Sprintf("Number of test cases that currently fail but passed previously = %d", counter))
	}

}

/*
DBDeleteMostRecent - Delete the most recent logged pass/fail record for each test case.
One statement in one implicit transaction does all the work.
*/
func DBDeleteMostRecent() {

	counter, err := sqlExec(sqlDeleteMostRecent)
	if err != nil {
		FatalErr("DBDeleteMostRecent: DELETE failed", err)
	}

	Logger(fmt.Sprintf("Removed %d test case result records", counter))

}

/*
DBPrintMostRecent - Print the most recent logged pass/fail record for each test case
that still has a directory under tests, and write "test case, TAB, result" lines to the passfail file.
*/
func DBPrintMostRecent() {

	global := GetGlobalRef()

	rows, ok := sqlQuery(sqlSelectHistory)
	defer closeRows(rows, "DBPrintMostRecent")

	if !ok {
		return
	}

	// Open the passfail file for output/replace.
	file, err := os.Create(global.PassFailFilePath)
	if err != nil {
		FatalErr(fmt.Sprintf("os.Create(%s) failed", global.PassFailFilePath), err)
	}
	defer DeferredCloseFile(file)

	writer := bufio.NewWriter(file)
	testsDir := getTestsDir("DBPrintMostRecent")
	lastCase := ""
	counter := 0
	passes := 0
	fails := 0

	for rows.Next() {
		row := scanHistoryRow(rows, "DBPrintMostRecent")

		// Only the first (latest) row of each test case is of interest.
		if row.testCase == lastCase {
			continue
		}
		lastCase = row.testCase

		// Skip test cases that no longer exist.
		if !historyTestCaseDirExists(testsDir, row.testCase, "DBPrintMostRecent") {
			continue
		}

		counter++
		fmt.Printf("[%d]  %s  %-8s  %-6s  %dms  %dMB  %s\n",
			counter, row.testCase, row.jvm, row.result, row.etMsecs, row.allocMB, row.failText)
		_, _ = fmt.Fprintf(writer, "%s\t%s\n", row.testCase, row.result)
		switch row.result {
		case resultPassed:
			passes++
		case resultFailed:
			fails++
		}
	}

	err = writer.Flush()
	if err != nil {
		FatalErr(fmt.Sprintf("DBPrintMostRecent: writing %s failed", global.PassFailFilePath), err)
	}

	fmt.Printf("Printed %d records\n", counter)
	fmt.Printf("Passed test cases: %d\n", passes)
	fmt.Printf("Failed test cases: %d\n", fails)

}

/*
DBReportOrDeleteObsolete - Report, or delete, database records of obsolete test cases.
A test case is obsolete if it no longer has a directory under tests.
*/
func DBReportOrDeleteObsolete(flagDelete bool) {

	rows, ok := sqlQuery(sqlSelectHistory)
	defer closeRows(rows, "DBReportOrDeleteObsolete")

	if !ok {
		return
	}

	testsDir := getTestsDir("DBReportOrDeleteObsolete")
	var obsolete []string // obsolete test case names, each once
	lastCase := ""
	lastExists := true
	counter := 0

	for rows.Next() {
		row := scanHistoryRow(rows, "DBReportOrDeleteObsolete")

		// The directory is checked once per test case, not once per row.
		if row.testCase != lastCase {
			lastCase = row.testCase
			lastExists = historyTestCaseDirExists(testsDir, row.testCase, "DBReportOrDeleteObsolete")
			if !lastExists {
				obsolete = append(obsolete, row.testCase)
			}
		}
		if lastExists {
			continue
		}

		counter++
		if flagDelete {
			Logger(fmt.Sprintf("Removing database record for obsolete test case: %s %s %s %s",
				row.testCase, row.jvm, row.dateUTC, row.timeUTC))
		} else {
			Logger(fmt.Sprintf("Orphan database record: %s %s %s %s",
				row.testCase, row.jvm, row.dateUTC, row.timeUTC))
		}
	}

	if !flagDelete {
		fmt.Printf("Found %d orphaned database records\n", counter)
		return
	}

	var removed int64
	err := sqlTx(func(tx *sql.Tx) error {
		for _, testCase := range obsolete {
			count, err := execSQL(tx, sqlDeleteByTestCase, testCase)
			if err != nil {
				return err
			}
			removed += count
		}
		return nil
	})
	if err != nil {
		FatalErr("DBReportOrDeleteObsolete: DELETE failed", err)
	}
	fmt.Printf("Removed %d database records\n", removed)

}

/*
DBUpdateTestCaseName - Change the test case name in every history record of oldName to newName.
*/
func DBUpdateTestCaseName(oldName, newName string) {

	count, err := sqlExec(sqlRenameTestCase, newName, oldName)
	if err != nil {
		FatalErr(fmt.Sprintf("DBUpdateTestCaseName: update of %s to %s failed", oldName, newName), err)
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
		FatalText(fmt.Sprintf("DBGenerateTestCaseCsv: count must not be negative, got %d", count))
	}

	// In SQLite, a negative LIMIT means no limit.
	limit := count
	if count == 0 {
		limit = -1
	}

	rows, ok := sqlQuery(sqlSelectLastN, testCaseName, limit)
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
		Logger(fmt.Sprintf("DBGenerateTestCaseCsv: no results found for test case %s", testCaseName))
		return
	}

	// Form csvPath.
	curDir, err := os.Getwd()
	if err != nil {
		FatalErr("DBGenerateTestCaseCsv: os.Getwd() failed", err)
	}
	csvPath := filepath.Join(curDir, testCaseName+".csv")
	file, err := os.Create(csvPath)
	if err != nil {
		FatalErr(fmt.Sprintf("DBGenerateTestCaseCsv: os.Create(%s) failed", csvPath), err)
	}
	DeferredCloseFile(file)

	// encoding/csv quotes fields that contain commas, quotes, or newlines (fail_text may).
	writer := csv.NewWriter(file)
	records := [][]string{{colTestCase, colJvm, colDate, colTime, colResult, colFailText, colETmsecs, colAllocMB}}
	for _, row := range history {
		records = append(records, []string{row.testCase, row.jvm, row.dateUTC, row.timeUTC,
			row.result, row.failText, strconv.Itoa(row.etMsecs), strconv.Itoa(row.allocMB)})
	}
	err = writer.WriteAll(records) // includes Flush
	if err != nil {
		FatalErr(fmt.Sprintf("DBGenerateTestCaseCsv: writing %s failed", csvPath), err)
	}
	err = file.Close()
	if err != nil {
		FatalErr(fmt.Sprintf("DBGenerateTestCaseCsv: closing %s failed", csvPath), err)
	}

	fmt.Printf("DBGenerateTestCaseCsv: wrote %d result(s) to %s\n", len(history), csvPath)

}
