package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class Block {
    private ImageView myBlock;
    private int hitsLeft;
    private int powerUpType;


    public Block(ImageView block, int totalHits, int pwrup, double x, double y) {
        this.myBlock = block;
        this.hitsLeft = totalHits;
        this.powerUpType = pwrup;
        this.myBlock.setX(x);
        this.myBlock.setY(y);
    }

    public Bounds getBlockBounds() {
        return myBlock.getLayoutBounds();
    }

    public int getHitsLeft() {
        return this.hitsLeft;
    }

    public ImageView getBlock() {
        return myBlock;
    }


    public void gotHit() {
        this.hitsLeft -= 1;
    }

    public Boolean isPowerUp() {
        return this.powerUpType != 0;
    }

    public PowerUp getPowerUpType(){
        if(this.powerUpType == 1){
            return new WiderPaddle();
        } else if (this.powerUpType == 2){
            return new AdditionalBouncer();
        } else {
            return new AdditionalPoints();
        }

    }

    public Boolean wasDestroyed() {
        return (hitsLeft == 0);
    }
}

