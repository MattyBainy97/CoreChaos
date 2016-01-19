package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.CCItem;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDrop extends CCListener {

    public PlayerDrop(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {

        if (e.getItemDrop().getItemStack().equals(CCItem.redCore) || e.getItemDrop().getItemStack().equals(CCItem.purpleCore) || e.getItemDrop().getItemStack().equals(CCItem.greenCore) || e.getItemDrop().getItemStack().equals(CCItem.yellowCore)) {

            e.setCancelled(true);
            ChatUtilities.onePlayer("You cannot drop your Core", e.getPlayer());

        } else if (e.getItemDrop().getItemStack().equals(CCItem.redBuildOne) || e.getItemDrop().getItemStack().equals(CCItem.purpleBuildOne) || e.getItemDrop().getItemStack().equals(CCItem.greenBuildOne) || e.getItemDrop().getItemStack().equals(CCItem.yellowBuildOne)) {

            e.setCancelled(true);
            ChatUtilities.onePlayer("You cannot drop building material", e.getPlayer());

        } if(e.getItemDrop().getItemStack().equals(CCItem.spec)){
            
            e.setCancelled(true);
            
        } else {
            
            e.setCancelled(true);
            
        }

    }
}
