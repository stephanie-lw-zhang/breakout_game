package Breakout;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Bouncer {

    private int BOUNCER_SPEED = 100;
    private double myVelocityX = 1;
    private double myVelocityY = 1;
    private int numLives;
    private int score = 0;
    private ImageView myBouncer;
    private double screenWidth = 400;
    private double screenHeight = 400;
    public static final String SIZEPWR_IMAGE = "sizepower.gif";
    private Gameplay game;


    public Bouncer(ImageView myBouncer, Gameplay game){
        this.myBouncer = myBouncer;
        this.game =game;
        numLives = 3;
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getBouncer() {
        return myBouncer;
    }

    public int getNumLives(){
        return numLives;
    }

    public int getScore(){
        return score;
    }

    public void setPos(double x, double y){
        myBouncer.setX(x);
        myBouncer.setY(y);
    }

    public void incrementSpeed(int speed){
        this.BOUNCER_SPEED +=speed;
    }

    public void resetPos(){
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public void setVelocities(double x, double y){
        myVelocityX = x;
        myVelocityY = y;
    }

    public void move(double elapsedTime) {
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
            myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
            myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
            numLives--;
            game.changeLives(this.getNumLives());
        }
    }

    private double getMaxY(){
        return myBouncer.getLayoutBounds().getMaxY();
    }

    private double getMaxX(){
        return myBouncer.getLayoutBounds().getMaxX();
    }

    private double getMinY(){
        return myBouncer.getLayoutBounds().getMinY();
    }

    private double getMinX(){
        return myBouncer.getLayoutBounds().getMinX();
    }

    private Boolean topBottom(Block block) {
        double topEdge = this.getMinY();
        double bottomEdge = this.getMaxY();
        return (topEdge > block.getBlockBounds().getMinY() && bottomEdge <= block.getBlockBounds().getMinY() |
                bottomEdge < block.getBlockBounds().getMaxY() && topEdge >= block.getBlockBounds().getMaxY());
    }

    private Boolean leftRight(Block block){
        double leftEdge = this.getMinX();
        double rightEdge = this.getMaxX();
        return(leftEdge < block.getBlockBounds().getMinX() && rightEdge >= block.getBlockBounds().getMinX() |
                rightEdge > block.getBlockBounds().getMaxX() && leftEdge <= block.getBlockBounds().getMaxX());
    }

    //will check for intersections with blocks rather than rectangles
    public void checkIntersectBlock(Block shape, Group group){
        if(myBouncer.intersects(shape.getBlockBounds())){
            shape.gotHit();
            //Top bottom
            if(topBottom(shape)){
                myVelocityY *= -1;
            }
            if(leftRight(shape)){
                myVelocityX *= -1;
            }
            if(shape.getHitsLeft() == 0){
                group.getChildren().remove(shape.getBlock());
                score += 100;
                game.changeScore(score);
            }

        }
    }

    public Boolean intersectsBlock(Block shape) {
        return myBouncer.intersects(shape.getBlockBounds());
    }


    public void checkIntersectPaddle(ImageView shape){
        if(myBouncer.intersects(shape.getLayoutBounds())){
            myVelocityY *= -1;
        }
    }

    public void increaseNumLives(){
        numLives++;
    }

}