package entities.objects;

import utilities.Constants;

import static utilities.Constants.Direction.DOWN;
import static utilities.Constants.Turrets.*;

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
    private int tier;

    private float range, cd;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier = 1;

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

    public void upgradeTower() {
        this.tier++;
        switch (towerType) {
            case DARK_NINJA -> {
                dmg += 5; // Increase damage by 5
                range += 10; // Increase range by 10
                cd -= 2; // Reduce cooldown time
            }
            case YELLOW_NINJA -> {
                dmg += 4;
                range += 15;
                cd -= 3;
            }
            case RED_NINJA -> {
                dmg += 8;
                range += 12;
                cd -= 1;
            }
            case FLAMIE -> {
                dmg += 10;
                range += 8;
                cd -= 2.5f;
            }
            case KNIGHT -> {
                dmg += 15;
                range += 5;
                cd -= 2;
            }
            case SAMURAI -> {
                dmg += 20;
                range += 7;
                cd -= 3;
            }
            case ORANGE_SORCERER -> {
                dmg += 2;
                range += 20;
                cd -= 1.5f;
            }
            case RED_SORCERER -> {
                dmg += 3;
                range += 25;
                cd -= 2;
            }
            default -> {
                // If the tower type doesn't match any predefined types
                dmg += 1; // Slightly increase damage
                range += 5; // Slightly increase range
                cd -= 0.5f; // Slightly reduce cooldown
            }
        }
    }

    public int getTier() {
        return tier;
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

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(int dir) {
        this.direction = dir;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public boolean getState() {
        return attacking;
    }
}