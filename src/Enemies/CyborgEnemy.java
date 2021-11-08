package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Projectiles.LazerBeam;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;
import java.util.Timer;

// This class is for the cyborg
// It walks back and forth between two set points (startLocation and endLocation)
// Every so often (based on shootTimer) the cyborg will shoot lazers from its arms
public class CyborgEnemy extends Enemy {

    // start and end location defines the two points that it walks between
    // is only made to walk along the x axis and has no air ground state logic, so make sure both points have the same Y value
    protected Point startLocation;
    protected Point endLocation;
    
    // Whether or not the game is waiting for LazerBeams to launch
    private boolean wait = false;

    protected float movementSpeed = 0.5f;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    // timer is used to determine when a lazer is to be shot out
    protected Stopwatch shootTimer = new Stopwatch();
    
    protected Stopwatch delay = new Stopwatch();

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected cyborgState cyborgState;
    protected cyborgState previouscyborgState;

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
        cyborgState = cyborgState.WALK;
        previouscyborgState = cyborgState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        // every 2 seconds, the lazer will be shot out
        shootTimer.setWaitTime(2000);
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        // if shoot timer is up and cyborg is not currently shooting, set its state to SHOOT
        if (shootTimer.isTimeUp() && cyborgState != cyborgState.SHOOT) {
            cyborgState = cyborgState.SHOOT;
        }

        super.update(player);

        // if cyborg is walking, determine which direction to walk in based on facing direction
        if (cyborgState == cyborgState.WALK) {
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
        } else if (cyborgState == cyborgState.SHOOT) {
            if (previouscyborgState == cyborgState.WALK) {
                shootTimer.setWaitTime(300);
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT" : "SHOOT";
            } else if (shootTimer.isTimeUp()) {

                // define where LazerBeam will spawn on map (x location) relative to cyborg enemy's location
                // and define its movement speed
                int LazerBeamX;
                float movementSpeed;
                LazerBeamX = Math.round(getX());
                movementSpeed = 1.5f;

                // define where LazerBeam will spawn on the map (y location) relative to cyborg enemy's location
                int LazerBeamY = Math.round(getY() + 15);

                // create LazerBeam enemy
                LazerBeam LazerBeam = new LazerBeam(new Point(LazerBeamX + 20, LazerBeamY), movementSpeed, 1000);
                LazerBeam LazerBeam2 = new LazerBeam(new Point(LazerBeamX - 10, LazerBeamY), -movementSpeed, 1000);

                // add LazerBeam enemy to the map for it to offically spawn in the level
                map.addProjectile(LazerBeam);
                map.addProjectile(LazerBeam2);

                // change cyborg back to its WALK state  0.5 seconds after shooting, reset shootTimer to wait another 2 seconds before shooting again
                delay.setWaitTime(500);
                wait = true;
                shootTimer.setWaitTime(2000);
            }
            // 
            else if(delay.isTimeUp() && wait == true) {
            	cyborgState = cyborgState.WALK;
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
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
		                    .withScale(2)
		                    .withBounds(0, 0, 7, 22)
		                    .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(2)
                            .withBounds(0, 0, 13, 22)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
                            .withScale(2)
                            .withBounds(0, 0, 7, 22)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2)
                            .withBounds(0, 0, 13, 22)
                            .build(),
            });

            put("WALK_RIGHT", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
		                    .withScale(2)
		                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
		                    .withBounds(0, 0, 7, 22)
		                    .build(),
            		new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 0, 13, 22)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 0, 7, 22)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 0, 13, 22)
                            .build(),
            });

            put("SHOOT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(2)
                            .withBounds(0, 0, 13, 22)
                            .build(),
            });
        }};
    }

    public enum cyborgState {
        WALK, SHOOT
    }
}
