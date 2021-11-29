package Projectiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntityStatus;
import Level.Player;
import Level.Projectile;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;
import java.util.HashMap;

// This class is for the bone enemy that the dog class shoots out
// it will travel in a straight line (x axis) for a set time before disappearing
// it will disappear early if it collides with a solid map tile
public class Bone extends Projectile {
    private float movementSpeed;
    private Stopwatch existenceTimer = new Stopwatch();

    public Bone(Point location, float movementSpeed, int existenceTime) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Bone.png"), 13, 13), "DEFAULT");
        this.movementSpeed = movementSpeed;

        // how long the bone will exist for before disappearing
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
            // move bone forward
            moveXHandleCollision(movementSpeed);
            super.update(player);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if bone collides with anything solid on the x axis, it is removed
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public void touchedPlayer(Player player) {
        // if bone touches player, it disappears
        super.touchedPlayer(player);
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }
    
    public void touchedEnemy(Enemy enemy) {
    	
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
		                    .withScale(2)
		                    .withBounds(1, 1, 13, 13)
		                    .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(2)
                            .withBounds(1, 1, 13, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2)
                            .withBounds(1, 1, 13, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 1, 13, 13)
                            .build()
            });
        }};
    }
}
