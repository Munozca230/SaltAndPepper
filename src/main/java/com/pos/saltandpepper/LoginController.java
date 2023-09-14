package com.pos.saltandpepper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the application's login view.
 */
public class LoginController {

    /**
     * Model for login-related operations.
     */
    public LoginModel loginModel = new LoginModel();

    @FXML
    private Button btnGoToRegister;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public Label labelWarning;

    /**
     * Method executed when attempting to log in.
     *
     * @param event The event that triggers the action (e.g., clicking the login button).
     * @throws SQLException If an error occurs while verifying credentials in the database.
     */
    @FXML
    void loginUser(ActionEvent event) throws SQLException {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (!loginModel.verifyCredentials(user, pass)) {
            labelWarning.setText("Incorrect");
        } else {
            labelWarning.setText("Welcome");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("user-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(scene);

                UserController userController = loader.getController();
                userController.setUsername(user);

                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Switches to the registration view when the "Go to Register" button is clicked.
     *
     * @param event The event that triggers the action (e.g., clicking the "Go to Register" button).
     */
    @FXML
    void goToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGoToRegister.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
