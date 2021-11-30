package Engine;

import GameObject.IntersectableRectangle;

/**
 * Indicates that an object is able to collide
 * @author Thomas Kwashnak
 */
public interface Collidable extends IntersectableRectangle {
    boolean intersects(Collidable other);
    boolean overlaps(Collidable other);

    interface InstantDeath extends Collidable {}
    interface Damage extends Collidable {
        int getDamage();
    }
    interface PreventJump extends Collidable {
        int getJumpDelay();
    }
}
