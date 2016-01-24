package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageByEntity extends CCListener {

    public static Player playerHit;

    public PlayerDamageByEntity(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {

        if (GameState.isState(GameState.FIGHT)) {

            Entity damaged = event.getEntity();
            Entity damager = event.getDamager();

            if (damaged instanceof Player) {

                if (damager instanceof Player) {

                    Player p = (Player) damager;
                    Player hurt = (Player) damaged;

                    if (PlayerHandler.spec.contains(p.getUniqueId())) {

                        event.setCancelled(true);

                    } else {

                        boolean isDamagedRed = PlayerHandler.red.contains(hurt.getUniqueId());
                        boolean isDamagedPurple = PlayerHandler.purple.contains(hurt.getUniqueId());
                        boolean isDamagedGreen = PlayerHandler.green.contains(hurt.getUniqueId());
                        boolean isDamagedYellow = PlayerHandler.yellow.contains(hurt.getUniqueId());

                        boolean isDamagerRed = PlayerHandler.red.contains(p.getUniqueId());
                        boolean isDamagerPurple = PlayerHandler.purple.contains(p.getUniqueId());
                        boolean isDamagerGreen = PlayerHandler.green.contains(p.getUniqueId());
                        boolean isDamagerYellow = PlayerHandler.yellow.contains(p.getUniqueId());

                        if ((isDamagedRed && isDamagerRed) || (isDamagedPurple && isDamagerPurple) || (isDamagedGreen && isDamagerGreen) || (isDamagedYellow && isDamagerYellow)) {

                            event.setCancelled(true);

                        } else {

                            event.setDamage(5);
                            
                        }
                    }

                } else {

                    event.setCancelled(true);

                }

            } else {

                event.setCancelled(true);

            }

        } else {

            event.setCancelled(true);

        }
    }
}
