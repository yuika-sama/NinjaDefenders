package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class MushroomMan extends Enemy{

    public MushroomMan(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, MUSHROOM_MAN, em);
    }


}
