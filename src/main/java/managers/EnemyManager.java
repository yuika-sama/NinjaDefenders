package managers;

import enemies.*;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import static helpz.Constants.Enemies.*;


public class EnemyManager {

    private Playing playing;
    private BufferedImage[] enemyImgs;
    private Enemy testEnemy;
    private float speed = 0.5f;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;


    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImgs = new BufferedImage[4];
        this.start = start;
        this.end = end;
        loadEnemyImgs();
        addEnemy(ORC);
        addEnemy(BAT);
        addEnemy(KNIGHT);
        addEnemy(WOLF);
    }


    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for (int i=0; i<4; i++){
            enemyImgs[i] = atlas.getSubimage(32 * i, 32, 32, 32);
        }
    }

    public void update(){
        for (Enemy e:enemies){
            updateEnemyMove(e);
        }
    }

    public void updateEnemyMove(Enemy e) {

        if (e.getLastDir() == -1){
            setNewDirectionAndMove(e);
        }

        int newX = (int)(e.getX() + getSpeedX(e.getLastDir()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDir()));

        if (getTileType(newX, newY) == ROAD_TILE){
            e.move(speed, e.getLastDir());
        } else if(isEnd(e)){
            System.out.println("Game Set!");

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
            int newY = (int)(e.getY() + getSpeedY(UP));
            if (getTileType((int)e.getX(), newY) == ROAD_TILE){
                e.move(speed, UP);
            } else {
                e.move(speed, DOWN);
            }
        } else {
            int newX = (int)(e.getX() + getSpeedX(RIGHT));
            if (getTileType(newX, (int)e.getY()) == ROAD_TILE){
                e.move(speed, RIGHT);
            } else {
                e.move(speed, LEFT);
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

    private boolean isEnd(Enemy e) {
        return e.getX() == end.getxCord() * 32 && e.getY() == end.getyCord() * 32;
    }

    private int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }

    private float getSpeedY(int dir) {
        if (dir == UP){
            return -speed;
        } else if (dir == DOWN){
            return speed + 32;
        }
        return 0;
    }

    private float getSpeedX(int dir) {
        if (dir == LEFT)
			return -speed;
		else if (dir == RIGHT)
			return speed + 32;

		return 0;
    }

    public void addEnemy(int enemyType){
        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;
        switch (enemyType){
            case ORC:
                enemies.add(new Orc(x, y, 0));
                break;
            case BAT:
                enemies.add(new Bat(x, y, 0));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y, 0));
                break;
            case WOLF:
                enemies.add(new Wolf(x, y, 0));
                break;
        }
    }

    public void draw(Graphics g){
        for (Enemy e:enemies){
            drawEnemy(e, g);
        }
    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }
}
