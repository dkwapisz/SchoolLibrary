package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.tableViewItems.Rental;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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

public class MyRentalsController {

    @FXML
    private Pane pane;

    @FXML
    private TableView<Rental> rentalTableView;

    @FXML
    private TableColumn<Rental, String> titleColumn;

    @FXML
    private TableColumn<Rental, String> authorColumn;

    @FXML
    private TableColumn<Rental, String> rentalBeginDateColumn;

    @FXML
    private TableColumn<Rental, String> rentalEndDateColumn;

    public void initialize() throws SQLException {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        rentalBeginDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        rentalEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        rentalBeginDateColumn.setComparator((t, t1) -> {
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

        rentalEndDateColumn.setComparator((t, t1) -> {
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

        rentalTableView.setRowFactory(tv -> {
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

        PreparedStatement getRentals = Main.getDB().prepareQuery("SELECT a.bid as 'book_id', b.title, b.author, a.start_of_rental, a.end_of_rental FROM books_list b INNER JOIN actual_rentals a USING(bid) WHERE uid = (?)");
        getRentals.setInt(1, Main.getUserId());
        ResultSet resultRentals = getRentals.executeQuery();

        while (resultRentals.next()) {
            rentalTableView.getItems().add(new Rental(resultRentals.getInt("book_id"), resultRentals.getString("title"), resultRentals.getString("author"), String.valueOf(resultRentals.getDate("start_of_rental")), String.valueOf(resultRentals.getDate("end_of_rental"))));
        }
    }

    @FXML
    void goToBook() throws IOException {
        if (rentalTableView.getSelectionModel().getSelectedItem() != null) {
            Main.setActualBid(rentalTableView.getSelectionModel().getSelectedItem().getBid());
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

}
