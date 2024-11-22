package com.mycompany.towerdefense;

import input.MyKeyboardListener;
import input.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private final Game game;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    private void setPanelSize() {
        Dimension dms = new Dimension(800, 640);
        setMinimumSize(dms);
        setPreferredSize(dms);
        setMaximumSize(dms);
    }

    public void initInput() {
        MyMouseListener myMouseListener = new MyMouseListener(game);
        MyKeyboardListener myKeyboardListener = new MyKeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(myKeyboardListener);

        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.getRender().render(g);
    }
}
