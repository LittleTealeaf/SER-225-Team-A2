package GameObject;

public interface Overlappable extends Hitbox {
    boolean overlaps(Overlappable other);
}
