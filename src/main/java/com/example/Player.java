package com.example;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player{
	private Hitbox hitbox;
	private int ID = 0;
	private KeyHandler keyH;
	private ArrayList<ArrayList <BufferedImage>> imageSets = new ArrayList<>(8);
	private int actionDuration = 0;//duration remaining of an action in frame
	private int CD = 0;//duration remaing of cool down to excecute anouther action, in frames
	private long timeLastDraw = System.currentTimeMillis();//time since last sprite was drawn, in miliseconds
	private int drawingInterval = 500;//interval to update sprite, in miliseconds
	final int g = 1;//acceleration due to gravity
    public enum Status{
        IDLE/* code 0 */, JUMPING/* code 1 */, PUNCHING/* code 2 */, CROUCHING/* code 3 */,
		RUNNING_LEFT/* code 4 */, RUNNING_RIGHT/* code 5 */, BLOCKING/* code 6 */,  
		STUNNED/* code 7 */, DOWN/* code 8 */;
    }
	private int statusInInt = 0;
    //player's x/y location with respect to map
    public int xPos;
    public int yPos;
    public int yVelocity = 0;
    public int xVelocity = 0;
    public int hp = 100;
	PlayerSprite sprite;
    public Status status = Status.IDLE;
    public Player(int x, int y, KeyHandler k, int ID){
    	this.ID = ID;
		sprite = new PlayerSprite(ID);
    	keyH = k;
        xPos = x;
        yPos = y;
        hitbox = new Hitbox(x, y, sprite.currentImage().getWidth(), sprite.currentImage().getHeight());                                                
		timeLastDraw = System.currentTimeMillis();
	}
    
	public void updateHitboxes(){
	}
    public void update() {
    	if(keyH == null) {//no key pressed, no action
    		return;
    	}
        if(CD != 0){// on cooldown no action
            return;
        }
    	//movement
		if(keyH.leftPressed){
			xVelocity = -2;
		    if(status != Status.JUMPING)
		    	sprite.yoMyStatusChangedTo(status = Status.RUNNING_LEFT);
	    }
		if(keyH.rightPressed){
			xVelocity = 2;
		    if(status != Status.JUMPING)
		    	sprite.yoMyStatusChangedTo(status = Status.RUNNING_RIGHT);
	    }
	    xPos += xVelocity;
	    if(!keyH.leftPressed) {
			xVelocity = 0;
	    	if(status == Status.RUNNING_LEFT)
	    		sprite.yoMyStatusChangedTo(status = Status.IDLE);
	    }
	    if(!keyH.rightPressed) {
			xVelocity = 0;
	    	if(status == Status.RUNNING_RIGHT)
	    		sprite.yoMyStatusChangedTo(status = Status.IDLE);
	    	
	    }
	    if(status == Status.IDLE && keyH.jumpPressed) {//jump
	    	sprite.yoMyStatusChangedTo(status = Status.JUMPING);
			drawingInterval = 100;
			yVelocity = 15;
		}
	    if(yPos == 0 && yVelocity < 0){//landing
            sprite.yoMyStatusChangedTo(status = Status.IDLE);
            yVelocity = 0;
        }
	    if(status == Status.JUMPING){//in air
	    	yPos += yVelocity;
            yVelocity = yVelocity - g;
        }
        //attack
	    if(status != Status.JUMPING) {
	        if(keyH.jabPressed){
				sprite.yoMyStatusChangedTo(status = Status.PUNCHING);
		    }
	        if(!keyH.jabPressed && status == Status.PUNCHING){
				sprite.yoMyStatusChangedTo(status = Status.IDLE);
		    }
	    }
        //denfense
        if(keyH.crouchPressed){
			status = Status.CROUCHING;
	    }
        if(!keyH.crouchPressed && status == Status.CROUCHING){
			status = Status.IDLE;
	    }
		//update sprite:
		if(System.currentTimeMillis()-timeLastDraw >= drawingInterval){
			timeLastDraw = System.currentTimeMillis();
			sprite.update();
		}
		updateHitboxes();
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        BufferedImage image = sprite.currentImage();
        //System.out.println(xPos + ", " + yPos);
        if(image != null) {
        	g2.drawImage(image, xPos, 500-(image.getHeight()+yPos + 50), image.getWidth(), image.getHeight(), null);
        }
        else {
        	g2.drawRect(xPos, 500/* screen height */ - (200 /* height */ + yPos + 50/*ground level*/), 200, 200);
        }
        if(hitbox.visible) {
        	g2.drawRect(hitbox.x, 500/* screen height */- (hitbox.height + hitbox.y + 50/*ground level*/)/*ground level*/, hitbox.width, hitbox.height);
        }
    }
    
}