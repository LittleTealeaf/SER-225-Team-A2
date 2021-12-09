package Enemies;

import Builders.FrameBuilder;
import Engine.GamePanel;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Projectiles.Bone;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;

/**
 *  This class is for the green dog enemy that shoots bones
 *  It walks back and forth between two set points (startLocation and endLocation)
 *  Every so often (based on shootTimer) it will shoot a bone enemy
 */
public class Dog extends Enemy {

    // different speeds depending on the difficulty
    private static final float NORMAL_SPEED = 1f, HARD_SPEED = 1.2f, HARDCORE_SPEED = 1.4f;
    private static final float NORMAL_BONE_SPEED = 1.5f, HARD_BONE_SPEED = 1.7f, HARDCORE_BONE_SPEED = 1.9f;
    private final Direction startFacingDirection;
    // start and end location defines the two points that it walks between
    // is only made to walk along the x axis and has no air ground state logic, so make sure both points have the same Y value
    protected Point startLocation;
    protected Point endLocation;
    protected Direction facingDirection;
    protected boolean isInAir;
    // timer is used to determine when a bone is to be shot out
    protected Stopwatch shootTimer = new Stopwatch();
    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected DogState dogState;
    protected DogState previousdogState;
    float movementSpeed = NORMAL_SPEED;

    public Dog(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("Dog.png"), 30, 26), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        dogState = DogState.WALK;
        previousdogState = dogState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        isInAir = false;
        // every 2 seconds, the bone will be shot out
        shootTimer.setWaitTime(2000);
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;
        float boneMovementSpeed = NORMAL_BONE_SPEED;

        // set the movement speed of the enemy and fireball attack depending on what difficulty it selected
        if (GamePanel.getDifficulty() == 2) {
            movementSpeed = HARD_SPEED;
            boneMovementSpeed = HARD_BONE_SPEED;
        } else if (GamePanel.getDifficulty() == 1) {
            movementSpeed = HARDCORE_SPEED;
            boneMovementSpeed = HARDCORE_BONE_SPEED;
        }

        // if shoot timer is up and dog is not currently shooting, set its state to SHOOT
        if (shootTimer.isTimeUp() && dogState != DogState.SHOOT) {
            dogState = DogState.SHOOT;
        }

        super.update(player);

        // if dog is walking, determine which direction to walk in based on facing direction
        if (dogState == DogState.WALK) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
            }

            // if dog reaches the start or end location, it turns around
            // dog may end up going a bit past the start or end location depending on movement speed
            // this calculates the difference and pushes the enemy back a bit so it ends up right on the start or end location
            if (getX1() + getScaledWidth() >= endBound) {
                float difference = endBound - (getScaledX2());
                moveXHandleCollision(-difference);
                facingDirection = Direction.LEFT;
            } else if (getX1() <= startBound) {
                float difference = startBound - getX1();
                moveXHandleCollision(difference);
                facingDirection = Direction.RIGHT;
            }

            // if dog is shooting, it first turns read for 1 second
            // then the bone is actually shot out
        } else if (dogState == DogState.SHOOT) {
            if (previousdogState == DogState.WALK) {
                shootTimer.setWaitTime(500);
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer.isTimeUp()) {

                // define where bone will spawn on map (x location) relative to dog enemy's location
                // and define its movement speed
                int boneX;
                if (facingDirection == Direction.RIGHT) {
                    boneX = Math.round(getX()) + getScaledWidth();
                } else {
                    boneX = Math.round(getX());
                    boneMovementSpeed = -boneMovementSpeed;
                }

                // define where bone will spawn on the map (y location) relative to dog enemy's location
                int boneY = Math.round(getY()) + 4;

                // create bone enemy
                Bone bone = new Bone(new Point(boneX, boneY), boneMovementSpeed, 2000);

                // add bone enemy to the map for it to offically spawn in the level
                map.addProjectile(bone);

                // change dog back to its WALK state after shooting, reset shootTimer to wait another 2 seconds before shooting again
                dogState = DogState.WALK;
                shootTimer.setWaitTime(2000);
            }
        }
        previousdogState = dogState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if dog enemy collides with something on the x axis, it turns around and walks the other way
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<>() {{
            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200).withScale(2).withBounds(-5, 0, 35, 13).build(), new FrameBuilder(
                    spriteSheet.getSprite(0, 1), 200).withScale(2).withBounds(-5, 0, 35, 13).build(), new FrameBuilder(
                    spriteSheet.getSprite(0, 2), 200).withScale(2).withBounds(-5, 0, 35, 13).build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200).withScale(2).withBounds(0, 0, 35, 13).build(), new FrameBuilder(
                    spriteSheet.getSprite(1, 1), 200).withScale(2).withBounds(0, 0, 35, 13).build(), new FrameBuilder(spriteSheet.getSprite(1, 2),
                                                                                                                      200
            ).withScale(2).withBounds(0, 0, 35, 13).build()
            });

            put("SHOOT_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 2), 0).withScale(2).withBounds(-5, 0, 35, 13).build(),
                    });

            put("SHOOT_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 0).withScale(2).withBounds(0, 0, 35, 13).build(),
                    });
        }};
    }

    public enum DogState {
        WALK,
        SHOOT
    }
}
