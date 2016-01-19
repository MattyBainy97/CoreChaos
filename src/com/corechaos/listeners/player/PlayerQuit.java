package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.Game;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import com.corechaos.listeners.CCListener;
import com.corechaos.threads.GameTimer;
import com.corechaos.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit extends CCListener {

    public PlayerQuit(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerLeaveEvent(final PlayerQuitEvent q) {

        Player p = q.getPlayer();
        q.setQuitMessage("");
        PlayerHandler.removePlayer(p);

        if (GameState.isState(GameState.IN_LOBBY)) {

            Game.setCanStart(Bukkit.getOnlinePlayers().size() >= 4);

        }

        if (PlayerHandler.spec.contains(p.getUniqueId())) {

            PlayerHandler.removeSpec(p);

        }

        if (PlayerHandler.red.contains(p.getUniqueId()) || PlayerHandler.purple.contains(p.getUniqueId()) || PlayerHandler.green.contains(p.getUniqueId()) || PlayerHandler.yellow.contains(p.getUniqueId())) {

            final boolean isRed = PlayerHandler.red.contains(q.getPlayer().getUniqueId());
            final boolean isPurple = PlayerHandler.purple.contains(q.getPlayer().getUniqueId());
            final boolean isGreen = PlayerHandler.green.contains(q.getPlayer().getUniqueId());
            final boolean isYellow = PlayerHandler.yellow.contains(q.getPlayer().getUniqueId());
            final ChatColor cc;

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

            if (GameState.isState(GameState.BUILD) || GameState.isState(GameState.FIGHT)) {
                if (GameTimer.timeUntilEnd > 60 || GameTimer.timeUntilEnd == 0) {
                    ChatUtilities.broadcast(cc + q.getPlayer().getName() + ChatColor.GOLD + " has left the game");
                    int taskID = CoreChaos.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CoreChaos.plugin, new Runnable() {
                        @Override
                        public void run() {

                            if (!PlayerHandler.players.contains(q.getPlayer().getUniqueId())) {

                                PlayerHandler.addSpec(q.getPlayer());

                                ChatUtilities.broadcast(cc + q.getPlayer().getName() + ChatColor.GOLD + " has been removed for disconnecting");

                                if (isRed) {
                                    PlayerHandler.removeRed(q.getPlayer());
                                    if (PlayerHandler.red.isEmpty()) {

                                        ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " has been eliminated");

                                    }
                                } else if (isPurple) {
                                    PlayerHandler.removePurple(q.getPlayer());
                                    if (PlayerHandler.purple.isEmpty()) {

                                        ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " has been eliminated");

                                    }
                                } else if (isGreen) {
                                    PlayerHandler.removeGreen(q.getPlayer());
                                    if (PlayerHandler.green.isEmpty()) {

                                        ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " has been eliminated");

                                    }
                                } else if (isYellow) {
                                    PlayerHandler.removeYellow(q.getPlayer());
                                    if (PlayerHandler.yellow.isEmpty()) {

                                        ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " has been eliminated");

                                    }
                                }

                            }

                        }
                    }, 1200L);

                    Tasks.addDisconnect(p, taskID);

                } else {

                    PlayerHandler.addSpec(q.getPlayer());

                    ChatUtilities.broadcast(cc + q.getPlayer().getName() + ChatColor.GOLD + " has been removed for disconnecting");

                    if (isRed) {
                        PlayerHandler.removeRed(q.getPlayer());
                        if (PlayerHandler.red.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.RED + "Red Team" + ChatColor.GOLD + " has been eliminated");

                        }
                    } else if (isPurple) {
                        PlayerHandler.removePurple(q.getPlayer());
                        if (PlayerHandler.purple.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.GOLD + " has been eliminated");

                        }
                    } else if (isGreen) {
                        PlayerHandler.removeGreen(q.getPlayer());
                        if (PlayerHandler.green.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Team" + ChatColor.GOLD + " has been eliminated");

                        }
                    } else if (isYellow) {
                        PlayerHandler.removeYellow(q.getPlayer());
                        if (PlayerHandler.yellow.isEmpty()) {

                            ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Team" + ChatColor.GOLD + " has been eliminated");

                        }
                    }

                }
            }
        }
    }
}
