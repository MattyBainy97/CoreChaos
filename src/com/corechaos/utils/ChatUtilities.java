package com.corechaos.utils;

import com.corechaos.GameState;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtilities {

    public static void broadcast(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(starter() + msg);
        }
    }

    public static void broadcastNoStarter(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(msg);
        }
    }

    public static void redBroadcast(String msg) {
        if (!PlayerHandler.red.isEmpty()) {
            for (UUID uuid : PlayerHandler.red) {
                if (!Tasks.isDisconnected(uuid)) {
                    Bukkit.getPlayer(uuid).sendMessage(redStarter() + msg);
                }
            }
        }
    }

    public static void purpleBroadcast(String msg) {
        if (!PlayerHandler.purple.isEmpty()) {
            for (UUID uuid : PlayerHandler.purple) {
                if (!Tasks.isDisconnected(uuid)) {
                    Bukkit.getPlayer(uuid).sendMessage(purpleStarter() + msg);
                }
            }
        }
    }

    public static void greenBroadcast(String msg) {
        if (!PlayerHandler.green.isEmpty()) {
            for (UUID uuid : PlayerHandler.green) {
                if (!Tasks.isDisconnected(uuid)) {
                    Bukkit.getPlayer(uuid).sendMessage(greenStarter() + msg);
                }
            }
        }
    }

    public static void yellowBroadcast(String msg) {
        if (!PlayerHandler.yellow.isEmpty()) {
            for (UUID uuid : PlayerHandler.yellow) {
                if (!Tasks.isDisconnected(uuid)) {
                    Bukkit.getPlayer(uuid).sendMessage(yellowStarter() + msg);
                }
            }
        }
    }

    public static void infoBar(String msg) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftplayer = (CraftPlayer) p;
            PlayerConnection connection = craftplayer.getHandle().playerConnection;
            IChatBaseComponent warning = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
            PacketPlayOutChat packet = new PacketPlayOutChat();

            try {
                Field field = packet.getClass().getDeclaredField("a");
                field.setAccessible(true);
                field.set(packet, warning);
                field.setAccessible(!field.isAccessible());

                Field Field2 = packet.getClass().getDeclaredField("b");
                Field2.setAccessible(true);
                Field2.set(packet, (byte) 2);
                Field2.setAccessible(!Field2.isAccessible());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            connection.sendPacket(packet);
        }

    }

    public static void showList(Player p) {

        if (GameState.isState(GameState.BUILD) || GameState.isState(GameState.FIGHT)) {

            String specs = "";
            String red = "";
            String purple = "";
            String green = "";
            String yellow = "";

            for (UUID uuid : PlayerHandler.red) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player c = Bukkit.getPlayer(uuid);
                    if (uuid == PlayerHandler.red.get(PlayerHandler.red.size() - 1)) {
                        red = red + c.getName();
                    } else {
                        red = red + c.getName() + ", ";
                    }
                }
            }

            for (UUID uuid : PlayerHandler.purple) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player c = Bukkit.getPlayer(uuid);
                    if (uuid == PlayerHandler.purple.get(PlayerHandler.purple.size() - 1)) {
                        purple = purple + c.getName();
                    } else {
                        purple = purple + c.getName() + ", ";
                    }
                }
            }

            for (UUID uuid : PlayerHandler.green) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player c = Bukkit.getPlayer(uuid);
                    if (uuid == PlayerHandler.green.get(PlayerHandler.green.size() - 1)) {
                        green = green + c.getName();
                    } else {
                        green = green + c.getName() + ", ";
                    }
                }
            }

            for (UUID uuid : PlayerHandler.yellow) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player c = Bukkit.getPlayer(uuid);
                    if (uuid == PlayerHandler.yellow.get(PlayerHandler.yellow.size() - 1)) {
                        yellow = yellow + c.getName();
                    } else {
                        yellow = yellow + c.getName() + ", ";
                    }
                }
            }

            for (UUID uuid : PlayerHandler.spec) {

                if (!Tasks.isDisconnected(uuid)) {
                    Player c = Bukkit.getPlayer(uuid);
                    if (uuid == PlayerHandler.spec.get(PlayerHandler.spec.size() - 1)) {
                        specs = specs + c.getName();
                    } else {
                        specs = specs + c.getName() + ", ";
                    }
                }

            }

            p.sendMessage(LIGHT_PURPLE + "" + BOLD + "Online Players");
            if (!red.equals("")) {
                p.sendMessage(RED + "Red Team: " + GOLD + red);
            }
            if (!purple.equals("")) {
                p.sendMessage(DARK_PURPLE + "Purple Team: " + GOLD + purple);
            }
            if (!green.equals("")) {
                p.sendMessage(DARK_GREEN + "Green Team: " + GOLD + green);
            }
            if (!yellow.equals("")) {
                p.sendMessage(YELLOW + "Yellow Team: " + GOLD + yellow);
            }
            if (!specs.equals("")) {
                p.sendMessage(DARK_RED + "DEAD: " + GOLD + specs);
            }

        } else {

            String online = "";

            for (UUID uuid : PlayerHandler.players) {

                Player c = Bukkit.getPlayer(uuid);
                if (uuid == PlayerHandler.players.get(PlayerHandler.players.size() - 1)) {
                    online = online + c.getName();
                } else {
                    online = online + c.getName() + ", ";
                }

            }

            p.sendMessage(LIGHT_PURPLE + "" + BOLD + "Online Players");
            p.sendMessage(GREEN + "ONLINE: " + GOLD + online);

        }

    }

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    public static void chat(String msg, Player player) {

        if (GameState.isState(GameState.BUILD) || GameState.isState(GameState.FIGHT)) {
            if (PlayerHandler.players.contains(player.getUniqueId())) {

                if (PlayerHandler.red.contains(player.getUniqueId())) {
                    broadcastNoStarter(chatStarter(player) + RED + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else if (PlayerHandler.purple.contains(player.getUniqueId())) {
                    broadcastNoStarter(chatStarter(player) + DARK_PURPLE + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else if (PlayerHandler.green.contains(player.getUniqueId())) {
                    broadcastNoStarter(chatStarter(player) + DARK_GREEN + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else if (PlayerHandler.yellow.contains(player.getUniqueId())) {
                    broadcastNoStarter(chatStarter(player) + YELLOW + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                }

            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    if (PlayerHandler.spec.contains(p.getUniqueId())) {

                        p.sendMessage(DARK_RED + "DEAD " + chatStarter(player) + DARK_AQUA + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);

                    }

                }
            }
        } else {
            Database.openConnection();
            Bukkit.broadcastMessage(GRAY + "(" + YELLOW + Database.getCc(player, "points") + GRAY + ") " + chatStarter(player) + DARK_AQUA + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
            Database.closeConnection();
        }

    }

    public static String chatStarter(Player p) {

        Database.openConnection();

        int points = Database.getCc(p, "points");
        int highestPoints = Database.getHighestPoints();

        Database.closeConnection();

        if (points == highestPoints) {

            return RED + "§lC" + GOLD + "§lo" + YELLOW + "§lr" + GREEN + "§le " + BLUE + "§lC" + DARK_PURPLE + "§lr" + LIGHT_PURPLE + "§lu" + RED + "§ls" + GOLD + "§lh" + YELLOW + "§le" + GREEN + "§lr " + BLUE + "§l| ";

        } else {

            if (points >= 0 && points <= 49) {

                return GOLD + "Apprentice | ";

            } else if (points >= 50 && points <= 249) {

                return LIGHT_PURPLE + "Beater | ";

            } else if (points >= 250 && points <= 999) {

                return BLUE + "Breaker | ";

            } else if (points >= 1000 && points <= 2499) {

                return YELLOW + "Smasher | ";

            } else if (points >= 2500 && points <= 4999) {

                return GREEN + "Destroyer | ";

            } else if (points >= 5000 && points <= 9999) {

                return RED + "Destructive | ";

            } else if (points >= 10000 && points <= 49999) {

                return AQUA + "Explosive | ";

            } else if (points >= 50000) {

                return DARK_GREEN + "Demolitionist | ";

            }
        }

        return "";

    }

    public static void records(OfflinePlayer player, Player send) {

        Database.openConnection();

        try {

            double kd = Database.getOfflineCc(player, "kills") / Database.getOfflineCc(player, "deaths");
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            
            send.sendMessage(GRAY + "[" + AQUA + "CoreChaos" + RED + " Records" + GRAY + "]");
            send.sendMessage(GOLD + "User: " + YELLOW + player.getName());
            send.sendMessage(GOLD + "Points: " + YELLOW + Database.getOfflineCc(player, "points"));
            send.sendMessage(GOLD + "Games: " + YELLOW + Database.getOfflineCc(player, "games"));
            send.sendMessage(GOLD + "Wins: " + YELLOW + Database.getOfflineCc(player, "wins"));
            send.sendMessage(GOLD + "K/D Ratio: " + YELLOW + df.format(kd));
            send.sendMessage(GOLD + "Cores Destroyed: " + YELLOW + Database.getOfflineCc(player, "destroyed"));
            send.sendMessage(GRAY + "[" + AQUA + "CoreChaos" + RED + " Records" + GRAY + "]");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            Database.closeConnection();

        }


    }

    private static String redStarter() {

        return GRAY + "[" + RED + "Red" + GRAY + "] " + RED;

    }

    private static String purpleStarter() {

        return GRAY + "[" + DARK_PURPLE + "Purple" + GRAY + "] " + DARK_PURPLE;

    }

    private static String greenStarter() {

        return GRAY + "[" + DARK_GREEN + "Green" + GRAY + "] " + DARK_GREEN;

    }

    private static String yellowStarter() {

        return GRAY + "[" + YELLOW + "Yellow" + GRAY + "] " + YELLOW;

    }

    public static String starter() {

        return GRAY + "[" + AQUA + "CoreChaos" + GRAY + "] " + GOLD;

    }
}
