package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.Game;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKick extends CCListener {

    public PlayerKick(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {

        Player p = e.getPlayer();
        PlayerHandler.removePlayer(p);
        e.setLeaveMessage("");

        boolean isRed = PlayerHandler.red.contains(p.getUniqueId());
        boolean isPurple = PlayerHandler.purple.contains(p.getUniqueId());
        boolean isGreen = PlayerHandler.green.contains(p.getUniqueId());
        boolean isYellow = PlayerHandler.yellow.contains(p.getUniqueId());
        ChatColor cc;

        if (isRed) {
            cc = ChatColor.RED;
        } else if (isPurple) {
            cc = ChatColor.DARK_PURPLE;
        } else if (isGreen) {
            cc = ChatColor.DARK_GREEN;
        } else if (isYellow) {
            cc = ChatColor.YELLOW;
        } else {
            cc = ChatColor.RESET;
        }

        if (GameState.isState(GameState.IN_LOBBY)) {

            Game.setCanStart(Bukkit.getOnlinePlayers().size() >= 4);

        }

        if (PlayerHandler.spec.contains(p.getUniqueId())) {

            PlayerHandler.removeSpec(p);

        }

        PlayerHandler.addSpec(p);

        ChatUtilities.broadcast(cc + p.getName() + ChatColor.GOLD + " has been kicked from the game");

        if (isRed) {
            PlayerHandler.removeRed(p);
            if (PlayerHandler.red.isEmpty()) {

                ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " has been eliminated");

            }
        } else if (isPurple) {
            PlayerHandler.removePurple(p);
            if (PlayerHandler.purple.isEmpty()) {

                ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " has been eliminated");

            }
        } else if (isGreen) {
            PlayerHandler.removeGreen(p);
            if (PlayerHandler.green.isEmpty()) {

                ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " has been eliminated");

            }
        } else if (isYellow) {
            PlayerHandler.removeYellow(p);
            if (PlayerHandler.yellow.isEmpty()) {

                ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " has been eliminated");

            }
        }

    }
}
