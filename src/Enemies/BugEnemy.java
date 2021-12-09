package Enemies;

import Builders.FrameBuilder;
import Engine.GamePanel;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

/**
 *  This class is for the black bug enemy
 *  enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
 *  if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
 */
public class BugEnemy extends Enemy {

    private static final float GRAVITY = .5f;

    // different speeds depending on the difficulty
    private static final float NORMAL_SPEED = 0.5f, HARD_SPEED = 0.7f, HARDCORE_SPEED = 0.9f;
    private final Direction startFacingDirection;
    float movementSpeed = NORMAL_SPEED;
    private Direction facingDirection;
    private boolean isInAir;

    public BugEnemy(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BugEnemy.png"), 24, 15), "WALK_LEFT");
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        isInAir = false;
    }

    @Override
    public void update(Player player) {
        float moveAmountX = 0;
        float moveAmountY = 0;

        // set the movement speed of the enemy depending on what difficulty it selected
        if (GamePanel.getDifficulty() == 2) {
            movementSpeed = HARD_SPEED;
        } else if (GamePanel.getDifficulty() == 1) {
            movementSpeed = HARDCORE_SPEED;
        }

        // add gravity (if in air, this will cause bug to fall)
        moveAmountY += GRAVITY;

        // if on ground, walk forward based on facing direction
        if (!isInAir) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }
        }

        // move bug
        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);

        super.update(player);
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
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
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        // if bug is colliding with the ground, change its air ground state to GROUND
        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR
        if (direction == Direction.DOWN) {
            isInAir = !hasCollided;
        }
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<>() {{
            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(2).withBounds(6, 6, 12, 7).build(), new FrameBuilder(
                    spriteSheet.getSprite(0, 1), 100).withScale(2).withBounds(6, 6, 12, 7).build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(6, 6, 12,
                                                                                                                                            7
                                                                                                                                           ).build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(2).withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(
                            6, 6, 12, 7).build()
            });
        }};
    }
}
