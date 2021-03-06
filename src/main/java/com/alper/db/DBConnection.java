package com.alper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnection {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("auth");
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(bundle.getString("dbUrl"),bundle.getString("dbUsername"),bundle.getString("dbPassword"));
    }

}