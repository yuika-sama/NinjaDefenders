package helpz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    private static final String folderPath = "src/main/resources/";

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
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

    public static int[][] GetLevelData(String name) {
        File txtFile = new File(folderPath + name + ".txt");
        if (txtFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(txtFile);
            return Utils.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println(name + ".txt does not exist");
            return new int[0][0]; // Returning empty array instead of null
        }
    }

    public static void CreateLevel(String name, int[] idArr) {
        File newLevel = new File(folderPath + name + ".txt");
        if (newLevel.exists()) {
            System.out.println("File " + name + " already exists");
        } else {
            try {
                if (newLevel.createNewFile()) {
                    WriteToFile(newLevel, idArr);
                    System.out.println("File " + name + " created successfully.");
                } else {
                    System.out.println("Failed to create file " + name);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating new level file", e);
            }
        }
    }

    private static void WriteToFile(File f, int[] idArr) {
        try (PrintWriter pw = new PrintWriter(f)) {
            for (int i : idArr) {
                pw.println(i);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found while writing", e);
        }
    }

    public static void SaveLevel(String name, int[][] idArr) {
        File txtFile = new File(folderPath + name + ".txt");
        if (txtFile.exists()) {
            WriteToFile(txtFile, Utils._2DIntTo1DInt(idArr));
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
}
