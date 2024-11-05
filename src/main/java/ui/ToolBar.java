package ui;

import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class ToolBar extends Bar {
    private final Editing editing;
    private final Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();
    private MyButton bMenu, bSave;
    private Tile selectedTile;
    private MyButton bGrass, bWater, bRoadS, bRoadC, bWaterC, bWaterB, bWaterI;
    private MyButton currentButton;
    private int currentId;

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bSave = new MyButton("Save", 2, 674, 100, 30);

        int w = 50, h = 50, xStart = 110, yStart = 650, xOffset = (int) (w * 1.1f), i = 0;

        bGrass = new MyButton("Grass", xStart, yStart, w, h, i++);
        bWater = new MyButton("Water", xStart + xOffset, yStart, w, h, i++);

        initMapButtons(bRoadS, editing.getGame().getTileManager().getRoadS(), xStart, yStart, xOffset, w, h, i++);
        initMapButtons(bRoadC, editing.getGame().getTileManager().getRoadC(), xStart, yStart, xOffset, w, h, i++);
        initMapButtons(bWaterC, editing.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, w, h, i++);
        initMapButtons(bWaterB, editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
        initMapButtons(bWaterI, editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);
    }

    private void initMapButtons(MyButton b, ArrayList<Tile> list, int x, int y, int xOffset, int w, int h, int id) {
        b = new MyButton("", x + xOffset * id, y, w, h, id);
        map.put(b, list);
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void draw(Graphics g) {
        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);
        drawSelectedTile(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);
//        drawTileButton(g);

        drawGeneralButtons(g, bGrass);
        drawGeneralButtons(g, bWater);
        drawSelectedTile(g);
        drawMapButtons(g);
    }

    private void drawGeneralButtons(Graphics g, MyButton b) {
        g.drawImage(getButtonImg(b.getId()), b.x, b.y, b.width, b.height, null);
        drawFeedBackButton(g, b);
    }

    private void drawFeedBackButton(Graphics g, MyButton b) {
        //mouse hover
        if (b.isMouseOver()) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.black);
        }
        g.drawRect(b.x, b.y, b.width, b.height);

        //mouse pressed
        if (b.isMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }

    private void drawMapButtons(Graphics g) {

        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().getFirst().getSprite();

            g.drawImage(img, b.x, b.y, b.width, b.height, null);

            drawFeedBackButton(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null) {
            g.drawImage(selectedTile.getSprite(), 550, 670, 50, 50, null);
            g.setColor(Color.YELLOW);
            g.drawRect(550, 670, 50, 50);
        }
    }

    private BufferedImage getButtonImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (bWater.getBounds().contains(x, y)) {
            currentButton = bWater;
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
        } else if (bGrass.getBounds().contains(x, y)) {
            currentButton = bGrass;
            selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selectedTile);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    selectedTile = map.get(b).getFirst();
                    editing.setSelectedTile(selectedTile);
                    currentButton = b;
                    currentId = 0;
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        bGrass.setMouseOver(false);
        bWater.setMouseOver(false);
        for (MyButton b : map.keySet()) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMouseOver(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMouseOver(true);
        } else if (bGrass.getBounds().contains(x, y)) {
            bGrass.setMouseOver(true);
        } else {
            for (MyButton b : map.keySet()) {
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
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMousePressed(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMousePressed(true);
        } else if (bGrass.getBounds().contains(x, y)) {
            bGrass.setMousePressed(true);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bWater.resetBooleans();
        bGrass.resetBooleans();
        for (MyButton b : map.keySet()) {
            b.resetBooleans();
        }
    }

    public void rotateSprite() {
        if (currentButton == bGrass || currentButton == bWater) {
            return;
        }
        currentId++;
        if (currentId >= map.get(currentButton).size()) {
            currentId = 0;
        }
        selectedTile = map.get(currentButton).get(currentId);
        editing.setSelectedTile(selectedTile);
    }
}