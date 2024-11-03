package scenes;

import com.mycompany.towerdefense.Game;
import helpz.LevelBuild;
import helpz.LoadSave;
import managers.TileManager;
import objects.Tile;
import ui.BottomBar;
import ui.MyButton;

import java.awt.*;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class Playing extends GameScene implements SceneMethods{
    private int[][] lvl;
    private TileManager tileManager;
    private MyButton bMenu;
    private BottomBar bottomBar;

    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelected;

    private int lastTileX, lastTileY;
    private int lastTileID;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
        bottomBar = new BottomBar(0, 640, 640, 100, this);

        loadDefaultLevel();
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");

    }

    public void saveLevel(){
        LoadSave.SaveLevel("new_level", lvl);
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i=0; i<arr.length; i++){
            arr[i] = 0;
        }
        LoadSave.CreateLevel("new level", arr);
    }

    public TileManager getTileManager(){
        return tileManager;
    }

    @Override
    public void render(Graphics g) {
        for (int y=0; y<lvl.length; y++){
            for (int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                g.drawImage(tileManager.getSprite(id),x*32 , y*32, null);
            }
        }
        drawSelectedTile(g);
        bottomBar.draw(g);
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
        drawSelected = true;
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile!=null && drawSelected){
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY,32, 32, null);

        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }

    }

    private void changeTile(int x, int y) {
        if (x < 0 || y < 0 || x > 640 || y > 640)
            return;
        if (selectedTile!=null){
            int tileX = x/32, tileY =y/32;
            if (lastTileX == tileX &&
                lastTileY == tileY &&
                lastTileID == selectedTile.getId())
                return;
            lastTileY = tileY;
            lastTileX = tileX;
            lastTileID = selectedTile.getId();
            lvl[tileY][tileX] = selectedTile.getId();

        }
    }

    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseMoved(x, y);
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
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y >= 640) {

        } else {
            changeTile(x, y);
        }
    }
}
