package GameObject;

/**
 * Indicates that an object can be checked for intersection with another intersectable
 * @author Thomas Kwashnak
 */
public interface Intersectable extends Hitbox {

    /**
     * Checks the intersection with another intersectable
     * @param other Intersectable to check intersection with
     * @return {@code true} if this intersectable and the provided intersectable intersect
     */
    boolean intersects(Intersectable other);
}
