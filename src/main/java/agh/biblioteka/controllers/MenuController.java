package agh.biblioteka.controllers;

        import agh.biblioteka.app.Main;
        import javafx.application.Platform;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Hyperlink;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class MenuController {

    @FXML
    private Pane pane;

    @FXML
    private Hyperlink userHyperlink;

    @FXML
    private Button adminPanelButton;


    public void initialize() throws SQLException {
        if (!Main.isADMIN()) {
            adminPanelButton.setDisable(true);
            adminPanelButton.setVisible(false);
        }

        PreparedStatement nameStatement = Main.getDB().prepareQuery("SELECT name, surname FROM users WHERE uid = (?)");
        nameStatement.setInt(1, Main.getUserId());
        ResultSet nameResult = nameStatement.executeQuery();
        nameResult.next();
        String name = nameResult.getString("name") + " " + nameResult.getString("surname");
        userHyperlink.setText("Hello " + name + "!");
    }

    @FXML
    void goToUser() throws IOException {
        Main.setActualUid(Main.getUserId());
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/userProfile-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void exit() {
        Platform.exit();
    }

    @FXML
    void findBook() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/searchBook-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout() throws IOException {
        Main.setADMIN(false);
        Main.setUserId(0);
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/login-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void adminPanel() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/adminPanel-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void myRentals() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/myRentals-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void rentalHistory() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/rentalHistory-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void settings() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/settings-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
