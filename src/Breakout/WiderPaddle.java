package Breakout;

import javafx.scene.Group;

import java.util.ArrayList;

public class WiderPaddle extends PowerUp{
    private static final String powerUpSizeImage = "sizepower.gif";

    public WiderPaddle() {
        super(powerUpSizeImage);
    }


    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, ArrayList bouncerList) {
        this.disappears(root);
        paddle.widenPaddle();
    }
}
