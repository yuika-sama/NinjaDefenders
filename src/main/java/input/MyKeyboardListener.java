package input;

import com.mycompany.towerdefense.Game;
import com.mycompany.towerdefense.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.mycompany.towerdefense.GameState.EDIT;

public class MyKeyboardListener implements KeyListener {

    private final Game game;

    public MyKeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameState.gameStates == EDIT) {
            //do nothing
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
