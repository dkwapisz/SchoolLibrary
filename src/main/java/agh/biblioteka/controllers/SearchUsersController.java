package agh.biblioteka.controllers;

import agh.biblioteka.app.Main;
import agh.biblioteka.tableViewItems.User;
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

public class SearchUsersController {

    @FXML
    private Pane pane;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> surnameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> classColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField classTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    private ArrayList<User> users;

    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        usersTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        goToUser();
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
        usersTableView.getItems().clear();
        usernameTextField.clear();
        emailTextField.clear();
        phoneTextField.clear();
        classTextField.clear();
        nameTextField.clear();
        surnameTextField.clear();
    }

    @FXML
    void goToUser() throws IOException {
        if (usersTableView.getSelectionModel().getSelectedItem() != null) {
            Main.setActualUid(usersTableView.getSelectionModel().getSelectedItem().getUid());
            Stage stage = (Stage) pane.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/userProfile-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void search() throws SQLException {
        PreparedStatement selectUsers = Main.getDB().prepareQuery("SELECT uid, name, surname, class, username, email, phone_number FROM users WHERE name LIKE (?) AND surname LIKE (?) AND (class LIKE (?) OR class IS NULL) AND username LIKE (?) AND email LIKE (?) AND phone_number LIKE (?)");
        selectUsers.setString(1, nameTextField.getText() + "%");
        selectUsers.setString(2, surnameTextField.getText() + "%");
        selectUsers.setString(3, "%" + classTextField.getText() + "%");
        selectUsers.setString(4, usernameTextField.getText() + "%");
        selectUsers.setString(5, emailTextField.getText() + "%");
        selectUsers.setString(6, phoneTextField.getText() + "%");
        ResultSet resultUsers = selectUsers.executeQuery();

        users = new ArrayList<>();

        while (resultUsers.next()) {
            users.add(new User(resultUsers.getInt("uid"), resultUsers.getString("name"), resultUsers.getString("surname"), resultUsers.getString("class"), resultUsers.getString("username"), resultUsers.getString("email"), resultUsers.getInt("phone_number")));
        }

        pagination.setPageFactory(this::createPage);
    }



    private Node createPage(Integer pageIndex) {
        Integer itemsPerPage = 20;
        int nrOfUsers = users.size();
        int nrOfPages = (int) Math.ceil((double) nrOfUsers / (double) itemsPerPage);

        int page = pageIndex * itemsPerPage;

        pagination.setPageCount(nrOfPages);
        usersTableView.getItems().clear();

        for (int i = page; i < page + itemsPerPage && i < nrOfUsers; i++) {
            usersTableView.getItems().add(users.get(i));
        }

        return usersTableView;
    }
}
