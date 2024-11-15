package ui;

import helpz.Constants;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class ActionBar extends Bar {
    private final Playing playing;
    private MyButton bMenu;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayTower;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);

        towerButtons = new MyButton[3];
        int w = 50, h = 50, xStart = 110, yStart = 650, xOffset = (int) (w * 1.1f);

        for (int i=0; i < towerButtons.length; i++){
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for (MyButton b:towerButtons){
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null );
            drawFeedBackButton(g, b);
        }
    }

    private BufferedImage getButtonImg(int id) {
        return playing.getGame().getTileManager().getSprite(id);
    }

    public void draw(Graphics g) {
        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);

        drawDisplayedTower(g);
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayTower != null){
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.BLACK);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayTower.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Constants.Towers.getName(displayTower.getTowerType()), 490, 660);
            g.drawString("ID: " + displayTower.getId(), 490, 675);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);
        }
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawOval(displayTower.getX() + 16 - (int)displayTower.getRange(),
                displayTower.getY() + 16 - (int)displayTower.getRange(),
                (int)displayTower.getRange()*2,
                (int)displayTower.getRange()*2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {

        g.setColor(Color.CYAN);
        g.drawRect(displayTower.getX(), displayTower.getY(), 32, 32);

    }

    public void displayTower(Tower t) {
        displayTower = t;
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        } else {
            for (MyButton b:towerButtons){
                if (b.getBounds().contains(x, y)){
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b:towerButtons){
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b:towerButtons){
                if (b.getBounds().contains(x, y)){
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else {
            for (MyButton b:towerButtons){
                if (b.getBounds().contains(x, y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        for (MyButton b:towerButtons){
            b.resetBooleans();
        }
    }
}
