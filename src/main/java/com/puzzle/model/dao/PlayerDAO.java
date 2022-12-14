package com.puzzle.model.dao;

import com.puzzle.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDAO {
    private Connection con;

    public PlayerDAO(){
        this.con = new ConnectionFactory().getConnection();
        try {
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT count(*)  FROM pg_database WHERE datname = 'puzzlebd'");
            ResultSet rs = statement.getResultSet();
            rs.next();
            int count = rs.getInt(1);
            if (count <= 0){
                System.out.println("creating db");
                statement.executeUpdate("CREATE DATABASE PUZZLEBD");
            }

            DatabaseMetaData metaData = con.getMetaData();
            ResultSet resultSet = metaData.getTables(null,null,"player",null);
            if(!resultSet.next()){
                String query = "CREATE TABLE player"+"(id serial," +
                        "time double precision," +
                        "level integer not null," +
                        "moves integer not null," +
                        "winner boolean not null ," +
                        "playername varchar not null," +
                        "crazyfeature integer not null," +
                        "choice integer not null," +
                        "playerarray text[])";
                statement.executeUpdate(query);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean add(Player player){
        String sql = "INSERT INTO player(time,level,moves,winner,playerName,crazyFeature,choice) VALUES (?,?,?,?,?,?,?) RETURNING id; ";

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setDouble(1,player.getTime());
            statement.setInt(2,player.getLevel());
            statement.setInt(3,player.getMoves());
            statement.setBoolean(4,player.getWinner());
            statement.setString(5, player.getPlayerName());
            statement.setInt(6,player.getCrazyFeature());
            statement.setInt(7,player.getChoice());
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            player.setId(result.getLong("id"));

            statement.close();
            con.close();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Array convertToSQLArray(String[] array) throws SQLException {
        return con.createArrayOf("TEXT", array);
    }

    public String[] convertToJava(Array array) throws SQLException {
        String[] a;
        a = (String[]) array.getArray();
        return a;
    }

    public boolean update(Player player){
        String sql = "UPDATE player SET time = ?,level = ?,moves = ?,winner = ?,playername = ?,crazyfeature = ?, choice = ?, playerarray = ?  WHERE id = ?;";

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setDouble(1,player.getTime());
            statement.setInt(2,player.getLevel());
            statement.setInt(3,player.getMoves());
            statement.setBoolean(4,player.getWinner());
            statement.setString(5, player.getPlayerName());
            statement.setInt(6,player.getCrazyFeature());
            statement.setInt(7,player.getChoice());
            statement.setArray(8,convertToSQLArray(player.get1DTiles()));
            statement.setLong(9,player.getId());
            statement.execute();
            statement.close();
            con.close();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean delete(Player player){
        String sql = "DELETE FROM player WHERE id = ?;";

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1,player.getId());
            statement.execute();
            statement.close();
            con.close();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<Player> getList(){
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM player";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                Player player = new Player();
                player.setId(set.getLong("id"));
                player.setPlayerName(set.getString("playername"));
                player.setTime(set.getDouble("time"));
                player.setLevel(set.getInt("level"));
                player.setMoves(set.getInt("moves"));
                player.setCrazyFeature(set.getInt("crazyfeature"));
                player.setWinner(set.getBoolean("winner"));
                player.setChoice(set.getInt("choice"));
                if(set.getArray("playerarray") != null)
                    player.set1DTiles(convertToJava(set.getArray("playerarray")));
                players.add(player);
            }
            statement.close();
            set.close();
            con.close();

        } catch (SQLException e) {
            return null;
        }
        return players;
    }
}
