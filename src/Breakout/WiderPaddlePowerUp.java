package Breakout;

import javafx.scene.Group;

import java.util.List;

/**
 * Subclass of PowerUp that makes the paddle wider by 1.5 times.
 */

public class WiderPaddlePowerUp extends PowerUp{
    private static final String powerUpSizeImage = "sizepower.gif";

    /**
     * Constructs WiderPaddlePowerUp from set image.
     */
    public WiderPaddlePowerUp() {
        super(powerUpSizeImage);
    }

    /**
     * Removes powerup from screen and makes paddle width by 1.5 times.
     * It makes sure that the paddle remains in bounds even when it becomes wider by shifting it either left or right.
     * @param paddle
     * @param bouncer
     * @param root
     * @param bouncerList
     * @param game
     */
    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game) {
        this.disappears(root);
        paddle.widenPaddle(game.WIDTH);
        double shiftPaddleLength = 0.0;
        if(paddle.getPaddleBounds().getMaxX() > game.WIDTH){
            shiftPaddleLength = paddle.getPaddleBounds().getMaxX() - game.WIDTH;
        } if (paddle.getPaddleBounds().getMinX() < 0){
            shiftPaddleLength = paddle.getPaddleBounds().getMinX();
        }
        paddle.getPaddle().setX(paddle.getPaddle().getX() - shiftPaddleLength);
    }
}
