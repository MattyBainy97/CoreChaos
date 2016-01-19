package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class AsyncPlayerPreLogin extends CCListener {

    public AsyncPlayerPreLogin(CoreChaos pl) {
        super(pl);
    }

    @EventHandler
    public void playerPreLogin(AsyncPlayerPreLoginEvent e) {

        if (GameState.isState(GameState.BUILD)) {
            if (PlayerHandler.red.contains(e.getUniqueId()) || PlayerHandler.purple.contains(e.getUniqueId()) || PlayerHandler.green.contains(e.getUniqueId()) || PlayerHandler.yellow.contains(e.getUniqueId())) {
                
                e.allow();
                
            } else {
                
                e.disallow(Result.KICK_OTHER, ChatColor.RED + "Game is in build mode! Try joining in a few seconds!");
            }

        } else if (GameState.isState(GameState.FIGHT)) {

            e.allow();

        } else if (GameState.isState(GameState.POST_GAME)) {

            e.disallow(Result.KICK_OTHER, ChatColor.RED + "A game is ending!");

        } else if (GameState.isState(GameState.RESET)) {

            e.disallow(Result.KICK_OTHER, ChatColor.RED + "The server is restarting!");

        } else {

            e.allow();

        }

    }
}