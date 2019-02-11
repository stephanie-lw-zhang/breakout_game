package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Block {
    private ImageView myBlock;
    private int hitsLeft;
    private Boolean powerUp;


    public Block(String image, int totalHits, boolean pwrup, double x, double y) {
        myBlock = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(image)));
        this.hitsLeft = totalHits;
        this.powerUp = pwrup;
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

    public Boolean getPowerUp() {
        return this.powerUp;
    }

}
