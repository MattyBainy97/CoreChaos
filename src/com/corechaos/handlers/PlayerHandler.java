package com.corechaos.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHandler {

    public static ArrayList<UUID> players = new ArrayList<UUID>();
    public static ArrayList<UUID> red = new ArrayList<UUID>();
    public static ArrayList<UUID> purple = new ArrayList<UUID>();
    public static ArrayList<UUID> green = new ArrayList<UUID>();
    public static ArrayList<UUID> yellow = new ArrayList<UUID>();
    public static ArrayList<UUID> spec = new ArrayList<UUID>();

    public static void addPlayer(Player p) {

        players.add(p.getUniqueId());

    }

    public static void addSpec(Player p) {

        spec.add(p.getUniqueId());

    }

    public static void removePlayer(Player p) {

        players.remove(p.getUniqueId());

    }

    public static void removeRed(Player p) {

        red.remove(p.getUniqueId());

    }

    public static void removePurple(Player p) {

        purple.remove(p.getUniqueId());

    }

    public static void removeGreen(Player p) {

        green.remove(p.getUniqueId());

    }

    public static void removeYellow(Player p) {

        yellow.remove(p.getUniqueId());

    }

    public static void removeSpec(Player p) {

        spec.remove(p.getUniqueId());

    }

    public static void giveItems() {

        if (red.size() == 2) {

            for (UUID uuid : red) {

                Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.redBuild);
                
                for (int i = 1; i <= 32; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.redBuildOne);
                    
                }
                
            }

        } else {
            
            for (UUID uuid : red) {
                
                for (int i = 1; i <= 3; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.redBuild);
                    
                }
                
            }
            
        }

        if (purple.size() == 2) {

            for (UUID uuid : purple) {

                Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.purpleBuild);
                
                for (int i = 1; i <= 32; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.purpleBuildOne);
                    
                }
                
            }

        } else {
            
            for (UUID uuid : purple) {
                
                for (int i = 1; i <= 3; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.purpleBuild);
                    
                }
                
            }

        }
        
        if (green.size() == 2) {

            for (UUID uuid : green) {

                Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.greenBuild);
                
                for (int i = 1; i <= 32; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.greenBuildOne);
                    
                }
                
            }

        } else {
            
            for (UUID uuid : green) {
                
                for (int i = 1; i <= 3; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.greenBuild);
                    
                }
                
            }

        }
        
        if (yellow.size() == 2) {

            for (UUID uuid : yellow) {

                Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.yellowBuild);
                
                for (int i = 1; i <= 32; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.yellowBuildOne);
                    
                }
                
            }

        } else {
            
            for (UUID uuid : yellow) {
                
                for (int i = 1; i <= 3; i++) {
                    
                    Bukkit.getPlayer(uuid).getInventory().addItem(CCItem.yellowBuild);
                    
                }
                
            }

        }

    }

    public static void assignTeams() {

        Collections.shuffle(players);

        for (UUID uuid : players) {

            int[] currentTeamSizes = {red.size(), purple.size(), green.size(), yellow.size()};

            int smallestTeamSize = getSmallestTeam(currentTeamSizes);

            if (red.size() == smallestTeamSize) {

                red.add(uuid);
                CoreSB.addRed(Bukkit.getPlayer(uuid));

            } else if (purple.size() == smallestTeamSize) {

                purple.add(uuid);
                CoreSB.addPurple(Bukkit.getPlayer(uuid));

            } else if (green.size() == smallestTeamSize) {

                green.add(uuid);
                CoreSB.addGreen(Bukkit.getPlayer(uuid));

            } else if (yellow.size() == smallestTeamSize) {

                yellow.add(uuid);
                CoreSB.addYellow(Bukkit.getPlayer(uuid));

            }

        }

    }

    public static void clearAll() {

        players.clear();
        red.clear();
        purple.clear();
        green.clear();
        yellow.clear();
        spec.clear();

    }

    private static int getSmallestTeam(int[] sizes) {

        int smallest = 100;

        for (int i : sizes) {

            if (i < smallest) {

                smallest = i;

            }

        }

        return smallest;

    }
}
