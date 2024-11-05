package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage sprite[];
    private final String name;
    private final int id;

    public Tile(BufferedImage sprite, int id, String name) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.name = name;
    }
    public Tile(BufferedImage[] sprite, int id, String name) {
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
        return sprite[0];
    }

    public BufferedImage getSprite(int anim_id) {
        return sprite[anim_id];
    }

    public boolean isAnim(){
        return sprite.length > 1;
    }
}
