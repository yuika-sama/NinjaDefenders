package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class GreenSlime extends Enemy{
    public GreenSlime(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, GREEN_SLIME, em);
    }
}
