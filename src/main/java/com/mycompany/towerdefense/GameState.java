package com.mycompany.towerdefense;

public enum GameState {
    PLAYING, MENU, SETTING, EDIT;

    public static GameState gameStates = MENU;

    public static void setGameStates(GameState state){
        gameStates = state;
    }
}
