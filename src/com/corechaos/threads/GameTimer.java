package com.corechaos.threads;

import com.corechaos.GameState;
import com.corechaos.handlers.CoreHandler;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.CoreType;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.Game;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameTimer implements Runnable {

    public static int timeUntilEnd;

    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.FIGHT)) {
                timeUntilEnd = 600;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        Game.stop();
                        break;
                    }

                    if (GameState.isState(GameState.POST_GAME)) {
                        break;
                    }

                    if (PlayerHandler.players.size() <= 2) {

                        if (PlayerHandler.purple.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.yellow.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " win the game");

                            for (UUID uuid : PlayerHandler.red) {

                                if (!Tasks.isDisconnected(uuid)) {

                                    Database.openConnection();
                                    Player p = Bukkit.getPlayer(uuid);
                                    Database.updateCcTable(p, "points", Database.getCc(p, "points") + 20);
                                    ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points for winning", p);
                                    Database.updateCcTable(p, "wins", Database.getCc(p, "wins") + 1);
                                    Database.closeConnection();

                                }

                            }

                            Game.stop();
                            break;

                        } else if (PlayerHandler.red.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.yellow.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " win the game");

                            for (UUID uuid : PlayerHandler.purple) {

                                if (!Tasks.isDisconnected(uuid)) {

                                    Database.openConnection();
                                    Player p = Bukkit.getPlayer(uuid);
                                    Database.updateCcTable(p, "points", Database.getCc(p, "points") + 20);
                                    ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points for winning", p);
                                    Database.updateCcTable(p, "wins", Database.getCc(p, "wins") + 1);
                                    Database.closeConnection();

                                }

                            }

                            Game.stop();
                            break;

                        } else if (PlayerHandler.purple.isEmpty() && PlayerHandler.red.isEmpty() && PlayerHandler.yellow.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " win the game");

                            for (UUID uuid : PlayerHandler.green) {

                                if (!Tasks.isDisconnected(uuid)) {

                                    Database.openConnection();
                                    Player p = Bukkit.getPlayer(uuid);
                                    Database.updateCcTable(p, "points", Database.getCc(p, "points") + 20);
                                    ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points for winning", p);
                                    Database.updateCcTable(p, "wins", Database.getCc(p, "wins") + 1);
                                    Database.closeConnection();

                                }

                            }

                            Game.stop();
                            break;

                        } else if (PlayerHandler.purple.isEmpty() && PlayerHandler.green.isEmpty() && PlayerHandler.red.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " win the game");

                            for (UUID uuid : PlayerHandler.yellow) {

                                if (!Tasks.isDisconnected(uuid)) {

                                    Database.openConnection();
                                    Player p = Bukkit.getPlayer(uuid);
                                    Database.updateCcTable(p, "points", Database.getCc(p, "points") + 20);
                                    ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points for winning", p);
                                    Database.updateCcTable(p, "wins", Database.getCc(p, "wins") + 1);
                                    Database.closeConnection();

                                }

                            }

                            Game.stop();
                            break;

                        }

                    }

                    if (timeUntilEnd == 600) {

                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "" + ChatColor.BOLD + "FIGHT TIME");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "PvP is now enabled");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "You may now run to other team's bases to destroy their Core");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "Make sure to defend your own core from attackers");
                        ChatUtilities.broadcast(ChatColor.YELLOW + "10 " + ChatColor.GOLD + "minutes left");

                        LocationUtilities.isCorePlaced();

                    } else if (timeUntilEnd == 300) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "5 " + ChatColor.GOLD + "minutes left");

                    } else if (timeUntilEnd == 180) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "3 " + ChatColor.GOLD + "minutes left");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "" + ChatColor.BOLD + "CORE MELTDOWN");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "ERROR! ERROR! ERROR! ERROR! ERROR!");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "ALL ALIVE CORES HAVE BEEN SET TO 10 HEALTH");

                        if (CoreHandler.isCoreAlive(CoreType.RED)) {

                            CoreHandler.setHealth(CoreType.RED, 10);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.PURPLE)) {

                            CoreHandler.setHealth(CoreType.PURPLE, 10);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.GREEN)) {

                            CoreHandler.setHealth(CoreType.GREEN, 10);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.YELLOW)) {

                            CoreHandler.setHealth(CoreType.YELLOW, 10);
                            CoreSB.updateSB();
                            
                        }

                    } else if (timeUntilEnd == 60) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "1 " + ChatColor.GOLD + "minute left");

                    } else if (timeUntilEnd == 30) {
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "" + ChatColor.BOLD + "CORE CORRUPTION");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "ERROR! ERROR! ERROR! ERROR! ERROR!");
                        ChatUtilities.broadcastNoStarter(ChatColor.DARK_RED + "ALL ALIVE CORES HAVE BEEN DESTROYED");
                        
                    } else if (timeUntilEnd < 6 && timeUntilEnd > 1) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left");
                        
                        if (CoreHandler.isCoreAlive(CoreType.RED)) {

                            CoreHandler.setHealth(CoreType.RED, 0);
                            CoreHandler.destroyCore(CoreType.RED);
                            Bukkit.getServer().getWorld("4Corners").playSound(LocationUtilities.redCore, Sound.EXPLODE, 5, 1);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.PURPLE)) {

                            CoreHandler.setHealth(CoreType.PURPLE, 0);
                            CoreHandler.destroyCore(CoreType.PURPLE);
                            Bukkit.getServer().getWorld("4Corners").playSound(LocationUtilities.purpleCore, Sound.EXPLODE, 5, 1);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.GREEN)) {

                            CoreHandler.setHealth(CoreType.GREEN, 0);
                            CoreHandler.destroyCore(CoreType.GREEN);
                            Bukkit.getServer().getWorld("4Corners").playSound(LocationUtilities.greenCore, Sound.EXPLODE, 5, 1);
                            CoreSB.updateSB();

                        }
                        if (CoreHandler.isCoreAlive(CoreType.YELLOW)) {

                            CoreHandler.setHealth(CoreType.YELLOW, 0);
                            CoreHandler.destroyCore(CoreType.YELLOW);
                            Bukkit.getServer().getWorld("4Corners").playSound(LocationUtilities.yellowCore, Sound.EXPLODE, 5, 1);
                            CoreSB.updateSB();

                        }

                    }  else if (timeUntilEnd == 1) {

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
    }
    catch (InterruptedException e

    
    

) {
            }
        }
    }
}
