package Enemies;

import Builders.FrameBuilder;
import Engine.GamePanel;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Projectiles.LazerBeam;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;

/**
 *  This class is for the cyborg
 *  It walks back and forth between two set points (startLocation and endLocation)
 *  Every so often (based on shootTimer) the cyborg will shoot lazers from its arms
 */
public class CyborgEnemy extends Enemy {

    // different speeds depending on the difficulty
    private static final float NORMAL_SPEED = 0.5f, HARD_SPEED = 0.7f, HARDCORE_SPEED = 0.9f;
    private static final float NORMAL_LAZER_SPEED = 1.5f, HARD_LAZER_SPEED = 1.7f, HARDCORE_LAZER_SPEED = 1.9f;
    // start and end location defines the two points that it walks between
    // is only made to walk along the x axis and has no air ground state logic, so make sure both points have the same Y value
    protected final Point startLocation;
    protected final Point endLocation;
    // timer is used to determine when a lazer is to be shot out
    protected final Stopwatch shootTimer = new Stopwatch();
    protected final Stopwatch delay = new Stopwatch();
    private final Direction startFacingDirection;
    protected Direction facingDirection;
    protected boolean isInAir;
    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected CyborgState cyborgState;
    protected CyborgState previouscyborgState;
    float movementSpeed = NORMAL_SPEED;
    // Whether or not the game is waiting for LazerBeams to launch
    private boolean wait = false;

    public CyborgEnemy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("CyborgEnemy.png"), 14, 23), "WALK_LEFT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        cyborgState = CyborgState.WALK;
        previouscyborgState = cyborgState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        isInAir = false;

        // every 2 seconds, the lazer will be shot out
        shootTimer.setWaitTime(2000);
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;
        float lazerMovementSpeed = NORMAL_LAZER_SPEED;

        // set the movement speed of the enemy and lazer attack depending on what difficulty it selected
        if (GamePanel.getDifficulty() == 2) {
            movementSpeed = HARD_SPEED;
            lazerMovementSpeed = HARD_LAZER_SPEED;
        } else if (GamePanel.getDifficulty() == 1) {
            movementSpeed = HARDCORE_SPEED;
            lazerMovementSpeed = HARDCORE_LAZER_SPEED;
        }

        // if shoot timer is up and cyborg is not currently shooting, set its state to SHOOT
        if (shootTimer.isTimeUp() && cyborgState != CyborgState.SHOOT) {
            cyborgState = CyborgState.SHOOT;
        }

        super.update(player);

        // if cyborg is walking, determine which direction to walk in based on facing direction
        if (cyborgState == CyborgState.WALK) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
            }

            // if cyborg reaches the start or end location, it turns around
            // cyborg may end up going a bit past the start or end location depending on movement speed
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

            // if cyborg is shooting, it first turns read for 1 second
            // then the LazerBeam is actually shot out
        } else if (cyborgState == CyborgState.SHOOT) {
            if (previouscyborgState == CyborgState.WALK) {
                shootTimer.setWaitTime(300);
                currentAnimationName = "SHOOT";
            } else if (shootTimer.isTimeUp()) {

                // define where LazerBeam will spawn on map (x location) relative to cyborg enemy's location
                // and define its movement speed
                int LazerBeamX;
                LazerBeamX = Math.round(getX());

                // define where LazerBeam will spawn on the map (y location) relative to cyborg enemy's location
                int LazerBeamY = Math.round(getY() + 15);

                // create LazerBeam enemy
                LazerBeam LazerBeam = new LazerBeam(new Point(LazerBeamX + 20, LazerBeamY), lazerMovementSpeed, 1000);
                LazerBeam LazerBeam2 = new LazerBeam(new Point(LazerBeamX - 10, LazerBeamY), -lazerMovementSpeed, 1000);

                // add LazerBeam enemy to the map for it to offically spawn in the level
                map.addProjectile(LazerBeam);
                map.addProjectile(LazerBeam2);

                // change cyborg back to its WALK state  0.5 seconds after shooting, reset shootTimer to wait another 2 seconds before shooting again
                delay.setWaitTime(500);
                wait = true;
                shootTimer.setWaitTime(2000);
            }
            // 
            else if (delay.isTimeUp() && wait) {
                cyborgState = CyborgState.WALK;
                wait = false;
            }
        }
        previouscyborgState = cyborgState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if cyborg enemy collides with something on the x axis, it turns around and walks the other way
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
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200).withScale(2).withBounds(0, 0, 7, 22).build(), new FrameBuilder(
                    spriteSheet.getSprite(0, 0), 200).withScale(2).withBounds(0, 0, 13, 22).build(), new FrameBuilder(
                    spriteSheet.getSprite(0, 1), 200).withScale(2).withBounds(0, 0, 7, 22).build(), new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2).withBounds(
                            0, 0, 13, 22).build(),
                    });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(
                            0, 0, 7, 22).build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(
                            0, 0, 13, 22).build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(
                            0, 0, 7, 22).build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(
                            0, 0, 13, 22).build(),
                    });

            put("SHOOT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200).withScale(2).withBounds(0, 0, 13, 22).build(),
                    });
        }};
    }

    public enum CyborgState {
        WALK,
        SHOOT
    }
}
