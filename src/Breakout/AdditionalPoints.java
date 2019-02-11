package Breakout;

import javafx.scene.Group;

import java.util.ArrayList;

public class AdditionalPoints extends PowerUp {
    private static final String powerUpPointsImage = "pointspower.gif";

    public AdditionalPoints() {
        super(powerUpPointsImage);
    }

    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, ArrayList bouncerList) {
        this.disappears(root);
        bouncer.setScore( bouncer.getScore() + 50 );
    }


}
