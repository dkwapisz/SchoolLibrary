package agh.biblioteka.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import agh.biblioteka.app.Main;
import agh.biblioteka.app.PasswordManagement;
import agh.biblioteka.app.RegexPatterns;
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

public class RegisterController {

    @FXML
    private Pane pane;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField classTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private PasswordField confirmPassTextField;

    @FXML
    private Button backToLoginButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private Label promptLabel;

    private String name;
    private String surname;
    private String studentClass;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String hashedPassword;
    private String salt;

    @FXML
    void createAccount() throws NoSuchAlgorithmException, SQLException {

        if (checkCredentials()) {
            setData();
            if (!userExist()) {
                salt = PasswordManagement.createSalt();
                hashedPassword = PasswordManagement.hashPassword(password, salt);

                PreparedStatement register = Main.getDB().prepareQuery("INSERT INTO users(name, surname, class, username, salt, password, email, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                register.setString(1, name);
                register.setString(2, surname);
                register.setString(3, studentClass);
                register.setString(4, username);
                register.setString(5, salt);
                register.setString(6, hashedPassword);
                register.setString(7, email);
                register.setInt(8, Integer.parseInt(phoneNumber));
                register.execute();

                promptLabel.setText("Account created. Now you can login.");
            } else {
                promptLabel.setText("Username, phone number or email already exists.");
            }
        }
    }

    @FXML
    void backToLogin() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/login-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setData() {
        name = nameTextField.getText().substring(0, 1).toUpperCase() + nameTextField.getText().substring(1).toLowerCase();
        surname = surnameTextField.getText().substring(0, 1).toUpperCase() + surnameTextField.getText().substring(1).toLowerCase();
        username = usernameTextField.getText().toLowerCase();
        email = emailTextField.getText().toLowerCase();
        phoneNumber = phoneNumberTextField.getText();
        password = passTextField.getText();

        if (!classTextField.getText().equals("")) {
            studentClass = classTextField.getText().charAt(0) + classTextField.getText().substring(1).toUpperCase();
        } else {
            studentClass = null;
        }
    }

    private boolean userExist() throws SQLException {

        PreparedStatement checkUser = Main.getDB().prepareQuery("SELECT username FROM users WHERE username = (?) OR email = (?) OR phone_number = (?)");
        checkUser.setString(1, username);
        checkUser.setString(2, email);
        checkUser.setInt(3, Integer.parseInt(phoneNumber));

        ResultSet result = checkUser.executeQuery();

        return result.next();
    }

    private boolean checkCredentials() {
        boolean isCorrect = true;

        if (!RegexPatterns.matchName(nameTextField.getText())) {
            nameTextField.setPromptText("Incorrect name");
            nameTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchName(surnameTextField.getText())) {
            surnameTextField.setPromptText("Incorrect surname");
            surnameTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchEmail(emailTextField.getText())) {
            emailTextField.setPromptText("Incorrect email");
            emailTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchClass(classTextField.getText()) && !classTextField.getText().equals("")) {
            classTextField.setPromptText("Incorrect class");
            classTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchUsername(usernameTextField.getText())) {
            usernameTextField.setPromptText("Incorrect username");
            usernameTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchPhone(phoneNumberTextField.getText())) {
            phoneNumberTextField.setPromptText("Incorrect number");
            phoneNumberTextField.clear();
            isCorrect = false;
        }
        if (!RegexPatterns.matchPassword(passTextField.getText())) {
            passTextField.setPromptText("Incorrect password");
            passTextField.clear();
            confirmPassTextField.clear();
            isCorrect = false;
        }
        if (!passTextField.getText().equals(confirmPassTextField.getText())) {
            passTextField.setPromptText("Enter same passwords");
            passTextField.clear();
            confirmPassTextField.clear();
            isCorrect = false;
        }

        if (!isCorrect) {
            promptLabel.setText("Incorrect register data.");
        }

        return isCorrect;
    }

}
