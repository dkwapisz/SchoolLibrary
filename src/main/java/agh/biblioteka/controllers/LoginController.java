package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.app.PasswordManagement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Pane pane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Label promptLabel;

    @FXML
    void login() throws IOException, NoSuchAlgorithmException, SQLException {
        if (checkCredentials()) {
            Stage stage = (Stage) pane.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/menu-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean checkCredentials() throws NoSuchAlgorithmException, SQLException {
        String username, salt, password, hashedPassword, hashedPasswordDB;

        username = usernameTextField.getText().toLowerCase();

        if (username.equals("")) {
            promptLabel.setText("Incorrect credentials");
            return false;
        }

        PreparedStatement selectUser = Main.getDB().prepareQuery("SELECT uid, username, salt, password FROM users WHERE username = (?)");
        selectUser.setString(1, username);
        ResultSet resultUser = selectUser.executeQuery();

        if (!resultUser.next()) {
            promptLabel.setText("User not exist");
            return false;
        }

        salt = resultUser.getString("salt");
        password = passTextField.getText();
        hashedPassword = PasswordManagement.hashPassword(password, salt);
        hashedPasswordDB = resultUser.getString("password");

        if (hashedPassword.equalsIgnoreCase(hashedPasswordDB)) {

            Main.setUserId(resultUser.getInt("uid"));

            PreparedStatement selectAdmin = Main.getDB().prepareQuery("SELECT admin_id FROM admin WHERE uid = (?)");
            selectAdmin.setInt(1, Main.getUserId());
            ResultSet resultAdmin = selectAdmin.executeQuery();

            Main.setADMIN(resultAdmin.next());

            return true;
        } else {
            promptLabel.setText("Incorrect password");
            return false;
        }
    }

    @FXML
    void register() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/register-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void exit() {
        Platform.exit();
    }

}
