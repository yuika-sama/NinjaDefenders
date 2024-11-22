package scenes;

import com.mycompany.towerdefense.Game;
import helpz.LoadSave;
import objects.PathPoint;
import objects.Tile;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.ROAD_TILE;

public class Editing extends GameScene implements SceneMethods {
    private final ToolBar toolBar;
    private final Game game;
    private int[][] lvl;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelected;
    private int lastTileX, lastTileY, lastTileID;
    private int animId;
    private int tick;
    private PathPoint start, end;


    public Editing(Game game) {
        super(game);
        this.game = game;
        loadDefaultLevel();
        toolBar = new ToolBar(640, 0, 160, 640, this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoint("new_level");
        assert points != null;
        start = points.getFirst();
        end = points.get(1);
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnim(id)){
                    g.drawImage(getSprite(id, animId), x * 32, y * 32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
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

    @Override
    public void render(Graphics g) {
        updateTick();
        drawLevel(g);
        toolBar.draw(g);
        drawSelectedTile(g);
        drawPathPoint(g);
        drawHighLight(g);
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawPathPoint(Graphics g) {
        if (start != null){
            g.drawImage(toolBar.getPathStart(), start.getxCord()*32, start.getyCord()*32, 32, 32, null);
        }
        if (end != null){
            g.drawImage(toolBar.getPathEnd(), end.getxCord()*32, end.getyCord()*32, 32, 32, null);
        }
    }

    private void updateTick() {
        tick++;
        if (tick >= 25){
            tick = 0;
            animId++;
            if (animId >= 4){
                animId = 0;
            }
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawSelected) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
        drawSelected = true;
    }

    public void saveLevel() {
        LoadSave.SaveLevel("new_level", lvl, start, end);
        this.game.getPlaying().setLevel(lvl);
    }

    private void changeTile(int x, int y) {
        if (x < 0 || y < 0 || x > 640 || y > 640) return;
        if (selectedTile != null) {
            int tileX = x / 32, tileY = y / 32;
            if (selectedTile.getId() >= 0){
                if (lastTileX == tileX && lastTileY == tileY && lastTileID == selectedTile.getId()) return;
                lastTileY = tileY;
                lastTileX = tileX;
                lastTileID = selectedTile.getId();
                lvl[tileY][tileX] = selectedTile.getId();
            } else {
                int id = lvl[tileY][tileX];
                if (game.getTileManager().getTile(id).getTileType() == ROAD_TILE){
                    if (selectedTile.getId() == -1){
                        start = new PathPoint(tileX, tileY);
                    } else {
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 640) {
            toolBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    public void mouseMoved(int x, int y) {
        if (x >= 640) {
            toolBar.mouseMoved(x, y);
            drawSelected = false;
        } else {
            drawSelected = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (x >= 640) {
            toolBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (x >= 640) {
            toolBar.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (x < 640) {
            changeTile(x, y);
        }
    }

    public void mouseClicked(MouseEvent e, int x, int y) {
        if (e.getButton() == MouseEvent.BUTTON3){
            if (x < 640){
                toolBar.rotateSprite();
            }
        }
    }
}
