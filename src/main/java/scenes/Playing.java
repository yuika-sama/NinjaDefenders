package scenes;

import core.Game;
import core.GameState;
import entities.enemies.Enemy;
import utilities.Constants;
import utilities.LoadSave;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import entities.objects.PathPoint;
import entities.objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {
    private final TowerManager towerManager;
    private final EnemyManager enemyManager;
    private final ProjectileManager projectileManager;
    private final WaveManager waveManager;
    private final ActionBar actionBar;
    private final Game game;
    private Tower selectedTower;
    private int[][] lvl;
    private int mouseX, mouseY;
    private int animId;
    private int tick;
    private PathPoint start, end;
    private int goldTick = 0;
    private boolean gamePaused;

    private int life = 10;

    public Playing(Game game) {
        super(game);
        this.game = game;
        loadDefaultLevel();
        actionBar = new ActionBar(640, 0, 160, 640, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoint("new_level");
        assert points != null;
        start = points.getFirst();
        end = points.get(1);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }
    public void setInitPoint(PathPoint start, PathPoint end) {
        this.start = start;
        this.end = end;
    }

    public void update() {
        if (!gamePaused) {
            updateTick();
            waveManager.update();

            goldTick++;
            if (goldTick % (60 * 3) == 0) {
                actionBar.addGold(1);
            }

            if (isWaveCleared()) {
                if (isMoreWaves()) {
                    waveManager.startWaveTimer();
                    if (isWaveTimerOver()) {
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemiesIndex();
                    }
                } else {
                    GameState.setGameStates(GameState.VICTORY);
                    return;
                }
            }

            if (isTimeForNewEnemy()) {
                spawnEnemies();
            }

            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isMoreWaves() {
        return waveManager.isMoreWaves();
    }

    private boolean isWaveCleared() {
        if (waveManager.isWaveNotEmpty()) {
            return false;
        }

        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void spawnEnemies() {
        enemyManager.spawnEnemies(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            return waveManager.isWaveNotEmpty();
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        updateTick();
        drawLevel(g);
        enemyManager.draw(g);
        actionBar.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);
        drawSelectedTower(g);
        drawHighLight(g);
        drawLife(g);

        if (isGamePaused()) {
            drawPauseStatus(g);
        }
    }

    private void drawLife(Graphics g) {
        String text = "Life: " + this.life;
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(text); // Get the width of the text
        int textHeight = metrics.getHeight();      // Get the height of the text
        int x = 5;                                 // X-coordinate for text and rectangle
        int y = 20;                                // Y-coordinate for baseline of the text
        int padding = 4;                           // Padding around the text

        // Draw the rectangle (background)
        g.setColor(Color.BLACK);              // Background color
        g.fillRect(x - padding, y - textHeight + padding, textWidth + 2 * padding, textHeight);
        g.setColor(Color.GRAY);
        g.fillRect(0, y - textHeight + padding - 1, textWidth + 2 * padding - 2, textHeight - 2);

        // Draw the text
        g.setColor(Color.BLACK);                   // Text color
        g.drawString(text, x, y);
    }

    private void drawPauseStatus(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(320 - 80, 320 - 25, 160, 50);
        g.setColor(Color.BLACK);
        g.drawRect(320 - 80 - 1, 320 - 25 - 1, 161, 51);
        g.setColor(Color.WHITE);
        g.setFont(new Font("LucidaSans", Font.BOLD, 16));
        g.drawString("Game Paused!!!", 320 - 80 + 20, 320 + 5);
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;
        if (xCord < 0 || xCord > 19) {
            return 0;
        }
        if (yCord < 0 || yCord > 19) {
            return 0;
        }
        int id = lvl[yCord][xCord];
        return game.getTileManager().getTile(id).getTileType();
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {
            g.drawImage(towerManager.getTowerFirstSprite()[selectedTower.getTowerType()], mouseX, mouseY, null);
        }
    }

    private void updateTick() {
        tick++;
        if (tick >= 25) {
            tick = 0;
            animId++;
            if (animId >= 4) {
                animId = 0;
            }
        }
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnim(id)) {
                    g.drawImage(getSprite(id, animId), x * 32, y * 32, null);
                } else {
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    private boolean isAnim(int id) {
        return game.getTileManager().isAnim(id);
    }

    private BufferedImage getSprite(int spriteId) {
        return game.getTileManager().getSprite(spriteId);
    }

    private BufferedImage getSprite(int spriteId, int animId) {
        return game.getTileManager().getAnimSprite(spriteId, animId);
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower != null) {
                if (isGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);

                        removeGold(selectedTower.getTowerType());

                        selectedTower = null;
                    }
                }
            } else {
                Tower t = getTowerAt(mouseX, mouseY);
                Enemy e = getEnemyAt(mouseX, mouseY);
                actionBar.displayTower(t);
                actionBar.displayEnemy(e);
            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);
    }

    private Enemy getEnemyAt(int mouseX, int mouseY) {
        return enemyManager.getEnemyAt(mouseX, mouseY);
    }

    private Tower getTowerAt(int mouseX, int mouseY) {
        return towerManager.getTowerAt(mouseX, mouseY);
    }

    private boolean isGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return (tileType == GRASS_TILE);
    }

    public void mouseMoved(int x, int y) {
        if (x >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (x >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void mouseClicked(MouseEvent e, int x, int y) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            selectedTower = null;
        }
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void rewardPlayer(int enemyType) {
        actionBar.addGold(Constants.Monsters.getReward(enemyType));

    }

    public void removeTower(Tower displayTower) {
        towerManager.removeTower(displayTower);
    }

    public void upgradeTower(Tower displayTower) {
        towerManager.upgradeTower(displayTower);
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void removeLife() {
        this.life--;
        if (life <= 0) {
            GameState.setGameStates(GameState.GAME_OVER);
        }
    }

    public void hardReset() {
        life = 25;

        actionBar.hardReset();

        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        goldTick = 0;
        gamePaused = false;
    }
}
