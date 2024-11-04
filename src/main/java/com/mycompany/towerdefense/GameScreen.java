package com.mycompany.towerdefense;

import input.MyKeyboardListener;
import input.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private final Game game;
    private Dimension dms;
    private MyMouseListener myMouseListener;
    private MyKeyboardListener myKeyboardListener;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    private void setPanelSize() {
        dms = new Dimension(640, 740);
        setMinimumSize(dms);
        setPreferredSize(dms);
        setMaximumSize(dms);
    }

    public void initInput() {
        myMouseListener = new MyMouseListener(game);
        myKeyboardListener = new MyKeyboardListener(game);

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
