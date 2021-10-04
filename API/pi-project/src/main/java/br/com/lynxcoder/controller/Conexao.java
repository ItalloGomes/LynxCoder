package br.com.lynxcoder.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection(){

        try {

            String url = "jdbc:mysql://localhost:3306/dbLynxCoder";
            String user = "root";
            String pass = "asd123";

            return DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void closeConnection(Connection conn) {

        try{

            if(conn != null){
                conn.close();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

}
