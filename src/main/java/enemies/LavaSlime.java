package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class LavaSlime extends Enemy{
    public LavaSlime(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, LAVA_SLIME, em);
    }
}
