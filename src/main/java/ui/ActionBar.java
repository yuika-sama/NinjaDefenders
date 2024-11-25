package ui;

import entities.enemies.Enemy;
import utilities.Constants;
import entities.objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static core.GameState.MENU;
import static core.GameState.setGameStates;

public class ActionBar extends Bar {
    private final Playing playing;
    private final DecimalFormat formatter;
    private MyButton bMenu;
    private MyButton[] towerButtons;
    private MyButton sellTower;
    private MyButton upgradeTower;
    private Tower displayTower, selectedTower;
    private Enemy displayedEnemy;

    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

    private MyButton bPause;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 642, 2, 75, 30);
        bPause = new MyButton("Pause", 719, 2, 75, 30);

        towerButtons = new MyButton[8];
        int w = 50, h = 50, xStart = 650, yStart = 110, Offset = (int) (w * 1.1f);

        for (int i = 0; i < 4; i++) {
            towerButtons[i] = new MyButton("", xStart, yStart + Offset * i, w, h, i);
        }
        for (int i = 4; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + 54, yStart + Offset * (i - 4), w, h, i);
        }

        sellTower = new MyButton("Sell", 659, 585, 50, 30);
        upgradeTower = new MyButton("Upgrade", 711, 585, 50, 30);
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

        //gold
        drawGoldAmount(g);
        if (showTowerCost) drawTowerCost(g);

        //pause text
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(650, 350, 130, 70);
        g.setColor(Color.BLACK);
        g.drawRect(650, 350, 130, 70);

        g.drawString(getTowerCostName(), 655, 370);
        g.drawString("Cost: " + getTowerCost() + "g", 655, 390);

        if (isCostMoreThanGold()) {
            g.setColor(Color.RED);
            g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
            g.drawString("Can't Afford", 655, 410);
            g.setColor(Color.BLACK);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        } else {
            g.drawString("Affordable", 655, 410);
        }
    }

    private boolean isCostMoreThanGold() {
        return gold < getTowerCost();
    }

    private int getTowerCost() {
        return Constants.Turrets.getTowerCost(towerCostType);
    }

    private String getTowerCostName() {
        return Constants.Turrets.getName(towerCostType);
    }

    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: " + this.gold + "g", 650, 345);

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
        if (playing.getWaveManager().isWaveTimerStarted()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left: " + formattedText, 650, 90);
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bPause.draw(g);
        for (MyButton b : towerButtons) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawFeedBackButton(g, b);
        }
    }

    private void drawDisplayedEnemy(Graphics g) {
        if (displayedEnemy != null) {
            int offSet = 30;
            g.setColor(Color.gray);
            g.fillRect(650, 410 + offSet, 120, 220);
            g.setColor(Color.BLACK);
            g.drawRect(650, 410 + offSet, 120, 220);
            g.drawRect(665, 420 + offSet, 50, 50);
            g.drawImage(playing.getEnemyManager().getEnemyFaceset()[displayedEnemy.getEnemyType()],
                    665,
                    420 + offSet,
                    50,
                    50,
                    null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString(Constants.Monsters.getName(displayedEnemy.getEnemyType()), 665, 490 + offSet);
            g.drawString("HP: " + displayedEnemy.getHp(), 665, 510 + offSet);
            g.drawString("Speed: " + Constants.Monsters.getSpeed(displayedEnemy.getEnemyType()), 665, 530 + offSet);

            drawDisplayedEnemyBorder(g);
        }
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayTower != null) {
            int offSet = 30;
            g.setColor(Color.gray);
            g.fillRect(650, 410 + offSet, 120, 180);
            g.setColor(Color.BLACK);
            g.drawRect(650, 410 + offSet, 120, 180);
            g.drawRect(665, 420 + offSet, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayTower.getTowerType()],
                    665,
                    420 + offSet,
                    50,
                    50,
                    null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString(Constants.Turrets.getName(displayTower.getTowerType()), 665, 490 + offSet);
            g.drawString("Range: " + displayTower.getRange(), 665, 510 + offSet);
            g.drawString("Dmg: " + displayTower.getDmg(), 665, 530 + offSet);
            String formattedText = formatter.format(displayTower.getCd());
            g.drawString("CD time: " + formattedText, 665, 550 + offSet);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            g.setFont(new Font("LucidaSans", Font.PLAIN, 13));
            sellTower.draw(g);
            drawFeedBackButton(g, sellTower);

            if (displayTower.getTier() < Constants.Turrets.getTowerMaxTier(displayTower.getTowerType())) {
                upgradeTower.draw(g);
                drawFeedBackButton(g, upgradeTower);
            }

            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.setColor(Color.BLACK);
            g.drawString("Tier: " + displayTower.getTier(), 720, 465);
            g.setColor(Color.decode("#FFD700"));
            if (sellTower.isMouseOver()) {
                g.drawString("Cost:", 720, 480);
                g.drawString(getSellAmount(displayTower) + "g", 720, 495);
            } else if (upgradeTower.isMouseOver()) {
                if (!(gold >= getUpgradeAmount(displayTower))) {
                    g.setColor(Color.RED);
                }
                g.drawString("Cost:", 720, 480);
                g.drawString(getUpgradeAmount(displayTower) + "g", 720, 495);
            }
            g.setColor(Color.BLACK);
        }
    }

    private int getSellAmount(Tower displayTower) {
        int upgradeCost = (displayTower.getTier() - 1) * getUpgradeAmount(displayTower);
        upgradeCost *= 0.6f;
        return (int) (Constants.Turrets.getTowerCost(displayTower.getTowerType()) * 0.6f + upgradeCost);
    }

    private int getUpgradeAmount(Tower displayTower) {
        return (int) (Constants.Turrets.getTowerCost(displayTower.getTowerType()) * 0.3f);
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
        } else if (bPause.getBounds().contains(x, y)){
            togglePause();
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellingTower();
                    return;
                } else if (upgradeTower.getBounds().contains(x,
                        y) && displayTower.getTier() < Constants.Turrets.getTowerMaxTier(displayTower.getTowerType()) && gold >= getUpgradeAmount(
                        displayTower)) {
                    upgradingTower();
                    return;
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (!isGoldEnough(b.getId())) {
                        return;
                    }
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());
        if (playing.isGamePaused()){
            bPause.setText("Unpause");
        } else {
            bPause.setText("Pause");
        }
    }

    private void upgradingTower() {
        playing.upgradeTower(displayTower);
        gold -= getUpgradeAmount(displayTower);
    }

    private void sellingTower() {
        playing.removeTower(displayTower);
        gold += (int) (Constants.Turrets.getTowerCost(displayTower.getTowerType()) * 0.6f);

        int upgradeCost = (displayTower.getTier() - 1) * getUpgradeAmount(displayTower);
        upgradeCost *= 0.6f;
        gold += upgradeCost;

        displayTower = null;
    }

    private boolean isGoldEnough(int id) {
        return gold >= Constants.Turrets.getTowerCost(id);
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x, y)){
            bPause.setMouseOver(true);
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x,
                        y) && displayTower.getTier() < Constants.Turrets.getTowerMaxTier(displayTower.getTowerType())) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bPause.getBounds().contains(x, y)){
            bPause.setMousePressed(true);
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x,
                        y) && displayTower.getTier() < Constants.Turrets.getTowerMaxTier(displayTower.getTowerType())) {
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }
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
        bPause.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void payForTower(int towerType) {
        this.gold -= Constants.Turrets.getTowerCost(towerType);
    }

    public void addGold(int reward) {
        this.gold += reward;
    }

    public void hardReset() {
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayTower = null;
        displayedEnemy = null;
    }
}
