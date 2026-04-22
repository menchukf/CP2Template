package com.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    	KeyHandler keyH = new KeyHandler();
        JFrame window = new JFrame();
        window.addKeyListener(keyH);
        Game game = new Game(keyH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allow us to close the window by clicking "X" botton
        window.setTitle("hood fighter");
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        GameGraphic graphic = new GameGraphic(game, true);
        window.add(graphic);
        graphic.startGraphicThread();
        window.pack();
        window.setVisible(true);
    }
}

