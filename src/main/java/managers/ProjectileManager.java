package managers;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Projectile.*;
import static helpz.Constants.Towers.*;

public class ProjectileManager {

    private final Playing playing;
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] proj_imgs, explore_imgs;
    private int proj_id = 0;
    private boolean drawExpore;
    private ArrayList<Explosion> explosions = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    private void importImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[3];

        for (int i=0; i < 3; i++){
            proj_imgs[i] = atlas.getSubimage((7+i) * 32, 32, 32, 32);
        }

        importExplotion(atlas);
    }

    private void importExplotion(BufferedImage atlas) {
        explore_imgs = new BufferedImage[7];

        for(int i=0; i<7; i++){
            explore_imgs[i] = atlas.getSubimage(i*32, 32 * 2, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e){
        int type = getProjType(t);

        int xDis = (int) (t.getX() - e.getX());
        int yDis = (int) (t.getY() - e.getY());
        int totalDis = Math.abs(xDis) + Math.abs(yDis);

        float xPercent = (float)Math.abs(xDis) / totalDis;

        float xSpeed = xPercent * helpz.Constants.Projectile.getSpeed(type);
        float ySpeed = helpz.Constants.Projectile.getSpeed(type) - xSpeed;

        if (t.getX() > e.getX()){
            xSpeed *= -1;
        }
        if (t.getY() > e.getY()){
            ySpeed *= -1;
        }

        float angle = 0;

        if (type == ARROW){
            float atanValue = (float)Math.atan(yDis/(float)xDis);
            angle = (float)Math.toDegrees(atanValue);
            if (xDis < 0){
                angle += 180;
            }
        }



        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), angle, proj_id++, type));
    }

    public void update(){
        for (Projectile p:projectiles){
            if (p.isActive()){
                p.move();
                if (isProjectileHit(p)){
                    p.setActive(false);
                    if (p.getProjectileType() == BOMB){
                        explosions.add(new Explosion(p.getPos()));
                        kaboomNearEnemies(p);
                    }
                } else if (p.getPos().x <= 0 || p.getPos().x > 640){
                    p.setActive(false);
                } else if (p.getPos().y <= 0 || p.getPos().y > 640){
                    p.setActive(false);
                } else {
                    //do nothing
                }
            }
        }

        for (Explosion e:explosions){
            if (e.getExploreId() < 7){
                e.update();
            }
        }
    }

    private void kaboomNearEnemies(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()){
            if (e.isAlive()){
                float radius = 40.0f;
                float xDis = Math.abs(p.getPos().x - e.getX());
                float yDis = Math.abs(p.getPos().y - e.getY());
                float realDis = (float)Math.hypot(xDis, yDis);

                if (realDis <= radius){
                    e.hurt(p.getDmg());
                }
            }
        }
    }

    private boolean isProjectileHit(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()){
            if (e.isAlive()){
                if (e.getBounds().contains(p.getPos()) && p.isActive()){
                    e.hurt(p.getDmg());
                    if (p.getProjectileType() == CHAINS){
                        e.slow();
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p:projectiles){
            if (p.isActive()){
                if (p.getProjectileType() == ARROW){
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getAngle()));
                    g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(p.getAngle()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int)p.getPos().x  -16, (int)p.getPos().y  -16, null);
                }
            } else {
                //do nothing
            }
        }

        drawExploreAnim(g2d);
    }

    private void drawExploreAnim(Graphics2D g2d) {
        for (Explosion e:explosions){
            if (e.getExploreId() < 7){
                g2d.drawImage(explore_imgs[e.getExploreId()], (int)e.getPos().x - 16, (int)e.getPos().y - 16, null);
            }
        }
    }

    private int getProjType(Tower t){
        switch (t.getTowerType()){
            case ARCHER:
                return ARROW;
            case WIZARD:
                return CHAINS;
            case CANNON:
                return BOMB;
        }
        return 0;
    }

    public class Explosion{
        private Point2D.Float pos;
        private int exploreTick = 0, exploreId = 0;

        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }

        public void update(){
            exploreTick++;
            if (exploreTick >= 12){
                exploreTick = 0;
                exploreId++;
                if (exploreId >= 7){
                    exploreId = 0;
                    drawExpore = false;
                }
            }
        }

        public Point2D.Float getPos() {
            return pos;
        }

        public int getExploreId() {
            return exploreId;
        }
    }
}