package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.app.RegexPatterns;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditBookController {

    @FXML
    private Pane pane;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField publisherTextField;

    @FXML
    private TextField yearOfPublicationTextField;

    @FXML
    private TextField nrOfCopiesTextField;

    @FXML
    private TextField nrOfPagesTextField;

    @FXML
    private TextField targetGroupTextField;

    @FXML
    private Label promptLabel;

    private int nrOfPages, yearOfPublication, nrOfCopies;
    private String title, author, genre, publisher, description, targetGroup;

    public void initialize() throws SQLException {

        PreparedStatement getBook = Main.getDB().prepareQuery("SELECT title, author, description, genre, publisher, year_of_publication, target_group, number_of_pages, number_of_copies FROM books_list WHERE bid = (?)");
        getBook.setInt(1, Main.getActualBid());
        ResultSet resultBook = getBook.executeQuery();
        resultBook.next();

        titleTextField.setText(resultBook.getString("title"));
        authorTextField.setText(resultBook.getString("author"));
        descriptionTextArea.setText(resultBook.getString("description"));
        genreTextField.setText(resultBook.getString("genre"));
        publisherTextField.setText(resultBook.getString("publisher"));
        yearOfPublicationTextField.setText(resultBook.getString("year_of_publication"));
        targetGroupTextField.setText(resultBook.getString("target_group"));
        nrOfPagesTextField.setText(resultBook.getString("number_of_pages"));
        nrOfCopiesTextField.setText(resultBook.getString("number_of_copies"));

    }

    private boolean checkData() {
        boolean isCorrect = true;

        if (descriptionTextArea.getText().equals("") || titleTextField.getText().equals("")
                || authorTextField.getText().equals("") || genreTextField.getText().equals("")
                || publisherTextField.getText().equals("") || yearOfPublicationTextField.getText().equals("")
                || nrOfCopiesTextField.getText().equals("") || nrOfPagesTextField.getText().equals("")
                || targetGroupTextField.getText().equals("")) {

            promptLabel.setText("Fields cannot be empty");
            return false;
        }

        if (!RegexPatterns.matchYear(yearOfPublicationTextField.getText())) {
            yearOfPublicationTextField.setPromptText("Incorrect year");
            yearOfPublicationTextField.clear();
            isCorrect = false;
        } else if (Integer.parseInt(yearOfPublicationTextField.getText()) < 100 || Integer.parseInt(yearOfPublicationTextField.getText()) > 2021) {
            yearOfPublicationTextField.setPromptText("Value should be: 100-2021");
            yearOfPublicationTextField.clear();
            isCorrect = false;
        }

        if (!RegexPatterns.matchNrOfPages(nrOfPagesTextField.getText())) {
            nrOfPagesTextField.setPromptText("Incorrect number of pages");
            nrOfPagesTextField.clear();
            isCorrect = false;
        }

        if (!RegexPatterns.matchNrOfCopies(nrOfCopiesTextField.getText())) {
            nrOfCopiesTextField.setPromptText("Incorrect number of pages");
            nrOfCopiesTextField.clear();
            isCorrect = false;
        }

        if (!isCorrect) {
            promptLabel.setText("Incorrect input data");
        }

        return isCorrect;
    }

    private void setData() {
        title = titleTextField.getText();
        author = authorTextField.getText();
        genre = genreTextField.getText();
        targetGroup = targetGroupTextField.getText();
        yearOfPublication = Integer.parseInt(yearOfPublicationTextField.getText());
        nrOfCopies = Integer.parseInt(nrOfCopiesTextField.getText());
        nrOfPages = Integer.parseInt(nrOfPagesTextField.getText());
        publisher = publisherTextField.getText();
        description = descriptionTextArea.getText();
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
    void clear() {
        descriptionTextArea.clear();
        titleTextField.clear();
        authorTextField.clear();
        genreTextField.clear();
        publisherTextField.clear();
        yearOfPublicationTextField.clear();
        nrOfCopiesTextField.clear();
        nrOfPagesTextField.clear();
        targetGroupTextField.clear();
        promptLabel.setText("Cleared.");
    }

    @FXML
    void editBook() throws SQLException {
        if (checkData()) {
            setData();

            PreparedStatement editBook = Main.getDB().prepareQuery("UPDATE books_list SET title = (?), author = (?), genre = (?), target_group = (?), year_of_publication = (?), number_of_copies = (?), number_of_pages = (?), publisher = (?), description = (?) WHERE bid = (?)");
            editBook.setString(1, title);
            editBook.setString(2, author);
            editBook.setString(3, genre);
            editBook.setString(4, targetGroup);
            editBook.setInt(5, yearOfPublication);
            editBook.setInt(6, nrOfCopies);
            editBook.setInt(7, nrOfPages);
            editBook.setString(8, publisher);
            editBook.setString(9, description);
            editBook.setInt(10, Main.getActualBid());
            editBook.execute();

            promptLabel.setText("Book edited correctly");
        }
    }

}
