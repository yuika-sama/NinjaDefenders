package entities.enemies;

import managers.EnemyManager;

import static utilities.Constants.Monsters.*;

public class RobotGreen extends Enemy{

    public RobotGreen(float x, float y, int ID, EnemyManager em) {
        super(x, y, ID, ROBOT_GREEN, em);
    }


}
