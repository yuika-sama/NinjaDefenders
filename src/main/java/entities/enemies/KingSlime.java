package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class KingSlime extends Enemy{

    public KingSlime(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, KING_SLIME, em);
    }

}
