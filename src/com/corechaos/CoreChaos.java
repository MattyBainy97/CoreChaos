/*
 * Corners Plugin
 * Created by: Matty
 * Project started: 2016-01-16
 */
package com.corechaos;

import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.inventory.ClickSlot;
import com.corechaos.listeners.player.AsyncPlayerPreLogin;
import com.corechaos.listeners.player.OnChat;
import com.corechaos.listeners.player.PlayerDamage;
import com.corechaos.listeners.player.PlayerDamageByEntity;
import com.corechaos.listeners.player.PlayerDeath;
import com.corechaos.listeners.player.PlayerDrop;
import com.corechaos.listeners.player.PlayerInteract;
import com.corechaos.listeners.player.PlayerJoin;
import com.corechaos.listeners.player.PlayerKick;
import com.corechaos.listeners.player.PlayerMove;
import com.corechaos.listeners.player.PlayerQuit;
import com.corechaos.listeners.player.PlayerRegen;
import com.corechaos.listeners.player.PlayerRespawn;
import com.corechaos.listeners.world.BlockBreak;
import com.corechaos.listeners.world.BlockPlace;
import com.corechaos.listeners.world.ListPing;
import com.corechaos.threads.StartCountdown;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreChaos extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        new Thread(new StartCountdown()).start();

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.kickPlayer("Rejoin.");

        }

        LocationUtilities.clearArea();

        GameState.setState(GameState.IN_LOBBY);

        registerListeners();
        CCItem.setMetas();

        Bukkit.setDefaultGameMode(GameMode.SURVIVAL);
        World w = Bukkit.getServer().getWorld("4Corners");
        w.setAutoSave(false);
        w.setTime(0);
        w.setStorm(false);
        w.setWeatherDuration(9999999);
        for (Entity e : w.getEntities()) {

            e.remove();

        }

        CoreSB.initializeScoreboard();

    }

    @Override
    public void onDisable() {

        plugin = null;

        CoreSB.unregisterTeams();
        PlayerHandler.clearAll();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (commandLabel.equalsIgnoreCase("list")) {

            ChatUtilities.showList((Player) sender);

        }

        if (commandLabel.equalsIgnoreCase("start")) {

            if (GameState.isState(GameState.IN_LOBBY)) {

                StartCountdown.forceStart = true;

            } else {

                ChatUtilities.onePlayer("A game is already in progress", (Player) sender);

            }

        }

        if (commandLabel.equalsIgnoreCase("red")) {

            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();

            Database.openConnection();
            int passnum = Database.getPasses(p);

            if (GameState.isState(GameState.IN_LOBBY)) {

                if (passnum > 1) {

                    if (PlayerHandler.red.contains(uuid)) {

                        ChatUtilities.onePlayer("You are already on " + ChatColor.RED + "Red Team", p);

                    } else {

                        if (Bukkit.getOnlinePlayers().size() > 4) {

                            if (PlayerHandler.red.size() < 2) {

                                if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.RED + "Red Team", p);

                                }

                                PlayerHandler.red.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.RED + "Red Team" + ChatColor.GOLD + " is full", p);

                            }

                        } else {

                            if (PlayerHandler.red.size() < 1) {

                                if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.RED + "Red Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.RED + "Red Team", p);

                                }

                                PlayerHandler.red.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.RED + "Red Team" + ChatColor.GOLD + " cannot be joined at this time", p);

                            }

                        }

                    }
                } else if (passnum > -1) {

                    ChatUtilities.onePlayerServer(ChatColor.GOLD + "You don't have any passes!", p);

                }

            } else {

                ChatUtilities.onePlayer("You cannot join a team while a game is in progress", p);

            }

            Database.closeConnection();

        }

        if (commandLabel.equalsIgnoreCase("purple")) {

            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();

            Database.openConnection();
            int passnum = Database.getPasses(p);

            if (GameState.isState(GameState.IN_LOBBY)) {

                if (passnum > 1) {

                    if (PlayerHandler.purple.contains(uuid)) {

                        ChatUtilities.onePlayer("You are already on " + ChatColor.DARK_PURPLE + "Purple Team", p);

                    } else {

                        if (Bukkit.getOnlinePlayers().size() > 4) {

                            if (PlayerHandler.purple.size() < 2) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                }

                                PlayerHandler.purple.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " is full", p);

                            }

                        } else {

                            if (PlayerHandler.purple.size() < 1) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.DARK_PURPLE + "Purple Team", p);

                                }

                                PlayerHandler.purple.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " cannot be joined at this time", p);

                            }

                        }

                    }
                } else if (passnum > -1) {

                    ChatUtilities.onePlayerServer(ChatColor.GOLD + "You don't have any passes!", p);

                }

            } else {

                ChatUtilities.onePlayer("You cannot join a team while a game is in progress", p);

            }

            Database.closeConnection();

        }
        
        if (commandLabel.equalsIgnoreCase("green")) {

            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();

            Database.openConnection();
            int passnum = Database.getPasses(p);

            if (GameState.isState(GameState.IN_LOBBY)) {

                if (passnum > 1) {

                    if (PlayerHandler.green.contains(uuid)) {

                        ChatUtilities.onePlayer("You are already on " + ChatColor.DARK_GREEN + "Green Team", p);

                    } else {

                        if (Bukkit.getOnlinePlayers().size() > 4) {

                            if (PlayerHandler.green.size() < 2) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                }

                                PlayerHandler.green.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " is full", p);

                            }

                        } else {

                            if (PlayerHandler.green.size() < 1) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else if (PlayerHandler.yellow.contains(uuid)) {

                                    PlayerHandler.yellow.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " and joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.DARK_GREEN + "Green Team", p);

                                }

                                PlayerHandler.green.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " cannot be joined at this time", p);

                            }

                        }

                    }
                } else if (passnum > -1) {

                    ChatUtilities.onePlayerServer(ChatColor.GOLD + "You don't have any passes!", p);

                }

            } else {

                ChatUtilities.onePlayer("You cannot join a team while a game is in progress", p);

            }

            Database.closeConnection();

        }
        
        if (commandLabel.equalsIgnoreCase("yellow")) {

            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();

            Database.openConnection();
            int passnum = Database.getPasses(p);

            if (GameState.isState(GameState.IN_LOBBY)) {

                if (passnum > 1) {

                    if (PlayerHandler.yellow.contains(uuid)) {

                        ChatUtilities.onePlayer("You are already on " + ChatColor.YELLOW + "Yellow Team", p);

                    } else {

                        if (Bukkit.getOnlinePlayers().size() > 4) {

                            if (PlayerHandler.yellow.size() < 2) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else {

                                    ChatUtilities.onePlayer("You joined " + ChatColor.YELLOW + "Yellow Team", p);

                                }

                                PlayerHandler.yellow.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " is full", p);

                            }

                        } else {

                            if (PlayerHandler.yellow.size() < 1) {

                                if (PlayerHandler.red.contains(uuid)) {

                                    PlayerHandler.red.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.RED + "Red Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else if (PlayerHandler.purple.contains(uuid)) {

                                    PlayerHandler.purple.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else if (PlayerHandler.green.contains(uuid)) {

                                    PlayerHandler.green.remove(uuid);
                                    ChatUtilities.onePlayer("You left " + ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " and joined " + ChatColor.YELLOW + "Yellow Team", p);

                                } else {

                                    ChatUtilities.onePlayerServer("You joined " + ChatColor.YELLOW + "Yellow Team", p);

                                }

                                PlayerHandler.yellow.add(uuid);
                                Database.updatePasses(p, Database.getPasses(p) - 1);
                                ChatUtilities.onePlayer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);

                            } else {

                                ChatUtilities.onePlayer(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " cannot be joined at this time", p);

                            }

                        }

                    }
                } else if (passnum > -1) {

                    ChatUtilities.onePlayerServer(ChatColor.GOLD + "You don't have any passes!", p);

                }

            } else {

                ChatUtilities.onePlayer("You cannot join a team while a game is in progress", p);

            }

            Database.closeConnection();

        }

        if (commandLabel.equalsIgnoreCase("records")) {

            Player p = (Player) sender;

            if (args.length == 0) {

                ChatUtilities.records(p, p);

            } else if (args.length == 1) {

                try {

                    Database.openConnection();

                    OfflinePlayer targetPlayer = p.getServer().getOfflinePlayer(args[0]);
                    if (Database.ccTableContainsOfflinePlayer(targetPlayer)) {

                        ChatUtilities.records(targetPlayer, p);

                    } else if (Database.playerTableContainsOfflinePlayer(targetPlayer)) {

                        ChatUtilities.onePlayerServer("This player has never played " + ChatColor.AQUA + "CoreChaos", p);

                    } else {

                        ChatUtilities.onePlayerServer("This player has never been on " + ChatColor.RED + "Red" + ChatColor.GREEN + "Apple" + ChatColor.RED + "Core", p);

                    }

                } catch (Exception e) {

                    ChatUtilities.onePlayerServer("Wrong use of this command!", p);

                } finally {

                    Database.closeConnection();

                }

            } else {

                ChatUtilities.onePlayerServer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("passes")) {

            Player p = (Player) sender;
            if (args.length == 0) {

                Database.openConnection();
                ChatUtilities.onePlayerServer(ChatColor.GOLD + "You have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + " passes remaining!", p);
                Database.closeConnection();

            } else {

                ChatUtilities.onePlayerServer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("pass")) {
            Player p = (Player) sender;
            Database.openConnection();
            if (p.isOp() == true) {
                if (args.length == 0) {
                    ChatUtilities.onePlayerServer("Wrong use of this command!", p);
                } else if (args.length == 1) {
                    ChatUtilities.onePlayerServer("Wrong use of this command!", p);
                } else if (args.length == 2) {
                    try {
                        Player targetPlayer = p.getServer().getPlayer(args[0]);
                        try {
                            int i = Integer.parseInt(args[1]);
                            if (i >= 0 && i <= 100) {
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "You have recieved " + ChatColor.GREEN + i + ChatColor.GOLD + " passes!", targetPlayer);
                                if (targetPlayer != p) {
                                    ChatUtilities.onePlayerServer(ChatColor.GOLD + "You have sent " + ChatColor.GREEN + i + ChatColor.GOLD + " passes to " + ChatColor.DARK_AQUA + targetPlayer.getName() + ChatColor.GOLD + "!", p);
                                }
                                Database.updatePasses(targetPlayer, Database.getPasses(targetPlayer) + i);
                            } else {
                                ChatUtilities.onePlayerServer(ChatColor.GOLD + "Enter a serious number... " + ChatColor.RED + i + ChatColor.GOLD + " is just too many...", p);
                            }
                        } catch (Exception e) {
                            ChatUtilities.onePlayerServer("Wrong use of this command!", p);
                        }

                    } catch (Exception e) {
                        ChatUtilities.onePlayerServer("Player is not online!", p);
                    }
                }
            }
            Database.closeConnection();
        }

        if (commandLabel.equalsIgnoreCase("msg") || commandLabel.equalsIgnoreCase("tell")) {
            Player player = (Player) sender;
            final HashMap<String, String> hashmap = new HashMap<String, String>();
            if (args.length < 2) {
                ChatUtilities.onePlayerServer(ChatColor.RED + "Too few arguments", (Player) sender);
                ChatUtilities.onePlayerServer(ChatColor.RED + "/msg <player> <message>", (Player) sender);
                return true;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                ChatUtilities.onePlayerServer(ChatColor.RED + "That player is not online", (Player) sender);
                return true;
            }
            String msg = "";
            for (String s : args) {
                msg = msg + " " + s;
            }
            Bukkit.getPlayer(args[0]).sendMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.GRAY + " »» " + ChatColor.DARK_AQUA + args[0] + ChatColor.GRAY + " »" + ChatColor.WHITE + msg.replaceFirst(" " + args[0], ""));
            player.sendMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.GRAY + " »» " + ChatColor.DARK_AQUA + args[0] + ChatColor.GRAY + " »" + ChatColor.WHITE + msg.replaceFirst(" " + args[0], ""));
            hashmap.put(player.getName(), args[0]);
            hashmap.put(args[0], player.getName());
            return true;
        }

        return false;

    }

    public void registerListeners() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new PlayerDamageByEntity(this), this);
        pm.registerEvents(new PlayerDamage(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerMove(this), this);
        pm.registerEvents(new PlayerDrop(this), this);
        pm.registerEvents(new PlayerKick(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerRegen(this), this);
        pm.registerEvents(new PlayerRespawn(this), this);
        pm.registerEvents(new ClickSlot(this), this);
        pm.registerEvents(new OnChat(this), this);
        pm.registerEvents(new AsyncPlayerPreLogin(this), this);
        pm.registerEvents(new ListPing(this), this);
        pm.registerEvents(new BlockBreak(this), this);
        pm.registerEvents(new BlockPlace(this), this);

    }
}
