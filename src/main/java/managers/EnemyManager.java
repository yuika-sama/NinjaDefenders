package managers;

import enemies.*;
import helpz.Constants;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import static helpz.Constants.Monsters.*;


public class EnemyManager {

    private final Playing playing;
    private final BufferedImage[] enemyImgs;
    private BufferedImage slowEffect;

    private final ArrayList<Enemy> enemies = new ArrayList<>();

    private final PathPoint start;
    private final PathPoint end;

    Map<String, Map<Integer, BufferedImage[]>> animImgs = new HashMap<>();
    private final BufferedImage[] enemyFaceset = new BufferedImage[14];

    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImgs = new BufferedImage[14];
        this.start = start;
        this.end = end;

        loadEffectImg();
        loadEnemyImgs();
        loadEnemyAnim();

    }

    public Enemy getEnemyAt(int mouseX, int mouseY){
        final int TOLERANCE = 16;
        for (Enemy e:enemies){
            double distance = Math.sqrt(
                Math.pow(e.getX() - mouseX, 2) +
                Math.pow(e.getY() - mouseY, 2)
            );
            if (distance <= TOLERANCE) {
                return e;
            }
        }
        return null;
    }

    private void loadEnemyAnim() {
        for (int i=0; i<14; i++){
            String enemyName = Constants.Monsters.getName(i);
            int spriteType = Constants.Monsters.getSpriteType(i);
            Map<Integer, BufferedImage[]> anim_with_direction = new HashMap<>();
            BufferedImage[] animLoad = LoadSave.loadAnimFrames(enemyImgs[i], spriteType);
            if (spriteType == 1){
                anim_with_direction.put(LEFT, Arrays.copyOfRange(animLoad, 3, 6));
                anim_with_direction.put(RIGHT, Arrays.copyOfRange(animLoad, 6, 9));
                anim_with_direction.put(UP, Arrays.copyOfRange(animLoad, 9, 12));
                anim_with_direction.put(DOWN, Arrays.copyOfRange(animLoad, 0, 3));
                enemyFaceset[i] =animLoad[12];
            } else if (spriteType == 2){
                anim_with_direction.put(LEFT, Arrays.copyOfRange(animLoad, 8, 12));
                anim_with_direction.put(RIGHT, Arrays.copyOfRange(animLoad, 12, 16));
                anim_with_direction.put(UP, Arrays.copyOfRange(animLoad, 4, 8));
                anim_with_direction.put(DOWN, Arrays.copyOfRange(animLoad, 0, 4));
                enemyFaceset[i] =animLoad[16];
            }
            animImgs.put(enemyName, anim_with_direction);
        }
    }

    private void loadEffectImg() {
        slowEffect = Objects.requireNonNull(LoadSave.getSpriteAtlas()).getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    private void loadEnemyImgs() {
        for (int i=0; i<14; i++){
            enemyImgs[i] = LoadSave.getSpriteByName(Constants.Monsters.getSpriteName(i), Constants.Monsters.getSpriteType(i));
        }
    }

    public void draw(Graphics g){
        for (Enemy e:enemies){
            if (e.isAlive()){
                drawEnemy(e, g);
                drawHealthBar(e, g);
                drawEffect(e, g);
            }
        }
    }

    private void drawEffect(Enemy e, Graphics g) {
        if (e.isSlowed()){
            g.drawImage(slowEffect, (int)e.getX(), (int)e.getY(), null);
        }
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)e.getX() + 16 - (getNewBarWidth(e) / 2), (int)e.getY() - 10, getNewBarWidth(e), 3);
    }

    private int getNewBarWidth(Enemy e){
        int HPBarWidth = 20;
        return (int)(HPBarWidth * e.getHealthBarFloat());
    }

    private void drawEnemy(Enemy e, Graphics g) {
        int dir = e.getLastDir();
        int frameIndex = (int) ((System.currentTimeMillis() / 100) % 4); // Số khung hình mặc định là 4
        String _enemyType = e.getName();

        Map<Integer, BufferedImage[]> enemyFrames = animImgs.get(_enemyType);

        if (enemyFrames != null) {
            BufferedImage[] frames = enemyFrames.get(dir);

            if (frames != null && frames.length > 0) {
                frameIndex = frameIndex % frames.length;
                g.drawImage(frames[frameIndex], (int) e.getX(), (int) e.getY(), null);
            } else {
                System.err.println("Frames not found for direction: " + dir + " of enemy type: " + _enemyType);
            }
        } else {
            System.err.println("Animation frames not found for enemy type: " + _enemyType);
        }
    }


    public void update(){
        for (Enemy e:enemies){
            if (e.isAlive()){
                updateEnemyMove(e);
            }
        }
    }

    public void spawnEnemies(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void updateEnemyMove(Enemy e) {
        if (e.getLastDir() == -1){
            setNewDirectionAndMove(e);
        }

        int newX = (int)(e.getX() + getSpeedX(e.getLastDir(), e.getEnemyType()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDir(), e.getEnemyType()));

        if (getTileType(newX, newY) == ROAD_TILE){
            e.move(getSpeed(e.getEnemyType()), e.getLastDir());
        } else if(isEnd(e)){
                e.kill();

        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        int xCord = (int)(e.getX()/32);
        int yCord = (int)(e.getY()/32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (isEnd(e)){
            return;
        }

        if (dir == LEFT || dir == RIGHT){
            int newY = (int)(e.getY() + getSpeedY(UP, e.getEnemyType()));
            if (getTileType((int)e.getX(), newY) == ROAD_TILE){
                e.move(getSpeed(e.getEnemyType()), UP);
            } else {
                e.move(getSpeed(e.getEnemyType()), DOWN);
            }
        } else {
            int newX = (int)(e.getX() + getSpeedX(RIGHT, e.getEnemyType()));
            if (getTileType(newX, (int)e.getY()) == ROAD_TILE){
                e.move(getSpeed(e.getEnemyType()), RIGHT);
            } else {
                e.move(getSpeed(e.getEnemyType()), LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch(dir){
            case RIGHT:
                if (xCord < 19){
                    xCord++;
                }
                break;
            case DOWN:
                if (yCord < 19){
                    yCord++;
                }
                break;
        }
        e.setPos(xCord*32, yCord*32);
    }

    public void addEnemy(int enemyType) {
    int x = start.getxCord() * 32;
    int y = start.getyCord() * 32;
    switch (enemyType) {
        case GREEN_SLIME:
            enemies.add(new GreenSlime(x, y, 0, this));
            break;
        case BIG_GREEN_SLIME:
            enemies.add(new BigGreenSlime(x, y, 0, this));
            break;
        case DARK_GREEN_SLIME:
            enemies.add(new DarkGreenSlime(x, y, 0, this));
            break;
        case LAVA_SLIME:
            enemies.add(new LavaSlime(x, y, 0, this));
            break;
        case PINK_SLIME:
            enemies.add(new PinkSlime(x, y, 0, this));
            break;
        case TOXIC_SLIME:
            enemies.add(new ToxicSlime(x, y, 0, this));
            break;
        case KING_SLIME:
            enemies.add(new KingSlime(x, y, 0, this));
            break;
        case MUSHROOM_MAN:
            enemies.add(new MushroomMan(x, y, 0, this));
            break;
        case GOBLIN:
            enemies.add(new Goblin(x, y, 0, this));
            break;
        case DEMON_GREEN:
            enemies.add(new DemonGreen(x, y, 0, this));
            break;
        case ROBOT_GREEN:
            enemies.add(new RobotGreen(x, y, 0, this));
            break;
        case SKELETON_P:
            enemies.add(new Skeleton(x, y, 0, this));
            break;
        case SPIRIT:
            enemies.add(new Spirit(x, y, 0, this));
            break;
        case TENGU:
            enemies.add(new Tengu(x, y, 0, this));
            break;
    }
}

    private boolean isEnd(Enemy e) {
        return e.getX() == end.getxCord() * 32 && e.getY() == end.getyCord() * 32;
    }

    private int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }

    private float getSpeedY(int dir, int enemyType) {
        if (dir == UP){
            return -getSpeed(enemyType);
        } else if (dir == DOWN){
            return getSpeed(enemyType) + 32;
        }
        return 0;
    }

    private float getSpeedX(int dir, int enemyType) {
        if (dir == LEFT)
			return -getSpeed(enemyType);
		else if (dir == RIGHT)
			return getSpeed(enemyType) + 32;

		return 0;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public BufferedImage[] getEnemyFaceset(){
        return enemyFaceset;
    }

    public int getAmountAlivingEnemies() {
        int amount = 0;
        for (Enemy e:enemies){
            if (e.isAlive()){
                amount++;
            }
        }
        return amount;
    }

    public void rewardMoney(int enemyType) {
        playing.rewardPlayer(enemyType);
    }
}
