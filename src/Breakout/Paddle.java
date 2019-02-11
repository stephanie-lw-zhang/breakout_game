package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle {
    private static final String PADDLE_IMAGE = "paddle.gif";
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


    public void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
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


//    public static void createPaddle(int width, int height, Group root){
//        Image imagePaddle = new Image(PADDLE_IMAGE);
//        ImageView otherPaddle = new ImageView(imagePaddle);
//        Paddle paddle = new Paddle(otherPaddle);
//        root.getChildren().add(otherPaddle);
//        paddle.setInitialPosition(width, height);
//    }




}
