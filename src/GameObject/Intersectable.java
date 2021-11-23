package GameObject;

import Engine.Vector;

public interface Intersectable extends Hitbox {
    boolean intersects(Intersectable other);
}
