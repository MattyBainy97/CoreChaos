package com.corechaos;

public enum GameState {
    
    IN_LOBBY(true), BUILD(true), FIGHT(true), POST_GAME(false), RESET(false);
    
    private boolean canJoin;
    
    private static GameState currentState;
    
    GameState(boolean canJoin){
        this.canJoin = canJoin;
    }
    
    public boolean canJoin(){
        return canJoin;
    }
    
    public static void setState(GameState state){
        GameState.currentState = state;
    }
    
    public static boolean isState(GameState state){
        return GameState.currentState == state;
    }
    
    public static GameState getState(){
        return currentState;
    }
    
}
