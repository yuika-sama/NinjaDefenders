package com.mycompany.towerdefense;


import helpz.LoadSave;
import managers.TileManager;
import objects.PathPoint;
import scenes.Editing;
import scenes.GameMenu;
import scenes.Playing;
import scenes.Setting;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Game extends JFrame implements Runnable {
    private final double UPS_SET = 60.0;
    private final double FPS_SET = 120.0;
    private BufferedImage img;

    private GameScreen gameScreen;
    private Render render;
    private GameMenu menu;
    private Playing playing;
    private Setting setting;
    private Editing editing;

    private TileManager tileManager;
    private PathPoint pEnd, pStart;

    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initClasses();
        createDefaultLevel();

        add(gameScreen);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInput();
        game.start();
    }

    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        playing = new Playing(this);
        setting = new Setting(this);
        menu = new GameMenu(this);
        editing = new Editing(this);
    }

    private void start() {
        Thread gameThread = new Thread(this) {};
        gameThread.start();
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        LoadSave.CreateLevel("new_level", arr, pStart, pEnd);
    }


    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;
        long now;

        while (true) {
            //render
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }
            //update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = System.nanoTime();
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    private void updateGame() {
        switch (GameState.gameStates){
            case PLAYING:
                playing.update();
                break;
            case MENU:
                break;
            case EDIT:
                break;
            case SETTING:
                break;
            default:
                break;
        }
    }

    public Render getRender() {
        return render;
    }

    public GameMenu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Setting getSetting() {
        return setting;
    }

    public Editing getEditor() {
        return editing;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}
