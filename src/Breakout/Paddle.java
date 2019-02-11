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

    public void widenPaddle(){
        myPaddle.setFitWidth(myPaddle.getBoundsInParent().getWidth() * 1.5);
    }

//    public ImageView createPaddle(Group root, int width, int height){
//        var imagePaddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
//        myPaddle = new ImageView(imagePaddle);
//        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
//        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());
//        root.getChildren().add(myPaddle);
//        return myPaddle;
//    }


}
