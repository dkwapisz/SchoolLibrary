package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookController {

    @FXML
    private Pane pane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label nrOfPagesLabel;

    @FXML
    private Label targetGroupLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label availableCopiesLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label yearOfPublicationLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label nrOfCopiesLabel;

    @FXML
    private Button rentButton;

    @FXML
    private Button editBookButton;

    @FXML
    private Button deleteBookButton;

    @FXML
    private Button returnBookButton;

    private int availableCopies;

    public void initialize() throws SQLException {
        PreparedStatement getAvailable = Main.getDB().prepareQuery("SELECT b.number_of_copies - count(a.bid) AS 'available' FROM books_list b LEFT JOIN actual_rentals a USING(bid) WHERE b.bid = (?) GROUP BY b.bid");
        getAvailable.setInt(1, Main.getActualBid());
        ResultSet resultAvailable = getAvailable.executeQuery();
        resultAvailable.next();
        availableCopies = resultAvailable.getInt("available");

        if (!Main.isADMIN()) {
            editBookButton.setDisable(true);
            deleteBookButton.setDisable(true);
            editBookButton.setVisible(false);
            deleteBookButton.setVisible(false);
        }

        PreparedStatement getBook = Main.getDB().prepareQuery("SELECT title, author, description, genre, publisher, year_of_publication, target_group, number_of_pages, number_of_copies FROM books_list WHERE bid = (?)");
        getBook.setInt(1, Main.getActualBid());
        ResultSet resultBook = getBook.executeQuery();
        resultBook.next();

        titleLabel.setText("Title: " + resultBook.getString("title"));
        authorLabel.setText("Author: " + resultBook.getString("author"));
        descriptionLabel.setText("Description: \n" + resultBook.getString("description"));
        genreLabel.setText("Genre: " + resultBook.getString("genre"));
        publisherLabel.setText("Publisher: " + resultBook.getString("publisher"));
        yearOfPublicationLabel.setText("Year of publication: " + resultBook.getString("year_of_publication"));
        targetGroupLabel.setText("Target group: " + resultBook.getString("target_group"));
        nrOfPagesLabel.setText("Number of pages: " + resultBook.getString("number_of_pages"));
        nrOfCopiesLabel.setText("Number of copies: " + resultBook.getString("number_of_copies"));
        availableCopiesLabel.setText("Available copies: " + availableCopies);

        if (availableCopies == 0) {
            rentButton.setDisable(true);
        } else if (availableCopies > 0) {
            rentButton.setDisable(false);
        }

        PreparedStatement bookRented = Main.getDB().prepareQuery("SELECT uid, bid FROM actual_rentals WHERE uid = (?) AND bid = (?)");
        bookRented.setInt(1, Main.getUserId());
        bookRented.setInt(2, Main.getActualBid());
        ResultSet resultBookRent = bookRented.executeQuery();

        if (resultBookRent.next()) {
            returnBookButton.setVisible(true);
            returnBookButton.setDisable(false);
        } else {
            returnBookButton.setVisible(false);
            returnBookButton.setDisable(true);
        }
    }

    @FXML
    void deleteBook() throws SQLException {
        PreparedStatement deleteBook = Main.getDB().prepareQuery("DELETE FROM books_list WHERE bid = (?)");
        deleteBook.setInt(1, Main.getActualBid());
        deleteBook.execute();

        titleLabel.setText("Book deleted");
        authorLabel.setText("");
        descriptionLabel.setText("");
        genreLabel.setText("");
        publisherLabel.setText("");
        yearOfPublicationLabel.setText("");
        targetGroupLabel.setText("");
        nrOfPagesLabel.setText("");
        nrOfCopiesLabel.setText("");
        availableCopiesLabel.setText("");
    }

    @FXML
    void editBook() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/editBook-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    void backToSearch() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/searchBook-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void rentBook() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/rentBook-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void returnBook() throws SQLException {
        returnBookButton.setVisible(false);
        returnBookButton.setDisable(true);

        if (rentButton.isDisable()) {
            rentButton.setDisable(false);
        }

        PreparedStatement deleteRental = Main.getDB().prepareQuery("DELETE FROM actual_rentals WHERE uid = (?) AND bid = (?)");
        deleteRental.setInt(1, Main.getUserId());
        deleteRental.setInt(2, Main.getActualBid());
        deleteRental.execute();

        availableCopies++;
        availableCopiesLabel.setText("Available copies: " + availableCopies);
    }

}
