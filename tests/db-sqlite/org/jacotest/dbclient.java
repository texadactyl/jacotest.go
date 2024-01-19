package org.jacotest;

import java.sql.*;

public class dbclient {

    static String connUrl = "jdbc:sqlite:test.db"; 
    static String queryString = "SELECT test_case, date_utc, time_utc, result, fail_text FROM history ORDER BY test_case, date_utc DESC, time_utc DESC";

    private static Connection connect() {
        Connection conn = null;
        try {
            System.out.printf("Connection URL: %s\n", connUrl);
            conn = DriverManager.getConnection(connUrl);
            System.out.println("Connected to the jacotest database.");
        } catch (SQLException ex) {
            throw new AssertionError(ex.getMessage());
        }
        return conn;
    }

    private static void query(Connection conn) {
        ResultSet rs;
        try {
            System.out.printf("Query string: %s\n", queryString);
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(queryString);
        } catch (SQLException ex) {
            throw new AssertionError(ex.getMessage());
        }
        try {
            while (rs.next()) {
                System.out.println(rs.getString("test_case") +  "\t" +
                        rs.getString("date_utc") + "\t" +
                        rs.getString("time_utc") + "\t" +
                        rs.getString("result") );
            }
        } catch (SQLException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conn = connect();
        query(conn);
    }
}