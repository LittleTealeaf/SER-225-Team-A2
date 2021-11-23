package GameObject;

import Engine.Vector;

/**
 * Indicates that a class contains a HitBox. The hitbox is split up into two locations, the min value and the max value.
 *
 * <p>
 *     Given a rectangle with 4 locations, as listed: {@code (x1, y1), (x1, y2), (x2, y1), (x2, y2)}, the min and max locations would be as follows
 *     <ul><li><b>Min Location:</b> {@code (x1, y1)}</li>
 *     <li><b>Max Location:</b> {@code (x2, y2)}</li></ul>
 *     Where {@code x1 < x2} and {@code y1 < y2}
 * </p>
 * @author Thomas Kwashnak
 */
public interface Hitbox {

    /**
     * Returns the upper left point of a hitbox. (<i>Assuming that the top left corner is {@code (0,0)}</i>). In short, given a hitbox with the
     * points {@code (x1, y1), (x1, y2), (x2, y1), (x2, y2)}, returns {@code (x1, y1)} where {@code x1 < x2} and {@code y1 < y2}
     * @return A vector representing {@code (x1, y1)}
     * @see Hitbox
     */
    Vector getMinLocation();
    /**
     * Returns the bottom right point of a hitbox. (<i>Assuming that the top left corner is {@code (0,0)}</i>). In short, given a hitbox with the
     * points {@code (x1, y1), (x1, y2), (x2, y1), (x2, y2)}, returns {@code (x2, y2)} where {@code x2 > x1} and {@code y2 > y1}
     * @return A vector representing {@code (x2, y2)}
     * @see Hitbox
     */
    Vector getMaxLocation();
}
