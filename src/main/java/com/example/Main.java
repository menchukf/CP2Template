package com.example;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allow us to close the window by clicking "X" botton
        window.setTitle("hood fighter");
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        GameGraphic graphic = new GameGraphic(g);
        window.add(graphic);
        window.pack();
        window.setVisible(true);
        graphic.startGameThread();
    }
}