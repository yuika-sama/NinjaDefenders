package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class DemonGreen extends Enemy{

    public DemonGreen(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, DEMON_GREEN, enemyManager);
    }


}
