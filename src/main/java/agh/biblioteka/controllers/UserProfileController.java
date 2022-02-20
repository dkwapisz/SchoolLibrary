package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.app.PasswordManagement;
import agh.biblioteka.app.RegexPatterns;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserProfileController {

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
    private Button backToMenuButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField actualRentalsLabel;

    @FXML
    private Label forthcomingRentalEndLabel;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label promptLabel;

    @FXML
    private Button makeAdminButton;

    @FXML
    private Label adminLabel;

    private String name, surname, studentClass, username, email, phoneNumber;

    public void initialize() throws SQLException {
        setButtonsUnavailable();
        if (Main.isADMIN()) {
            editButton.setVisible(true);
            editButton.setDisable(false);

            PreparedStatement selectAdmin = Main.getDB().prepareQuery("SELECT uid FROM admin WHERE uid = (?)");
            selectAdmin.setInt(1, Main.getActualUid());
            ResultSet resultAdmin = selectAdmin.executeQuery();

            if (Main.getActualUid() == Main.getUserId() || resultAdmin.next()) {
                makeAdminButton.setVisible(false);
                makeAdminButton.setDisable(true);
                adminLabel.setText("ADMIN ACCOUNT");
            }
        } else {
            editButton.setVisible(false);
            editButton.setDisable(true);
            makeAdminButton.setVisible(false);
            makeAdminButton.setDisable(true);
        }

        PreparedStatement actualRentals = Main.getDB().prepareQuery("SELECT bid, end_of_rental FROM actual_rentals WHERE uid = (?)");
        actualRentals.setInt(1, Main.getActualUid());
        ResultSet resultRentals = actualRentals.executeQuery();

        int rentalsCount = 0;
        long lastDifference = 999999;
        String closestRentalDate = "";
        int closestRentalBid = 0;
        String closestRentalTitle = "";

        while (resultRentals.next()) {
            rentalsCount++;
            if (lastDifference > (resultRentals.getDate("end_of_rental").toLocalDate().toEpochDay() - LocalDate.now().toEpochDay())) {
                lastDifference = resultRentals.getDate("end_of_rental").toLocalDate().toEpochDay() - LocalDate.now().toEpochDay();
                closestRentalDate = String.valueOf(resultRentals.getDate("end_of_rental"));
                closestRentalBid = resultRentals.getInt("bid");
            }
        }

        if (rentalsCount > 0) {
            PreparedStatement getBook = Main.getDB().prepareQuery("SELECT title FROM books_list WHERE bid = (?)");
            getBook.setInt(1, closestRentalBid);
            ResultSet resultBook = getBook.executeQuery();
            if (resultBook.next()) {
                closestRentalTitle = resultBook.getString("title");
            }
        }

        PreparedStatement selectUser = Main.getDB().prepareQuery("SELECT username, name, surname, class, email, phone_number FROM users WHERE uid = (?)");
        selectUser.setInt(1, Main.getActualUid());
        ResultSet resultUser = selectUser.executeQuery();
        resultUser.next();

        usernameTextField.setText(resultUser.getString("username"));
        nameTextField.setText(resultUser.getString("name"));
        surnameTextField.setText(resultUser.getString("surname"));
        classTextField.setText(resultUser.getString("class"));
        emailTextField.setText(resultUser.getString("email"));
        phoneNumberTextField.setText(String.valueOf(resultUser.getInt("phone_number")));
        actualRentalsLabel.setText("Actual rentals: " + rentalsCount); //calculate actual rentals

        if (classTextField.getText() == null) {
            classTextField.setText("");
        }

        if (rentalsCount > 0) {
            forthcomingRentalEndLabel.setText("The forthcoming end of the rental: " + closestRentalTitle + " " + closestRentalDate);
        } else {
            forthcomingRentalEndLabel.setText("You do not have any rentals.");
        }

    }

    @FXML
    void makeAdmin() throws SQLException {

        PreparedStatement insertAdmin = Main.getDB().prepareQuery("INSERT INTO admin(uid) VALUES (?)");
        insertAdmin.setInt(1, Main.getActualUid());
        insertAdmin.execute();

        promptLabel.setText("User " + usernameTextField.getText() + " became admin");
        makeAdminButton.setVisible(false);
        makeAdminButton.setDisable(true);
    }

    @FXML
    void backToMenu() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/menu-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void confirm() throws SQLException {
        if (checkCredentials()) {
            setData();

            PreparedStatement updateData = Main.getDB().prepareQuery("UPDATE users SET name = (?), surname = (?), class = (?), email = (?), username = (?), phone_number = (?) WHERE uid = (?)");
            updateData.setString(1, name);
            updateData.setString(2, surname);
            updateData.setString(3, studentClass);
            updateData.setString(4, email);
            updateData.setString(5, username);
            updateData.setInt(6, Integer.parseInt(phoneNumber));
            updateData.setInt(7, Main.getActualUid());
            updateData.execute();

            setButtonsUnavailable();
            promptLabel.setText("You change data successfully");
        }
    }

    @FXML
    void delete() throws SQLException {

        PreparedStatement deleteUser = Main.getDB().prepareQuery("DELETE FROM users WHERE uid = (?)");
        deleteUser.setInt(1, Main.getActualUid());
        deleteUser.execute();

        promptLabel.setText("You deleted user from database successfully");
        setButtonsUnavailable();
        editButton.setDisable(true);
        editButton.setVisible(false);
        usernameTextField.clear();
        nameTextField.clear();
        surnameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
        classTextField.clear();
        actualRentalsLabel.clear();
        forthcomingRentalEndLabel.setText("");
    }

    @FXML
    void edit() {
        promptLabel.setText("");
        setButtonsAvailable();
    }

    @FXML
    void resetPassword() throws NoSuchAlgorithmException, SQLException {
        String password = PasswordManagement.generateRandomPassword();
        String salt = PasswordManagement.createSalt();
        String hashedPassword = PasswordManagement.hashPassword(password, salt);

        PreparedStatement changePassword = Main.getDB().prepareQuery("UPDATE users SET salt = (?), password = (?) WHERE uid = (?)");
        changePassword.setString(1, salt);
        changePassword.setString(2, hashedPassword);
        changePassword.setInt(3, Main.getActualUid());
        changePassword.execute();

        promptLabel.setText("Generated new password: " + password);
    }

    private void setData() {
        name = nameTextField.getText().substring(0, 1).toUpperCase() + nameTextField.getText().substring(1).toLowerCase();
        surname = surnameTextField.getText().substring(0, 1).toUpperCase() + surnameTextField.getText().substring(1).toLowerCase();
        username = usernameTextField.getText().toLowerCase();
        email = emailTextField.getText().toLowerCase();
        phoneNumber = phoneNumberTextField.getText();

        if (!classTextField.getText().equals("")) {
            studentClass = classTextField.getText().charAt(0) + classTextField.getText().substring(1).toUpperCase();
        } else {
            studentClass = null;
        }
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

        if (!isCorrect) {
            promptLabel.setText("Incorrect data.");
        }

        return isCorrect;
    }

    private void setButtonsAvailable() {
        confirmButton.setVisible(true);
        confirmButton.setDisable(false);
        resetPasswordButton.setVisible(true);
        resetPasswordButton.setDisable(false);

        if (!(Main.getActualUid() == Main.getUserId())) {
            deleteUserButton.setVisible(true);
            deleteUserButton.setDisable(false);
        }

        nameTextField.setDisable(false);
        surnameTextField.setDisable(false);
        emailTextField.setDisable(false);
        phoneNumberTextField.setDisable(false);
        classTextField.setDisable(false);
        usernameTextField.setDisable(false);

        nameTextField.setEditable(true);
        surnameTextField.setEditable(true);
        emailTextField.setEditable(true);
        phoneNumberTextField.setEditable(true);
        classTextField.setEditable(true);
        usernameTextField.setEditable(true);
    }

    private void setButtonsUnavailable() {
        confirmButton.setVisible(false);
        confirmButton.setDisable(true);
        resetPasswordButton.setVisible(false);
        resetPasswordButton.setDisable(true);
        deleteUserButton.setVisible(false);
        deleteUserButton.setDisable(true);

        nameTextField.setDisable(true);
        surnameTextField.setDisable(true);
        emailTextField.setDisable(true);
        phoneNumberTextField.setDisable(true);
        classTextField.setDisable(true);
        usernameTextField.setDisable(true);

        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        emailTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        classTextField.setEditable(false);
        usernameTextField.setEditable(false);
    }

}
