package com.puzzle.model.DAO;

import java.sql.*;
public class ConnectionFactory {

    public Connection getConnection(){
            String username = "postgres";
            String password = "Fbmaya97";
            String url = "jdbc:postgresql://localhost:5432/puzzleBD";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
