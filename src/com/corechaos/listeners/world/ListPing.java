package com.corechaos.listeners.world;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ListPing extends CCListener{

    public ListPing(CoreChaos pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if (GameState.isState(GameState.IN_LOBBY)) {

            e.setMotd(ChatUtilities.starter() + GRAY + " - " + GREEN + "LOBBY");
            
        } else if (GameState.isState(GameState.BUILD)){
            
            e.setMotd(ChatUtilities.starter() + GRAY + " - " + DARK_RED + "BUILD MODE");
            
        } else if(GameState.isState(GameState.FIGHT)) {
            
            e.setMotd(ChatUtilities.starter() + GRAY + " - " + GREEN + "FIGHT MODE");

        } else if (GameState.isState(GameState.POST_GAME)) {
            
            e.setMotd(ChatUtilities.starter() + GRAY + " - " + ChatColor.DARK_RED + "GAME ENDING");
            
        } else if (GameState.isState(GameState.RESET)) {
            
            e.setMotd(ChatUtilities.starter() + GRAY + " - " + ChatColor.DARK_RED + "SERVER RESTART");
            
        }
        
    }
    
}
