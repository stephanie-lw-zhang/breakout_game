package Breakout;

import javafx.scene.Group;

import java.util.List;

public class WiderPaddlePowerUp extends PowerUp{
    private static final String powerUpSizeImage = "sizepower.gif";

    public WiderPaddlePowerUp() {
        super(powerUpSizeImage);
    }

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
