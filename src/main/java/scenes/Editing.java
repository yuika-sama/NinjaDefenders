package scenes;

import com.mycompany.towerdefense.Game;
import helpz.LoadSave;
import objects.Tile;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {
    private final ToolBar toolBar;
    private final Game game;
    private int[][] lvl;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelected;
    private int lastTileX, lastTileY, lastTileID;


    public Editing(Game game) {
        super(game);
        this.game = game;
        loadDefaultLevel();
        toolBar = new ToolBar(0, 640, 640, 100, this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    private BufferedImage getSprite(int spriteId) {
        return game.getTileManager().getSprite(spriteId);
    }

    @Override
    public void render(Graphics g) {
        toolBar.draw(g);
        drawLevel(g);
        drawSelectedTile(g);
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
        LoadSave.SaveLevel("new_level", lvl);
        game.getPlaying().setLevel(lvl);
    }

    private void changeTile(int x, int y) {
        if (x < 0 || y < 0 || x > 640 || y > 640) return;
        if (selectedTile != null) {
            int tileX = x / 32, tileY = y / 32;
            if (lastTileX == tileX && lastTileY == tileY && lastTileID == selectedTile.getId()) return;
            lastTileY = tileY;
            lastTileX = tileX;
            lastTileID = selectedTile.getId();
            lvl[tileY][tileX] = selectedTile.getId();

        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            toolBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    public void mouseMoved(int x, int y) {
        if (y >= 640) {
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
        if (y >= 640) {
            toolBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (y >= 640) {
            toolBar.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y < 640) {
            changeTile(x, y);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
//            toolBar.rotateSprite();
//            System.out.println("Lol don't try. I closed this method");
        }
    }

    public void mouseClicked(MouseEvent e, int x, int y) {
        if (e.getButton() == MouseEvent.BUTTON3){
            if (y < 640){
                toolBar.rotateSprite();
            }
        }
    }
}
