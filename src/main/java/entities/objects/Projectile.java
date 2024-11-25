package entities.objects;

import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private int id;
    private int dmg;
    private int projectileType;
    private boolean active = true;
    private float xSpeed;
    private float ySpeed;
    private float angle;


    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float angle, int id, int projectileType){
        pos = new Point2D.Float(x, y);
        this.id = id;
        this.dmg = dmg;
        this.projectileType = projectileType;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
    }

    public void reUse(int x, int y, float xSpeed, float ySpeed, int dmg, float angle) {
        pos = new Point2D.Float(x, y);
        this.dmg = dmg;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
        active = true;
    }

    public void move (){
        pos.x += xSpeed;
        pos.y += ySpeed;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public int getDmg() {
        return dmg;
    }

    public int getId() {
        return id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public float getAngle() {
        return angle;
    }
}