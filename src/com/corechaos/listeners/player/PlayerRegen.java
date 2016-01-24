package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.CoreSB;
import com.corechaos.listeners.CCListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerRegen extends CCListener{

    public PlayerRegen(CoreChaos pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onPlayerRegainHealthEvent(EntityRegainHealthEvent event) {
        
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {
            
            event.setCancelled(true);
            
        }
        
    }
}
