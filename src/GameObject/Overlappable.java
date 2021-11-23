package GameObject;

/**
 * Indicates that an object can be checked if it overlaps with another overlappable
 * @author Thomas Kwashnak
 */
public interface Overlappable extends Hitbox {
    /**
     * Checks the overlapping with another Overlappable
     * @param other Overlappable to check overlapping with
     * @return {@code true} if this intersectable and the provided intersectable intersect
     */
    boolean overlaps(Overlappable other);
}
