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
	_ "github.com/mattn/go-sqlite3" // Import go-sqlite3 library
	"os"
)

const Tracing = false

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
const driverDatabase = "sqlite3"

// Assigned and used at run-time
var pathDatabase string
var sqliteDatabase *sql.DB

/*
Internal function to run an SQL statement and handle any errors.
*/
func sqlFunc(text string) {

	if Tracing {
		msg := fmt.Sprintf("DBTRACE: sqlFunc: %s", text)
		Logger(msg)
	}
	statement, err := sqliteDatabase.Prepare(text) // Prepare SQL Statement
	if err != nil {
		FmtFatal("sqlFunc: sqliteDatabase.Prepare failed", text, err)
	}

	_, err = statement.Exec() // Execute SQL Statements
	if err != nil {
		FmtFatal("sqlFunc: statement.Exec failed", text, err)
	}

}

/*
Internal function to initialise a jacotest database.

* Create database (includes file creation/re-creation).
* Create history table and all of its columns, a combination of which is the primary index.
* Create secondary indexes.
*/
func initDB() {

	text := "CREATE TABLE " + tableHistory + " ("
	text += colTestCase + " VARCHAR NOT NULL, "
	text += colJvm + " VARCHAR NOT NULL, "
	text += colDate + " VARCHAR NOT NULL, "
	text += colTime + " VARCHAR NOT NULL, "
	text += colResult + " VARCHAR NOT NULL, "
	text += colFailText + " VARCHAR, "
	text += "PRIMARY KEY (" + colTestCase + ", " + colDate + ", " + colTime + ") )"
	if Tracing {
		msg := fmt.Sprintf("DBTRACE: initDB: %s", text)
		Logger(msg)
	}
	sqlFunc(text)

	text = "CREATE INDEX " + ixTestCaseName + " ON " + tableHistory + " (" + colTestCase + ")"
	if Tracing {
		msg := fmt.Sprintf("DBTRACE: initDB: %s", text)
		Logger(msg)
	}
	sqlFunc(text)

}

/*
DBOpen - Database Open

* If the database directory is not present, create it.
* If the history.db file in the database directory is not present, call initDB.
* Connect to DB.
* Validate DB.
*/
func DBOpen() {

	if Tracing {
		Logger("DBTRACE: DBOpen begins")
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
		if Tracing {
			Logger("DBTRACE: DBOpen database file inaccessible: " + err.Error())
		}
		sqliteDatabase, err = sql.Open(driverDatabase, pathDatabase)
		if err != nil {
			msg := fmt.Sprintf("DBOpen: sql.Open(create) with driver %s failed", driverDatabase)
			FmtFatal(msg, pathDatabase, err)
		}
		initDB()
		return
	}

	// Connect to pre-existing database
	if Tracing {
		Logger("DBTRACE: DBOpen database file exists")
	}
	sqliteDatabase, err = sql.Open(driverDatabase, pathDatabase)
	if err != nil {
		msg := fmt.Sprintf("DBOpen: sql.Open(pre-existing) with driver %s failed", driverDatabase)
		FmtFatal(msg, pathDatabase, err)
	}

	// sqliteDatabase stays open until process exit

	// TODO: Validate database

}

/*
DBClose - Store a PASSED jacotest test case result.Close the database
*/
func DBClose() {

	if Tracing {
		Logger("DBTRACE: DBClose")
	}
	err := sqliteDatabase.Close()
	if err != nil {
		msg := fmt.Sprintf("DBClose: sql.Close with driver %s failed", driverDatabase)
		FmtFatal(msg, pathDatabase, err)
	}

}

/*
DBStorePassed - Store a PASSED jacotest test case result.
*/
func DBStorePassed(testCaseName string) {
	global := GetGlobalRef()
	q_jvm := "'" + global.JvmName + "'"
	q_tcn := "'" + testCaseName + "'"
	q_date := "'" + getUtcDate() + "'"
	q_time := "'" + getUtcTime() + "'"
	text := "INSERT INTO " + tableHistory + " VALUES("
	text += q_tcn + ", " + q_jvm + ", " + q_date + ", " + q_time + ", 'passed', NULL)"
	if Tracing {
		msg := fmt.Sprintf("DBTRACE: DBStorePassed: %s", text)
		Logger(msg)
	}
	sqlFunc(text)
}

/*
DBStoreFailed - Store a FAILED jacotest test case result.
*/
func DBStoreFailed(testCaseName, failText string) {
	global := GetGlobalRef()
	q_jvm := "'" + global.JvmName + "'"
	q_tcn := "'" + testCaseName + "'"
	q_date := "'" + getUtcDate() + "'"
	q_time := "'" + getUtcTime() + "'"
	q_fail_text := "'" + failText + "'"
	text := "INSERT INTO " + tableHistory + " VALUES("
	text += q_tcn + ", " + q_jvm + ", " + q_date + ", " + q_time + ", 'failed', " + q_fail_text + ")"
	if Tracing {
		msg := fmt.Sprintf("DBTRACE: DBStoreFailed: %s", text)
		Logger(msg)
	}
	sqlFunc(text)
}
