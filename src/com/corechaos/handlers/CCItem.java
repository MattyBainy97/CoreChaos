package com.corechaos.handlers;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CCItem {

    public static ItemStack redCore = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.RED.getData());
    public static ItemStack purpleCore = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.PURPLE.getData());
    public static ItemStack greenCore = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.GREEN.getData());
    public static ItemStack yellowCore = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.YELLOW.getData());
    
    public static ItemStack redBuild = new ItemStack(Material.WOOL, 64, DyeColor.RED.getData());
    public static ItemStack purpleBuild = new ItemStack(Material.WOOL, 64, DyeColor.PURPLE.getData());
    public static ItemStack greenBuild = new ItemStack(Material.WOOL, 64, DyeColor.GREEN.getData());
    public static ItemStack yellowBuild = new ItemStack(Material.WOOL, 64, DyeColor.YELLOW.getData());
    
    public static ItemStack redBuildOne = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
    public static ItemStack purpleBuildOne = new ItemStack(Material.WOOL, 1, DyeColor.PURPLE.getData());
    public static ItemStack greenBuildOne = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
    public static ItemStack yellowBuildOne = new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData());
    
    public static ItemStack spec = new ItemStack(Material.COMPASS, 1);

    public static void setMetas() {

        ItemMeta redCoreMeta = (ItemMeta) redCore.getItemMeta();
        redCoreMeta.setDisplayName(ChatColor.RESET.RED + "Red Core");
        redCore.setItemMeta(redCoreMeta);
        ItemMeta purpleCoreMeta = (ItemMeta) purpleCore.getItemMeta();
        purpleCoreMeta.setDisplayName(ChatColor.RESET.DARK_PURPLE + "Purple Core");
        purpleCore.setItemMeta(purpleCoreMeta);
        ItemMeta greenCoreMeta = (ItemMeta) greenCore.getItemMeta();
        greenCoreMeta.setDisplayName(ChatColor.RESET.DARK_GREEN + "Green Core");
        greenCore.setItemMeta(greenCoreMeta);
        ItemMeta yellowCoreMeta = (ItemMeta) yellowCore.getItemMeta();
        yellowCoreMeta.setDisplayName(ChatColor.RESET.YELLOW + "Yellow Core");
        yellowCore.setItemMeta(yellowCoreMeta);
        
        ItemMeta redBuildMeta = (ItemMeta) redBuild.getItemMeta();
        redBuildMeta.setDisplayName(ChatColor.RESET.RED + "Red Base Material");
        redBuild.setItemMeta(redBuildMeta);
        redBuildOne.setItemMeta(redBuildMeta);
        ItemMeta purpleBuildMeta = (ItemMeta) purpleBuild.getItemMeta();
        purpleBuildMeta.setDisplayName(ChatColor.RESET.DARK_PURPLE + "Purple Base Material");
        purpleBuild.setItemMeta(purpleBuildMeta);
        purpleBuildOne.setItemMeta(purpleBuildMeta);
        ItemMeta greenBuildMeta = (ItemMeta) greenBuild.getItemMeta();
        greenBuildMeta.setDisplayName(ChatColor.RESET.DARK_GREEN + "Green Base Material");
        greenBuild.setItemMeta(greenBuildMeta);
        greenBuildOne.setItemMeta(greenBuildMeta);
        ItemMeta yellowBuildMeta = (ItemMeta) yellowBuild.getItemMeta();
        yellowBuildMeta.setDisplayName(ChatColor.RESET.YELLOW + "Yellow Base Material");
        yellowBuild.setItemMeta(yellowBuildMeta);
        yellowBuildOne.setItemMeta(yellowBuildMeta);
        
        ItemMeta mspec = (ItemMeta) spec.getItemMeta();
        mspec.setDisplayName(ChatColor.YELLOW + "SPECTATE");
        spec.setItemMeta(mspec);

    }
}