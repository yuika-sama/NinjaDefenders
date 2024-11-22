package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class Spirit extends Enemy{

    public Spirit(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, SPIRIT, em);
    }


}
