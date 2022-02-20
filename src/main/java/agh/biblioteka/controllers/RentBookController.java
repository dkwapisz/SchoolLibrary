package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RentBookController {

    @FXML
    private Pane pane;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label promptLabel;

    @FXML
    private Button rentBookButton;

    //Renting day limit
    private final int MAX_RENT_LIMIT = 31;

    public void initialize() throws SQLException {

        PreparedStatement getBook = Main.getDB().prepareQuery("SELECT title, author FROM books_list WHERE bid = (?)");
        getBook.setInt(1, Main.getActualBid());
        ResultSet resultBook = getBook.executeQuery();
        resultBook.next();

        titleLabel.setText("Title: " + resultBook.getString("title"));
        authorLabel.setText("Author: " + resultBook.getString("author"));

        PreparedStatement getUserRentals = Main.getDB().prepareQuery("SELECT start_of_rental, end_of_rental FROM actual_rentals WHERE uid = (?) AND bid = (?)");
        getUserRentals.setInt(1, Main.getUserId());
        getUserRentals.setInt(2, Main.getActualBid());
        ResultSet resultUserRentals = getUserRentals.executeQuery();

        PreparedStatement getAvailable = Main.getDB().prepareQuery("SELECT b.number_of_copies - count(a.bid) AS 'available' FROM books_list b LEFT JOIN actual_rentals a USING(bid) WHERE b.bid = (?) GROUP BY b.bid");
        getAvailable.setInt(1, Main.getActualBid());
        ResultSet resultAvailable = getAvailable.executeQuery();
        resultAvailable.next();

        if (resultUserRentals.next()) {
            setButtonsUnavailable();
            promptLabel.setText("Rented: " + resultUserRentals.getDate("start_of_rental") + " to " + resultUserRentals.getDate("end_of_rental"));
        } else {
            if (resultAvailable.getInt("available") > 0) {
                setButtonsAvailable();
            } else {
                setButtonsUnavailable();
                promptLabel.setText("No copies available");
            }
        }

    }

    @FXML
    void backToBook() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/book-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void rentBook() {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            promptLabel.setText("Please enter start and end dates.");
            return;
        }

        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            if (endDatePicker.getValue().isBefore(startDatePicker.getValue()) || startDatePicker.getValue().equals(endDatePicker.getValue())) {
                promptLabel.setText("Please enter correct dates.");
                return;
            }

            if (startDatePicker.getValue().isBefore(LocalDate.now())) {
                promptLabel.setText("Start date cannot begin in past.");
                return;
            }

            if (endDatePicker.getValue().toEpochDay() - startDatePicker.getValue().toEpochDay() > MAX_RENT_LIMIT) {
                promptLabel.setText("Renting time limit: " + MAX_RENT_LIMIT + "days");
                return;
            }

            try {
                PreparedStatement addRental = Main.getDB().prepareQuery("INSERT INTO actual_rentals(uid, bid, start_of_rental, end_of_rental) VALUES (?, ?, ?, ?)");
                addRental.setInt(1, Main.getUserId());
                addRental.setInt(2, Main.getActualBid());
                addRental.setDate(3, Date.valueOf(startDatePicker.getValue()));
                addRental.setDate(4, Date.valueOf(endDatePicker.getValue()));

                PreparedStatement addToHistory = Main.getDB().prepareQuery("INSERT INTO rental_history(uid, bid, rental_date) VALUES(?, ?, ?)");
                addToHistory.setInt(1, Main.getUserId());
                addToHistory.setInt(2, Main.getActualBid());
                addToHistory.setDate(3, Date.valueOf(startDatePicker.getValue()));

                addRental.execute();
                addToHistory.execute();
                promptLabel.setText("You rented the book!");

                setButtonsUnavailable();

            } catch (SQLException e) {
                e.printStackTrace();
                promptLabel.setText("You reached limit of rentals (5)");
            }
        }
    }

    private void setButtonsUnavailable() {
        rentBookButton.setVisible(false);
        rentBookButton.setDisable(true);

        startDatePicker.setDisable(true);
        startDatePicker.setEditable(false);

        endDatePicker.setDisable(true);
        startDatePicker.setEditable(false);
    }

    private void setButtonsAvailable() {
        rentBookButton.setVisible(true);
        rentBookButton.setDisable(false);

        startDatePicker.setDisable(false);
        startDatePicker.setEditable(true);

        endDatePicker.setDisable(false);
        startDatePicker.setEditable(true);
    }
}
