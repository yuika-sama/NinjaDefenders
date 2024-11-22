package ui;

import enemies.Enemy;
import helpz.Constants;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class ActionBar extends Bar {
    private final Playing playing;

    private MyButton bMenu;
    private MyButton[] towerButtons;

    private Tower displayTower, selectedTower;
    private Enemy displayedEnemy;

    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 642, 2, 100, 30);

        towerButtons = new MyButton[8];
        int w = 50, h = 50, xStart = 650, yStart = 110, Offset = (int) (w * 1.1f);

        for (int i = 0; i < 4; i++) {
            towerButtons[i] = new MyButton("", xStart, yStart + Offset * i, w, h, i);
        }
        for (int i=4; i<towerButtons.length; i++){
            towerButtons[i] = new MyButton("", xStart + 54, yStart + Offset * (i-4), w, h, i);
        }
    }

    public void draw(Graphics g) {
        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);

        //displayed tower
        drawDisplayedTower(g);
        drawDisplayedEnemy(g);
        
        //waves
        drawWaveInfo(g);
    }

    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + " / " + size, 650, 70);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountAlivingEnemies();
        g.drawString("Enemies Left: " + remaining, 650, 50);

    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()){
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left: " + formattedText, 650, 90);
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        for (MyButton b : towerButtons) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawFeedBackButton(g, b);
        }
    }

    private void drawDisplayedEnemy(Graphics g) {
        if (displayedEnemy != null){
            g.setColor(Color.gray);
            g.fillRect(645, 410, 115, 220);
            g.setColor(Color.BLACK);
            g.drawRect(645, 410, 115, 220);
            g.drawRect(660, 420, 50, 50);
            g.drawImage(playing.getEnemyManager().getEnemyFaceset()[displayedEnemy.getEnemyType()], 660, 420, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString(Constants.Monsters.getName(displayedEnemy.getEnemyType()), 660, 490);
            g.drawString("ID: " + displayedEnemy.getID(), 660, 510);
            g.drawString("HP: " + displayedEnemy.getHp(), 660, 530);
            g.drawString("Speed: " + Constants.Monsters.getSpeed(displayedEnemy.getEnemyType()), 660, 550);

            drawDisplayedEnemyBorder(g);
        }
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayTower != null) {
            g.setColor(Color.gray);
            g.fillRect(645, 410, 115, 220);
            g.setColor(Color.BLACK);
            g.drawRect(645, 410, 115, 220);
            g.drawRect(660, 420, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayTower.getTowerType()], 660, 420, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString(Constants.Turrets.getName(displayTower.getTowerType()), 660, 490);
            g.drawString("ID: " + displayTower.getId(), 660, 510);
            g.drawString("Range: " + displayTower.getRange(), 660, 530);
            g.drawString("Dmg: " + displayTower.getDmg(), 660, 550);
            g.drawString("CD time: " + displayTower.getCd(), 660, 570);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);
        }
    }

    public void displayEnemy(Enemy e) {
        displayedEnemy = e;
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawOval(displayTower.getX() + 16 - (int) displayTower.getRange(),
                displayTower.getY() + 16 - (int) displayTower.getRange(),
                (int) displayTower.getRange() * 2,
                (int) displayTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayTower.getX(), displayTower.getY(), 32, 32);
    }

    private void drawDisplayedEnemyBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect((int) displayedEnemy.getX(), (int) displayedEnemy.getY(), 32, 32);
    }

    public void displayTower(Tower t) {
        displayTower = t;
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
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
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }
}
