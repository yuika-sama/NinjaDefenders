package enemies;

import static helpz.Constants.Enemies.*;

public class Wolf extends Enemy{
    public Wolf(float x, float y, int ID) {
        super(x, y, ID, WOLF);
        hp = 50;
    }
}
