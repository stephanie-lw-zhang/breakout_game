package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle {

    private ImageView myPaddle;
    private double paddleWidth;

    public static final int MOVER_SPEED = 5;


    public Paddle(ImageView myPaddle){
        this.myPaddle = myPaddle;
        this.paddleWidth = myPaddle.getLayoutBounds().getWidth();
    }

    public ImageView getPaddle() {
        return myPaddle;
    }

    public void handleKeyInput (KeyCode code, int height) {
        if (code == KeyCode.RIGHT & this.getPaddle().getX()< height - this.getPaddle().getImage().getWidth()) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT & this.getPaddle().getX()>0) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }
    }

    public Bounds getPaddleBounds() {
        return myPaddle.getLayoutBounds();
    }

    public void widenPaddle(int screenWidth){
        double newPaddleWidth = myPaddle.getBoundsInParent().getWidth() * 1.5;
        if (newPaddleWidth < screenWidth){
            myPaddle.setFitWidth(newPaddleWidth);
        }
    }

    public void setInitialPosition(int width, int height) {
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());
    }

}
