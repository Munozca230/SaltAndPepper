package com.pos.saltandpepper;

import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Model class responsible for handling user registration operations.
 */
public class RegisterModel {
    private static final String PEPPER = "my_secure_password";

    /**
     * Saves a new user and their password in the database after hashing the password.
     *
     * @param user The username of the new user.
     * @param pass The plain text password of the new user.
     */
    public void saveUserAndPassword(String user, String pass) {
        try {
            // Generate a salt
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);

            // Combine the pepper, salt, and password
            String hashedPassword = hashedPassword(PEPPER, salt, pass);

            Connection connection = SqliteConnection.Connector();

            String sql = "INSERT INTO user (username, password, salt) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setBytes(3, salt);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Saved successfully");
        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }

    /**
     * Checks if a user with the specified username already exists in the database.
     *
     * @param username The username to check for existence.
     * @return True if the username exists in the database, false otherwise.
     * @throws SQLException If an error occurs during database access.
     */
    public boolean isUserOnDatabase(String username) throws SQLException {
        Connection connection = null;
        try {
            connection = SqliteConnection.Connector();

            String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return count > 0;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }
        return false;
    }

    public String hashedPassword(String pepper, byte[] salt, String pass) {
        String saltedPassword = pepper + Base64.getEncoder().encodeToString(salt) + pass;
        return BCrypt.hashpw(saltedPassword, BCrypt.gensalt());
    }
}
