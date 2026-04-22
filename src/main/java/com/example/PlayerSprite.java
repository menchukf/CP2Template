package com.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.example.Player.Status;
public class PlayerSprite {
    private boolean forward = true;//used for travesal
    private ArrayList<ArrayList <BufferedImage>> imageSets = new ArrayList<>(5);
    public int imageSetIndex = 0;//the set of images depending on player's status
    private int currentImageIndex = 0;//the index of the current sprite within a set
    public PlayerSprite(int id){
        for (int i = 0; i< 6; i++){
            imageSets.add(new ArrayList<>(1));
        }
        if(id == 1){
            try {
                String path = "/workspaces/HoodFighterTesting/src/main/java/com/example/player/";
                imageSets.get(0).add(ImageIO.read(new File(path+"idle.png")));
                imageSets.get(1).add(ImageIO.read(new File(path+"jump1.png")));
                imageSets.get(1).add(ImageIO.read(new File(path+"jump2.png")));
                imageSets.get(1).add(ImageIO.read(new File(path+"jump3.png")));
                imageSets.get(2).add(ImageIO.read(new File(path+"jab1.png")));
                imageSets.get(2).add(ImageIO.read(new File(path+"jab2.png")));
                imageSets.get(3).add(ImageIO.read(new File(path+"crouch.png")));
                imageSets.get(4).add(ImageIO.read(new File(path+"left1.png")));
                imageSets.get(4).add(ImageIO.read(new File(path+"left2.png")));
                imageSets.get(4).add(ImageIO.read(new File(path+"left3.png")));
                imageSets.get(4).add(ImageIO.read(new File(path+"left4.png")));
                imageSets.get(5).add(ImageIO.read(new File(path+"right1.png")));
                imageSets.get(5).add(ImageIO.read(new File(path+"right2.png")));
                imageSets.get(5).add(ImageIO.read(new File(path+"right3.png")));
                imageSets.get(5).add(ImageIO.read(new File(path+"right4.png")));
            }
            catch(IOException e) {
                System.err.println("image misteriously vanished");
            }
        }
        else if (id == 2){

        }
        System.out.println(imageSets.get(2).size());
    }
    //precondition: A sprite updating frame reached
    //postcondition: update the current image to a new frame according to player's status
    public void update(){
        System.out.println(currentImageIndex);
        if(imageSetIndex == 1){//we are jumping
            System.out.println("We are jumping");
            //the travelsel below allow us to access image in 1-2-3-2-1 instead of the conventional 123-123
            if(forward){
                if(currentImageIndex == 2){
                    currentImageIndex = 1;
                    forward = false;
                }
                else{
                    System.out.println("here");
                    currentImageIndex++;
                }
            }
            else{
                if(currentImageIndex == 0){
                    currentImageIndex = 1;
                    forward = true;
                }
                else
                    currentImageIndex--;
            }
        }
        else{
            if(currentImageIndex < imageSets.get(imageSetIndex).size()-1)//if currentImage index will be in bound
                currentImageIndex++;
            else
                currentImageIndex = 0;
        }
    }

    //precondition: player's status changed
    //postcondition: change the imageSetIndex accordingly based on the players Status in int.
    public void yoMyStatusChangedTo(Status status){
        if(status == Status.IDLE)
            imageSetIndex = 0;
        else if(status == Status.JUMPING)
            imageSetIndex = 1;
        else if(status == Status.PUNCHING)
            imageSetIndex = 2;
        else if(status == Status.CROUCHING)
            imageSetIndex = 3;
        else if(status == Status.RUNNING_LEFT)
            imageSetIndex = 4;
        else if(status == Status.RUNNING_RIGHT)
            imageSetIndex = 5;
        else
            imageSetIndex = 1000000;
    }
    public BufferedImage currentImage(){
        if(imageSetIndex >= imageSets.size()){//image set index out of bound
            return null;//should results in drawing a rectangle
        }
        if(currentImageIndex >= imageSets.get(imageSetIndex).size()){
            //currentImageIndex out of bound(shouldn't happen)
            try {//just give them the reverse idle image
                System.out.println("currentImageIndex:" + currentImageIndex + ", is out of bound, something went wrong");
                return ImageIO.read(new File("/workspaces/HoodFighterTesting/src/main/java/com/example/player/reverseIdle"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //
        return imageSets.get(imageSetIndex).get(currentImageIndex);
    }
}
