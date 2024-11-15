package enemies;

import helpz.Constants;

import java.awt.*;

import static helpz.Constants.Direction.*;

public abstract class Enemy {
    protected float x, y;
    protected Rectangle bounds;
    protected int hp, maxHP;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;

    protected boolean alive = true;

    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = -1;

        setStartHealth();
    }

    private void setStartHealth() {
        hp = Constants.Enemies.getStartHealth(enemyType);
        maxHP = hp;
    }

    public void hurt(int dmg) {
        this.hp -= dmg;
        if (this.hp <= 0) {
            alive = false;
        }
    }

    public void move(float speed, int dir) {
        lastDir = dir;

        if (slowTick < slowTickLimit){
            slowTick++;
            speed *= 0.2f;
        }

        switch (dir) {
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
        updateHitbox();
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void setPos(int x, int y) {
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

    public float getHealthBarFloat() {
        return hp / (float) maxHP;
    }

    public boolean isAlive() {
        return alive;
    }

    public void slow() {
        slowTick = 0;
    }

    public boolean isSlowed(){
        return (slowTickLimit > slowTick);
    }
}
