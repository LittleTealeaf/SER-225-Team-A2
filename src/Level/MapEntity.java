package Level;

import Engine.CollisionType;
import Engine.Vector;
import GameObject.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class represents a map entity, which is any "entity" on a map besides the player
// it is basically a game object with a few extra features for handling things like respawning
public class MapEntity extends GameObject {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected CollisionType collisionType = CollisionType.DEFAULT;

    // if true, if entity goes out of the camera's update range, and then ends up back in range, the entity will "respawn" back to its starting parameters
    protected boolean respawnEnabled = true;

    // if true, enemy cannot go out of camera's update range
    protected boolean isUpdateOffScreen = false;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(spriteSheet, x, y, startingAnimation);
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public MapEntity(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, RectangleOld bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    public void initialize() {
        pos.set(startPosition);
        amountMoved = new Vector(0, 0);
        updateCurrentFrame();
    }

    public MapEntityStatus getMapEntityStatus() {
        return mapEntityStatus;
    }

    public void setMapEntityStatus(MapEntityStatus mapEntityStatus) {
        this.mapEntityStatus = mapEntityStatus;
    }

    public boolean isRespawnEnabled() {
        return respawnEnabled;
    }

    public void setIsRespawnable(boolean isRespawnable) {
        this.respawnEnabled = isRespawnable;
    }

    public boolean isUpdateOffScreen() {
        return isUpdateOffScreen;
    }

    public void setIsUpdateOffScreen(boolean isUpdateOffScreen) {
        this.isUpdateOffScreen = isUpdateOffScreen;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }
}
