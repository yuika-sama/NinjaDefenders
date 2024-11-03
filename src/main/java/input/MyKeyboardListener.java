package input;

import com.mycompany.towerdefense.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A){
            GameState.gameStates = GameState.MENU;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S){
            GameState.gameStates = GameState.PLAYING;
        }
        else if (e.getKeyCode() == KeyEvent.VK_D){
            GameState.gameStates = GameState.SETTING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
