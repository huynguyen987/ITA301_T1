/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MPMS_1"; // Update with your DB URL
    private static final String JDBC_USERNAME = "root";                   // Update with your DB username
    private static final String JDBC_PASSWORD = "12345678";                   // Update with your DB password

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }
}

