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

            StartCountdown.forceStart = true;

        }

        if (commandLabel.equalsIgnoreCase("records")) {

            Player p = (Player) sender;

            if (args.length == 0) {

                ChatUtilities.records(p, p);

            } else if (args.length == 1) {

                try {
                    
                    Database.openConnection();

                    OfflinePlayer targetPlayer = p.getServer().getOfflinePlayer(args[0]);
                    if(Database.ccTableContainsOfflinePlayer(targetPlayer)){
                        
                        ChatUtilities.records(targetPlayer, p);
                        
                    } else if(Database.playerTableContainsOfflinePlayer(targetPlayer)) {
                        
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Red" + ChatColor.GREEN + "Apple" + ChatColor.RED + "Core" + ChatColor.GRAY + "] " + ChatColor.GOLD + "This player has never played " + ChatColor.AQUA + "CoreChaos");
                        
                    } else {
                        
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Red" + ChatColor.GREEN + "Apple" + ChatColor.RED + "Core" + ChatColor.GRAY + "] " + ChatColor.GOLD + "This player has never been on " + ChatColor.RED + "Red" + ChatColor.GREEN + "Apple" + ChatColor.RED + "Core");
                        
                    }

                } catch (Exception e) {
                    
                    ChatUtilities.onePlayer("Wrong use of this command!", p);
                    
                } finally {
                    
                    Database.closeConnection();
                    
                }

            } else {

                ChatUtilities.onePlayer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("passes")) {

            Player p = (Player) sender;
            if (args.length == 0) {

                Database.openConnection();
                ChatUtilities.onePlayer(ChatColor.GOLD + "You have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + " passes remaining!", p);
                Database.closeConnection();

            } else {

                ChatUtilities.onePlayer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("pass")) {
            Player p = (Player) sender;
            Database.openConnection();
            if (p.isOp() == true) {
                if (args.length == 0) {
                    ChatUtilities.onePlayer("Wrong use of this command!", p);
                } else if (args.length == 1) {
                    ChatUtilities.onePlayer("Wrong use of this command!", p);
                } else if (args.length == 2) {
                    try {
                        Player targetPlayer = p.getServer().getPlayer(args[0]);
                        try {
                            int i = Integer.parseInt(args[1]);
                            if (i >= 0 && i <= 100) {
                                ChatUtilities.onePlayer(ChatColor.GOLD + "You have recieved " + ChatColor.GREEN + i + ChatColor.GOLD + " passes!", targetPlayer);
                                if (targetPlayer != p) {
                                    ChatUtilities.onePlayer(ChatColor.GOLD + "You have sent " + ChatColor.GREEN + i + ChatColor.GOLD + " passes to " + ChatColor.DARK_AQUA + targetPlayer.getName() + ChatColor.GOLD + "!", p);
                                }
                                Database.updatePasses(targetPlayer, Database.getPasses(targetPlayer) + i);
                            } else {
                                ChatUtilities.onePlayer(ChatColor.GOLD + "Enter a serious number... " + ChatColor.RED + i + ChatColor.GOLD + " is just too many...", p);
                            }
                        } catch (Exception e) {
                            ChatUtilities.onePlayer("Wrong use of this command!", p);
                        }

                    } catch (Exception e) {
                        ChatUtilities.onePlayer("Player is not online!", p);
                    }
                }
            }
            Database.closeConnection();
        }

        if (commandLabel.equalsIgnoreCase("msg") || commandLabel.equalsIgnoreCase("tell")) {
            Player player = (Player) sender;
            final HashMap<String, String> hashmap = new HashMap<String, String>();
            if (args.length < 2) {
                ChatUtilities.onePlayer(ChatColor.RED + "Too few arguments", (Player) sender);
                ChatUtilities.onePlayer(ChatColor.RED + "/msg <player> <message>", (Player) sender);
                return true;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                ChatUtilities.onePlayer(ChatColor.RED + "That player is not online", (Player) sender);
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
