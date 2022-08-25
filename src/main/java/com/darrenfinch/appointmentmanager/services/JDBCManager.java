package com.darrenfinch.appointmentmanager.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCManager {
    private final String protocol = "jdbc";
    private final String vendor = ":mysql:";
    private final String location = "//localhost/";
    private final String databaseName = "client_schedule";
    private final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER"; // LOCAL
    private final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private final String userName = "sqlUser"; // Username
    private final String password = "Passw0rd!"; // Password
    private Connection connection;  // Connection Interface

    public void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
