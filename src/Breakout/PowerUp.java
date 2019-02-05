package Breakout;

import Breakout.Block;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp {
    private ImageView myPowerUp;
    public static final String SIZEPWR_IMAGE = "sizepower.gif";
    public static final int POWERUP_SPEED = 50;


    public PowerUp(Block block){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(SIZEPWR_IMAGE));
        myPowerUp = new ImageView(image);
        myPowerUp.setX((block.getBlockBounds().getMaxX() + block.getBlockBounds().getMinX())/2);
        myPowerUp.setY(block.getBlockBounds().getMaxY());
    }

    public ImageView getMyPowerUp() {
        return myPowerUp;
    }

    public void move(double elapsedTime){
        myPowerUp.setY(myPowerUp.getY() + POWERUP_SPEED * elapsedTime);
    }

}
