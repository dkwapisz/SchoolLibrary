package agh.biblioteka.app;

import java.sql.*;
import com.jcraft.jsch.*;

public class Database {

    private final static String DB_URL = "jdbc:mysql://localhost:4321/database"; //put your database name
    private final static String DB_USER = "username"; //put your database username
    private final static String DB_PASS = "password"; //put your database password
    private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final static String SSH_USER = "username"; //put your SSH username
    private final static String SSH_HOST = "host"; //put your SSH host
    private final static int SSH_PORT = 22;
    private final static String SSH_PASS = "password"; //put your SSH password here

    private Connection connection;
    private Session session;

    public Database() {
        try {
            session = new JSch().getSession(SSH_USER, SSH_HOST, SSH_PORT);
            session.setPassword(SSH_PASS);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(4321, "localhost", 3306);
            System.out.println("Successfully connected to " + SSH_HOST + " via SSH.");
        } catch (JSchException e) {
            System.err.println("SSH connection failed");
            e.printStackTrace();
        }

        try {
            Class.forName(Database.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Successfully connected to " + DB_USER + " MySQL Database.");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            e.printStackTrace();
        }
    }

    public void closeConnections() {
        try {
            connection.close();
            session.disconnect();
        } catch (SQLException e) {
            System.err.println("Closing connection failed");
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareQuery(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("Creating Prepared Statement failed");
            e.printStackTrace();
            return null;
        }
    }

}
