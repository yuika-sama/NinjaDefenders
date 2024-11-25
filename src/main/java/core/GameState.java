package core;

public enum GameState {
    PLAYING, MENU, SETTING, EDIT, GAME_OVER, VICTORY;

    public static GameState gameStates = MENU;

    public static void setGameStates(GameState state) {
        gameStates = state;
    }
}