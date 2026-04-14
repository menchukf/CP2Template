package com.example;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class KeyHandler implements KeyListener{
    public boolean jumpPressed, leftPressed, rightPressed, jabPressed, shieldPressed;
    public KeyHandler(){

    }
    public void keyTyped(KeyEvent e){

    }
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            jumpPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            jumpPressed = true;
        }
        if(code == KeyEvent.VK_S){
            jabPressed = true;
        }
    }
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            jumpPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            jumpPressed = false;
        }
        if(code == KeyEvent.VK_S){
            jabPressed = false;
        }
    }
}
