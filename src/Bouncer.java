
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Bouncer {

    public static final int BOUNCER_SPEED = 30;

    private int myVelocityX = 1;
    private int myVelocityY = 1;
    private int x, y;
    private ImageView myBouncer;


    public Bouncer(ImageView myBouncer, int x, int y){
        this.myBouncer = myBouncer;
        this.x = x;
        this.y = y;
    }

    public ImageView getView(){
        return myBouncer;
    }

    public void move(double elapsedTime) {
        myBouncer.setX(myBouncer.getX() - myVelocityX * BOUNCER_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() - myVelocityY * BOUNCER_SPEED * elapsedTime);
    }

    public void bounce(double screenWidth, double screenHeight){
        if(myBouncer.getX()<0){
            myVelocityX *= -1;
        }
        if(myBouncer.getY()<0){
            myVelocityY *=-1;
        }
        if(myBouncer.getX()>screenHeight){
            myVelocityX *=-1;
        }
        if(myBouncer.getY()>screenHeight){
            move(0);
        }
    }
    public void reset(){

    }
}
