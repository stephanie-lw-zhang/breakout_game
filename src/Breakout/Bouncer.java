package Breakout;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Bouncer {

    public static final int BOUNCER_SPEED = 150;

    private int myVelocityX = 1;
    private int myVelocityY = 1;
    private int numLives;
    private ImageView myBouncer;
    private double screenWidth = 400;
    private double screenHeight = 400;


    public Bouncer(ImageView myBouncer){
        this.myBouncer = myBouncer;
        numLives = 3;
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getView() {
        return myBouncer;
    }

    public int getNumLives(){
        return numLives;
    }

    public void setPos(double x, double y){
        myBouncer.setX(x);
        myBouncer.setY(y);
    }

    public void move(double elapsedTime) {
        this.setPos(myBouncer.getX() - myVelocityX * BOUNCER_SPEED * elapsedTime,
                myBouncer.getY() - myVelocityY * BOUNCER_SPEED * elapsedTime);
        if(myBouncer.getX()<0){
            myVelocityX *= -1;
        }
        if(myBouncer.getY()<0){
            myVelocityY *=-1;
        }
        if(myBouncer.getX()>screenHeight-myBouncer.getImage().getHeight()){
            myVelocityX *=-1;
        }
        if(myBouncer.getY()>screenWidth-myBouncer.getImage().getWidth()){
            myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
            myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
            numLives--;
            new GamePlay().changeLives(this);
        }
    }
    //will check for intersections with blocks rather than rectangles
    public void checkIntersectBlock(Rectangle shape, Group group){
        if(myBouncer.intersects(shape.getLayoutBounds())){
            group.getChildren().remove(shape);
        }
        //code to change direction of ball
    }

    public void checkIntersectPaddle(Rectangle shape){
        if(myBouncer.intersects(shape.getLayoutBounds())){
            myVelocityY *= -1;
        }
    }

    public void increaseNumLives(){
        numLives++;
    }

}