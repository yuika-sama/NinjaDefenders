package ui;

import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mycompany.towerdefense.GameState.MENU;
import static com.mycompany.towerdefense.GameState.setGameStates;

public class ToolBar extends Bar{
    private Editing editing;
    private MyButton bMenu, bSave;
    private Tile selectedTile;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();
    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bSave = new MyButton("Save", 2, 674, 100, 30);

        int w = 50, h = 50, xStart = 110, yStart = 650, xOffset = (int)(w * 1.1f), i = 0;

        for (Tile tile:editing.getGame().getTileManager().tiles){
            tileButtons.add(new MyButton(tile.getName() , xStart + xOffset * i, yStart, w, h, i++));
        }
    }

    private void saveLevel() {
        editing.saveLevel();

    }

    public void draw(Graphics g){
        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);
        drawSelectedTile(g);
    }

    private void drawButtons(Graphics g){
        bMenu.draw(g);
        bSave.draw(g);
        drawTileButton(g);
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile!=null){
            g.drawImage(selectedTile.getSprite(), 550, 670, 50,50, null);
            g.setColor(Color.YELLOW);
            g.drawRect(550, 670, 50, 50);
        }
    }

    private void drawTileButton(Graphics g) {
        for (MyButton b : tileButtons){
            //background
            g.drawImage(getButtonImg(b.getId()), b.x, b.y, b.width, b.height, null);

            //mouse hover
            if (b.isMouseOver()){
                g.setColor(Color.white);
            } else {
                g.setColor(Color.black);
            }
            g.drawRect(b.x, b.y, b.width, b.height);

            //mouse pressed
            if (b.isMousePressed()){
                g.drawRect(b.x+1, b.y+1, b.width-2, b.height-2);
                g.drawRect(b.x+2, b.y+2, b.width-4 , b.height-4);
            }
        }
    }

    private BufferedImage getButtonImg(int id){
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)){
            setGameStates(MENU);
        } else if (bSave.getBounds().contains(x, y)){
            saveLevel();
//            System.out.println("Save button clicked");
        }

        else {
            for (MyButton b:tileButtons){
                if (b.getBounds().contains(x, y)){
                    selectedTile = editing.getGame().getTileManager().getTile(b.getId());
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        for (MyButton b : tileButtons){
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x, y)){
            bSave.setMouseOver(true);
        }
        else {
            for (MyButton b : tileButtons){
                if (b.getBounds().contains(x, y)){
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        } else if (bSave.getBounds().contains(x, y)){
            bSave.setMousePressed(true);
        }
        else {
            for (MyButton b : tileButtons){
                if (b.getBounds().contains(x, y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        for (MyButton b:tileButtons){
            b.resetBooleans();
        }
    }

}
