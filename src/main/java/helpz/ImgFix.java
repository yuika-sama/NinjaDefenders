package helpz;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImgFix {
    public static BufferedImage getRotImg(BufferedImage img, int rotAngle) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage new_img = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = new_img.createGraphics();

        g2d.rotate(Math.toRadians(rotAngle), (double) w / 2, (double) h / 2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return new_img;
    }

    public static BufferedImage buildImg(BufferedImage[] imgs) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage new_img = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D g2d = new_img.createGraphics();

        for (BufferedImage i : imgs) {
            g2d.drawImage(i, 0, 0, null);
        }
        g2d.dispose();
        return new_img;
    }

    public static BufferedImage getBuildRotImg(BufferedImage[] imgs, int rotAngle, int rotAtIndex) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage new_img = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D g2d = new_img.createGraphics();

        for (int i = 0; i < imgs.length; i++) {
            if (rotAtIndex == i) {
                g2d.rotate(Math.toRadians(rotAngle), (double) w / 2, (double) h / 2);
            }
            g2d.drawImage(imgs[i], 0, 0, null);
            if (rotAtIndex == i) {
                g2d.rotate(Math.toRadians(-rotAngle), (double) w / 2, (double) h / 2);
            }
        }

        g2d.dispose();
        return new_img;
    }

    //rotate second image only
    public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs, BufferedImage secondImg, int rotAngle) {
        int w = secondImg.getWidth();
        int h = secondImg.getHeight();

        BufferedImage[] arr = new BufferedImage[imgs.length];

        for (int i = 0; i < imgs.length; i++) {
            BufferedImage new_img = new BufferedImage(w, h, imgs[0].getType());
            Graphics2D g2d = new_img.createGraphics();
            g2d.drawImage(imgs[i], 0, 0, null);
            g2d.rotate(Math.toRadians(rotAngle), (double) w / 2, (double) h / 2);
            g2d.drawImage(secondImg, 0, 0, null);
            g2d.dispose();
            arr[i] = new_img;
        }
        return arr;
    }
}
