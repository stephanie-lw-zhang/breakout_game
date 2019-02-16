package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * This is a abstract class for all powerups.
 */
abstract public class PowerUp {
    private ImageView myPowerUp;
    private static final int POWERUP_SPEED = 40;

    /**
     * Constructs each powerup and assigns ImageView to myPowerUp
     * @param image (the name of the file under resources)
     */
    public PowerUp(String image){
        var imagePowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(image));
        myPowerUp = new ImageView(imagePowerUp);
    }


    private void setPosition(Block block){
        this.getMyPowerUp().setX((block.getBlockBounds().getMaxX() + block.getBlockBounds().getMinX())/2);
        this.getMyPowerUp().setY(block.getBlockBounds().getMaxY());
    }

    /**
     * used for ImageView-specific functions
     * @return ImageView of powerup
     */
    public ImageView getMyPowerUp(){
        return myPowerUp;
    }

    private void movePowerUp(double elapsedTime){
        this.getMyPowerUp().setY(this.getMyPowerUp().getY() + POWERUP_SPEED * elapsedTime);
    }

    /**
     * returns whether or not the powerup intersects with the paddle
     * @param paddle
     * @return True or False
     */
    public Boolean intersectsPaddle(Paddle paddle) {
        return myPowerUp.intersects(paddle.getPaddleBounds());
    }


    /**
     * Removes the powerup from the screen. Needed for when the powerup intersects with the paddle.
     * @param root
     */
    public void disappears(Group root){
        root.getChildren().remove(myPowerUp);
    }

    /**
     * Applies the powerup within the game. This method is abstract and must be filled in within the subclasses.
     * Not every parameter is used in each call, but they are all used at some point.
     * @param paddle
     * @param bouncer
     * @param root
     * @param bouncerList
     * @param game
     */
    public abstract void applyPowerUp (Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game);

    /**
     * The method called for each "step" in the animation.
     * It moves the powerup on screen and applies the powerup if it intersects with the paddle.
     * @param powerUpList
     * @param paddle
     * @param bouncer
     * @param root
     * @param bouncerList
     * @param elapsedTime
     * @param game
     */
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

    /**
     * Creates a powerup on screen, sets it to the correct position,
     * and adds it to the list of powerups so the game can keep track of each powerup.
     * @param block
     * @param powerUpList
     * @param root
     */
    public static void createPowerUp(Block block, List<PowerUp> powerUpList, Group root) {
        PowerUp powerUp = block.getPowerUpType();
        powerUpList.add(powerUp);
        root.getChildren().add(powerUp.getMyPowerUp());
        powerUp.setPosition(block);
    }

    /**
     * Creates powerup specifically for the tester, mainly different from createPowerUp in that it returns a PowerUp.
     * This is needed to keep track of powerups in the tester.
     * @param x
     * @param y
     * @param powerUpType
     * @param powerUpList
     * @param root
     * @return PowerUp
     */
    public static PowerUp createPowerUpForTest(double x, double y, int powerUpType, List<PowerUp> powerUpList, Group root){
        PowerUp powerUp = new WiderPaddlePowerUp();
        if (powerUpType == 2){
            powerUp = new AddBouncerPowerUp();
        } if (powerUpType == 3){
            powerUp = new AddPointsPowerUp();
        }
        powerUp.getMyPowerUp().setX(x);
        powerUp.getMyPowerUp().setY(y);
        powerUpList.add(powerUp);
        root.getChildren().add( powerUp.getMyPowerUp() );
        return powerUp;
    }


}
