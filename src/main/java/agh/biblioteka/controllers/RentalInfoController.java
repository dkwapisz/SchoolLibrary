package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalInfoController {

    @FXML
    private Pane pane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Hyperlink userHyperlink;

    @FXML
    private Label promptLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button deleteRentalButton;

    @FXML
    private Button editRentalButton;

    public void initialize() throws SQLException {

        PreparedStatement getUserInfo = Main.getDB().prepareQuery("SELECT username FROM users WHERE uid = (?)");
        getUserInfo.setInt(1, Main.getActualUid());
        ResultSet resultUser = getUserInfo.executeQuery();
        resultUser.next();

        PreparedStatement getBookInfo = Main.getDB().prepareQuery("SELECT title, author, start_of_rental, end_of_rental FROM books_list INNER JOIN actual_rentals USING(bid) WHERE rid = (?)");
        getBookInfo.setInt(1, Main.getActualRid());
        ResultSet resultBook = getBookInfo.executeQuery();
        resultBook.next();

        titleLabel.setText("Title: " + resultBook.getString("title"));
        authorLabel.setText("Author: " + resultBook.getString("author"));
        userHyperlink.setText("Username: " + resultUser.getString("username"));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String startDate = format.format(resultBook.getDate("start_of_rental"));
        String endDate = format.format(resultBook.getDate("end_of_rental"));

        startDatePicker.setValue(setDate(startDate));
        endDatePicker.setValue(setDate(endDate));

        setButtonsUnavailable();
    }

    @FXML
    void backToRental() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/searchRentals-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void editRental() {
        promptLabel.setText("");
        setButtonsAvailable();
    }

    @FXML
    void goToUser() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/userProfile-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteRental() throws SQLException {
        PreparedStatement deleteRental = Main.getDB().prepareQuery("DELETE FROM actual_rentals WHERE rid = (?)");
        deleteRental.setInt(1, Main.getActualRid());
        deleteRental.execute();

        editRentalButton.setDisable(true);
        editRentalButton.setVisible(false);

        promptLabel.setText("Deleted successfully.");
        setButtonsUnavailable();
    }

    @FXML
    void confirmEdit() throws SQLException {
        PreparedStatement updateRental = Main.getDB().prepareQuery("UPDATE actual_rentals SET start_of_rental = (?), end_of_rental = (?) WHERE rid = (?)");
        updateRental.setDate(1, Date.valueOf(startDatePicker.getValue()));
        updateRental.setDate(2, Date.valueOf(endDatePicker.getValue()));
        updateRental.setInt(3, Main.getActualRid());
        updateRental.execute();

        promptLabel.setText("Edited successfully.");
        setButtonsUnavailable();
    }

    private void setButtonsAvailable() {
        startDatePicker.setEditable(true);
        endDatePicker.setEditable(true);
        startDatePicker.setDisable(false);
        endDatePicker.setDisable(false);
        confirmButton.setVisible(true);
        confirmButton.setDisable(false);
        deleteRentalButton.setVisible(true);
        deleteRentalButton.setDisable(false);
    }

    private void setButtonsUnavailable() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        startDatePicker.setDisable(true);
        endDatePicker.setDisable(true);
        confirmButton.setVisible(false);
        confirmButton.setDisable(true);
        deleteRentalButton.setVisible(false);
        deleteRentalButton.setDisable(true);
    }

    public static LocalDate setDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

}
