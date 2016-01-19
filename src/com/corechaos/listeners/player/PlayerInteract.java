package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerInteract extends CCListener {

    public PlayerInteract(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent i) {

        if (i.getAction() == Action.RIGHT_CLICK_AIR) {

            if (i.getPlayer().getItemInHand().equals(CCItem.spec)) {

                Inventory inv = Bukkit.createInventory(null, 9, "Spectate Players");

                for (int j = 0; j < PlayerHandler.players.size(); j++) {
                    String playerName = Bukkit.getPlayer(PlayerHandler.players.get(j)).getName();
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(playerName);
                    meta.setDisplayName(playerName);
                    skull.setItemMeta(meta);
                    inv.setItem(j, skull);
                }

                i.getPlayer().openInventory(inv);

            }
        }
        
        if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (i.getPlayer().getItemInHand().equals(CCItem.spec)) {

                Inventory inv = Bukkit.createInventory(null, 9, "Spectate Players");

                for (int j = 0; j < PlayerHandler.players.size(); j++) {
                    String playerName = Bukkit.getPlayer(PlayerHandler.players.get(j)).getName();
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(playerName);
                    meta.setDisplayName(playerName);
                    skull.setItemMeta(meta);
                    inv.setItem(j, skull);
                }

                i.getPlayer().openInventory(inv);

            }
            
        }
    }
}

            