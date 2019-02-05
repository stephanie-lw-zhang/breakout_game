package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class Bouncer {

    public static final int BOUNCER_SPEED = 100;

    private double myVelocityX = 1;
    private double myVelocityY = 1;
    private int numLives;
    private ImageView myBouncer;
    private double screenWidth = 400;
    private double screenHeight = 400;
    public static final String SIZEPWR_IMAGE = "sizepower.gif";


    public Bouncer(ImageView myBouncer){
        this.myBouncer = myBouncer;
        numLives = 3;
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        //this.setVelocities(Math.random()+0.5, Math.random()+0.5);
    }

    public ImageView getBouncer() {
        return myBouncer;
    }

    public int getNumLives(){
        return numLives;
    }

    public void setPos(double x, double y){
        myBouncer.setX(x);
        myBouncer.setY(y);
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
            new Gameplay().changeLives(this);
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
        if (topEdge > block.getBlockBounds().getMinY() && bottomEdge <= block.getBlockBounds().getMinY()) {
            return TRUE;
        }
        else if (bottomEdge < block.getBlockBounds().getMaxY() && topEdge >= block.getBlockBounds().getMaxY()) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    private Boolean leftRight(Block block){
        double leftEdge = this.getMinX();
        double rightEdge = this.getMaxX();

        if(leftEdge < block.getBlockBounds().getMinX() && rightEdge >= block.getBlockBounds().getMinX()){
            return TRUE;
        }
        else if(rightEdge > block.getBlockBounds().getMaxX() && leftEdge <= block.getBlockBounds().getMaxX()){
            return TRUE;
        } else {
            return FALSE;
        }

    }

    //will check for intersections with blocks rather than rectangles
    public void checkIntersectBlock(Block shape, Group group){
        if(myBouncer.intersects(shape.getBlockBounds())){
            shape.gotHit();
            //Top bottom
            if(topBottom(shape)){
                myVelocityY *= -1;

            }
            else if(leftRight(shape)){
                myVelocityX *= -1;
            }
            if(shape.getHitsLeft() == 0){
                group.getChildren().remove(shape.getBlock());
            }

        }
    }

    public Boolean intersectsBlock(Block shape) {
        if (myBouncer.intersects(shape.getBlockBounds())) {
            return TRUE;
        } else {
            return FALSE;
        }
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