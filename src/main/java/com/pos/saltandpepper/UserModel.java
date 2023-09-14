package com.pos.saltandpepper;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Represents the user-related functionality in the application.
 */
public class UserModel {
    private static final String PEPPER = "my_secure_password";

    /**
     * Updates the password for a user in the database.
     *
     * @param username    The username of the user whose password is to be updated.
     * @param newPassword The new password to set for the user.
     */
    public void updatePassword(String username, String newPassword) {
        try {
            // Create a new salt
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);

            // Combine the pepper, salt, and new password
            String saltedPassword = PEPPER + Base64.getEncoder().encodeToString(salt) + newPassword;

            Connection connection = SqliteConnection.Connector();

            // Prepare the SQL statement to update the password
            String sql = "UPDATE user SET password = ?, salt = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, saltedPassword);
            preparedStatement.setBytes(2, salt);
            preparedStatement.setString(3, username);

            int rowsUpdated = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Failed to update password.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update password: " + e.getMessage());
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param username The username of the user to be deleted.
     */
    public void deleteUser(String username) {
        try {
            Connection connection = SqliteConnection.Connector();

            // Prepare the SQL statement to delete the user
            String sql = "DELETE FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            int rowsDeleted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete user: " + e.getMessage());
        }
    }
}
