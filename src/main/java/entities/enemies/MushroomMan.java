package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class MushroomMan extends Enemy{

    public MushroomMan(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, MUSHROOM_MAN, em);
    }


}
