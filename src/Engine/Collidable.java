package Engine;

import GameObject.IntersectableRectangle;

/**
 * Indicates that an object is able to collide. Requires that the object also implements methods listed in {@link IntersectableRectangle}, which
 * provides the details of the "hitbox"
 *
 * @author Thomas Kwashnak
 */
public interface Collidable extends IntersectableRectangle {

    /**
     * Determines if the collidable object intersects another collidable object
     *
     * @param other Object to check collision
     *
     * @return {@code true} if the object intersects with the other collidable object, {@code false} otherwise
     */
    boolean intersects(Collidable other);

    /**
     * Determines if the collidable object overlaps another collidable object
     *
     * @param other Object to check collision
     *
     * @return {@code true} if the object overlaps with the other collidable object, {@code false} otherwise
     */
    boolean overlaps(Collidable other);

    /**
     * Indicates that any class that implements this interface causes instant death when the player contacts that object
     *
     * @author Thomas Kwashnak
     */
    interface InstantDeath extends Collidable {}

    /**
     * Indicates that any class that implements this interface causes damage, and will deal damage specified in {@link #getDamage()} whenever it
     * contacts with the player.
     *
     * @author Thomas Kwashnak
     */
    interface Damage extends Collidable {

        /**
         * The amount of damage to deal to the player
         *
         * @return Number of hearts to remove from the player's health
         */
        int getDamage();
    }

    /**
     * Indicates that any class that implements this interface will prevent the player from jumping for a period of time specified in
     * {@link #getJumpDelay()}.
     *
     * @author Thomas Kwashnak
     */
    interface PreventJump extends Collidable {

        /**
         * @return Number of milliseconds that the player is not able to jump for after colliding with this object
         */
        int getJumpDelay();
    }
}
