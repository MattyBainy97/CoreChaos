package com.corechaos.listeners.world;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.GameState;
import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreHandler;
import com.corechaos.handlers.CoreType;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends CCListener {

    public BlockPlace(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent p) {

        Block block = p.getBlock();
        
        if(PlayerHandler.spec.contains(p.getPlayer().getUniqueId())){
            
            p.setCancelled(true);
            
        }

        if (GameState.isState(GameState.BUILD)) {

            if (block.getType().equals(Material.STAINED_GLASS)) {

                String msg = p.getPlayer().getName() + " placed the Core at " + block.getX() + ", " + block.getY() + ", " + block.getZ();

                if (block.getData() == DyeColor.RED.getData()) {

                    ChatUtilities.redBroadcast(msg);
                    CoreHandler.setLocation(CoreType.RED, p.getBlock().getLocation());

                } else if (block.getData() == DyeColor.PURPLE.getData()) {

                    ChatUtilities.purpleBroadcast(msg);
                    CoreHandler.setLocation(CoreType.PURPLE, p.getBlock().getLocation());

                } else if (block.getData() == DyeColor.GREEN.getData()) {

                    ChatUtilities.greenBroadcast(msg);
                    CoreHandler.setLocation(CoreType.GREEN, p.getBlock().getLocation());

                } else if (block.getData() == DyeColor.YELLOW.getData()) {

                    ChatUtilities.yellowBroadcast(msg);
                    CoreHandler.setLocation(CoreType.YELLOW, p.getBlock().getLocation());

                }

            }

            if (PlayerHandler.red.contains(p.getPlayer().getUniqueId())) {

                if (!LocationUtilities.isInside(block.getLocation(), new Location(Bukkit.getWorld("4Corners"), 6, 33, 6), new Location(Bukkit.getWorld("4Corners"), 18, 62, 18))) {

                    p.setCancelled(true);

                }

            } else if (PlayerHandler.purple.contains(p.getPlayer().getUniqueId())) {

                if (!LocationUtilities.isInside(block.getLocation(), new Location(Bukkit.getWorld("4Corners"), 6, 33, -6), new Location(Bukkit.getWorld("4Corners"), 18, 62, -18))) {

                    p.setCancelled(true);

                }

            } else if (PlayerHandler.green.contains(p.getPlayer().getUniqueId())) {

                if (!LocationUtilities.isInside(block.getLocation(), new Location(Bukkit.getWorld("4Corners"), -6, 33, 6), new Location(Bukkit.getWorld("4Corners"), -18, 62, 18))) {

                    p.setCancelled(true);

                }

            } else if (PlayerHandler.yellow.contains(p.getPlayer().getUniqueId())) {

                if (!LocationUtilities.isInside(block.getLocation(), new Location(Bukkit.getWorld("4Corners"), -6, 33, -6), new Location(Bukkit.getWorld("4Corners"), -18, 62, -18))) {

                    p.setCancelled(true);

                }

            }

        }

        if (LocationUtilities.isInside(block.getLocation(), new Location(Bukkit.getWorld("4Corners"), 18, 41, 18), new Location(Bukkit.getWorld("4Corners"), -18, 41, -18))) {
        
            p.setCancelled(true);
        
        }


    }
}
