package com.pos.saltandpepper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller class for user-related actions and views.
 */
public class UserController {
    private UserModel userModel = new UserModel();
    private LoginModel loginModel = new LoginModel();

    @FXML
    private Label labelText;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtOldPassword;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnDelete;
    private String username;

    /**
     * Handles the change password action for the user.
     *
     * @param actionEvent The event triggered by the user's action.
     */
    public void changePassword(ActionEvent actionEvent) {
        String pass = txtPassword.getText();
        String oldPass = txtOldPassword.getText();

        if (!loginModel.verifyCredentials(username, oldPass)) {
            labelText.setText("Incorrect password");
        } else {
            labelText.setText("Password changed");
            userModel.updatePassword(username, pass);
            txtOldPassword.clear();
            txtPassword.clear();
        }
    }

    /**
     * Handles the go back action to the login view.
     *
     * @param event The event triggered by the user's action.
     */
    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the delete user action, showing a confirmation dialog before deletion.
     *
     * @param actionEvent The event triggered by the user's action.
     */
    public void deleteUser(ActionEvent actionEvent) {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete User");
        alert.setContentText("Are you sure you want to delete this user?");

        // Show the alert and wait for user response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            userModel.deleteUser(username);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnGoBack.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the username for the current user session.
     *
     * @param username The username of the logged-in user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
