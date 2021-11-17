package Players;


import Level.Player_Old;
import Utils.Point;

//TODO make this the new Cat Options with a factory to create the player
public enum Avatar {
    CAT_ORANGE(p -> new Cat_Old("Cat.png", p)),
    CAT_BLUE(p -> new Cat_Old("CatBlue.png", p)),
    CAT_GREEN(p -> new Cat_Old("CatGreen.png", p));


    private PlayerFactory factory;

    Avatar(PlayerFactory factory) {
        this.factory = factory;
    }

    public Player_Old generatePlayer(Point startingPoint) {
        return factory.generatePlayer(startingPoint);
    }

    public interface PlayerFactory {
        Player_Old generatePlayer(Point startingPoint);
    }
}
