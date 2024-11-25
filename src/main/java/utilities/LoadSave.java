package utilities;

import entities.objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    private static final String folderPath = "src/main/resources/";

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img;
        try (InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png")) {
            if (is == null) {
                System.out.println("spriteatlas.png not found in resources.");
                return null;
            }
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Error reading sprite atlas", e);
        }
        return img;
    }

    public static BufferedImage getSpriteByName(String name, int spriteType) {
        BufferedImage img;
        if (spriteType == 1){
            name = "enemies/StandardEnemies/" + name + ".png";
        } else if (spriteType == 2){
            name = "enemies/SpecialEnemies/" + name + ".png";
        } else if (spriteType == 3){
            name = "turrets/" + name + ".png";
        } else if (spriteType == 4){
            name += ".png";
        } else{
            return null;
        }
        try (InputStream is = LoadSave.class.getClassLoader().getResourceAsStream(name)) {
            if (is == null) {
                System.out.println(name + " is not found in resources.");
                return null;
            }
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Error reading sprite atlas", e);
        }
        return img;
    }

    public static BufferedImage[] loadAnimFrames(BufferedImage spriteSheet, int spriteType) {
        BufferedImage[] frames = new BufferedImage[17];
        if (spriteType == 1) {
           for (int y=0; y<4; y++){
               for (int x = 0; x < 3; x++){
                   int id = y*3 + x;
                   frames[id] = spriteSheet.getSubimage(x * 32, y*32, 32, 32);
               }
           }
           frames[12] = spriteSheet.getSubimage(32 * 3, 0, 32, 32);
        } else if (spriteType == 2) {;
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    int id = x * 4 + y;
                    frames[id] = spriteSheet.getSubimage(x * 32, y * 32, 32, 32);
                }
            }
            frames[16] = spriteSheet.getSubimage(32 * 4, 0, 32, 32);
        } else if (spriteType == 3){
            int id = 0;
            for (int x = 0; x < 4; x++){
                frames[id++] = spriteSheet.getSubimage(32 * x, 0, 32, 32);
            }
            for (int x = 0; x<4; x++){
                for (int y=1; y<3; y++){
                    frames[id++] = spriteSheet.getSubimage(32 * x, 32*y, 32, 32);
                }
            }
            frames[12] = spriteSheet.getSubimage(32 * 4, 0, 32, 32);
        }
        else {
            throw new IllegalArgumentException("Unknown spriteType: " + spriteType);
        }

        return frames;
    }



    public static int[][] GetLevelData(String name) {
        File txtFile = new File(folderPath + name + ".txt");
        if (txtFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(txtFile);
            return Utils.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println(name + ".txt does not exist");
            return new int[0][0];
        }
    }

    public static void CreateLevel(String name, int[] idArr, PathPoint pStart, PathPoint pEnd) {
        File newLevel = new File(folderPath + name + ".txt");
        if (newLevel.exists()) {
            System.out.println("File " + name + " already exists");
        } else {
            try {
                if (newLevel.createNewFile()) {
                    WriteToFile(newLevel, idArr, pStart, pEnd);
                    System.out.println("File " + name + " created successfully.");
                } else {
                    System.out.println("Failed to create file " + name);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating new level file", e);
            }
        }
    }

    private static void WriteToFile(File f, int[] idArr, PathPoint pStart, PathPoint pEnd) {
        try (PrintWriter pw = new PrintWriter(f)) {
            for (int i : idArr) {
                pw.println(i);
            }
            pw.println(pStart.getxCord());
            pw.println(pStart.getyCord());
            pw.println(pEnd.getxCord());
            pw.println(pEnd.getyCord());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found while writing", e);
        }
    }

    public static void SaveLevel(String name, int[][] idArr, PathPoint pStart, PathPoint pEnd) {
        File txtFile = new File(folderPath + name + ".txt");
        if (txtFile.exists()) {
            WriteToFile(txtFile, Utils._2DIntTo1DInt(idArr), pStart, pEnd);
            System.out.println("Saved");
        } else {
            System.out.println(name + ".txt is not exists");
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isEmpty()) {
                    list.add(Integer.parseInt(line));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found while reading", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing level data", e);
        }
        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoint(String name) {
        File txtFile = new File(folderPath + name + ".txt");
        if (txtFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(txtFile);
            ArrayList<PathPoint> points = new ArrayList<>();

            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println(name + ".txt does not exist");
            return null;
        }
    }
}
