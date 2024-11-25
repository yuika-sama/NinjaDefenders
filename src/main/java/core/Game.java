package core;

import utilities.LoadSave;
import managers.TileManager;
import entities.objects.PathPoint;
import scenes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main Game class that initializes the game and handles game loop logic.
 */
public class Game extends JFrame implements Runnable {
    // Constants for updates per second (UPS) and frames per second (FPS)
    private static final double UPS_SET = 60.0;
    private static final double FPS_SET = 120.0;

    // Game assets and components
    private BufferedImage img;
    private GameScreen gameScreen;
    private Render render;
    private GameMenu menu;
    private Playing playing;
    private Setting setting;
    private Editing editing;
    private GameOver gameOver;
    private TileManager tileManager;
    private Victory victory;
    private PathPoint pEnd, pStart;

    // Constructor
    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initClasses();
        createDefaultLevel();

        add(gameScreen);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        setTitle("Ninja defenders");

        ImageIcon img = new ImageIcon("src/main/resources/gameIcon.png");
        setIconImage(img.getImage());
    }

    /**
     * Initializes all necessary game classes and components.
     */
    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        playing = new Playing(this);
        setting = new Setting(this);
        menu = new GameMenu(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);
        victory = new Victory(this);
    }

    /**
     * Creates a default level with a basic configuration.
     */
    private void createDefaultLevel() {
        int[] arr = new int[400];
        // Initialize level data with default values
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        LoadSave.CreateLevel("new_level", arr, pStart, pEnd);
    }

    // Entry point of the game
    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInput();
        game.start();
    }

    /**
     * Starts the game thread.
     */
    private void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Main game loop: handles updating and rendering.
     */
    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        while (true) {
            long now = System.nanoTime();

            // Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            // Output FPS and UPS every second
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    /**
     * Updates the game state based on the current game mode.
     */
    private void updateGame() {
        switch (GameState.gameStates) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
            case EDIT:
            case SETTING:
            case GAME_OVER:
                // Add specific updates for other states if necessary
                break;
            case VICTORY:
                break;
            default:
                break;
        }
    }

    // Getters for game components
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

    public GameOver getGameOver() {
        return gameOver;
    }

    public Victory getVictory(){
        return victory;
    }
}
