package objects;

import helpz.Constants;

public class Tower {
    private int x, y, id, towerType;

    private float dmg, range, cd;

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

    public float getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCd() {
        return cd;
    }
}
