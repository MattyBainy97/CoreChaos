package com.corechaos.listeners.world;

import com.corechaos.CoreChaos;
import com.corechaos.GameState;
import com.corechaos.GameState;
import com.corechaos.handlers.CCItem;
import com.corechaos.handlers.CoreHandler;
import com.corechaos.handlers.CoreType;
import com.corechaos.handlers.CoreSB;
import com.corechaos.handlers.Database;
import com.corechaos.handlers.PlayerHandler;
import com.corechaos.handlers.Tasks;
import com.corechaos.listeners.CCListener;
import com.corechaos.utils.ChatUtilities;
import com.corechaos.utils.LocationUtilities;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends CCListener {

    public BlockBreak(CoreChaos pl) {

        super(pl);

    }

    @EventHandler
    public void onBreakBlockEvent(BlockBreakEvent b) {

        Location groundCornerOne = new Location(Bukkit.getWorld("4Corners"), 18, 33, -18);
        Location groundCornerTwo = new Location(Bukkit.getWorld("4Corners"), -18, 33, 18);
        Block block = b.getBlock();
        
        if(PlayerHandler.spec.contains(b.getPlayer().getUniqueId())){
            
            b.setCancelled(true);
            
        }

        if (LocationUtilities.isInside(block.getLocation(), groundCornerOne, groundCornerTwo)) {

            b.setCancelled(true);

        } else if (block.getType().equals(Material.STAINED_GLASS)) {

            if (GameState.isState(GameState.BUILD)) {
                String msg = b.getPlayer().getName() + " picked up the Core";

                if (block.getData() == DyeColor.RED.getData()) {

                    b.getPlayer().getInventory().addItem(CCItem.redCore);
                    ChatUtilities.redBroadcast(msg);
                    CoreHandler.setLocation(CoreType.RED, null);

                } else if (block.getData() == DyeColor.PURPLE.getData()) {

                    b.getPlayer().getInventory().addItem(CCItem.purpleCore);
                    ChatUtilities.purpleBroadcast(msg);
                    CoreHandler.setLocation(CoreType.PURPLE, null);

                } else if (block.getData() == DyeColor.GREEN.getData()) {

                    b.getPlayer().getInventory().addItem(CCItem.greenCore);
                    ChatUtilities.greenBroadcast(msg);
                    CoreHandler.setLocation(CoreType.GREEN, null);

                } else if (block.getData() == DyeColor.YELLOW.getData()) {

                    b.getPlayer().getInventory().addItem(CCItem.yellowCore);
                    ChatUtilities.yellowBroadcast(msg);
                    CoreHandler.setLocation(CoreType.YELLOW, null);

                }
            } else if (GameState.isState(GameState.FIGHT)) {

                boolean isRed = PlayerHandler.red.contains(b.getPlayer().getUniqueId());
                boolean isPurple = PlayerHandler.purple.contains(b.getPlayer().getUniqueId());
                boolean isGreen = PlayerHandler.green.contains(b.getPlayer().getUniqueId());
                boolean isYellow = PlayerHandler.yellow.contains(b.getPlayer().getUniqueId());

                if (block.getData() == DyeColor.RED.getData()) {

                    if (isRed) {

                        ChatUtilities.onePlayer("You cannot break your core after build time", b.getPlayer());
                        b.setCancelled(true);

                    } else {
                        
                        CoreHandler.damageCore(CoreType.RED);
                        CoreSB.updateSB();

                        if (isPurple) {

                            ChatUtilities.purpleBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.RED + "Red Core");
                            ChatUtilities.redBroadcast("Your core was damaged by " + ChatColor.DARK_PURPLE + b.getPlayer().getName());

                        } else if (isGreen) {

                            ChatUtilities.greenBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.RED + "Red Core");
                            ChatUtilities.redBroadcast("Your core was damaged by " + ChatColor.DARK_GREEN + b.getPlayer().getName());

                        } else if (isYellow) {

                            ChatUtilities.yellowBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.RED + "Red Core");
                            ChatUtilities.redBroadcast("Your core was damaged by " + ChatColor.YELLOW + b.getPlayer().getName());

                        }

                        if (CoreHandler.getHealth(CoreType.RED) != 0) {

                            for(UUID uuid : PlayerHandler.red){
                                
                                if(!Tasks.isDisconnected(uuid)){
                                    
                                    Player p = Bukkit.getPlayer(uuid);
                                    p.playSound(p.getLocation(), Sound.GLASS, 2, 1);
                                    
                                }
                                
                            }
                            b.setCancelled(true);

                        } else {

                            ChatUtilities.broadcast(ChatColor.RED + "Red Core" + ChatColor.GOLD + " has been destroyed");
                            CoreHandler.destroyCore(CoreType.RED);
                            Bukkit.getServer().getWorld("4Corners").playSound(b.getBlock().getLocation(), Sound.EXPLODE, 5, 1);
                            
                            Database.openConnection();
                            Database.updateCcTable(b.getPlayer(), "points", Database.getCc(b.getPlayer(), "points") + 20);
                            Database.updateCcTable(b.getPlayer(), "destroyed", Database.getCc(b.getPlayer(), "destroyed") + 1);
                            ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points", b.getPlayer());
                            Database.closeConnection();

                        }

                    }

                } else if (block.getData() == DyeColor.PURPLE.getData()) {

                    if (isPurple) {

                        ChatUtilities.onePlayer("You cannot break your core after build time", b.getPlayer());
                        b.setCancelled(true);

                    } else {

                        CoreHandler.damageCore(CoreType.PURPLE);
                        CoreSB.updateSB();

                        if (isRed) {

                            ChatUtilities.redBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_PURPLE + "Purple Core");
                            ChatUtilities.purpleBroadcast("Your core was damaged by " + ChatColor.RED + b.getPlayer().getName());

                        } else if (isGreen) {

                            ChatUtilities.greenBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_PURPLE + "Purple Core");
                            ChatUtilities.purpleBroadcast("Your core was damaged by " + ChatColor.DARK_GREEN + b.getPlayer().getName());

                        } else if (isYellow) {

                            ChatUtilities.yellowBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_PURPLE + "Purple Core");
                            ChatUtilities.purpleBroadcast("Your core was damaged by " + ChatColor.YELLOW + b.getPlayer().getName());

                        }

                        if (CoreHandler.getHealth(CoreType.PURPLE) != 0) {

                            for(UUID uuid : PlayerHandler.purple){
                                
                                if(!Tasks.isDisconnected(uuid)){
                                    
                                    Player p = Bukkit.getPlayer(uuid);
                                    p.playSound(p.getLocation(), Sound.GLASS, 2, 1);
                                    
                                }
                                
                            }
                            
                            b.setCancelled(true);

                        } else {

                            ChatUtilities.broadcast(ChatColor.DARK_PURPLE + "Purple Core" + ChatColor.GOLD + " has been destroyed");
                            CoreHandler.destroyCore(CoreType.PURPLE);
                            Bukkit.getServer().getWorld("4Corners").playSound(b.getBlock().getLocation(), Sound.EXPLODE, 5, 1);

                            Database.openConnection();
                            Database.updateCcTable(b.getPlayer(), "points", Database.getCc(b.getPlayer(), "points") + 20);
                            Database.updateCcTable(b.getPlayer(), "destroyed", Database.getCc(b.getPlayer(), "destroyed") + 1);
                            ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points", b.getPlayer());
                            Database.closeConnection();
                            
                        }

                    }

                } else if (block.getData() == DyeColor.GREEN.getData()) {

                    if (isGreen) {

                        ChatUtilities.onePlayer("You cannot break your core after build time", b.getPlayer());
                        b.setCancelled(true);

                    } else {

                        CoreHandler.damageCore(CoreType.GREEN);
                        CoreSB.updateSB();

                        if (isPurple) {

                            ChatUtilities.purpleBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_GREEN + "Green Core");
                            ChatUtilities.greenBroadcast("Your core was damaged by " + ChatColor.DARK_PURPLE + b.getPlayer().getName());

                        } else if (isRed) {

                            ChatUtilities.redBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_GREEN + "Green Core");
                            ChatUtilities.greenBroadcast("Your core was damaged by " + ChatColor.RED + b.getPlayer().getName());

                        } else if (isYellow) {

                            ChatUtilities.yellowBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.DARK_GREEN + "Green Core");
                            ChatUtilities.greenBroadcast("Your core was damaged by " + ChatColor.YELLOW + b.getPlayer().getName());

                        }

                        if (CoreHandler.getHealth(CoreType.GREEN) != 0) {
                            
                            for(UUID uuid : PlayerHandler.green){
                                
                                if(!Tasks.isDisconnected(uuid)){
                                    
                                    Player p = Bukkit.getPlayer(uuid);
                                    p.playSound(p.getLocation(), Sound.GLASS, 2, 1);
                                    
                                }
                                
                            }

                            b.setCancelled(true);

                        } else {

                            ChatUtilities.broadcast(ChatColor.DARK_GREEN + "Green Core" + ChatColor.GOLD + " has been destroyed");
                            CoreHandler.destroyCore(CoreType.GREEN);
                            Bukkit.getServer().getWorld("4Corners").playSound(b.getBlock().getLocation(), Sound.EXPLODE, 5, 1);

                            Database.openConnection();
                            Database.updateCcTable(b.getPlayer(), "points", Database.getCc(b.getPlayer(), "points") + 20);
                            Database.updateCcTable(b.getPlayer(), "destroyed", Database.getCc(b.getPlayer(), "destroyed") + 1);
                            ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points", b.getPlayer());
                            Database.closeConnection();
                            
                        }

                    }

                } else if (block.getData() == DyeColor.YELLOW.getData()) {

                    if (isYellow) {

                        ChatUtilities.onePlayer("You cannot break your core after build time", b.getPlayer());
                        b.setCancelled(true);

                    } else {

                        CoreHandler.damageCore(CoreType.YELLOW);
                        CoreSB.updateSB();

                        if (isPurple) {

                            ChatUtilities.purpleBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.YELLOW + "Yellow Core");
                            ChatUtilities.yellowBroadcast("Your core was damaged by " + ChatColor.DARK_PURPLE + b.getPlayer().getName());

                        } else if (isGreen) {

                            ChatUtilities.greenBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.YELLOW + "Yellow Core");
                            ChatUtilities.yellowBroadcast("Your core was damaged by " + ChatColor.DARK_GREEN + b.getPlayer().getName());

                        } else if (isRed) {

                            ChatUtilities.redBroadcast(b.getPlayer().getName() + " damaged the " + ChatColor.YELLOW + "Yellow Core");
                            ChatUtilities.yellowBroadcast("Your core was damaged by " + ChatColor.RED + b.getPlayer().getName());

                        }

                        if (CoreHandler.getHealth(CoreType.YELLOW) != 0) {

                            for(UUID uuid : PlayerHandler.yellow){
                                
                                if(!Tasks.isDisconnected(uuid)){
                                    
                                    Player p = Bukkit.getPlayer(uuid);
                                    p.playSound(p.getLocation(), Sound.GLASS, 2, 1);
                                    
                                }
                                
                            }
                            
                            b.setCancelled(true);

                        } else {

                            ChatUtilities.broadcast(ChatColor.YELLOW + "Yellow Core" + ChatColor.GOLD + " has been destroyed");
                            CoreHandler.destroyCore(CoreType.YELLOW);
                            Bukkit.getServer().getWorld("4Corners").playSound(b.getBlock().getLocation(), Sound.EXPLODE, 5, 1);
                            
                            Database.openConnection();
                            Database.updateCcTable(b.getPlayer(), "points", Database.getCc(b.getPlayer(), "points") + 20);
                            Database.updateCcTable(b.getPlayer(), "destroyed", Database.getCc(b.getPlayer(), "destroyed") + 1);
                            ChatUtilities.onePlayer("You gained " + ChatColor.GREEN + "20" + ChatColor.GOLD + " points", b.getPlayer());
                            Database.closeConnection();

                        }

                    }

                } else {

                    b.setCancelled(true);

                }
            }

        } else if (block.getType().equals(Material.WOOL)) {

            if (block.getData() == DyeColor.RED.getData()) {

                b.getPlayer().getInventory().addItem(CCItem.redBuildOne);

            } else if (block.getData() == DyeColor.PURPLE.getData()) {

                b.getPlayer().getInventory().addItem(CCItem.purpleBuildOne);

            } else if (block.getData() == DyeColor.GREEN.getData()) {

                b.getPlayer().getInventory().addItem(CCItem.greenBuildOne);

            } else if (block.getData() == DyeColor.YELLOW.getData()) {

                b.getPlayer().getInventory().addItem(CCItem.yellowBuildOne);

            }

            block.setType(Material.AIR);
            b.setCancelled(true);

        }
    }
}
