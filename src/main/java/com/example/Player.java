package com.example;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player{
	private Hitbox hitbox;
	private int ID = 0;
	private KeyHandler keyH;
	private BufferedImage idle_right, idle_left, jump1, jump2, jump3, jab1, jab2, right1, right2, right3, right4, left1, left2, left3, left4, crouch;
	private int actionDuration = 0;//duration remaining of an action in frame
	private int CD = 0;//duration remaing of cool down to excecute anouther action, in frames
	private int spriteCount = 0;//the index of the current frame of an animation, determine which image get paint.
	private int spriteMax = 1;//the amount of frame in a set action, used to loop through though a set of image, depends on player status
	private int spirteFrameCount = 0;//the frame past since last spirt update
	private int spirtePeriod = 1;//how long, in frame(depends on the status) player update its image
	final int g = 1;
    public enum Status{
        IDLE, JUMPING, PUNCHING, KICKING, BLOCKING, RUNNING_LEFT, RUNNING_RIGHT, STUNNED, DOWN, CROUCHING;
    }
    //player's x/y location with respect to map
    public int xPos;
    public int yPos;
    public int yVelocity = 0;
    public int xVelocity = 0;
    public int hp = 100;
    public Status status = Status.IDLE;
    public Player(int x, int y, KeyHandler k, int ID){
    	try {
			String path = "/workspaces/HoodFighterTesting/src/main/java/com/example/player/";
        	idle_right = ImageIO.read(new File(path+"idle.png"));
        	idle_left = ImageIO.read(new File(path+"reverseIdle.png"));
        	jump1 = ImageIO.read(new File(path+"jump1.png"));
        	jump2 = ImageIO.read(new File(path+"jump2.png"));
        	jump3 = ImageIO.read(new File(path+"jump3.png"));
        	jab1 = ImageIO.read(new File(path+"jab1.png"));
        	jab2 = ImageIO.read(new File(path+"jab2.png"));
        	crouch = ImageIO.read(new File(path+"crouch.png"));
        	left1 = ImageIO.read(new File(path+"left1.png"));
        	left2 = ImageIO.read(new File(path+"left2.png"));
        	left3 = ImageIO.read(new File(path+"left3.png"));
        	left4 = ImageIO.read(new File(path+"left4.png"));
        	right1 = ImageIO.read(new File(path+"right1.png"));
        	right2 = ImageIO.read(new File(path+"right2.png"));
        	right3 = ImageIO.read(new File(path+"right3.png"));
        	right4 = ImageIO.read(new File(path+"right4.png"));
        }
        catch(IOException e) {
        	System.err.println("image misteriously vanished");
        }
    	this.ID = ID;
    	keyH = k;
        xPos = x;
        yPos = y;
        hitbox = new Hitbox(x, y, getCurrentSprite().getWidth(), getCurrentSprite().getHeight());                                                
    }
    
    public BufferedImage getCurrentSprite() {
    	BufferedImage imageReturned = null;
    	if(status == Status.IDLE) {
    		//System.out.println("IDLE");
    		if(spriteCount == 0) {
    			imageReturned = idle_right;
    		}
    	}
    	if(status == Status.PUNCHING) {
    		//System.out.println("PUNCH");
    		if(spriteCount == 0) {
    			imageReturned = jab2;
    		}
    	}
    	if(status == Status.CROUCHING) {
    		//System.out.println("crouch");
    		if(spriteCount == 0) {
    			imageReturned = crouch;
    		}
    	}
    	if(status == Status.JUMPING) {
    		//System.out.println("JUMP");
    		if(spriteCount == 0) {
    			System.out.println("JUMPING");
    			imageReturned = jump3;
    		}
    	}
		if(status == Status.RUNNING_LEFT){
			if(spriteCount == 0) {
    			System.out.println("JUMPING");
    			imageReturned = jump3;
    		}
		}
    	return imageReturned;
    }
	public void updateHitboxes(){
		hitbox.updatePos(xPos+25, yPos+20);
		hitbox.width = getCurrentSprite().getWidth()-50;
		if(status == Status.CROUCHING){
			hitbox.height = getCurrentSprite().getHeight()-128;
			hitbox.updatePos(xPos+25, yPos + 64);
			return;
		}
		hitbox.height = getCurrentSprite().getHeight()-40;
	}
    public void updateSprite() {
    	if(spirteFrameCount == spirtePeriod-1) {//updating frame reached
    		//code below update a frame
	    	for (int i = 0; i < spriteMax; i++) {
	    		spriteCount++;
	    		if(i == spriteMax -1) {//last sprite image
	    			spriteCount = 0;
	    		}

	    	}
	    	spirteFrameCount = 0;//reset Counter
    	}
    	else {
    		spirteFrameCount++;//push frame counter
    	}
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
		    	status = Status.RUNNING_LEFT;
	    }
		if(keyH.rightPressed){
			xVelocity = 2;
		    if(status != Status.JUMPING)
		    	status = Status.RUNNING_RIGHT;
	    }
	    xPos += xVelocity;
	    if(!keyH.leftPressed) {
			xVelocity = 0;
	    	if(status == Status.RUNNING_LEFT)
	    		status = Status.IDLE;
	    }
	    if(!keyH.rightPressed) {
			xVelocity = 0;
	    	if(status == Status.RUNNING_RIGHT)
	    		status = Status.IDLE;
	    	
	    }
	    if(status == Status.IDLE && keyH.jumpPressed) {//jump
	    	status = Status.JUMPING;
			yVelocity = 15;
		}
	    if(yPos == 0 && yVelocity < 0){//landing
            status = Status.IDLE;
            yVelocity = 0;
        }
	    if(status == Status.JUMPING){//in air
	    	yPos += yVelocity;
            yVelocity = yVelocity - g;
        }
        //attack
	    if(status != Status.JUMPING) {
	        if(keyH.jabPressed){
				status = Status.PUNCHING;
		    }
	        if(!keyH.jabPressed && status == Status.PUNCHING){
				status = Status.IDLE;
		    }
	    }
        //denfense
        if(keyH.crouchPressed){
			status = Status.CROUCHING;
	    }
        if(!keyH.crouchPressed && status == Status.CROUCHING){
			status = Status.IDLE;
	    }
		updateSprite();
		updateHitboxes();
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        BufferedImage currentSprite = getCurrentSprite();
        //System.out.println(xPos + ", " + yPos);
        if(currentSprite != null) {
        	g2.drawImage(currentSprite, xPos, 500-(currentSprite.getHeight()+yPos + 50), currentSprite.getWidth(), currentSprite.getHeight(), null);
        }
        else {
        	g2.drawRect(xPos, 500/* screen height */ - (200 /* height */ + yPos + 50/*ground level*/), 200, 200);
        }
        if(hitbox.visible) {
        	g2.drawRect(hitbox.x, 500/* screen height */- (hitbox.height + hitbox.y + 50/*ground level*/)/*ground level*/, hitbox.width, hitbox.height);
        }
    }
    
}