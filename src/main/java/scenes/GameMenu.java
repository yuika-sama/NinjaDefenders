package scenes;

import com.mycompany.towerdefense.Game;
import com.mycompany.towerdefense.GameState;
import input.MyKeyboardListener;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static com.mycompany.towerdefense.GameState.*;

public class GameMenu extends GameScene implements SceneMethods{
    private Random random;
    private BufferedImage img;
    private final ArrayList<BufferedImage> sprites = new ArrayList<>();

    private MyButton bPlaying, bSetting, bEdit, bQuit;

    public GameMenu(Game game) {
        super(game);
        random = new Random();
        importImg();
        loadSprite();
        initButtons();
    }

    private void initButtons() {
        int w =150;
        int h = w/3;
        int x = 640/2 - w/2;
        int y = 150;
        int yOffset =100;
        bPlaying = new MyButton("Play", x, y, w,  h);
        bEdit = new MyButton("Edit", x, y + yOffset, w, h);
        bSetting = new MyButton("Setting", x, y + yOffset*2, w, h);
        bQuit = new MyButton("Quit", x, y+yOffset*3, w, h);
    }

    @Override
    public void render(Graphics g) {
        drawButton(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)){
            setGameStates(PLAYING);
        } else if (bSetting.getBounds().contains(x, y)){
            setGameStates(SETTING);
        } else if (bQuit.getBounds().contains(x, y)){
            System.exit(0);
        } else if (bEdit.getBounds().contains(x, y)){
            setGameStates(EDIT);
        }
    }

    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bSetting.setMouseOver(false);
        bQuit.setMouseOver(false);
        if (bPlaying.getBounds().contains(x, y)){
            bPlaying.setMouseOver(true);
        } else if (bSetting.getBounds().contains(x, y)){
            bSetting.setMouseOver(true);
        } else if (bQuit.getBounds().contains(x, y)){
            bQuit.setMouseOver(true);
        } else if (bEdit.getBounds().contains(x, y)){
            bEdit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)){
            bPlaying.setMousePressed(true);
        } else if (bSetting.getBounds().contains(x, y)){
            bSetting.setMousePressed(true);
        } else if (bQuit.getBounds().contains(x, y)){
            bQuit.setMousePressed(true);
        } else if (bEdit.getBounds().contains(x, y));
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bSetting.resetBooleans();
        bQuit.resetBooleans();
        bEdit.resetBooleans();
    }

    private void drawButton(Graphics g) {
        bPlaying.draw(g);
        bSetting.draw(g);
        bQuit.draw(g);
        bEdit.draw(g);
    }

    private void loadSprite() {
        for (int y=0; y<3; y++){
            for (int x=0; x<10; x++){
                sprites.add(img.getSubimage(32*x, 32*y, 32, 32));
            }
        }
    }
    private int getRndInt(){
        return random.nextInt(sprites.size());
    }
    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/spriteatlas.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
