package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class Tengu extends Enemy{

    public Tengu(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, TENGU, em);
    }


}
