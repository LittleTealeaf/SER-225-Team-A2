package Players;


import Level.Player;
import Level.Player_Old;
import Utils.Point;

//TODO make this the new Cat Options with a factory to create the player
public enum Avatar {
    CAT_ORANGE(p -> new Cat("Cat.png", p)),
    CAT_BLUE(p -> new Cat("CatBlue.png", p)),
    CAT_GREEN(p -> new Cat("CatGreen.png", p));


    private PlayerFactory factory;

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
