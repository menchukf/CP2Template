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
	public int xPos;
    public int yPos;
    public int yVelocity = 0;
    public int xVelocity = 0;
    public int hp = 100;
	public int flow = 1000;
	private Hitbox hitbox = new Hitbox(0,0,0,0);
	private Hitbox attackingHitbox = new Hitbox(xPos,yPos,0,0);
	private int ID = 0;
	private KeyHandler keyH;
	private ArrayList<ArrayList <BufferedImage>> imageSets = new ArrayList<>(8);
	private int actionDuration = 0;//duration remaining of an action in frame
	private int drawInterval = 5;//interval to update sprite, in frames
	private int drawIndex = 0;//used as a timer, +1 every frame, player update, when DrawIndex = DrawInterval-1
	final int g = 1;//acceleration due to gravity
    public enum Status{
        IDLE/* code 0 */, JUMPING/* code 1 */, PUNCHING/* code 2 */, CROUCHING/* code 3 */,
		RUNNING_LEFT/* code 4 */, RUNNING_RIGHT/* code 5 */, BLOCKING/* code 6 */,  
		STUNNED/* code 7 */, DOWN/* code 8 */;
    }
    //player's x/y location with respect to map
	PlayerSprite sprite;
    public Status status = Status.IDLE;
    public Player(int x, int y, KeyHandler k, int ID){
    	this.ID = ID;
		sprite = new PlayerSprite(ID);
    	keyH = k;
        xPos = x;
        yPos = y;
	}
    
	public void updateHitboxes(){
		hitbox.x = xPos+40;
		hitbox.y = yPos+20;
		if(sprite.currentImage() != null){
			hitbox.width = sprite.currentImage().getWidth()-80;
			hitbox.height = sprite.currentImage().getHeight()-40;
			if(status == Status.CROUCHING){
				hitbox.y = yPos;
				hitbox.height = sprite.currentImage().getHeight()/2;
			}
			if(status == Status.PUNCHING){
				attackingHitbox.x = hitbox.x + hitbox.width;
				attackingHitbox.y = hitbox.y + hitbox.height/3*2+5;
				attackingHitbox.height = 20;
				attackingHitbox.width = 40;
				attackingHitbox.vanished = false;
				if(actionDuration <25 && actionDuration >= 5){//damaging frame
					attackingHitbox.width = 80;
				}
			}
		}
		else{
			hitbox.width = 200;
			hitbox.height = 300;
		}
		
	}
    public void update() {
		if(actionDuration != 0){
			actionDuration--;
		}
		else if(actionDuration == 0){//a duration based action
			if(status == Status.PUNCHING){
				sprite.yoMyStatusChangedTo(status = Status.IDLE);
				attackingHitbox.vanish();
			}
		}
		if(keyH == null) {//no key pressed, no action
			updateHitboxes();
    		return;
    	}
		//if movement key released: change the status
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
		if (flow >= 0){
			//movement
			if(keyH.leftPressed){
				xVelocity = -2;
				flow -= 2;
				if(status != Status.JUMPING && status == Status.IDLE)
					sprite.yoMyStatusChangedTo(status = Status.RUNNING_LEFT);
			}
			if(keyH.rightPressed){
				xVelocity = 2;
				flow -= 2;
				if(status != Status.JUMPING && status == Status.IDLE)
					sprite.yoMyStatusChangedTo(status = Status.RUNNING_RIGHT);
			}
			xPos += xVelocity;
			if(status == Status.IDLE && keyH.jumpPressed) {//jump
				sprite.yoMyStatusChangedTo(status = Status.JUMPING);
				drawInterval = 10;
				drawIndex = 0;
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
					drawInterval = 5;
					drawIndex = 0;
					actionDuration = 15;
					flow -= 300;
				}
			}
		}
        //denfense
        if(keyH.crouchPressed){
			sprite.yoMyStatusChangedTo(status = Status.CROUCHING);
	    }
        if(!keyH.crouchPressed && status == Status.CROUCHING){
			sprite.yoMyStatusChangedTo(status = Status.IDLE);
	    }
		if(status == Status.IDLE){
			flow += 10;
		}
		if(status == Status.BLOCKING || status == Status.CROUCHING){
			flow += 5;
		}
		//update sprite:
		if(drawIndex == drawInterval-1){
			sprite.update();
			drawIndex = 0;
		}
		else{
			drawIndex++;
		}
		
		updateHitboxes();
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        BufferedImage image = sprite.currentImage();
        if(image != null) {
        	g2.drawImage(image, xPos, 500-(image.getHeight()+yPos + 50), image.getWidth(), image.getHeight(), null);
        }
        else {
        	g2.drawRect(xPos, 500/* screen height */ - (200 /* height */ + yPos + 50/*ground level*/), 200, 200);
        }
        if(hitbox.visible) {
        	g2.drawRect(hitbox.x, 500/* screen height */- (hitbox.height + hitbox.y + 50/*ground level*/)/*ground level*/, hitbox.width, hitbox.height);
        }
		if(!attackingHitbox.vanished){
			g2.drawRect(attackingHitbox.x, 500/* screen height */- (attackingHitbox.height + attackingHitbox.y + 50/*ground level*/)/*ground level*/, attackingHitbox.width, attackingHitbox.height);
		}
    }
    
}