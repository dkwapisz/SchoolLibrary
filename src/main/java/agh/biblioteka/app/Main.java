package agh.biblioteka.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private static boolean ADMIN = true; // If logged user is an admin
    private static int USER_ID; // Currently logged user ID
    private static int actualBid;
    private static int actualUid;
    private static int actualRid;
    private final static Database DB = new Database();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("School Library");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {

        DB.closeConnections();
        System.out.println("Connections closed.");

        super.stop();
    }

    public static Database getDB() {
        return Main.DB;
    }

    public static int getUserId() {
        return USER_ID;
    }
    public static void setUserId(int userId) {
        USER_ID = userId;
    }

    public static int getActualRid() {
        return actualRid;
    }
    public static void setActualRid(int actualRid) {
        Main.actualRid = actualRid;
    }

    public static int getActualUid() {
        return actualUid;
    }
    public static void setActualUid(int actualUid) {
        Main.actualUid = actualUid;
    }

    public static int getActualBid() {
        return actualBid;
    }
    public static void setActualBid(int actualBid) {
        Main.actualBid = actualBid;
    }

    public static boolean isADMIN() {
        return ADMIN;
    }
    public static void setADMIN(boolean ADMIN) {
        Main.ADMIN = ADMIN;
    }

    public static void main(String[] args) {
        launch();
    }
}
