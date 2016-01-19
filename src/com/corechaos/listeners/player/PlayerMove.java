package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove extends CCListener {

    public PlayerMove(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent m) {

        m.getPlayer().setFoodLevel(40);
        CoreSB.setHealth(m.getPlayer());

        if (GameState.isState(GameState.BUILD)) {

            if (PlayerHandler.red.contains(m.getPlayer().getUniqueId())) {
                
                if (!LocationUtilities.isInside(m.getTo(), new Location(Bukkit.getWorld("4Corners"), 6, 33, 6), new Location(Bukkit.getWorld("4Corners"), 18, 62, 18))) {

                    m.getPlayer().teleport(Bukkit.getServer().getWorld("4Corners").getHighestBlockAt(12, 12).getLocation());
                    ChatUtilities.onePlayer("Please stay in your plot until it's time to fight", m.getPlayer());

                }
                
            } else if (PlayerHandler.purple.contains(m.getPlayer().getUniqueId())) {
                
                if (!LocationUtilities.isInside(m.getTo(), new Location(Bukkit.getWorld("4Corners"), 6, 33, -5), new Location(Bukkit.getWorld("4Corners"), 18, 62, -18))) {

                    m.getPlayer().teleport(Bukkit.getServer().getWorld("4Corners").getHighestBlockAt(12, -12).getLocation());
                    ChatUtilities.onePlayer("Please stay in your plot until it's time to fight", m.getPlayer());

                }
                
            } else if (PlayerHandler.green.contains(m.getPlayer().getUniqueId())) {
                
                if (!LocationUtilities.isInside(m.getTo(), new Location(Bukkit.getWorld("4Corners"), -5, 33, 6), new Location(Bukkit.getWorld("4Corners"), -18, 62, 18))) {

                    m.getPlayer().teleport(Bukkit.getServer().getWorld("4Corners").getHighestBlockAt(-12, 12).getLocation());
                    ChatUtilities.onePlayer("Please stay in your plot until it's time to fight", m.getPlayer());

                }
                
            } else if (PlayerHandler.yellow.contains(m.getPlayer().getUniqueId())) {
                
                if (!LocationUtilities.isInside(m.getTo(), new Location(Bukkit.getWorld("4Corners"), -5, 33, -5), new Location(Bukkit.getWorld("4Corners"), -18, 62, -18))) {

                    m.getPlayer().teleport(Bukkit.getServer().getWorld("4Corners").getHighestBlockAt(-12, -12).getLocation());
                    ChatUtilities.onePlayer("Please stay in your plot until it's time to fight", m.getPlayer());

                }
                
            }

        }

    }
}
