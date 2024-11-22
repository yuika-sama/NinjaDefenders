package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class PinkSlime extends Enemy{

    public PinkSlime(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, PINK_SLIME, em);
    }


}
