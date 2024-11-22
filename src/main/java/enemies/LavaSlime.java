package enemies;

import static helpz.Constants.Monsters.*;

public class LavaSlime extends Enemy{
    public LavaSlime(float x, float y, int ID) {
        super(x, y, ID, LAVA_SLIME);
    }
}
