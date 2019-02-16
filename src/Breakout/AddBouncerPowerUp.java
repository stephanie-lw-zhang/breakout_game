package Breakout;

import javafx.scene.Group;

import java.util.List;

/**
 * Subclass of PowerUp that creates an additional bouncer in the game.
 * If any of the bouncers fall off the screen when there are more than one bouncers the number of lives won't be affected,
 * And none of the bouncers regenerate until the number of lives decreases (but is till greater than 0).
 */
public class AddBouncerPowerUp extends PowerUp {
    private static final String powerUpBouncerImage = "extraballpower.gif";

    /**
     * Constructs AddBouncerPowerUp from set image
     */
    public AddBouncerPowerUp() {
        super( powerUpBouncerImage );
    }

    /**
     * creates additional bouncer and makes the powerup disappear from the screen
     * @param paddle
     * @param bouncer
     * @param root
     * @param bouncerList
     * @param game
     */
    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game) {
        this.disappears(root);
        bouncer.createAdditionalBouncer(root, bouncerList);
    }
}
