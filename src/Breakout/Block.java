package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {
    private static final String BLOCK_IMAGE_1 = "brick1.gif";
    private static final String BLOCK_IMAGE_2 = "brick2.gif";

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

//    public Block replaceBlock(int hitsLeft, Group root){
//        Image imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));
//        if (hitsLeft == 2){
//            imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_2));
//        }
//        ImageView tempBlockImage = new ImageView(imageBlock);
//        Block tempBlock = new Block(tempBlockImage, hitsLeft, this.powerUpType, this.getBlockBounds().getMinX(),  this.getBlockBounds().getMinY());
//        root.getChildren().remove(this.getBlock());
//        root.getChildren().add(tempBlock.getBlock());
//        return tempBlock;
//    }


    public void gotHit() {
        this.hitsLeft -= 1;
    }

    public Boolean isPowerUp() {
        return (this.powerUpType != 0);
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


    public Boolean wasDestroyed(){
        return (hitsLeft == 0);
    }
}

