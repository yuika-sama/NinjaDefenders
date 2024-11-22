package objects;

import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private final int id;
    private final int dmg;
    private final int projectileType;
    private boolean active = true;
    private final float xSpeed;
    private final float ySpeed;
    private final float angle;


    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float angle, int id, int projectileType){
        pos = new Point2D.Float(x, y);
        this.id = id;
        this.dmg = dmg;
        this.projectileType = projectileType;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
    }

    public void move (){
        pos.x += xSpeed;
        pos.y += ySpeed;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
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