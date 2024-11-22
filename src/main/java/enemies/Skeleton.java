package enemies;

import managers.EnemyManager;

import static helpz.Constants.Monsters.*;

public class Skeleton extends Enemy{

    public Skeleton(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, SKELETON_P, em);
    }


}
