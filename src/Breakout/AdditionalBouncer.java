package Breakout;

import javafx.scene.Group;

import java.util.ArrayList;

public class AdditionalBouncer extends PowerUp {
    private static final String powerUpBouncerImage = "extraballpower.gif";

    public AdditionalBouncer() {
        super( powerUpBouncerImage );
    }

    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, ArrayList bouncerList) {
        this.disappears(root);
        bouncer.createBouncer(root, bouncerList, 1);
    }
}
