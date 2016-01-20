package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.handlers.CoreHandler;
import com.corechaos.handlers.CoreType;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends CCListener {

    public PlayerDeath(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {

        e.getDrops().clear();
        e.setDeathMessage("");

        Player killed = (Player) e.getEntity();
        Player killer = (Player) killed.getKiller();

        boolean isRed = PlayerHandler.red.contains(killed.getUniqueId());
        boolean isPurple = PlayerHandler.purple.contains(killed.getUniqueId());
        boolean isGreen = PlayerHandler.green.contains(killed.getUniqueId());
        boolean isYellow = PlayerHandler.yellow.contains(killed.getUniqueId());
        
        if (isRed && !CoreHandler.isCoreAlive(CoreType.RED)) {

            PlayerHandler.addSpec(killed);
            PlayerHandler.removePlayer(killed);
            ChatUtilities.redBroadcast(killed.getName() + " was eliminated");
            ChatUtilities.onePlayer("You eliminated " + ChatColor.RED + killed.getName(), killer);

        } else if (isPurple && !CoreHandler.isCoreAlive(CoreType.PURPLE)) {

            PlayerHandler.addSpec(killed);
            PlayerHandler.removePlayer(killed);
            ChatUtilities.purpleBroadcast(killed.getName() + " was eliminated");
            ChatUtilities.onePlayer("You eliminated " + ChatColor.DARK_PURPLE + killed.getName(), killer);

        } else if (isGreen && !CoreHandler.isCoreAlive(CoreType.GREEN)) {

            PlayerHandler.addSpec(killed);
            PlayerHandler.removePlayer(killed);
            ChatUtilities.greenBroadcast(killed.getName() + " was eliminated");
            ChatUtilities.onePlayer("You eliminated " + ChatColor.DARK_GREEN + killed.getName(), killer);

        } else if (isYellow && !CoreHandler.isCoreAlive(CoreType.YELLOW)) {

            PlayerHandler.addSpec(killed);
            PlayerHandler.removePlayer(killed);
            ChatUtilities.yellowBroadcast(killed.getName() + " was eliminated");
            ChatUtilities.onePlayer("You eliminated " + ChatColor.YELLOW + killed.getName(), killer);

        } else {

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

            ChatUtilities.onePlayer("You killed " + cc + killed.getName(), killer);

        }
        
        Database.openConnection();
        Database.updateCcTable(killer, "kills", Database.getCc(killer, "kills") + 1);
        Database.updateCcTable(killer, "points", Database.getCc(killer, "points") + 5);
        ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "5" + ChatColor.GOLD + " points", killer);
        Database.updateCcTable(killed, "deaths", Database.getCc(killed, "deaths") + 1);
        Database.closeConnection();

        CoreChaos.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CoreChaos.plugin, new Runnable() {
            @Override
            public void run() {
                ((CraftPlayer) e.getEntity().getPlayer()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
            }
        }, 5L);


    }
}
