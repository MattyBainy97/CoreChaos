package com.corechaos.handlers;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.threads.BuildTimer;
import com.corechaos.threads.ReloadTimer;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Game {
    
    private static boolean canStart = false;
    private static boolean hasStarted = false;

    public static void start() {

        CoreChaos.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CoreChaos.plugin, new Runnable() {
            @Override
            public void run() {
                
                new Thread(new BuildTimer()).start();
                
                GameState.setState(GameState.BUILD);

                for (Player p : Bukkit.getOnlinePlayers()) {

                    Database.openConnection();
                    Database.updateCcTable(p, "games", Database.getCc(p, "games") + 1);
                    
                    p.setHealth(20.0);
                    p.setFoodLevel(40);
                    p.setLevel(0);
                    p.setExp(0);
                    CoreSB.showScoreboard();
                    CoreSB.setHealth(p);
                    p.setGameMode(GameMode.SURVIVAL);

                }
                
                PlayerHandler.assignTeams();
                PlayerHandler.giveItems();
                LocationUtilities.spawnCores();
                LocationUtilities.teleportToGame();

            }
        }, 5L);

        hasStarted = true;

    }

    public static void stop() {

            FireworkHandler.newFirework(LocationUtilities.lobby);
            FireworkHandler.newFirework(LocationUtilities.redRespawn);
            FireworkHandler.newFirework(LocationUtilities.purpleRespawn);
            FireworkHandler.newFirework(LocationUtilities.greenRespawn);
            FireworkHandler.newFirework(LocationUtilities.yellowRespawn);
            
            CoreChaos.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CoreChaos.plugin, new Runnable() {
                @Override
                public void run() {

                    FireworkHandler.newFirework(LocationUtilities.lobby);
                    FireworkHandler.newFirework(LocationUtilities.redRespawn);
                    FireworkHandler.newFirework(LocationUtilities.purpleRespawn);
                    FireworkHandler.newFirework(LocationUtilities.greenRespawn);
                    FireworkHandler.newFirework(LocationUtilities.yellowRespawn);

                }
            }, 20L);
            
            CoreChaos.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CoreChaos.plugin, new Runnable() {
                @Override
                public void run() {

                    FireworkHandler.newFirework(LocationUtilities.lobby);
                    FireworkHandler.newFirework(LocationUtilities.redRespawn);
                    FireworkHandler.newFirework(LocationUtilities.purpleRespawn);
                    FireworkHandler.newFirework(LocationUtilities.greenRespawn);
                    FireworkHandler.newFirework(LocationUtilities.yellowRespawn);

                }
            }, 40L);

        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "GAME OVER");
        GameState.setState(GameState.POST_GAME);
        new Thread(new ReloadTimer()).start();
        canStart = false;
        hasStarted = false;

    }

    public static boolean canStart() {

        return canStart;

    }

    public static boolean hasStarted() {

        return hasStarted;

    }

    public static void setCanStart(boolean b) {

        canStart = b;

    }
}
