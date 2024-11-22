package enemies;

import helpz.Constants;
import managers.EnemyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    protected EnemyManager enemyManager;

    public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = -1;

        setStartHealth();
    }

    private void setStartHealth() {
        hp = Constants.Monsters.getStartHealth(enemyType);
        maxHP = hp;
    }

    public void hurt(int dmg) {
        this.hp -= dmg;
        if (this.hp <= 0) {
            alive = false;
            enemyManager.rewardMoney(enemyType);
        }
    }

    public void kill() {
        System.out.println("Game set!!");
        alive = false;
        hp = 0;
    }

    public int getSpriteType(int enemyType){
        return helpz.Constants.Monsters.getSpriteType(enemyType);
    }

    public String getName(){
        return Constants.Monsters.getName(enemyType);
    }
    public void move(float speed, int dir) {
        lastDir = dir;

        if (slowTick < slowTickLimit) {
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

    public void slow() {
        slowTick = 0;
    }

    public boolean isSlowed() {
        return (slowTickLimit > slowTick);
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


}
