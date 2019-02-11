package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


abstract public class PowerUp {
    private ImageView myPowerUp;

    public PowerUp(String image){
        var imagePowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(image));
        myPowerUp = new ImageView(imagePowerUp);
    }

    public void setPosition(Block block){
        this.getMyPowerUp().setX((block.getBlockBounds().getMaxX() + block.getBlockBounds().getMinX())/2);
        this.getMyPowerUp().setY(block.getBlockBounds().getMaxY());
    }

    public ImageView getMyPowerUp(){
        return myPowerUp;
    }

    public void stepPowerUp(double elapsedTime){
        this.getMyPowerUp().setY(this.getMyPowerUp().getY() + 35 * elapsedTime);
    }

    public Boolean intersectsPaddle(Paddle paddle) {
        if (myPowerUp.intersects(paddle.getPaddleBounds())) {
            return Boolean.TRUE;
        } else{
            return Boolean.FALSE;
        }
    }

    public void disappears(Group root){
        root.getChildren().remove(myPowerUp);
    }

    public abstract void applyPowerUp (Paddle paddle, Bouncer bouncer, Group root, ArrayList bouncerList);

}
