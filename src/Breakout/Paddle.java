package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * Class for the paddle.
 */
public class Paddle {

    private ImageView myPaddle;
    private double paddleWidth;

    public static final int MOVER_SPEED = 5;

    /**
     * Constructs paddle.
     * @param myPaddle
     */
    public Paddle(ImageView myPaddle){
        this.myPaddle = myPaddle;
        this.paddleWidth = myPaddle.getLayoutBounds().getWidth();
    }

    /**
     * Needed for ImageView specific methods.
     * @return ImageView
     */
    public ImageView getPaddle() {
        return myPaddle;
    }

    /**
     * Makes paddle move left and right based on keys.
     * Ensures that the paddle doesn't move out of bounds.
     * @param code
     * @param height
     */
    public void handleKeyInput (KeyCode code, int height) {
        if (code == KeyCode.RIGHT & this.getPaddle().getX()< height - this.getPaddle().getBoundsInParent().getWidth()) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT & this.getPaddle().getX()>0) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }
    }

    /**
     * Directly returns the bounds so don't have to call myPaddle excessively.
     * @return Bounds
     */
    public Bounds getPaddleBounds() {
        return myPaddle.getLayoutBounds();
    }

    /**
     * Makes the paddle 1.5 times wider.
     * It makes sure the paddle is less than the screen width.
     * Used in the WiderPaddlePowerUp
     * @param screenWidth
     */
    public void widenPaddle(int screenWidth){
        double newPaddleWidth = myPaddle.getBoundsInParent().getWidth() * 1.5;
        if (newPaddleWidth < screenWidth){
            myPaddle.setFitWidth(newPaddleWidth);
        }
    }

    /**
     * Sets the initial position for the paddle, which is the bottom-middle of the screen.
     * @param width
     * @param height
     */
    public void setInitialPosition(int width, int height) {
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());
    }

    /**
     * Sets the x position of the paddle (only x because the y is always the bottom of the screen).
     * Used for testing only.
     * @param x
     */
    public void setPos(double x){
        myPaddle.setX(x);
    }

}
