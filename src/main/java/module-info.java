module agh.biblioteka {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires mysql.connector.java;
    requires jsch;

    exports agh.biblioteka.controllers;
    opens agh.biblioteka.controllers to javafx.fxml;
    exports agh.biblioteka.tableViewItems;
    opens agh.biblioteka.tableViewItems to javafx.fxml;
    exports agh.biblioteka.app;
    opens agh.biblioteka.app to javafx.fxml;
}