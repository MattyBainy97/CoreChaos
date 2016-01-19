package com.corechaos.utils;

import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreHandler;
import com.corechaos.handlers.CoreType;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

public class LocationUtilities {

    public static Location lobby = new Location(Bukkit.getWorld("4Corners"), 0.5, 34.0, 0.5);
    private static Location redCore = new Location(Bukkit.getWorld("4Corners"), 12, 34, 12);
    private static Location redPlayerOne = new Location(Bukkit.getWorld("4Corners"), 9.5, 34.0, 8.5, (float) -36.4, (float) 14.2);
    private static Location redPlayerTwo = new Location(Bukkit.getWorld("4Corners"), 8.5, 34.0, 9.5, (float) -54.4, (float) 14.2);
    public static Location redRespawn = new Location(Bukkit.getWorld("4Corners"), 5.5, 34.0, 5.5, (float) 135.0, (float) 0);
    private static Location purpleCore = new Location(Bukkit.getWorld("4Corners"), 12, 34, -12);
    private static Location purplePlayerOne = new Location(Bukkit.getWorld("4Corners"), 8.5, 34.0, -8.5, (float) -125.4, (float) 14.2);
    private static Location purplePlayerTwo = new Location(Bukkit.getWorld("4Corners"), 9.5, 34.0, -7.5, (float) -144.4, (float) 14.2);
    public static Location purpleRespawn = new Location(Bukkit.getWorld("4Corners"), 5.5, 34.0, -4.5, (float) 45.0, (float) 0);
    private static Location greenCore = new Location(Bukkit.getWorld("4Corners"), -12, 34, 12);
    private static Location greenPlayerOne = new Location(Bukkit.getWorld("4Corners"), -7.5, 34.0, 9.5, (float) 54.4, (float) 14.2);
    private static Location greenPlayerTwo = new Location(Bukkit.getWorld("4Corners"), -8.5, 34.0, 8.5, (float) 35.4, (float) 14.2);
    public static Location greenRespawn = new Location(Bukkit.getWorld("4Corners"), -4.5, 34.0, 5.5, (float) -135.0, (float) 0);
    private static Location yellowCore = new Location(Bukkit.getWorld("4Corners"), -12, 34, -12);
    private static Location yellowPlayerOne = new Location(Bukkit.getWorld("4Corners"), -8.5, 34.0, -7.5, (float) 146.4, (float) 14.2);
    private static Location yellowPlayerTwo = new Location(Bukkit.getWorld("4Corners"), -7.5, 34.0, -8.5, (float) 126.4, (float) 14.2);
    public static Location yellowRespawn = new Location(Bukkit.getWorld("4Corners"), -4.5, 34.0, -4.5, (float) -45.0, (float) 0);

    public static Boolean isInside(Location loc, Location corner1, Location corner2) {

        int xMin = 0;
        int xMax = 0;
        int yMin = 0;
        int yMax = 0;
        int zMin = 0;
        int zMax = 0;
        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();

        xMin = Math.min((int) corner1.getX(), (int) corner2.getX());
        xMax = Math.max((int) corner1.getX(), (int) corner2.getX());

        yMin = Math.min((int) corner1.getY(), (int) corner2.getY());
        yMax = Math.max((int) corner1.getY(), (int) corner2.getY());

        zMin = Math.min((int) corner1.getZ(), (int) corner2.getZ());
        zMax = Math.max((int) corner1.getZ(), (int) corner2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);

    }

    public static void teleportToGame() {

        if (PlayerHandler.red.size() > 0) {

            Bukkit.getPlayer(PlayerHandler.red.get(0)).teleport(redPlayerOne);
            if (PlayerHandler.red.size() == 2) {

                Bukkit.getPlayer(PlayerHandler.red.get(0)).teleport(redPlayerTwo);

            }

        }
        if (PlayerHandler.purple.size() > 0) {

            Bukkit.getPlayer(PlayerHandler.purple.get(0)).teleport(purplePlayerOne);
            if (PlayerHandler.purple.size() == 2) {

                Bukkit.getPlayer(PlayerHandler.purple.get(0)).teleport(purplePlayerTwo);

            }

        }
        if (PlayerHandler.green.size() > 0) {

            Bukkit.getPlayer(PlayerHandler.green.get(0)).teleport(greenPlayerOne);
            if (PlayerHandler.green.size() == 2) {

                Bukkit.getPlayer(PlayerHandler.green.get(0)).teleport(greenPlayerTwo);

            }

        }
        if (PlayerHandler.yellow.size() > 0) {

            Bukkit.getPlayer(PlayerHandler.yellow.get(0)).teleport(yellowPlayerOne);
            if (PlayerHandler.yellow.size() == 2) {

                Bukkit.getPlayer(PlayerHandler.yellow.get(0)).teleport(yellowPlayerTwo);

            }

        }

    }

    public static void spawnCores() {

        if (PlayerHandler.red.size() > 0) {

            redCore.getBlock().setType(Material.STAINED_GLASS);
            redCore.getBlock().setData(DyeColor.RED.getData());
            CoreHandler.setLocation(CoreType.RED, redCore);

        }
        if (PlayerHandler.purple.size() > 0) {

            purpleCore.getBlock().setType(Material.STAINED_GLASS);
            purpleCore.getBlock().setData(DyeColor.PURPLE.getData());
            CoreHandler.setLocation(CoreType.PURPLE, purpleCore);

        }
        if (PlayerHandler.green.size() > 0) {

            greenCore.getBlock().setType(Material.STAINED_GLASS);
            greenCore.getBlock().setData(DyeColor.GREEN.getData());
            CoreHandler.setLocation(CoreType.GREEN, greenCore);

        }
        if (PlayerHandler.yellow.size() > 0) {

            yellowCore.getBlock().setType(Material.STAINED_GLASS);
            yellowCore.getBlock().setData(DyeColor.YELLOW.getData());
            CoreHandler.setLocation(CoreType.YELLOW, yellowCore);

        }

    }

    public static void isCorePlaced() {

        if (CoreHandler.getLocation(CoreType.RED) == null || !isInside(CoreHandler.getLocation(CoreType.RED), new Location(Bukkit.getWorld("4Corners"), 6, 33, 6), new Location(Bukkit.getWorld("4Corners"), 18, 62, 18))) {

            for (UUID uuid : PlayerHandler.red) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p.isOnline()) {
                        if (p.getInventory().contains(CCItem.redCore)) {
                            p.getInventory().remove(CCItem.redCore);
                        }
                    }
                }
            }
            ChatUtilities.broadcast(ChatColor.RED + "Red Core" + ChatColor.GOLD + " has been destroyed as it was not placed");
            ChatUtilities.redBroadcast("You failed to place your Core so it has been destroyed");
            Bukkit.getServer().getWorld("4Corners").playSound(redCore, Sound.EXPLODE, 5, 1);
            CoreHandler.destroyCore(CoreType.RED);
            CoreHandler.setHealth(CoreType.RED, 0);
            CoreSB.updateSB();

        }
        if (CoreHandler.getLocation(CoreType.PURPLE) == null || !isInside(CoreHandler.getLocation(CoreType.PURPLE), new Location(Bukkit.getWorld("4Corners"), 6, 33, -6), new Location(Bukkit.getWorld("4Corners"), 18, 62, -18))) {

            for (UUID uuid : PlayerHandler.purple) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p.isOnline()) {
                        if (p.getInventory().contains(CCItem.purpleCore)) {
                            p.getInventory().remove(CCItem.purpleCore);
                        }
                    }
                }
            }
            ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Core" + ChatColor.GOLD + " has been destroyed as it was not placed");
            ChatUtilities.purpleBroadcast("You failed to place your Core so it has been destroyed");
            Bukkit.getServer().getWorld("4Corners").playSound(purpleCore, Sound.EXPLODE, 5, 1);
            CoreHandler.destroyCore(CoreType.PURPLE);
            CoreHandler.setHealth(CoreType.PURPLE, 0);
            CoreSB.updateSB();

        }
        if (CoreHandler.getLocation(CoreType.GREEN) == null || !isInside(CoreHandler.getLocation(CoreType.GREEN), new Location(Bukkit.getWorld("4Corners"), -6, 33, 6), new Location(Bukkit.getWorld("4Corners"), -18, 62, 18))) {

            for (UUID uuid : PlayerHandler.green) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p.isOnline()) {
                        if (p.getInventory().contains(CCItem.greenCore)) {
                            p.getInventory().remove(CCItem.greenCore);
                        }
                    }
                }
            }
            ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Core" + ChatColor.GOLD + " has been destroyed as it was not placed");
            ChatUtilities.greenBroadcast("You failed to place your Core so it has been destroyed");
            Bukkit.getServer().getWorld("4Corners").playSound(greenCore, Sound.EXPLODE, 5, 1);
            CoreHandler.destroyCore(CoreType.GREEN);
            CoreHandler.setHealth(CoreType.GREEN, 0);
            CoreSB.updateSB();

        }
        if (CoreHandler.getLocation(CoreType.YELLOW) == null || !isInside(CoreHandler.getLocation(CoreType.YELLOW), new Location(Bukkit.getWorld("4Corners"), -6, 33, -6), new Location(Bukkit.getWorld("4Corners"), -18, 62, -18))) {

            for (UUID uuid : PlayerHandler.yellow) {
                if (!Tasks.isDisconnected(uuid)) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p.isOnline()) {
                        if (p.getInventory().contains(CCItem.yellowCore)) {
                            p.getInventory().remove(CCItem.yellowCore);
                        }
                    }
                }
            }
            ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Core" + ChatColor.GOLD + " has been destroyed as it was not placed");
            ChatUtilities.yellowBroadcast("You failed to place your Core so it has been destroyed");
            Bukkit.getServer().getWorld("4Corners").playSound(yellowCore, Sound.EXPLODE, 5, 1);
            CoreHandler.destroyCore(CoreType.YELLOW);
            CoreHandler.setHealth(CoreType.YELLOW, 0);
            CoreSB.updateSB();

        }

    }

    public static void clearArea() {

        int xMin = -18;
        int xMax = 18;
        int yMin = 34;
        int yMax = 62;
        int zMin = -18;
        int zMax = 18;

        ArrayList<Block> blocks = new ArrayList<Block>();

        for (int x = xMin; x <= xMax; x++) {

            for (int z = zMin; z <= zMax; z++) {

                for (int y = yMin; y <= yMax; y++) {

                    blocks.add(Bukkit.getWorld("4Corners").getBlockAt(x, y, z));

                }

            }

        }

        for (Block b : blocks) {

            if (b.getState().getData() instanceof Wool || b.getData() == DyeColor.RED.getData() || b.getData() == DyeColor.PURPLE.getData() || b.getData() == DyeColor.GREEN.getData() || b.getData() == DyeColor.YELLOW.getData()) {

                b.setType(Material.AIR);

            }
        }
    }
}
