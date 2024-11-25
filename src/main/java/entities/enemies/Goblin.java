package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class Goblin extends Enemy{

    public Goblin(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, GOBLIN, enemyManager);
    }


}
