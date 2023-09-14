package com.pos.saltandpepper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Controller for the registration view of the application.
 */
public class RegisterController {

    /**
     * Model for registration-related operations.
     */
    public RegisterModel registerModel = new RegisterModel();

    @FXML
    private Button btnGoToLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label labelWarning;

    /**
     * Handles user registration when the "Register" button is clicked.
     *
     * @param event The event that triggers the action (e.g., clicking the "Register" button).
     * @throws SQLException If an error occurs during user registration or database access.
     */
    @FXML
    void registerUser(ActionEvent event) throws SQLException {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (registerModel.isUserOnDatabase(user)) {
            labelWarning.setText("Username already in use");
        } else {
            registerModel.saveUserAndPassword(user, pass);
            txtUsername.clear();
            txtPassword.clear();
            labelWarning.setText("User registered");
        }
    }

    /**
     * Switches to the login view when the "Go to Login" button is clicked.
     *
     * @param event The event that triggers the action (e.g., clicking the "Go to Login" button).
     */
    @FXML
    void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGoToLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
