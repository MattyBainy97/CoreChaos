/*
 * Corners Plugin
 * Created by: Matty
 * Project started: 2016-01-16
 */
package com.corechaos;

import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreSB;
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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        
        if(commandLabel.equalsIgnoreCase("list")){
            
            ChatUtilities.showList((Player) sender);
            
        }
        
        if(commandLabel.equalsIgnoreCase("start")){
            
            StartCountdown.forceStart = true;
            
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
