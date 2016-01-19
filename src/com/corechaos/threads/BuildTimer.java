package com.corechaos.threads;

import com.corechaos.GameState;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.ChatColor;

public class BuildTimer implements Runnable {

    private static int timeUntilEnd;

    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.BUILD)) {
                timeUntilEnd = 120;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        GameState.setState(GameState.FIGHT);
                        new Thread(new GameTimer()).start();
                        break;
                    }
                    
                    if (timeUntilEnd == 120) {

                        ChatUtilities.broadcastNoStarter(ChatColor.GREEN + "" + ChatColor.BOLD + "BUILD TIME");
                        ChatUtilities.broadcastNoStarter(ChatColor.GREEN + "Use this time to build a small base of operations for your team");
                        ChatUtilities.broadcastNoStarter(ChatColor.GREEN + "You may break the Core and place it within your base during this time");
                        ChatUtilities.broadcastNoStarter(ChatColor.GREEN + "Make sure you place down your Core during this time");
                        ChatUtilities.broadcast(ChatColor.YELLOW + "2 " + ChatColor.GOLD + "minutes left to build");

                    }else if (timeUntilEnd == 60) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "1 " + ChatColor.GOLD + "minute left to build");
                        ChatUtilities.broadcastNoStarter(ChatColor.GREEN + "Make sure to place down your Core before the time is up");

                    } else if (timeUntilEnd == 30 || (timeUntilEnd < 6 && timeUntilEnd > 1)) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left to build");

                    } else if (timeUntilEnd == 1) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " second left to build");

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
