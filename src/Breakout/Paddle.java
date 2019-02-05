package Breakout;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle {
    private ImageView myPaddle;
    public static final int MOVER_SPEED = 5;


    public Paddle(ImageView myPaddle){
        this.myPaddle = myPaddle;
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
