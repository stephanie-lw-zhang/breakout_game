package Breakout;

import javafx.scene.Group;

import java.util.List;

public class AddBouncerPowerUp extends PowerUp {
    private static final String powerUpBouncerImage = "extraballpower.gif";

    public AddBouncerPowerUp() {
        super( powerUpBouncerImage );
    }

    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game) {
        this.disappears(root);
        bouncer.createAdditionalBouncer(root, bouncerList);
    }
}
