package managers;

import entities.enemies.Enemy;
import utilities.LoadSave;
import entities.objects.Projectile;
import entities.objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.Projectile.*;
import static utilities.Constants.Turrets.*;

public class ProjectileManager {

    private final Playing playing;

    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] proj_imgs, explore_imgs;
    private int proj_id = 0;

    private boolean drawExplore;
    private final ArrayList<Explosion> explosions = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    private void importImgs(){
        BufferedImage atlas = LoadSave.getSpriteByName("projectiles", 4);
        proj_imgs = new BufferedImage[4];

        for (int i=0; i < 4; i++){
            assert atlas != null;
            proj_imgs[i] = atlas.getSubimage(i * 32, 0, 32, 32);
        }

        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        explore_imgs = new BufferedImage[7];

        for(int i=0; i<7; i++){
            explore_imgs[i] = atlas.getSubimage((i+4) *32, 0, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e){
        int type = getProjType(t);

        int xDis = (int) (t.getX() - e.getX());
        int yDis = (int) (t.getY() - e.getY());
        int totalDis = Math.abs(xDis) + Math.abs(yDis);

        float xPercent = (float)Math.abs(xDis) / totalDis;
        float xSpeed = xPercent * utilities.Constants.Projectile.getSpeed(type);
        float ySpeed = utilities.Constants.Projectile.getSpeed(type) - xSpeed;

        if (t.getX() > e.getX()){
            xSpeed *= -1;
        }
        if (t.getY() > e.getY()){
            ySpeed *= -1;
        }

        float angle = 0;
        if (type == KUNAI || type == FIRE){
            float atanValue = (float)Math.atan(yDis/(float)xDis);
            angle = (float)Math.toDegrees(atanValue);
            if (xDis < 0){
                angle += 180;
            }
        }
        else if (type == SLASH){
            float atanValue = (float)Math.atan(yDis/(float)xDis);
            angle = (float)Math.toDegrees(atanValue);
            if (yDis < 0 || xDis > 0){
                angle += 180;
            }
        }

        for (Projectile p:projectiles){
            if (!p.isActive()){
                if (p.getProjectileType() == type){
                    p.reUse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), angle);
                    return;
                }
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
                    if (p.getProjectileType() == FIRE){
                        explosions.add(new Explosion(p.getPos()));
                        kaboomNearEnemies(p);
                    }
                } else if (p.getPos().x <= 0 || p.getPos().x > 640){
                    p.setActive(false);
                } else if (p.getPos().y <= 0 || p.getPos().y > 640){
                    p.setActive(false);
                }
            }
        }

        explosions.removeIf(Explosion::isFinished);
        for(Explosion e:explosions){
            e.update();
        }
        drawExplore = !explosions.isEmpty();
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
        drawExplore = true;
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
                if (p.getProjectileType() != CHAINS){
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getAngle()));
                    g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(p.getAngle()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int)p.getPos().x  -16, (int)p.getPos().y  -16, null);
                }
            }
        }
        drawExploreAnim(g2d);
    }

    private void drawExploreAnim(Graphics2D g2d) {
        if (!drawExplore) return;

        for (Explosion e:explosions){
            if (e.getExploreId() < 7){
                g2d.drawImage(explore_imgs[e.getExploreId()], (int)e.getPos().x - 16, (int)e.getPos().y - 16, null);
            }
        }
    }

    private int getProjType(Tower t){
        return switch (t.getTowerType()) {
                case DARK_NINJA -> KUNAI;
                case YELLOW_NINJA -> KUNAI;
                case RED_NINJA -> KUNAI;
                case FLAMIE -> FIRE;
                case KNIGHT -> SLASH;
                case SAMURAI -> SLASH;
                case ORANGE_SORCERER -> CHAINS;
                case RED_SORCERER -> CHAINS;
                default -> 0;
            };
    }

    public class Explosion{
        private final Point2D.Float pos;
        private int exploreTick = 0, exploreId = 0;
        private boolean finished = false;

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
                    finished = true;
                    drawExplore = false;
                }
            }
        }

        public Point2D.Float getPos() {
            return pos;
        }

        public int getExploreId() {
            return exploreId;
        }

        public boolean isFinished(){
            return finished;
        }
    }
    public void reset(){
        projectiles.clear();
        explosions.clear();
        proj_id = 0;
    }
}