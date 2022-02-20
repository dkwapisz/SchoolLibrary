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

public class SearchRentalsController {

    @FXML
    private Pane pane;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<Rental> actualRentalsTableView;

    @FXML
    private TableColumn<Rental, String> titleColumn;

    @FXML
    private TableColumn<Rental, String> authorColumn;

    @FXML
    private TableColumn<Rental, String> usernameColumn;

    @FXML
    private TableColumn<Rental, String> startDateColumn;

    @FXML
    private TableColumn<Rental, String> endDateColumn;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private ArrayList<Rental> rentals;

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        startDateColumn.setComparator((t, t1) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = format.parse(t);
                Date d2 = format.parse(t1);
                return Long.compare(d1.getTime(), d2.getTime());
            } catch (ParseException p){
                p.printStackTrace();
            }
            return -1;
        });

        endDateColumn.setComparator((t, t1) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = format.parse(t);
                Date d2 = format.parse(t1);
                return Long.compare(d1.getTime(), d2.getTime());
            } catch (ParseException p){
                p.printStackTrace();
            }
            return -1;
        });

        actualRentalsTableView.setRowFactory(tv -> {
            TableRow<Rental> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        goToRental();
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
    void backToAdmin() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/adminPanel-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clear() {
        actualRentalsTableView.getItems().clear();
        titleTextField.clear();
        authorTextField.clear();
        usernameTextField.clear();
        startDatePicker.getEditor().clear();
        startDatePicker.setValue(null);
        endDatePicker.getEditor().clear();
        endDatePicker.setValue(null);
    }

    @FXML
    void goToRental() throws IOException {
        if (actualRentalsTableView.getSelectionModel().getSelectedItem() != null) {
            Main.setActualUid(actualRentalsTableView.getSelectionModel().getSelectedItem().getUid());
            Main.setActualBid(actualRentalsTableView.getSelectionModel().getSelectedItem().getBid());
            Main.setActualRid(actualRentalsTableView.getSelectionModel().getSelectedItem().getRid());
            Stage stage = (Stage) pane.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/rentalInfo-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void search() throws SQLException {

        String SQLDateQuery = "";
        boolean startDate = false, endDate = false;

        if (startDatePicker.getValue() != null) {
            SQLDateQuery += " AND start_of_rental = (?)";
            startDate = true;
        }
        if (endDatePicker.getValue() != null) {
            SQLDateQuery += " AND end_of_rental = (?)";
            endDate = true;
        }

        String searchQuery = "SELECT uid, rid, bid, title, author, username, start_of_rental, end_of_rental FROM (actual_rentals INNER JOIN users USING(uid)) INNER JOIN books_list USING(bid) WHERE title LIKE (?) AND author LIKE (?) AND username LIKE (?)" + SQLDateQuery;

        PreparedStatement getRentals = Main.getDB().prepareQuery(searchQuery);
        getRentals.setString(1, titleTextField.getText() + "%");
        getRentals.setString(2, authorTextField.getText() + "%");
        getRentals.setString(3, usernameTextField.getText() + "%");

        if (startDate && endDate) {
            getRentals.setDate(4, java.sql.Date.valueOf(startDatePicker.getValue()));
            getRentals.setDate(5, java.sql.Date.valueOf(endDatePicker.getValue()));
        } else if (startDate) {
            getRentals.setDate(4, java.sql.Date.valueOf(startDatePicker.getValue()));
        } else if (endDate) {
            getRentals.setDate(4, java.sql.Date.valueOf(endDatePicker.getValue()));
        }

        ResultSet resultRentals = getRentals.executeQuery();

        rentals = new ArrayList<>();

        while (resultRentals.next()) {
            rentals.add(new Rental(resultRentals.getInt("uid"), resultRentals.getInt("rid"), resultRentals.getInt("bid"), resultRentals.getString("title"), resultRentals.getString("author"), resultRentals.getString("username"), String.valueOf(resultRentals.getDate("start_of_rental")), String.valueOf(resultRentals.getDate("end_of_rental"))));
        }

        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(Integer pageIndex) {
        Integer itemsPerPage = 20;
        int nrOfRentals = rentals.size();
        int nrOfPages = (int) Math.ceil((double) nrOfRentals / (double) itemsPerPage);

        int page = pageIndex * itemsPerPage;

        pagination.setPageCount(nrOfPages);
        actualRentalsTableView.getItems().clear();

        for (int i = page; i < page + itemsPerPage && i < nrOfRentals; i++) {
            actualRentalsTableView.getItems().add(rentals.get(i));
        }

        return actualRentalsTableView;
    }
}
