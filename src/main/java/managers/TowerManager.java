package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Action.*;
import static helpz.Constants.Turrets.*;

public class TowerManager {
    private final Playing playing;
    private BufferedImage[] towerImgs;
    private final ArrayList<Tower> towers = new ArrayList<>();
    private final BufferedImage[] towerFaceset = new BufferedImage[9];
    private final BufferedImage[] towerFirstSprite = new BufferedImage[9];
    private int id = 0;

    //Name, Actions, Direction, Images
    private final Map<String, Map<Integer, Map<Integer, BufferedImage[]>>> towerAnim = new HashMap<>();

    public TowerManager(Playing playing) {
        this.playing = playing;
        
        loadTowerImgs();
        loadTowerAnim();
        addTower(new Tower(32, 32*3, 0, DARK_NINJA), 32, 32*3);
    }

    private void loadTowerAnim() {
        for (int i = 0; i<8; i++){
            String towerName = Constants.Turrets.getName(i);
            towerAnim.putIfAbsent(towerName, new HashMap<>());
            BufferedImage[] animLoad = LoadSave.loadAnimFrames(towerImgs[i], 3);
            towerFaceset[i] = animLoad[12];

            //Idle
            Map<Integer, BufferedImage[]> idleFrames = new HashMap<>();
            idleFrames.put(LEFT, new BufferedImage[]{animLoad[2]});
            idleFrames.put(UP, new BufferedImage[]{animLoad[1]});
            idleFrames.put(RIGHT, new BufferedImage[]{animLoad[3]});
            idleFrames.put(DOWN, new BufferedImage[]{animLoad[0]});
            towerFirstSprite[i] = animLoad[0];
            towerAnim.get(towerName).put(IDLE, idleFrames);

            //Attack
            Map<Integer, BufferedImage[]> attackFrames = new HashMap<>();
            attackFrames.put(LEFT, Arrays.copyOfRange(animLoad, 8, 10));
            attackFrames.put(RIGHT, Arrays.copyOfRange(animLoad, 10, 12));
            attackFrames.put(UP, Arrays.copyOfRange(animLoad, 6, 8));
            attackFrames.put(DOWN, Arrays.copyOfRange(animLoad, 4, 6));
            towerAnim.get(towerName).put(ATTACK, attackFrames);
        }
    }

    private void loadTowerImgs() {
        towerImgs = new BufferedImage[8];
        for (int i=0; i<8; i++){
            towerImgs[i] = LoadSave.getSpriteByName(Constants.Turrets.getSpriteName(i), 3);
        }
    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            BufferedImage img;
            String towerName = Constants.Turrets.getName(t.getTowerType());
            int direction = t.getDirection(); // Hướng hiện tại của turret

            if (t.isAttacking()) {
                BufferedImage[] attackAnim = towerAnim.get(towerName).get(ATTACK).get(direction);
                img = attackAnim[t.getAnimIndex() % attackAnim.length];
            } else {
                BufferedImage[] idleAnim = towerAnim.get(towerName).get(IDLE).get(direction);
                img = idleAnim[t.getAnimIndex() % idleAnim.length];
            }
            g.drawImage(img, t.getX(), t.getY(), null);
        }
    }

    public void update() {
        for (Tower t : towers) {
            Enemy targetEnemy = findNearestEnemyInRange(t);

            if (targetEnemy != null) {
                setDirection(t, targetEnemy);
                t.setAttacking(true);

                if (t.isCooldownOver()) {
                    playing.shootEnemy(t, targetEnemy);
                    t.resetCooldown();
                }
            } else {
                t.setAttacking(false);
            }
            t.update();
        }
    }

    private Enemy findNearestEnemyInRange(Tower t) {
        Enemy nearestEnemy = null;
        float shortestDistance = Float.MAX_VALUE;

        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float distance = (float) Math.sqrt(Math.pow(e.getX() - t.getX(), 2) + Math.pow(e.getY() - t.getY(), 2));
                if (distance <= t.getRange() && distance < shortestDistance) {
                    nearestEnemy = e;
                    shortestDistance = distance;
                }
            }
        }

        return nearestEnemy;
    }

    private void setDirection(Tower t, Enemy e) {
        float deltaX = e.getX() - t.getX();
        float deltaY = e.getY() - t.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            t.setDirection(deltaX > 0 ? RIGHT : LEFT);
        } else {
            t.setDirection(deltaY > 0 ? DOWN : UP);
        }
    }

    public void addTower(Tower selectedTower, int mouseX, int mouseY) {
        towers.add(new Tower(mouseX, mouseY, id++, selectedTower.getTowerType()));
    }

    public BufferedImage[] getTowerImgs(){
        return towerFaceset;
    }

    public BufferedImage[] getTowerFirstSprite(){
        return towerFirstSprite;
    }
    public Tower getTowerAt(int mouseX, int mouseY) {
        for (Tower t:towers){
            if (t.getX() == mouseX && t.getY() == mouseY){
                return t;
            }
        }
        return null;
    }

    public void removeTower(Tower displayTower) {
        for (int i = 0; i<towers.size(); i++){
            if (towers.get(i).getId() == displayTower.getId()){
                towers.remove(i);
            }
        }
    }

    public void upgradeTower(Tower displayTower) {
        for (Tower t:towers){
            if (t.getId() == displayTower.getId()){
                t.upgradeTower();
            }
        }
    }
}
