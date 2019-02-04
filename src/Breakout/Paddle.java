package Breakout;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle {
    private ImageView myPaddle;
    public static final int MOVER_SPEED = 5;


    public Paddle(ImageView myPaddle){
        this.myPaddle = myPaddle;
//        this.myPaddle.setX(screenWidth / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
//        this.myPaddle.setY(screenHeight / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getPaddle() {
        return myPaddle;
    }


    public void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }
    }
}
