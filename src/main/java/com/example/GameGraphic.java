package com.example;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GameGraphic extends JPanel implements Runnable{
    private Game game;
    private boolean playerControlled;//true for player 1 and false for player 2
    final int panelWidth = 1000;
    final int paneHeight = 500;
    private Thread graphicsThread;
    public GameGraphic(Game game, boolean myPlayer){
        this.game = game;
        this.setPreferredSize(new Dimension(panelWidth, paneHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);//no idea what this does
    }
    public void startGraphicThread(){
    	System.out.println("Graphic started");
    	graphicsThread = new Thread(this);
        graphicsThread.start();
    }

    public void run(){
        while(graphicsThread != null){
            double currentTime = System.nanoTime()/1000000.0;//current time in milisecond
            double interval = 1000.0/60.0;
            double nextDrawTime =  currentTime + interval;
            
            game.update();
            repaint();
            long sleepTime = (long) (nextDrawTime - System.nanoTime()/1000000.0);//this is negative some time for some reason
            try{
                Thread.sleep(sleepTime);
            }
           catch(InterruptedException e){
               System.err.println("WE CAN CRY NOW BECAUSE WE SHOULDN'T GET HERE");
            }

        }
    }

    public void update(){//logic
        game.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        game.player1().paintComponent(g);
    }
}