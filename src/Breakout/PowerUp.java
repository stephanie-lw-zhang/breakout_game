package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;


abstract public class PowerUp {
    private ImageView myPowerUp;
    private static final int POWERUP_SPEED = 40;

    public PowerUp(String image){
        var imagePowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(image));
        myPowerUp = new ImageView(imagePowerUp);
    }


    private void setPosition(Block block){
        this.getMyPowerUp().setX((block.getBlockBounds().getMaxX() + block.getBlockBounds().getMinX())/2);
        this.getMyPowerUp().setY(block.getBlockBounds().getMaxY());
    }

    public ImageView getMyPowerUp(){
        return myPowerUp;
    }

    private void movePowerUp(double elapsedTime){
        this.getMyPowerUp().setY(this.getMyPowerUp().getY() + POWERUP_SPEED * elapsedTime);
    }

    public Boolean intersectsPaddle(Paddle paddle) {
        return myPowerUp.intersects(paddle.getPaddleBounds());
    }

    public void disappears(Group root){
        root.getChildren().remove(myPowerUp);
    }

    public abstract void applyPowerUp (Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game);

    public static void stepPowerUp(List<PowerUp> powerUpList, Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, double elapsedTime, Gameplay game) {
        for(int i = 0; i<powerUpList.size(); i++){
            PowerUp currentPowerUp = powerUpList.get(i);
            currentPowerUp.movePowerUp(elapsedTime);
            if(currentPowerUp.intersectsPaddle(paddle)){
                currentPowerUp.applyPowerUp(paddle, bouncer, root, bouncerList, game);
                powerUpList.remove(i);
            }
        }
    }

    public static void createPowerUp(Block block, List<PowerUp> powerUpList, Group root) {
        PowerUp powerUp = block.getPowerUpType();
        powerUpList.add(powerUp);
        root.getChildren().add(powerUp.getMyPowerUp());
        powerUp.setPosition(block);
    }


}
