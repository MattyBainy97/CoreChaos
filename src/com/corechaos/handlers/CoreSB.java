package com.corechaos.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class CoreSB {
    
    private static Scoreboard gameBoard = null;
    private static Objective hearts = null;
    private static Objective alive = null;
    private static Score aliveRed = null;
    private static Score alivePurple = null;
    private static Score aliveGreen = null;
    private static Score aliveYellow = null;
    private static Score health = null;
    private static Team red = null;
    private static Team purple = null;
    private static Team green = null;
    private static Team yellow = null;
    
    public static void initializeScoreboard(){
        
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        gameBoard = manager.getNewScoreboard();
        
        hearts = gameBoard.registerNewObjective("health", "dummy");
        hearts.setDisplaySlot(DisplaySlot.BELOW_NAME);
        hearts.setDisplayName(ChatColor.RED + "❤");
        
        alive = gameBoard.registerNewObjective("test", "dummy");
        alive.setDisplaySlot(DisplaySlot.SIDEBAR);
        alive.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Core Status");
        
        red = gameBoard.registerNewTeam("red");
        red.setPrefix(ChatColor.RED + "");

        purple = gameBoard.registerNewTeam("purple");
        purple.setPrefix(ChatColor.DARK_PURPLE + "");
        
        green = gameBoard.registerNewTeam("green");
        green.setPrefix(ChatColor.DARK_GREEN + "");
        
        yellow = gameBoard.registerNewTeam("yellow");
        yellow.setPrefix(ChatColor.YELLOW + "");
        
    }
    
    public static void showScoreboard(){
        
        for(Player p : Bukkit.getOnlinePlayers()){
            
            p.setScoreboard(gameBoard);
            aliveRed = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Red Core:"));
            aliveRed.setScore(CoreHandler.getHealth(CoreType.RED));
            
            alivePurple = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_PURPLE + "Purple Core:"));
            alivePurple.setScore(CoreHandler.getHealth(CoreType.PURPLE));
            
            aliveGreen = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Green Core:"));
            aliveGreen.setScore(CoreHandler.getHealth(CoreType.GREEN));
            
            aliveYellow = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Yellow Core:"));
            aliveYellow.setScore(CoreHandler.getHealth(CoreType.YELLOW));
            
        }
        
    }
    
    public static void updateSB(){
        
        aliveRed = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Red Core:"));
            aliveRed.setScore(CoreHandler.getHealth(CoreType.RED));
            
            alivePurple = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_PURPLE + "Purple Core:"));
            alivePurple.setScore(CoreHandler.getHealth(CoreType.PURPLE));
            
            aliveGreen = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Green Core:"));
            aliveGreen.setScore(CoreHandler.getHealth(CoreType.GREEN));
            
            aliveYellow = alive.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Yellow Core:"));
            aliveYellow.setScore(CoreHandler.getHealth(CoreType.YELLOW));
        
    }
    
    public static void addRed(Player player){
        
        red.addPlayer(player);
        
    }
    
    public static void addPurple(Player player){
        
        purple.addPlayer(player);
        
    }
    
    public static void addGreen(Player player){
        
        green.addPlayer(player);
        
    }
    
    public static void addYellow(Player player){
        
        yellow.addPlayer(player);
        
    }
    
    public static void setHealth(Player p){
        
        health = hearts.getScore(p);
        health.setScore((int) p.getHealth() / 2);
        
    }
    
    public static void unregisterTeams(){
        
        red.unregister();
        purple.unregister();
        green.unregister();
        yellow.unregister();
        
    }
    
}