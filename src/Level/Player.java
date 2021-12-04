package Level;

import Engine.Collidable;
import Engine.KeyboardAction;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.PlayerState.Facing;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player as it navigates through a map.
 *
 * @author Thomas Kwashnak
 */
public abstract class Player extends GameObject {

    private static final int ATTACK_DELAY = 1500;
    private static final float MAX_FALL_VELOCITY = 6f, MAX_DEATH_FALL_VELOCITY = 10f, DEATH_Y_VELOCITY = -2.5f;
    public static int PLAYER_HEALTH = 3;
    private final List<PlayerListener> playerListeners = new ArrayList<>();
    private final Stopwatch attackDelay = new Stopwatch(), jumpDelay = new Stopwatch();
    protected float gravity, jumpHeight, walkSpeed, sprintSpeed, sprintAcceleration;
    private PlayerState playerState;
    private Facing facing;
    private LevelState levelState;
    private boolean inAir;
    private float absVelocityX, velocityY;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);

        playerState = PlayerState.STAND;
        levelState = LevelState.PLAYING;
        facing = Facing.RIGHT;
    }

    public void update() {
        super.update();

        //Redirects to update depending on player state
        switch (levelState) {
            case PLAYING -> updatePlaying();
            case DEAD -> updateDead();
            case WIN -> updateWin();
        }

        //Updates animation using the player's current playerState
        setCurrentAnimationName(playerState.get(facing));
    }

    /**
     * Updates the player as whenever it is in the playing state
     */
    private void updatePlaying() {
        //Applies gravity
        applyGravity(MAX_FALL_VELOCITY);

        //Update Player Action and Direction
        boolean moveLeftDown = KeyboardAction.GAME_MOVE_LEFT.isDown();
        boolean playerMove = moveLeftDown ^ KeyboardAction.GAME_MOVE_RIGHT.isDown(); //Only true if the player is moving in a direction
        facing = playerMove ? moveLeftDown ? Facing.LEFT : Facing.RIGHT : facing; //Update facing only if the player moved
        //Only move if the player moved and is not going out of bounds
        if (playerMove && ((facing == Facing.LEFT && x > 0) ^ (facing == Facing.RIGHT && x < map.getRightBound()))) {
            playerState = PlayerState.WALK;
            if (KeyboardAction.GAME_SPRINT.isDown() && absVelocityX >= walkSpeed) {
                if (absVelocityX < sprintSpeed) {
                    absVelocityX *= sprintAcceleration;
                }
            } else { //If player is not sprinting, or if the player started off sprinting from rest (where velocityX is 0)
                absVelocityX = walkSpeed;
            }
        } else { //Player is not moving
            absVelocityX = 0;
            playerState = PlayerState.STAND;
        }

        //Update Jump
        if (KeyboardAction.GAME_JUMP.isDown()) {
            if (jumpDelay.isTimeUp() && !inAir) {
                velocityY = -jumpHeight;
            }
        } else if (velocityY < 0) { //if the player releases while velocity is still up, cut short, remember that velocityY is inverse
            velocityY = 0;
        }

        //Update Attack
        if (KeyboardAction.GAME_ATTACK.isDown() && attackDelay.isTimeUp()) {
            int attackX = facing == Facing.RIGHT ? Math.round(getX()) + getScaledWidth() - 20 : Math.round(getX());
            map.addEnemy(new PlayerAttack(new Point(attackX, Math.round(getY()) + 10), facing.mod * 1.5f, 1000));
            attackDelay.setWaitTime(ATTACK_DELAY);
        }

        //If the player is in the air, set its animation based on velocityY
        if (inAir) {
            playerState = velocityY < 0 ? PlayerState.JUMP : PlayerState.FALL;
        } else if (KeyboardAction.GAME_CROUCH.isDown()) {
            absVelocityX = 0;
            playerState = PlayerState.CROUCH;
        }

        inAir = true; //inAir is updated in collisions
        //Moves while handling collisions
        moveYHandleCollision(velocityY);
        moveXHandleCollision(absVelocityX * facing.mod);

        //Updates player to death if their health hits 0
        if (PLAYER_HEALTH <= 0) {
            levelState = LevelState.DEAD;
        }
    }

    /**
     * Updates the player while it is dead
     */
    private void updateDead() {
        playerState = PlayerState.DEATH;
        if (currentFrameIndex > 0) { //Checks if it is not the first frame of the player's death animation
            if (map.getCamera().containsDraw(this)) {
                applyGravity(MAX_DEATH_FALL_VELOCITY);
            } else {
                for (PlayerListener listener : playerListeners) {
                    listener.onDeath();
                }
            }
        } else { //Only activates on the first frame of death animation
            velocityY = DEATH_Y_VELOCITY;
        }
        //Updates Y. Does not use "moveYHandleCollision" since the player can move through the map
        setY(y + velocityY);
    }

    /**
     * Updates the player after it completes a level
     */
    private void updateWin() {
        if (map.getCamera().containsDraw(this)) { //If the player can seen on the map
            facing = Facing.RIGHT;
            if (inAir) { //If the player is still falling
                playerState = PlayerState.FALL;
                applyGravity(MAX_FALL_VELOCITY);
                moveYHandleCollision(velocityY);
            } else { //Once the player hits the ground, walk to the right
                playerState = PlayerState.WALK;
                moveX(walkSpeed);
            }
        } else {
            for (PlayerListener listener : playerListeners) {
                //When the player is off the map, send a onLevelCompleted() to all listeners
                listener.onLevelCompleted();
            }
        }
    }

    /**
     * Updates the current velocityY with the current gravity
     *
     * @param maxFallVelocity Gravity Force
     */
    private void applyGravity(float maxFallVelocity) {
        if (velocityY < maxFallVelocity) {
            velocityY += gravity;
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if (hasCollided) { //If it has collided, handles the collision and sets the player's animation to standing
            handleCollision(MapTileCollisionHandler.lastCollidedTileX);
            if (playerState == PlayerState.WALK) { //If the player is walking (aka, not falling), then make the player standing
                playerState = PlayerState.STAND;
            }
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        if (hasCollided) {
            if (direction == Direction.DOWN) { //If direction is down, make inAir false as the player is ontop of something
                inAir = false;
            }
            velocityY = 0; //Reset velocity
            handleCollision(MapTileCollisionHandler.lastCollidedTileY);
        }
    }

    private void handleCollision(MapTile tile) {
        if (tile != null && tile.getTileType() == TileType.LETHAL) {
            //If the player hits a lethal tile, set its state to dead
            levelState = LevelState.DEAD;
        }
    }

    public void hurtPlayer(MapEntity mapEntity) {
        if (mapEntity instanceof Collidable.Damage) {
            PLAYER_HEALTH -= ((Damage) mapEntity).getDamage();
        } else if (mapEntity instanceof Collidable.InstantDeath) {
            PLAYER_HEALTH = 0;
        }
        if (mapEntity instanceof Collidable.PreventJump) {
            jumpDelay.setWaitTime(((Collidable.PreventJump) mapEntity).getJumpDelay());
        }
    }

    /**
     * Indicates that the player wins, and starts the winning animation
     */
    public void completeLevel() {
        levelState = LevelState.WIN;
    }

    /**
     * @return {@code True} if the player is in the air, {@code false} if the player is on some surface
     */
    public boolean isInAir() {
        return inAir;
    }

    /**
     * Adds a listener
     *
     * @param listener Methods to be checked whenever the player executes an event
     */
    public void addListener(PlayerListener listener) {
        playerListeners.add(listener);
    }

    /**
     * Updates the jump height of the player
     *
     * @param height Velocity to apply when the player jumps
     */
    public void setJumpHeight(int height) {
        this.jumpHeight = height;
    }
}
