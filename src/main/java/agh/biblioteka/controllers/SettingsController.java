package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.app.PasswordManagement;
import agh.biblioteka.app.RegexPatterns;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class SettingsController {

    @FXML
    private Pane pane;

    @FXML
    private PasswordField actualPassField;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField confirmNewPassField;

    @FXML
    private TextField newPhoneTextField;

    @FXML
    private TextField newEmailTextField;

    @FXML
    private Label promptLabel;

    private String email, password, phoneNumber;

    @FXML
    void backToMenu() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/menu-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeEmail() throws SQLException, NoSuchAlgorithmException {
        if (!RegexPatterns.matchEmail(newEmailTextField.getText())) {
            newEmailTextField.setPromptText("Incorrect email");
            newEmailTextField.clear();
            return;
        } else {
            email = newEmailTextField.getText().toLowerCase();
        }

        if (passwordCorrect()) {
            PreparedStatement updateEmail = Main.getDB().prepareQuery("UPDATE users SET email = (?) WHERE uid = (?)");
            updateEmail.setString(1, email);
            updateEmail.setInt(2, Main.getUserId());
            updateEmail.execute();
            promptLabel.setText("Email updated!");
        }
    }

    @FXML
    void changePassword() throws SQLException, NoSuchAlgorithmException {
        if (!RegexPatterns.matchPassword(newPassField.getText())) {
            newPassField.setPromptText("Incorrect password");
            newPassField.clear();
            return;
        } else {
            if (!newPassField.getText().equals(confirmNewPassField.getText())) {
                newPassField.setPromptText("Enter same passwords");
                newPassField.clear();
                confirmNewPassField.clear();
                return;
            }
        }

        if (passwordCorrect()) {
            password = newPassField.getText();
            String salt, hashedPassword;
            salt = PasswordManagement.createSalt();
            hashedPassword = PasswordManagement.hashPassword(password, salt);

            PreparedStatement updatePassword = Main.getDB().prepareQuery("UPDATE users SET salt = (?), password = (?) WHERE uid = (?)");
            updatePassword.setString(1, salt);
            updatePassword.setString(2, hashedPassword);
            updatePassword.setInt(3, Main.getUserId());
            updatePassword.execute();

            promptLabel.setText("Password updated!");
        }
    }

    @FXML
    void changePhoneNumber() throws SQLException, NoSuchAlgorithmException {
        if (!RegexPatterns.matchPhone(newPhoneTextField.getText())) {
            newPhoneTextField.setPromptText("Incorrect phone number");
            newPhoneTextField.clear();
            return;
        } else {
            phoneNumber = newPhoneTextField.getText();
        }

        if (passwordCorrect()) {
            PreparedStatement updatePhone = Main.getDB().prepareQuery("UPDATE users SET phone_number = (?) WHERE uid = (?)");
            updatePhone.setInt(1, Integer.parseInt(phoneNumber));
            updatePhone.setInt(2, Main.getUserId());
            updatePhone.execute();
            promptLabel.setText("Phone number updated!");
        }
    }


    private boolean passwordCorrect() throws SQLException, NoSuchAlgorithmException {
        String salt, password, hashedPassword, hashedPasswordDB;
        boolean correctPassword;

        password = actualPassField.getText();

        PreparedStatement selectPassword = Main.getDB().prepareQuery("SELECT password, salt FROM users WHERE uid = (?)");
        selectPassword.setInt(1, Main.getUserId());
        ResultSet resultPassword = selectPassword.executeQuery();
        resultPassword.next();

        salt = resultPassword.getString("salt");
        hashedPasswordDB = resultPassword.getString("password");

        hashedPassword = PasswordManagement.hashPassword(password, salt);

        correctPassword = hashedPassword.equalsIgnoreCase(hashedPasswordDB);

        if (correctPassword) {
            return true;
        } else {
            promptLabel.setText("Actual password incorrect");
            return false;
        }
    }

}
