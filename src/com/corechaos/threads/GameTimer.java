package com.corechaos.threads;

import com.corechaos.GameState;
import com.corechaos.handlers.Game;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import org.bukkit.ChatColor;

public class GameTimer implements Runnable {

    public static int timeUntilEnd;

    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.FIGHT)) {
                timeUntilEnd = 300;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        Game.stop();
                        break;
                    }

                    if (GameState.isState(GameState.POST_GAME)) {
                        break;
                    }
                    
                    if(PlayerHandler.players.size() <= 2){
                        
                        if(PlayerHandler.purple.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.yellow.isEmpty()){
                            
                            ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " win the game");
                            Game.stop();
                            break;
                            
                        }else if(PlayerHandler.red.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.yellow.isEmpty()){
                            
                            ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " win the game");
                            Game.stop();
                            break;
                            
                        }else if(PlayerHandler.purple.isEmpty() && PlayerHandler.red.isEmpty() && PlayerHandler.yellow.isEmpty()){
                            
                            ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " win the game");
                            Game.stop();
                            break;
                            
                        }else if(PlayerHandler.purple.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.red.isEmpty()){
                            
                            ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " win the game");
                            Game.stop();
                            break;
                            
                        }
                        
                    }

                    if (timeUntilEnd == 300) {

                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "" + ChatColor.BOLD + "FIGHT TIME");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "PvP is now enabled");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "You may now run to other team's bases to destroy their Core");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "Make sure to defend your own core from attackers");
                        ChatUtilities.broadcast(ChatColor.YELLOW + "5 " + ChatColor.GOLD + "minutes left");
                        
                        LocationUtilities.isCorePlaced();

                    } else if (timeUntilEnd == 180) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "3 " + ChatColor.GOLD + "minutes left");

                    } else if (timeUntilEnd == 60) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "1 " + ChatColor.GOLD + "minute left");

                    } else if (timeUntilEnd == 30 || (timeUntilEnd < 6 && timeUntilEnd > 1)) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left");

                    } else if (timeUntilEnd == 1) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " second left");

                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}