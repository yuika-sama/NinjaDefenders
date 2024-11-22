package objects;

import helpz.Constants;

import static helpz.Constants.Direction.*;

public class Tower {
    private final int x;
    private final int y;
    private final int id;
    private final int towerType;
    private int cdTick;
    private int dmg;

    private int direction = DOWN;
    private boolean attacking = false;
    private int animIndex, animTick = 0;

    private float range, cd;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;

        setDefaultDamage();
        setDefaultRange();
        setDefaultCoolDown();
    }

    private void setDefaultCoolDown() {
        cd = Constants.Turrets.getDefaultCoolDown(towerType) * 25;
    }

    private void setDefaultRange() {
        range = Constants.Turrets.getDefaultRange(towerType);
    }

    private void setDefaultDamage() {
        dmg = Constants.Turrets.getStartDmg(towerType);
    }

    public void update() {
        animTick++;
        cdTick++;
        int animSpeed = attacking ? 20 : 6;

        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;

            if (attacking) {
                animIndex %= 4;
            } else {
                animIndex %= 4;
            }
        }
    }

    public boolean isCooldownOver() {
        return cdTick >= cd;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getTowerType() {
        return towerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCd() {
        return cd;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public Integer getDirection() {
        return direction;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setDirection(int dir) {
        this.direction = dir;
    }

    public boolean getState(){
        return attacking;
    }
}