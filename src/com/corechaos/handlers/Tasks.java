package com.corechaos.handlers;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class Tasks {
    
    public static HashMap<UUID, Integer> disconnects = new HashMap<UUID, Integer>();
    
    public static void addDisconnect(Player p, int i){
        
        disconnects.put(p.getUniqueId(), i);
        
    }
    
    public static void removeDisconnect(Player p){
        
        disconnects.remove(p.getUniqueId());
        
    }
    
    public static boolean isDisconnected(UUID uuid){
        
        return disconnects.containsKey(uuid);
       
    }
    
    public static int getDisconnectNumber(Player p){
        
        return disconnects.get(p.getUniqueId());
        
    }
    
}
