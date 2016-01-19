package com.corechaos.handlers;

import static com.corechaos.handlers.CoreType.*;
import org.bukkit.Location;

public class CoreHandler {
    
    private static int redHealth = 100;
    private static int purpleHealth = 100;
    private static int greenHealth = 100;
    private static int yellowHealth = 100;
    
    private static boolean isRedAlive = true;
    private static boolean isPurpleAlive = true;
    private static boolean isGreenAlive = true;
    private static boolean isYellowAlive = true;
    
    private static Location redLocation = null;
    private static Location purpleLocation = null;
    private static Location greenLocation = null;
    private static Location yellowLocation = null;
    
    public static void damageCore(CoreType core){
        
        switch(core){
            
            case RED:
                redHealth -= 10;
                break;
            case PURPLE:
                purpleHealth -= 10;
                break;
            case GREEN:
                greenHealth -= 10;
                break;
            case YELLOW:
                yellowHealth -= 10;
                break;
            default:
                break;
            
        }
        
    }
    
    public static int getHealth(CoreType core){
        
        switch(core){
            
            case RED:
                return redHealth;
            case PURPLE:
                return purpleHealth;
            case GREEN:
                return greenHealth;
            case YELLOW:
                return yellowHealth;
            default:
                return 0;
            
        }
        
    }
   
    public static void setHealth(CoreType core, int health){
        
        switch(core){
            
            case RED:
                redHealth = health;
                break;
            case PURPLE:
                purpleHealth = health;
                break;
            case GREEN:
                greenHealth = health;
                break;
            case YELLOW:
                yellowHealth = health;
                break;
            default:
                break;
            
        }
        
    }
    
    public static Location getLocation(CoreType core){
        
        switch(core){
            
            case RED:
                return redLocation;
            case PURPLE:
                return purpleLocation;
            case GREEN:
                return greenLocation;
            case YELLOW:
                return yellowLocation;
            default:
                return null;
            
        }
        
    }
    
    public static void setLocation(CoreType core, Location l){
        
        switch(core){
            
            case RED:
                redLocation = l;
                break;
            case PURPLE:
                purpleLocation = l;
                break;
            case GREEN:
                greenLocation = l;
                break;
            case YELLOW:
                yellowLocation = l;
                break;
            default:
                break;
            
        }
        
    }
    
    public static boolean isCoreAlive(CoreType core){
        
        switch(core){
            
            case RED:
                return isRedAlive;
            case PURPLE:
                return isPurpleAlive;
            case GREEN:
                return isGreenAlive;
            case YELLOW:
                return isYellowAlive;
            default:
                return false;
            
        }
        
    }
    
    public static void destroyCore(CoreType core){
        
        switch(core){
            
            case RED:
                isRedAlive = false;
                break;
            case PURPLE:
                isPurpleAlive = false;
                break;
            case GREEN:
                isGreenAlive = false;
                break;
            case YELLOW:
                isYellowAlive = false;
                break;
            default:
                break;
            
        }
        
    }
    
}

