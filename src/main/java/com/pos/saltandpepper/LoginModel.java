package com.pos.saltandpepper;

import java.sql.*;
import java.util.Base64;

/**
 * Model class responsible for handling user login operations.
 */
public class LoginModel {
    Connection connection;

    /**
     * Checks if the database connection is active.
     *
     * @return True if the database connection is active, false otherwise.
     */
    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String PEPPER = "my_secure_password";

    /**
     * Verifies user credentials (username and password) against the database.
     *
     * @param username The username to verify.
     * @param password The password to verify.
     * @return True if the provided credentials are valid, false otherwise.
     */
    public boolean verifyCredentials(String username, String password) {
        try {
            Connection connection = SqliteConnection.Connector();

            String sql = "SELECT password, salt FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String saltedPasswordFromDB = resultSet.getString("password");
                byte[] saltFromDB = resultSet.getBytes("salt");

                // Combine everything
                String saltedPasswordToCheck = PEPPER + Base64.getEncoder().encodeToString(saltFromDB) + password;

                resultSet.close();
                preparedStatement.close();
                connection.close();

                return saltedPasswordFromDB.equals(saltedPasswordToCheck);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("failed: " + e.getMessage());
        }
        return false;
    }
}
