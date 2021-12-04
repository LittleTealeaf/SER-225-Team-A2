package Projectiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntityStatus;
import Level.Player;
import Level.Projectile;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;

// This class is for the lazer beam enemy that the DinosaurEnemy class shoots out
// it will travel in a straight line (x axis) for a set time before disappearing
// it will disappear early if it collides with a solid map tile
public class LazerBeam extends Projectile {

    private final float movementSpeed;
    private final Stopwatch existenceTimer = new Stopwatch();

    public LazerBeam(Point location, float movementSpeed, int existenceTime) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Lazer Beam.png"), 11, 6), "DEFAULT");
        this.movementSpeed = movementSpeed;

        // how long the lazer beam will exist for before disappearing
        existenceTimer.setWaitTime(existenceTime);

        // this will not respawn after it has been removed
        isRespawnable = false;

        initialize();
    }

    @Override
    public void update(Player player) {
        // if timer is up, set map entity status to REMOVED
        // the camera class will see this next frame and remove it permanently from the map
        if (existenceTimer.isTimeUp()) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            // move lazer beam forward
            moveXHandleCollision(movementSpeed);
            super.update(player);
        }
    }

    @Override
    public void touchedPlayer(Player player) {
        // if lazer beam touches player, it disappears
        super.touchedPlayer(player);
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if lazer beam collides with anything solid on the x axis, it is removed
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    public void touchedEnemy(Enemy enemy) {

    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0).withScale(2).withBounds(1, 1, 5, 5).build()
            });
        }};
    }
}
