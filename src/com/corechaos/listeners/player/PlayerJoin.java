package com.corechaos.listeners.player;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.Game;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import java.lang.reflect.Field;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin extends CCListener {

    public PlayerJoin(CoreChaos pl) {

        super(pl);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        
        Database.openConnection();

        try {

            if (Database.playerTableContainsPlayer(e.getPlayer())) {

                Database.updateLastLogin(e.getPlayer());

            } else {

                Database.addPlayerToPlayerTable(e.getPlayer());

            }

            if (!Database.ccTableContainsPlayer(e.getPlayer())) {

                Database.addPlayerToCcTable(e.getPlayer());

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            Database.closeConnection();

        }

        e.setJoinMessage("");
        final Player p = e.getPlayer();

        CraftPlayer craftplayer = (CraftPlayer) p;

        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent header = ChatSerializer.a("{\"text\": \"   §9Corners §cv1   \"}");
        IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"   §4Development Test   \"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, header);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footer);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        connection.sendPacket(packet);

        if (GameState.isState(GameState.IN_LOBBY)) {

            ChatUtilities.broadcast(ChatColor.DARK_AQUA + e.getPlayer().getName() + ChatColor.GOLD + " joined the game");
            PlayerHandler.addPlayer(p);
            p.getInventory().clear();
            p.getInventory().setHelmet(null);
            p.setHealth(20.0);
            p.setExp(0);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(LocationUtilities.lobby);
            Game.setCanStart(Bukkit.getOnlinePlayers().size() >= 4);

        } else {

            if (PlayerHandler.red.contains(p.getUniqueId()) || PlayerHandler.purple.contains(p.getUniqueId()) || PlayerHandler.green.contains(p.getUniqueId()) || PlayerHandler.yellow.contains(p.getUniqueId())) {

                Bukkit.getScheduler().cancelTask(Tasks.getDisconnectNumber(p));
                Tasks.removeDisconnect(p);
                
                boolean isRed = PlayerHandler.red.contains(e.getPlayer().getUniqueId());
                boolean isPurple = PlayerHandler.purple.contains(e.getPlayer().getUniqueId());
                boolean isGreen = PlayerHandler.green.contains(e.getPlayer().getUniqueId());
                boolean isYellow = PlayerHandler.yellow.contains(e.getPlayer().getUniqueId());
                ChatColor cc;
                
                if(GameState.isState(GameState.FIGHT)){
                    
                    p.getInventory().clear();
                    
                }

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

                PlayerHandler.addPlayer(p);
                ChatUtilities.broadcast(cc + e.getPlayer().getName() + ChatColor.GOLD + " rejoined the game");
                p.setGameMode(GameMode.SURVIVAL);
                CoreSB.showScoreboard();

            } else {

                PlayerHandler.addSpec(p);

                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    for (UUID uuid : PlayerHandler.spec) {
                        everyone.hidePlayer(Bukkit.getPlayer(uuid));
                    }
                }

                ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", p);
                p.getInventory().clear();
                p.getInventory().setHelmet(null);
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.getInventory().clear();
                p.setExp(0);
                p.getInventory().addItem(CCItem.spec);
                p.teleport(LocationUtilities.lobby);
                CoreSB.showScoreboard();

            }

        }

    }
}