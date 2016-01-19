package com.corechaos.listeners.inventory;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ClickSlot extends CCListener {

    public ClickSlot(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onClickSlot(InventoryClickEvent e) {
        if (!GameState.isState(GameState.IN_LOBBY)) {
            if (e.getSlot() >= 0) {
                Player p = (Player) e.getWhoClicked();
                if (PlayerHandler.spec.contains(p.getUniqueId())) {

                    Player tp = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
                    p.teleport(tp);
                    e.setResult(Event.Result.DENY);
                    e.setCancelled(true);

                }
            }
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
            }
        }
    }
}
