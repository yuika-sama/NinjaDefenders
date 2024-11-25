package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class DarkGreenSlime extends Enemy{

    public DarkGreenSlime(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, DARK_GREEN_SLIME, enemyManager);
    }

    
}
