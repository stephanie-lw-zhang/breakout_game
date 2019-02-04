package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Scanner;


public class Block {
    private ImageView myBlock;
    private int hitsLeft;


    public Block (ImageView block, int totalHits, double x, double y){
        this.myBlock = block;
        this.hitsLeft = totalHits;
        this.myBlock.setX(x);
        this.myBlock.setY(y);
    }

    public Bounds getBlockBounds(){
        return myBlock.getLayoutBounds();
    }

    public int getHitsLeft(){
        return this.hitsLeft;
    }

    public ImageView getBlock() {
        return myBlock;
    }


    public void gotHit(){
        this.hitsLeft -= 1;
    }







}
