package com.example;
import javax.swing.*;
import com.example.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GameGraphic extends JPanel implements Runnable{
    private Game game;
    private int playerX = 100;
    private int playerY = 100;
    final int panelWidth = 800;
    final int paneHeight = 400;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public GameGraphic(Game game){
        this.game = game;
        this.setPreferredSize(new Dimension(panelWidth, paneHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);//no idea what this does
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        while(gameThread == null){
            long currentTime = System.nanoTime();
            System.out.println("The current time is "+ currentTime);
            System.out.println("The game loop is running");
            update();

            repaint();
            try{
                Thread.sleep(1000000000/60);
            }
            catch(InterruptedException e){
                System.err.println("WE CAN CRY NOW BECAUSE WE SHOULDN'T GET HERE");
            }
            //a billion nanosecond is a second, divide by the FPS is the update interval

        }
    }

    public void update(){//logic
        if(keyH.leftPressed){
            playerX--;
        }
        if(keyH.rightPressed){
            playerX++;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /* 
        Graphics2D g2 = new Graphics2D(g);
        g2.drawRect(200, 200, 50, 50);
        g2.dispose();
        */
    }
}
