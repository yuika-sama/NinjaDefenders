package managers;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {

    public Tile ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R;
    public Tile WATER, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, R_WATER, B_WATER, L_WATER, T_WATER;
    public Tile TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE;
    public Tile GRASS;
    public BufferedImage atlas;

    public ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Tile> roadS = new ArrayList<>(); //
    public ArrayList<Tile> roadC = new ArrayList<>(); //road corner
    public ArrayList<Tile> corners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        creatingTiles();
    }

    private void creatingTiles() {
        int id = 0;
        //Default
        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, "Grass"));
        tiles.add(WATER = new Tile(getSprite(0, 0), id++, "Water"));
        //Water
        beaches.add(T_WATER = new Tile(ImgFix.buildImg(getImgs(0, 0, 6, 0)), id++, "T_Water"));
        beaches.add(R_WATER = new Tile(ImgFix.getRotImg(getSprite(6, 0), 90), id++, "R_Water"));
        beaches.add(B_WATER = new Tile(ImgFix.getRotImg(getSprite(6, 0), 180), id++, "B_Water"));
        beaches.add(L_WATER = new Tile(ImgFix.getRotImg(getSprite(6, 0), 270), id++, "L_Water"));
        //Water corner
        corners.add(BL_WATER_CORNER = new Tile(ImgFix.buildImg(getImgs(0, 0, 5, 0)), id++, "Bl_Water_Corner"));
        corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 5, 0), 90, 1), id++, "Tl_Water_Corner"));
        corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 5, 0), 180, 1), id++, "Tr_Water_Corner"));
        corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 5, 0), 270, 1), id++, "Br_Water_Corner"));
        //Road straight
        roadS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, "Road_LR"));
        roadS.add(ROAD_TB = new Tile(ImgFix.getRotImg(getSprite(8, 0), 90), id++, "Tb_road"));
        //Road corner
        roadC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, "Road_Bottom_To_Right"));
        roadC.add(ROAD_L_TO_B = new Tile(ImgFix.getRotImg(getSprite(7, 0), 90), id++, "Road_Left_To_Bottom"));
        roadC.add(ROAD_L_TO_T = new Tile(ImgFix.getRotImg(getSprite(7, 0), 180), id++, "Road_Left_To_Top"));
        roadC.add(ROAD_T_TO_R = new Tile(ImgFix.getRotImg(getSprite(7, 0), 270), id++, "Road_Top_To_Right"));
        //Isle
        islands.add(TL_ISLE = new Tile(ImgFix.buildImg(getImgs(0, 0, 4, 0)), id++, "TL_Isle"));
        islands.add(TR_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 4, 0), 90, 1), id++, "Tr_Isle"));
        islands.add(BR_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 4, 0), 180, 1), id++, "Br_Isle"));
        islands.add(BL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0, 0, 4, 0), 270, 1), id++, "Bl_Isle"));

        tiles.addAll(beaches);
        tiles.addAll(corners);
        tiles.addAll(roadS);
        tiles.addAll(roadC);
        tiles.addAll(islands);
    }

    private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[]{getSprite(firstX, firstY), getSprite(secondX, secondY)};
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    public ArrayList<Tile> getRoadS() {
        return roadS;
    }

    public ArrayList<Tile> getRoadC() {
        return roadC;
    }

    public ArrayList<Tile> getCorners() {
        return corners;
    }

    public ArrayList<Tile> getBeaches() {
        return beaches;
    }

    public ArrayList<Tile> getIslands() {
        return islands;
    }
}
