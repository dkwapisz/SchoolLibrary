package agh.biblioteka.controllers;

import agh.biblioteka.tableViewItems.Book;
import agh.biblioteka.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FindBookController {

    @FXML
    private Pane pane;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, Integer> yearColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, Integer> noOfPagesColumn;

    @FXML
    private TableColumn<Book, String> targetGroupColumn;

    @FXML
    private TableColumn<Book, Integer> availableCopiesColumn;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField publisherTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField targetGroupTextField;

    private ArrayList<Book> books;

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        noOfPagesColumn.setCellValueFactory(new PropertyValueFactory<>("noOfPages"));
        targetGroupColumn.setCellValueFactory(new PropertyValueFactory<>("targetGroup"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        booksTableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        goToBook();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        pagination.setPageCount(1);
    }

    @FXML
    void clear() {
        booksTableView.getItems().clear();
        titleTextField.clear();
        authorTextField.clear();
        yearTextField.clear();
        publisherTextField.clear();
        genreTextField.clear();
        targetGroupTextField.clear();
    }

    @FXML
    void goToBook() throws IOException {
        if (booksTableView.getSelectionModel().getSelectedItem() != null) {
            Main.setActualBid(booksTableView.getSelectionModel().getSelectedItem().getBid());
            Stage stage = (Stage) pane.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/book-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void search() throws SQLException {
        PreparedStatement selectBooks = Main.getDB().prepareQuery("SELECT b.bid, b.title, b.author, b.genre, b.year_of_publication, b.publisher, b.number_of_pages, b.target_group, (b.number_of_copies - count(a.bid)) AS 'available' FROM books_list b LEFT JOIN actual_rentals a USING(bid) WHERE title LIKE (?) AND author LIKE (?) AND genre LIKE (?) AND year_of_publication LIKE (?) AND publisher LIKE (?) AND target_group LIKE (?) GROUP BY b.bid");
        selectBooks.setString(1, titleTextField.getText() + "%");
        selectBooks.setString(2, authorTextField.getText() + "%");
        selectBooks.setString(3, genreTextField.getText() + "%");
        selectBooks.setString(4, yearTextField.getText() + "%");
        selectBooks.setString(5, publisherTextField.getText() + "%");
        selectBooks.setString(6, targetGroupTextField.getText() + "%");
        ResultSet resultBooks = selectBooks.executeQuery();

        books = new ArrayList<>();

        while (resultBooks.next()) {
            books.add(new Book(resultBooks.getInt("bid"), resultBooks.getString("title"), resultBooks.getString("author"), resultBooks.getString("genre"), resultBooks.getInt("year_of_publication"), resultBooks.getString("publisher"), resultBooks.getInt("number_of_pages"), resultBooks.getString("target_group"), resultBooks.getInt("available")));
        }

        pagination.setPageFactory(this::createPage);
    }

    @FXML
    void backToMenu() throws IOException {
        clear();
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/menu-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Node createPage(Integer pageIndex) {
        Integer itemsPerPage = 20;
        int nrOfBooks = books.size();
        int nrOfPages = (int) Math.ceil((double) nrOfBooks / (double) itemsPerPage);

        int page = pageIndex * itemsPerPage;

        pagination.setPageCount(nrOfPages);
        booksTableView.getItems().clear();

        for (int i = page; i < page + itemsPerPage && i < nrOfBooks; i++) {
            booksTableView.getItems().add(books.get(i));
        }

        return booksTableView;
    }
}
