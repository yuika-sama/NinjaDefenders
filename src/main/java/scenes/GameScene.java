package scenes;

import com.mycompany.towerdefense.Game;

public class GameScene {
    private final Game game;

    public GameScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
