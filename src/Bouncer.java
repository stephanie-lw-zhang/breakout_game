import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Bouncer {

    public static final int BOUNCER_SPEED = 60;

    private int myVelocityX = 1;
    private int myVelocityY = 1;
    private int numLives = 3;
    private ImageView myBouncer;
    private double screenWidth;
    private double screenHeight;


    public Bouncer(ImageView myBouncer){
        this.myBouncer = myBouncer;
        this.myBouncer.setX(screenWidth / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        this.myBouncer.setY(screenHeight / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getView() {
        return myBouncer;
    }

    public int getNumLives(){
        return numLives;
    }

    public void move(double elapsedTime) {
        myBouncer.setX(myBouncer.getX() - myVelocityX * BOUNCER_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() - myVelocityY * 0.5 * BOUNCER_SPEED * elapsedTime);
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
            myBouncer.setX(screenWidth / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
            myBouncer.setY(screenHeight / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
            numLives--;
        }
        if(numLives == 0){
            //game over
        }
    }
    //will check for intersections with blocks rather than rectangles
    public void checkIntersect(ImageView shape, Group group){
        if(myBouncer.intersects(shape.getLayoutBounds())){
            group.getChildren().remove(shape);
        }
        //code to change direction of ball
    }

    public void checkIntersectPaddle(ImageView shape){
        if(myBouncer.intersects(shape.getLayoutBounds())){
            myVelocityY *= -1;
        }
    }

}