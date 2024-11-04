package scenes;

import com.mycompany.towerdefense.Game;
import ui.MyButton;

import java.awt.*;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class Setting extends GameScene implements SceneMethods {
    private MyButton bMenu;

    public Setting(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 0, 0, 100, 30);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);
        drawButton(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bMenu.resetBooleans();
    }

    private void drawButton(Graphics g) {
        bMenu.draw(g);
    }
}
