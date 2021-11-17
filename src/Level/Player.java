package Level;

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
 * @author Thomas Kwashnak
 */
public abstract class Player extends GameObject {

    private static final int ATTACK_DELAY = 1500, JUMP_DELAY = 5000;
    private static final float MAX_FALL_VELOCITY = 6f, MAX_DEATH_FALL_VELOCITY = 10f, DEATH_Y_VELOCITY = -2.5f;
    public static int PLAYER_HEALTH = 3;
    protected float gravity, jumpHeight, walkSpeed, sprintSpeed, sprintAcceleration;
    private final List<PlayerListener> playerListeners = new ArrayList<>();
    private final Stopwatch attackDelay = new Stopwatch(), jumpDelay = new Stopwatch();
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
        switch (levelState) {
            case PLAYING -> updatePlaying();
            case DEAD -> updateDead();
            case WIN -> updateWin();
        }

        setCurrentAnimationName(playerState.get(facing));
    }

    private void updatePlaying() {
        applyGravity(MAX_FALL_VELOCITY);

        //Update Player Action and Direction
        boolean moveLeftDown = KeyboardAction.GAME_MOVE_LEFT.isDown();
        boolean playerMove = moveLeftDown ^ KeyboardAction.GAME_MOVE_RIGHT.isDown(); //Only true if the player is moving in a direction
        facing = playerMove ? moveLeftDown ? Facing.LEFT : Facing.RIGHT : facing; //Update facing if the player moved
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
        }

        //Updates player to death if their health hits 0
        if (PLAYER_HEALTH <= 0) {
            levelState = LevelState.DEAD;
        }

        inAir = true; //air is decided in moveYHandleCollision()
        super.moveYHandleCollision(velocityY);
        super.moveXHandleCollision(absVelocityX * facing.mod);
    }

    private void updateDead() {
        playerState = PlayerState.DEATH;
        if (currentFrameIndex > 0) {
            if (map.getCamera().containsDraw(this)) {
                applyGravity(MAX_DEATH_FALL_VELOCITY);
            } else for (PlayerListener listener : playerListeners) {
                listener.onDeath();
            }
        } else {
            velocityY = DEATH_Y_VELOCITY;
        }
        setY(y + velocityY);
    }

    private void updateWin() {
        if (map.getCamera().containsDraw(this)) {
            facing = Facing.RIGHT;
            if (inAir) {
                playerState = PlayerState.FALL;
                applyGravity(MAX_FALL_VELOCITY);
                moveYHandleCollision(velocityY);
            } else {
                playerState = PlayerState.WALK;
                moveXHandleCollision(walkSpeed);
            }
        } else for (PlayerListener listener : playerListeners) {
            listener.onLevelCompleted();
        }
    }

    private void applyGravity(float maxFallVelocity) {
        if (velocityY < maxFallVelocity) {
            velocityY += gravity;
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if (hasCollided) {
            handleCollision(MapTileCollisionHandler.lastCollidedTileX);
            if (playerState == PlayerState.WALK) {
                playerState = PlayerState.STAND;
            }
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        if (hasCollided) {
            if (direction == Direction.DOWN) {
                inAir = false;
            }
            velocityY = 0;
            handleCollision(MapTileCollisionHandler.lastCollidedTileY);
        }
    }

    private void handleCollision(MapTile tile) {
        if (tile != null && tile.getTileType() == TileType.LETHAL) {
            levelState = LevelState.DEAD;
        }
    }

    public void hurtPlayer(MapEntity mapEntity) {
        switch (mapEntity.getCollisionType()) {
            case DAMAGE -> PLAYER_HEALTH -= 1;
            case INSTANT_DEATH -> PLAYER_HEALTH = 0;
            case PREVENT_JUMP -> jumpDelay.setWaitTime(JUMP_DELAY);
        }
    }

    public void completeLevel() {
        levelState = LevelState.WIN;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void addListener(PlayerListener listener) {
        playerListeners.add(listener);
    }

    public void setJumpHeight(int height) {
        this.jumpHeight = height;
    }
}
