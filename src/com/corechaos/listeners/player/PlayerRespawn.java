package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn extends CCListener {

    public PlayerRespawn(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onPlayerRespawnEvent(final PlayerRespawnEvent r) {

        boolean isRed = PlayerHandler.red.contains(r.getPlayer().getUniqueId());
        boolean isPurple = PlayerHandler.purple.contains(r.getPlayer().getUniqueId());
        boolean isGreen = PlayerHandler.green.contains(r.getPlayer().getUniqueId());
        boolean isYellow = PlayerHandler.yellow.contains(r.getPlayer().getUniqueId());
        
        CoreSB.setHealth(r.getPlayer());

        if (isRed) {

            if (PlayerHandler.players.contains(r.getPlayer().getUniqueId())) {

                ChatUtilities.onePlayer("You were revived by your Core", r.getPlayer());
                r.setRespawnLocation(LocationUtilities.redRespawn);
                for (int i = 1; i <= 16; i++) {
                    r.getPlayer().getInventory().addItem(CCItem.redBuildOne);
                }

            } else {

                Player player = r.getPlayer();
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.hidePlayer(player);
                }
                ChatUtilities.onePlayer("You could not be revived as your Core is dead", player);
                ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", player);
                PlayerHandler.removeRed(player);
                if (PlayerHandler.red.isEmpty()) {

                    ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " has been eliminated");

                }
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.getInventory().clear();
                player.getInventory().addItem(CCItem.spec);
                r.setRespawnLocation(LocationUtilities.lobby);

            }

        } else if (isPurple) {

            if (PlayerHandler.players.contains(r.getPlayer().getUniqueId())) {

                ChatUtilities.onePlayer("You were revived by your Core", r.getPlayer());
                r.setRespawnLocation(LocationUtilities.purpleRespawn);
                for (int i = 1; i <= 16; i++) {
                    r.getPlayer().getInventory().addItem(CCItem.purpleBuildOne);
                }

            } else {

                Player player = r.getPlayer();
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.hidePlayer(player);
                }
                ChatUtilities.onePlayer("You could not be revived as your Core is dead", player);
                ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", player);
                PlayerHandler.removePurple(player);
                if (PlayerHandler.purple.isEmpty()) {

                    ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " has been eliminated");

                }
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.getInventory().clear();
                player.getInventory().addItem(CCItem.spec);
                r.setRespawnLocation(LocationUtilities.lobby);

            }

        } else if (isGreen) {

            if (PlayerHandler.players.contains(r.getPlayer().getUniqueId())) {

                ChatUtilities.onePlayer("You were revived by your Core", r.getPlayer());
                r.setRespawnLocation(LocationUtilities.greenRespawn);
                for (int i = 1; i <= 16; i++) {
                    r.getPlayer().getInventory().addItem(CCItem.greenBuildOne);
                }

            } else {

                Player player = r.getPlayer();
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.hidePlayer(player);
                }
                ChatUtilities.onePlayer("You could not be revived as your Core is dead", player);
                ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", player);
                PlayerHandler.removeGreen(player);
                if (PlayerHandler.green.isEmpty()) {

                    ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " has been eliminated");

                }
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.getInventory().clear();
                player.getInventory().addItem(CCItem.spec);
                r.setRespawnLocation(LocationUtilities.lobby);

            }

        } else if (isYellow) {

            if (PlayerHandler.players.contains(r.getPlayer().getUniqueId())) {

                ChatUtilities.onePlayer("You were revived by your Core", r.getPlayer());
                r.setRespawnLocation(LocationUtilities.yellowRespawn);
                for(int i = 1; i <= 16; i++){
                    r.getPlayer().getInventory().addItem(CCItem.yellowBuildOne);
                }

            } else {

                Player player = r.getPlayer();
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.hidePlayer(player);
                }
                ChatUtilities.onePlayer("You could not be revived as your Core is dead", player);
                ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", player);
                PlayerHandler.removeYellow(player);
                if (PlayerHandler.yellow.isEmpty()) {

                    ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " has been eliminated");

                }
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.getInventory().clear();
                player.getInventory().addItem(CCItem.spec);
                r.setRespawnLocation(LocationUtilities.lobby);

            }

        } else {

            r.setRespawnLocation(LocationUtilities.lobby);

        }

    }
}
