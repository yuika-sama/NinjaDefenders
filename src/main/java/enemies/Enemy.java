package enemies;

import java.awt.*;

import static helpz.Constants.Direction.*;

public abstract class Enemy {
    private float x, y;
    private Rectangle bounds;
    private int hp;
    private int ID;
    private int enemyType;
    private int lastDir;

    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int)x, (int) y, 32, 32);
        lastDir = -1;
    }

    public void move(float speed, int dir){
        lastDir = dir;
        switch(dir){
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHp() {
        return hp;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }
}
