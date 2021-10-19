package br.com.lynxcoder.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection(){

        try {

            String userLocal = "root";
            String passLocal = "asd123";
            String urlLocal = "jdbc:mysql://localhost:3306/dbLynxCoder";

            String userServer = "lynxcoder";
            String passServer = "#Gfgrupo3";
            String urlServer = "jdbc:sqlserver://srvlynxcoder.database.windows.net:1433;database=dbLynxCoder;encrypt=true;trustServerCertificate=true;";

            return DriverManager.getConnection(urlServer, userServer, passServer);

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

    public static void closeConnection(Connection conn, PreparedStatement pstm){
        try{

            if(conn != null && pstm != null){
                conn.close();
                pstm.close();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
