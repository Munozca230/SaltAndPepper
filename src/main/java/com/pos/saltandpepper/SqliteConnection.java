package com.pos.saltandpepper;

import java.sql.*;

/**
 * A utility class for establishing a connection to an SQLite database.
 */
public class SqliteConnection {
    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A connection to the SQLite database.
     * @throws RuntimeException If an error occurs while establishing the connection.
     */
    public static Connection Connector() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish the database connection
            Connection conn = DriverManager.getConnection("jdbc:sqlite:db.db");
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
