package Breakout;

import javafx.scene.Group;

import java.util.List;

public class AddPointsPowerUp extends PowerUp {
    private static final String powerUpPointsImage = "pointspower.gif";

    public AddPointsPowerUp() {
        super(powerUpPointsImage);
    }

    @Override
    public void applyPowerUp(Paddle paddle, Bouncer bouncer, Group root, List<Bouncer> bouncerList, Gameplay game) {
        this.disappears(root);
        game.changeScore(game.getScore() + 50);
    }


}
