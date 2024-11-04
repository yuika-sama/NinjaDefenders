package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage sprite;
    private final String name;
    private final int id;

    public Tile(BufferedImage sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getSprite() {
        return sprite;
    }


}
