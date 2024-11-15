package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Towers.*;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int id = 0;

    public TowerManager(Playing playing) {
        this.playing = playing;
        
        loadTowerImgs();
        addTower(new Tower(32*1, 32*3, 0, ARCHER), 32*1, 32*3);
    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs= new BufferedImage[3];
        for (int i =0 ; i<3; i++){
            assert atlas != null;
            towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32 , 32, 32);
        }
    }

    public void draw(Graphics g){
        for (Tower t:towers){
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }

    public void update(){
        for (Tower t:towers){
            t.update();
            attackToCloseEnemy(t);
        }
    }

    private void attackToCloseEnemy(Tower t) {
        for (Enemy e:playing.getEnemyManager().getEnemies()){
            if (e.isAlive()){
                if (isEnemyInRage(t, e)){
                    if (t.isCooldownOver()){
                        playing.shootEnemy(t, e);
                        t.resetCooldown();
                    }
                }
            }
        }
    }

    private boolean isEnemyInRage(Tower t, Enemy e) {
        int range = helpz.Utils.getDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();

    }

    public BufferedImage[] getTowerImgs(){
        return towerImgs;
    }

    public void addTower(Tower selectedTower, int mouseX, int mouseY) {
        towers.add(new Tower(mouseX, mouseY, id++, selectedTower.getTowerType()));
    }

    public Tower getTowerAt(int mouseX, int mouseY) {
        for (Tower t:towers){
            if (t.getX() == mouseX && t.getY() == mouseY){
                return t;
            }
        }
        return null;
    }
}
