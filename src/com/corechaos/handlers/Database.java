
package com.corechaos.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.entity.Player;

public class Database {
    
    private static Connection connection;
    
    public synchronized static void openConnection(){
        
        try{
            
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/redapplecore", "root", "");
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static void closeConnection(){
        
        try{
            
            connection.close();
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static Connection getConnection(){
        
        return connection;
        
    }
    
    public synchronized static boolean playerTableContainsPlayer(Player player){
        
        try{
            
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?;");
            sql.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = sql.executeQuery();
            boolean containsPlayer = resultSet.next();
            
            sql.close();
            resultSet.close();
            
            return containsPlayer;
            
        }catch(Exception e){
            
            e.printStackTrace();
            return false;
            
        }
        
    }
    
    public synchronized static boolean ccTableContainsPlayer(Player player){
        
        try{
            
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM corechaos WHERE uuid = ?;");
            sql.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = sql.executeQuery();
            boolean containsPlayer = resultSet.next();
            
            sql.close();
            resultSet.close();
            
            return containsPlayer;
            
        }catch(Exception e){
            
            e.printStackTrace();
            return false;
            
        }
        
    }
    
    public synchronized static void addPlayerToPlayerTable(Player player){
        
        try{
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
            Date date = new Date();
            
            PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO players(uuid, username, firstlogin, lastlogin, passes) VALUES(?, ?, ?, ?, 0);");
            newPlayer.setString(1, player.getUniqueId().toString());
            newPlayer.setString(2, player.getName());
            newPlayer.setString(3, dateFormat.format(date));
            newPlayer.setString(4, dateFormat.format(date));
            newPlayer.execute();
            
            newPlayer.close();
            
        }catch(Exception e){
        
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static void addPlayerToCcTable(Player player){
        
        try{
            
            PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO corechaos VALUES(?, 0, 0, 0, 0, 0, 0);");
            newPlayer.setString(1, player.getUniqueId().toString());
            newPlayer.execute();
            
            newPlayer.close();
            
        }catch(Exception e){
        
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static void updateLastLogin(Player player){
        
        try{
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
            Date date = new Date();
            
            PreparedStatement updatePlayer = connection.prepareStatement("UPDATE players SET lastlogin = ? WHERE uuid = ?;");
            updatePlayer.setString(1, dateFormat.format(date));
            updatePlayer.setString(2, player.getUniqueId().toString());
            updatePlayer.executeUpdate();
            
            updatePlayer.close();
            
        }catch(Exception e){
        
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static void updateCcTable(Player player, String column, int newValue){
        
        try{
            
            PreparedStatement updatePlayer = connection.prepareStatement("UPDATE corechaos SET " + column + " = ? WHERE uuid = ?;");
            updatePlayer.setInt(1, newValue);
            updatePlayer.setString(2, player.getUniqueId().toString());
            updatePlayer.executeUpdate();
            
            updatePlayer.close();
            
        }catch(Exception e){
        
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static void updatePasses(Player player, int newValue){
        
        try{
            
            PreparedStatement updatePlayer = connection.prepareStatement("UPDATE players SET passes = ? WHERE uuid = ?;");
            updatePlayer.setInt(1, newValue);
            updatePlayer.setString(2, player.getUniqueId().toString());
            updatePlayer.executeUpdate();
            
            updatePlayer.close();
            
        }catch(Exception e){
        
            e.printStackTrace();
            
        }
        
    }
    
    public synchronized static int getHighestPoints(){
        
        try{
            
            PreparedStatement getScore = connection.prepareStatement("SELECT MAX(points) FROM corechaos;");
            ResultSet resultSet = getScore.executeQuery();
            int result = 0;
            
            while(resultSet.next()){
                result = resultSet.getInt(1);
            }
            
            getScore.close();
            
            return result;
            
        }catch(Exception e){
        
            e.printStackTrace();
            
            return 0;
            
        }
        
    }
    
    public synchronized static int getCc(Player player, String column){
        
        try{
            
            PreparedStatement getScore = connection.prepareStatement("SELECT " + column + " FROM corechaos WHERE uuid = ?;");
            getScore.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = getScore.executeQuery();
            resultSet.next();
           
            int result = resultSet.getInt(column);
            
            getScore.close();
            
            return result;
            
        }catch(Exception e){
        
            e.printStackTrace();
            
            return 0;
            
        }
        
    }
    
    public synchronized static int getPasses(Player player){
        
        try{
            
            PreparedStatement getPasses = connection.prepareStatement("SELECT passes FROM players WHERE uuid = ?;");
            getPasses.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = getPasses.executeQuery();
            resultSet.next();
           
            int result = resultSet.getInt("passes");
            
            getPasses.close();
            
            return result;
            
        }catch(Exception e){
        
            e.printStackTrace();
            
            return 0;
            
        }
        
    }
    
}
