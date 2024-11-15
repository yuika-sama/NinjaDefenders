package objects;

import helpz.Constants;

public class Tower {
    private int x, y, id, towerType, cdTick, dmg;

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
        cd = Constants.Towers.getDefaultCoolDown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.Towers.getDefaultRange(towerType);
    }

    private void setDefaultDamage() {
        dmg = Constants.Towers.getStartDmg(towerType);
    }

    public void update(){
        cdTick++;
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
}
