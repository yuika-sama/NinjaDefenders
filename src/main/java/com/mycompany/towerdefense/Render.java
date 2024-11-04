package com.mycompany.towerdefense;

import java.awt.*;

public class Render {
    private final Game game;

    public Render(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        switch (GameState.gameStates) {
            case MENU:
                this.game.getMenu().render(g);
                break;
            case PLAYING:
                this.game.getPlaying().render(g);
                break;
            case SETTING:
                this.game.getSetting().render(g);
                break;
            case EDIT:
                this.game.getEditor().render(g);
        }
    }
}
