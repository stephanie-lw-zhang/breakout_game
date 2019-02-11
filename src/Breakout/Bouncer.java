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
//    private int score = 0;
    private ImageView myBouncer;
    private double screenWidth = 400;
    private double screenHeight = 400;
    private static final String SIZEPWR_IMAGE = "sizepower.gif";
    private Gameplay game;


    public Bouncer(ImageView myBouncer, Gameplay game){
        this.myBouncer = myBouncer;
        this.game = game;
        numLives = 3;
        myBouncer.setX(screenHeight / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(screenWidth / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getBouncer(){
        return myBouncer;
    }

    public int getNumLives(){
        return numLives;
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

    private void move(double elapsedTime) {
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
    private void checkIntersectBlock(Block block, List<PowerUp> powerUpList, Group root, Gameplay game){
        if(myBouncer.intersects(block.getBlockBounds())) {
            block.gotHit();
            if(this.topBottom(block)){
                myVelocityY *= -1;

            } else {
                myVelocityX *= -1;
            }
        }
        if(block.wasDestroyed()){
            updateScore(game, block);
            root.getChildren().remove(block.getBlock());
            if(block.isPowerUp()){
                createPowerUp(block, powerUpList, root);
            }
        }
    }


    private void checkIntersectPaddle(Paddle paddle){
        if(myBouncer.intersects(paddle.getPaddleBounds())){
            myVelocityY *= -1;
        }
    }

    public void stepBouncer(List<Bouncer> bouncerList,  List<Block> blockList, List<PowerUp> powerUpList, Paddle paddle, Group root, double elapsedTime, Gameplay game) {
        for(int i = 0; i < bouncerList.size(); i++){
            Bouncer currentBouncer = bouncerList.get(i);
            currentBouncer.move(elapsedTime);
            currentBouncer.checkIntersectPaddle(paddle);

            for(int j=0; j<blockList.size(); j++){
                Block currentBlock = blockList.get(j);
                currentBouncer.checkIntersectBlock(currentBlock, powerUpList, root, game);
                if(currentBlock.getHitsLeft()==0){
                    blockList.remove(j);
                }
            }
        }
    }



    public void increaseNumLives(){
        numLives++;
    }


    public void createAdditionalBouncer(Group root, List<Bouncer> bouncerList){
        var imageBouncer = new Image("ball.gif");
        ImageView otherBouncer = new ImageView(imageBouncer);
        Bouncer bouncer = new Bouncer(otherBouncer, game);
        bouncer.numLives = 1;
        root.getChildren().add(otherBouncer);
        bouncerList.add(bouncer);
    }
}