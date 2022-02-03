package com.example.exmpleadhi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DBConnector {
    public static Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aimlite", "root","");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public static ObservableList<Player> getPlayerList(String difficulty){
        ObservableList<Player> playerList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM tbl_leaderboard WHERE Difficulty = '" + difficulty + "'" + "ORDER BY score DESC LIMIT 8";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Player players;
            while(rs.next()){
                players = new Player(rs.getString("Nama"), rs.getString("Difficulty"), rs.getInt("Score"));
                playerList.add(players);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return playerList;
    }
    public static void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public abstract void showPlayers();
}