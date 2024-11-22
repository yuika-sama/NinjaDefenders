package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class BigGreenSlime extends Enemy {
    public BigGreenSlime(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, BIG_GREEN_SLIME, enemyManager);
    }
}
