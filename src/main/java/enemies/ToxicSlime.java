package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class ToxicSlime extends Enemy{

    public ToxicSlime(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, TOXIC_SLIME, em);
    }


}
