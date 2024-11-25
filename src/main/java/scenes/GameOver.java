package scenes;

import core.Game;
import core.GameState;
import ui.MyButton;

import java.awt.*;

public class GameOver extends GameScene implements SceneMethods{

    private MyButton bReplay, bMenu;

    private final Game game;

    public GameOver(Game game) {
        super(game);
        this.game = game;
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 800 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;
        bMenu = new MyButton("Menu", x, y + 150, w, h);
        bReplay = new MyButton("Replay", x, y + 150 + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.drawString("Game Over!!!", 255, 150);

        g.setColor(Color.BLACK);
        g.setFont(new Font("LucidaSans", Font.BOLD, 16));
        bMenu.draw(g);
        bReplay.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)){
            GameState.setGameStates(GameState.MENU);
            game.getPlaying().hardReset();
        } else if (bReplay.getBounds().contains(x, y)){
            game.getPlaying().hardReset();
            GameState.setGameStates(GameState.PLAYING);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(true);
        } else if (bReplay.getBounds().contains(x, y)){
            bReplay.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        } else if (bReplay.getBounds().contains(x, y)){
            bReplay.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
