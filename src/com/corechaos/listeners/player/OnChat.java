package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.YELLOW;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat extends CCListener{
    
    public OnChat(CoreChaos pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent pc) {
        
        String msg = pc.getMessage();
        pc.setCancelled(true);
        ChatUtilities.chat(msg, pc.getPlayer());
        
    }
    
}
