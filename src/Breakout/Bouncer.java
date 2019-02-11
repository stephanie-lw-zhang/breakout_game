package Breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class Bouncer {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private static final int BOUNCER_SPEED = 100;

    private double myVelocityX = 1;
    private double myVelocityY = 1;
    private int numLives;
    private double screenWidth = 400;
    private double screenHeight = 400;
    public static final String SIZEPWR_IMAGE = "sizepower.gif";
    private ImageView myBouncer;



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
            new Gameplay().changeLives(this.getNumLives());
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
        if((bouncerBottomEdge >= blockTopEdge && bouncerRightEdge <= blockRightEdge && bouncerLeftEdge >= blockLeftEdge) ||
                (bouncerTopEdge <= blockBottomEdge && bouncerRightEdge <= blockRightEdge && bouncerLeftEdge >= blockLeftEdge)){
            return Boolean.TRUE;
        } else{
            return Boolean.FALSE;
        }
    }



    //will check for intersections with blocks rather than rectangles
    public Group checkIntersectBlock(Block block, ArrayList powerUpList, Group root, double elapsedTime){
        if(myBouncer.intersects(block.getBlockBounds())) {
            block.gotHit();
            if(this.topBottom(block)){
                myVelocityY *= -1;

            } else {
                myVelocityX *= -1;
            }
        }
        if(block.getHitsLeft() == 0){
            if(block.isPowerUp()){
                PowerUp powerUp = block.getPowerUpType();
                powerUpList.add(powerUp);
                root.getChildren().add(powerUp.getMyPowerUp());
                powerUp.setPosition(block);
            }
            root.getChildren().remove(block.getBlock());
        }
        return root;
    }




    public void checkIntersectPaddle(Paddle paddle){
        if(myBouncer.intersects(paddle.getPaddleBounds())){
            myVelocityY *= -1;
        }
    }

    public void increaseNumLives(){
        numLives++;
    }

    public void createBouncer(Group root, ArrayList<Bouncer> bouncerList){
        var imageBouncer = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        ImageView otherBouncer = new ImageView(imageBouncer);
        Bouncer bouncer = new Bouncer(otherBouncer);
        root.getChildren().add(otherBouncer);
        bouncerList.add(bouncer);
    }

//    public void bouncerSticksToPaddle(Paddle paddle){
//        KeyCode code = KeyCode.K;
//        if(myBouncer.intersects(paddle.getPaddleBounds())){
//            double saveVelocityX = myVelocityX;
//            double saveVelocityY = myVelocityY;
//            this.setPos(paddle.getPaddleBounds().getCenterX(), paddle.getPaddleBounds().getMinY());
//            this.setVelocities(0,0);
//            if(code.equals(KeyCode.SPACE)){
//                setVelocities(saveVelocityX, saveVelocityY * -1);
//            }
//        }
//    }




}