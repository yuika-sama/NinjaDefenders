package scenes;

import com.mycompany.towerdefense.Game;
import enemies.Enemy;
import helpz.LoadSave;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {
    private final TowerManager towerManager;
    private final ActionBar actionBar;
    private final Game game;
    private int[][] lvl;
    private int mouseX, mouseY;
    private int animId;
    private final EnemyManager enemyManager;
    private int tick;

    private PathPoint start, end;

    private Tower selectedTower;
    private boolean drawSelected;

    private final ArrayList<Tower> towers = new ArrayList<>();

    private final ProjectileManager projectileManager;

    public Playing(Game game) {
        super(game);
        this.game = game;
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
    }

    public void update() {
        enemyManager.update();
        towerManager.update();
        projectileManager.update();
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
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
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
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower != null) {
                if (isGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        selectedTower = null;
                    }
                }
            } else {
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }
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
        if (y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
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

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void mouseClicked(MouseEvent e, int x, int y) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            selectedTower = null;
        }
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
    }
}
