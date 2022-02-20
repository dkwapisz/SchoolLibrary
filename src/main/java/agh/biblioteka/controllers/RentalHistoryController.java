package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.tableViewItems.Rental;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RentalHistoryController {

    @FXML
    private Pane pane;

    @FXML
    private TableView<Rental> historyTableView;

    @FXML
    private TableColumn<Rental, String> titleColumn;

    @FXML
    private TableColumn<Rental, String> authorColumn;

    @FXML
    private TableColumn<Rental, String> rentalDateColumn;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private Pagination pagination;

    private ArrayList<Rental> rentals;

    public void initialize() throws SQLException {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        rentalDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        rentalDateColumn.setComparator((t, t1) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = format.parse(t);
                Date d2 = format.parse(t1);
                return Long.compare(d1.getTime(), d2.getTime());
            } catch (ParseException p) {
                p.printStackTrace();
            }
            return -1;
        });

        historyTableView.setRowFactory(tv -> {
            TableRow<Rental> row = new TableRow<>();
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

        search();
    }

    @FXML
    void search() throws SQLException {
        PreparedStatement getRentals = Main.getDB().prepareQuery("SELECT his.bid as 'book_id', book.title, book.author, his.rental_date FROM books_list book INNER JOIN rental_history his USING(bid) WHERE uid = (?)");
        getRentals.setInt(1, Main.getUserId());
        ResultSet resultRentals = getRentals.executeQuery();

        rentals = new ArrayList<>();

        while(resultRentals.next()) {
            rentals.add(new Rental(resultRentals.getInt("book_id"), resultRentals.getString("title"), resultRentals.getString("author"), String.valueOf(resultRentals.getDate("rental_date"))));
        }

        pagination.setPageFactory(this::createPage);
    }

    @FXML
    void goToBook() throws IOException {
        if (historyTableView.getSelectionModel().getSelectedItem() != null) {
            Main.setActualBid(historyTableView.getSelectionModel().getSelectedItem().getBid());
            Stage stage = (Stage) pane.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/book-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void backToMenu() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/menu-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Node createPage(Integer pageIndex) {
        Integer itemsPerPage = 20;
        int nrOfRentals = rentals.size();
        int nrOfPages = (int) Math.ceil((double) nrOfRentals / (double) itemsPerPage);

        int page = pageIndex * itemsPerPage;

        pagination.setPageCount(nrOfPages);
        historyTableView.getItems().clear();

        for (int i = page; i < page + itemsPerPage && i < nrOfRentals; i++) {
            historyTableView.getItems().add(rentals.get(i));
        }

        return historyTableView;
    }
}
