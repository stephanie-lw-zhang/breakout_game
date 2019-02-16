package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

import static Breakout.PowerUp.createPowerUp;


public class Bouncer {

    private int BOUNCER_SPEED = 100;
    private double myVelocityX = 1;
    private double myVelocityY = 1;
    private int numLives;
    private ImageView myBouncer;
    private double screenWidth = 400;
    private double screenHeight = 400;
    private Gameplay game;

    /**
     * The constructor: takes an ImageView, a game, and number of lives
     * Sets the ball in the middle of the screen and initializes instance variables
     */
    public Bouncer(ImageView myBouncer, Gameplay game, int numLives){
        this.myBouncer = myBouncer;
        this.game = game;
        this.numLives = numLives;
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }
    
    /**
     * Returns the ImageView instance variable
     */
    public ImageView getBouncer(){
        return myBouncer;
    }
    
    /**
     * Returns the number of lives instance variable
     */
    public int getNumLives(){
        return numLives;
    }
    
    /**
     * Changes the value of the number of lives to the parameter value
     */
    public void setNumLives(int lives){
        this.numLives = lives;
    }
    
    /**
     * Sets the position of the bouncer to the parameters with coordinates (x,y)
     */
    public void setPos(double x, double y){
        myBouncer.setX(x);
        myBouncer.setY(y);
    }
    
    /**
     * Increases the speed of the bouncer by a paramter value
     */
    public void incrementSpeed(int speed){
        this.BOUNCER_SPEED +=speed;
    }
    
    /**
     * Resets the Bouncer to the center of the screen
     */
    public void resetPos(){
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }
    
    /**
     * Manually sets the velocity of the Bouncer to be multiplied by paramter values
     * x magnifies the X velocity, and y magnifies the Y velocity
     */
    public void setVelocities(double x, double y){
        myVelocityX = x;
        myVelocityY = y;
    }

    private void move(double elapsedTime, List<Bouncer> bouncerList, Group root) {
        myBouncer.setX(myBouncer.getX() - myVelocityX * BOUNCER_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() - myVelocityY * 0.5 * BOUNCER_SPEED * elapsedTime);

        if(myBouncer.getX() < 0){
            myVelocityX *= -1;
        }
        if(myBouncer.getY() < 0){
            myVelocityY *=-1;
        }
        if(myBouncer.getX() > screenHeight-myBouncer.getImage().getHeight()){
            myVelocityX *=-1;
        }
        if(myBouncer.getY()>screenWidth-myBouncer.getImage().getWidth()){
            if(bouncerList.size() == 1){
                myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
                myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
                this.setNumLives(this.getNumLives()-1);
                game.changeLives(this.getNumLives());
            } else {
                root.getChildren().remove(myBouncer);
                bouncerList.remove(bouncerList.indexOf(this));
            }
        }
    }


    private Boolean topBottom(Block block) {
        double blockBottomEdge = block.getBlockBounds().getMaxY();
        double blockTopEdge = block.getBlockBounds().getMinY();
        double blockRightEdge = block.getBlockBounds().getMaxX();
        double blockLeftEdge = block.getBlockBounds().getMinX();
        double bouncerBottomEdge = myBouncer.getBoundsInLocal().getMaxY();
        double bouncerTopEdge = myBouncer.getBoundsInLocal().getMinY();
        double bouncerRightEdge = myBouncer.getBoundsInLocal().getMaxX();
        double bouncerLeftEdge = myBouncer.getBoundsInLocal().getMinX();
        return((bouncerBottomEdge >= blockTopEdge && bouncerRightEdge <= blockRightEdge && bouncerLeftEdge >= blockLeftEdge) ||
                (bouncerTopEdge <= blockBottomEdge && bouncerRightEdge <= blockRightEdge && bouncerLeftEdge >= blockLeftEdge));
    }

    private void updateScore(Gameplay game, Block block){
        game.changeScore(game.getScore() + block.getTotalHits()*100);
    }


    //will check for intersections with blocks rather than rectangles
    private void checkIntersectBlock(Block block, List<PowerUp> powerUpList, List<Block> blockList, List<Bouncer> bouncerList, Group root, Gameplay game, Double elapsedTime){
        if(myBouncer.intersects(block.getBlockBounds())) {
            if (this.topBottom(block)) {
                myVelocityY *= -1;
            } else {
                myVelocityX *= -1;
            }
            this.move(elapsedTime, bouncerList, root);
            block.takeHit();
            if(block.wasDestroyed()){
                updateScore(game, block);
                root.getChildren().remove(block.getBlock());
                blockList.remove(blockList.indexOf(block));
                if(block.isPowerUp()){
                    createPowerUp(block, powerUpList, root);
                }
            } else {
                block.replaceBlock(root, blockList);
            }
        }
    }

    private void checkIntersectPaddle(Paddle paddle){
        if(myBouncer.intersects(paddle.getPaddleBounds())){
            myVelocityY *= -1;
        }
    }

    /**
     * Updates the Bouncers in a list, checks that they move correctly and intersect specific objects correctly
     */
    public void stepBouncer(List<Bouncer> bouncerList,  List<Block> blockList, List<PowerUp> powerUpList, Paddle paddle, Group root, double elapsedTime, Gameplay game) {
        for(int i = 0; i < bouncerList.size(); i++){
            Bouncer currentBouncer = bouncerList.get(i);
            currentBouncer.move(elapsedTime, bouncerList, root);
            currentBouncer.checkIntersectPaddle(paddle);
            for(int j=0; j<blockList.size(); j++){
                Block currentBlock = blockList.get(j);
                currentBouncer.checkIntersectBlock(currentBlock, powerUpList, blockList, bouncerList, root, game, elapsedTime);
            }
        }
    }

    /**
     * Increases the number of lives a bouncer has by one
     */
    public void increaseNumLives(){
        numLives++;
    }


    /**
     * creates a new Bouncer object with one life and adds it to the scene and bouncerList
     */
    public void createAdditionalBouncer(Group root, List<Bouncer> bouncerList){
        var imageBouncer = new Image("ball.gif");
        ImageView otherBouncer = new ImageView(imageBouncer);
        Bouncer bouncer = new Bouncer(otherBouncer, game, 1);
        root.getChildren().add(otherBouncer);
        bouncerList.add(bouncer);
    }

}