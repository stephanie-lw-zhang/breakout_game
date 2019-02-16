package Breakout;

import javafx.scene.Group;

import java.util.List;

/**
 * Subclass of the PowerUp that adds 50 points to the score.
 */
public class AddPointsPowerUp extends PowerUp {
    private static final String powerUpPointsImage = "pointspower.gif";

    /**
     * Constructs AddPointsPowerUp from set image.
     */
    public AddPointsPowerUp() {
        super(powerUpPointsImage);
    }

    /**
     * Makes the powerup disappear from the screen and adds 50 points to the score.
     * @param paddle
     * @param bouncer
     * @param root
     * @param bouncerList
     * @param game
     */
    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game) {
        this.disappears(root);
        game.changeScore(game.getScore() + 50);
    }


}
