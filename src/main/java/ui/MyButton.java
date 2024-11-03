package ui;

import java.awt.*;

public class MyButton {

    public int x, y, width, height, id;
    private String text;

    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    //for normal buttons
    public MyButton(String text, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = -1;

        initBounds();
    }

    //for tile buttons
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = id;

        initBounds();
    }

    public int getId() {
        return id;
    }

    private void initBounds(){
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g){
        //body
        drawBody(g);

        //border
        drawBorder(g);

        //text
        drawText(g);
    }

    private void drawBorder(Graphics g) {
        if (mousePressed){
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawRect(x+1, y+1, width - 2, height - 2);
            g.drawRect(x+2, y+2, width - 4, height -4);
        } else {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }

    }

    private void drawBody(Graphics g) {
        if (mouseOver){
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w/2 + width/2, y + h/2 + height/2);
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver(){
        return mouseOver;
    }

    public boolean isMousePressed(){
        return mousePressed;
    }

}
