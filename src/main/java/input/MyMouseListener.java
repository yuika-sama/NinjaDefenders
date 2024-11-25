package input;

import core.Game;
import core.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static core.GameState.EDIT;
import static core.GameState.PLAYING;

public class MyMouseListener implements MouseListener, MouseMotionListener {
    private final Game game;

    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameState.gameStates) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case SETTING:
                    game.getSetting().mouseClicked(e.getX(), e.getY());
                    break;
                case EDIT:
                    game.getEditor().mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameOver().mouseClicked(e.getX(), e.getY());
                    break;
                case VICTORY:
                    game.getVictory().mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3){
            if (GameState.gameStates == EDIT) {
                game.getEditor().mouseClicked(e, e.getX(), e.getY());
            } else if (GameState.gameStates == PLAYING){
                game.getPlaying().mouseClicked(e, e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameState.gameStates) {
                case MENU:
                    game.getMenu().mousePressed(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mousePressed(e.getX(), e.getY());
                    break;
                case SETTING:
                    game.getSetting().mousePressed(e.getX(), e.getY());
                    break;
                case EDIT:
                    game.getEditor().mousePressed(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameOver().mousePressed(e.getX(), e.getY());
                    break;
                case VICTORY:
                    game.getVictory().mousePressed(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameState.gameStates) {
                case MENU:
                    game.getMenu().mouseReleased(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseReleased(e.getX(), e.getY());
                    break;
                case SETTING:
                    game.getSetting().mouseReleased(e.getX(), e.getY());
                    break;
                case EDIT:
                    game.getEditor().mouseReleased(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameOver().mouseReleased(e.getX(), e.getY());
                    break;
                case VICTORY:
                    game.getVictory().mouseReleased(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.gameStates) {
            case MENU:
                game.getMenu().mouseDragged(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseDragged(e.getX(), e.getY());
                break;
            case SETTING:
                game.getSetting().mouseDragged(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditor().mouseDragged(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseDragged(e.getX(), e.getY());
                break;
            case VICTORY:
                game.getVictory().mouseDragged(e.getX(), e.getY());
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.gameStates) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            case SETTING:
                game.getSetting().mouseMoved(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditor().mouseMoved(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseMoved(e.getX(), e.getY());
                break;
            case VICTORY:
                game.getVictory().mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }
}