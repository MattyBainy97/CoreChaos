package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage extends CCListener {

    public PlayerDamage(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent d) {
        if (d.getEntity() instanceof Player) {
            Player p = (Player) d.getEntity();
            if (PlayerHandler.spec.contains(p.getUniqueId())) {
                d.setCancelled(true);
            }
            if (d.getCause() == EntityDamageEvent.DamageCause.FALL) {
                d.setCancelled(true);
            }
            if (d.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                d.setCancelled(true);
            }
            if (d.getCause() == EntityDamageEvent.DamageCause.VOID) {
                d.setCancelled(true);
            }
            if (d.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                d.setCancelled(true);
            }
        }
    }
}
