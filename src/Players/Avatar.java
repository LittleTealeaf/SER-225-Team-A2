package Players;

import Level.Player;
import Utils.Point;

public enum Avatar {
    CAT_ORANGE(p -> new Cat("Cat.png", p)),
    CAT_BLUE(p -> new Cat("CatBlue.png", p)),
    CAT_GREEN(p -> new Cat("CatGreen.png", p));

    private final PlayerFactory factory;

    Avatar(PlayerFactory factory) {
        this.factory = factory;
    }

    public Player generatePlayer(Point startingPoint) {
        return factory.generatePlayer(startingPoint);
    }

    public interface PlayerFactory {

        Player generatePlayer(Point startingPoint);
    }
}
