package Level;

import Engine.KeyLocker;
import Engine.KeyboardAction;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.PlayerState.Facing;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public abstract class Player extends GameObject {

    private static final int ATTACK_DELAY = 1500, JUMP_DELAY = 5000;
    private static final float MAX_FALL_VELOCITY = 6f, MAX_DEATH_FALL_VELOCITY = 10f , DEATH_Y_VELOCITY = -2.5f;

    protected float gravity, jumpHeight, walkSpeed, sprintSpeed, sprintAcceleration;

    public static int PLAYER_HEALTH = 3;

    protected Stopwatch attackDelay, jumpDelay;


    protected PlayerState playerState;
    protected Facing facing;
    protected LevelState levelState;

    protected boolean inAir;

    /**
     * VelocityX is absolute, and direction is decided from the facing.mod
     */
    private float velocityX,
            velocityY;

    private final List<PlayerListener> playerListeners = new ArrayList<>();

    public Player(SpriteSheet spriteSheet, float x,float y, String startingAnimationName) {
        super(spriteSheet,x,y,startingAnimationName);

        playerState = PlayerState.STAND;
        levelState = LevelState.PLAYING;
        facing = Facing.RIGHT;

        attackDelay = new Stopwatch();
        jumpDelay = new Stopwatch();
    }


    public void update() {
        //Is the super.update() ordering specific? Unsure?
        super.update();

        switch (levelState) {
            case PLAYING -> updatePlaying();
            case DEAD -> updateDead();
            case WIN -> updateWin();
        }

        //Update Animation
        setCurrentAnimationName(playerState.get(facing));
    }


    private void updatePlaying() {
        applyGravity(MAX_FALL_VELOCITY);
        keepInBounds();

        playerState = PlayerState.STAND;

        //Update Player Action and Direction
        boolean move_leftDown = KeyboardAction.GAME_MOVE_LEFT.isDown();
        if(move_leftDown ^ KeyboardAction.GAME_MOVE_RIGHT.isDown()) {
            facing = move_leftDown ? Facing.LEFT : Facing.RIGHT;
            playerState = PlayerState.WALK;

            if(KeyboardAction.GAME_SPRINT.isDown()) {
                if(velocityX < walkSpeed) {
                    velocityX = walkSpeed;
                } else if(velocityX < sprintSpeed) {
                    velocityX *= sprintAcceleration;
                }
            } else {
                velocityX = walkSpeed;
            }
        } else {
            velocityX = 0;
        }

        //Update Jump
        if(KeyboardAction.GAME_JUMP.isDown()) {
            if(jumpDelay.isTimeUp() && !inAir) {
                velocityY = -jumpHeight;
            }
        } else if(velocityY < 0) { //if the player releases while velocity is still up, cut short, remember that velocityY is inverse
            velocityY = 0;
        }

        //Update Attack
        if(KeyboardAction.GAME_ATTACK.isDown() && attackDelay.isTimeUp()) {
            attack();
        }

        //If the player is in the air, set its animation based on velocityY
        if(inAir) {
            playerState = velocityY < 0 ? PlayerState.JUMP : PlayerState.FALL;
        }

        //Updates player to death if their health hits 0
        if(PLAYER_HEALTH <= 0) {
            levelState = LevelState.DEAD;
        }

        inAir = true;
        super.moveYHandleCollision(velocityY);
        super.moveXHandleCollision(velocityX * facing.mod);
    }

    private void attack() {
        int attackX = facing == Facing.RIGHT ? Math.round(getX()) + getScaledWidth() - 20 : Math.round(getX());
        map.addEnemy(new PlayerAttack(new Point(attackX, Math.round(getY()) + 10), facing.mod * 1.5f, 1000));
        attackDelay.setWaitTime(ATTACK_DELAY);
    }

    private void keepInBounds() {
        if(x < 0) {
            velocityX = 0;
            setX(0);
        } else if(levelState != LevelState.WIN && x > map.getRightBound()) {
            velocityX = 0;
            setX(map.getRightBound());
        }
    }

    private void updateDead() {
        playerState = PlayerState.DEATH;
        if(currentFrameIndex == getCurrentAnimation().length - 1) {
            if(map.getCamera().containsDraw(this)) {
                applyGravity(MAX_DEATH_FALL_VELOCITY);
            } else for(PlayerListener listener :playerListeners) {
                listener.onDeath();
            }
        } else {
            velocityY = DEATH_Y_VELOCITY;
        }
        setY(y + velocityY);
    }

    private void updateWin() {
        if(map.getCamera().containsDraw(this)) {
            facing = Facing.RIGHT;
            if(inAir) {
                playerState = PlayerState.FALL;
                applyGravity(MAX_FALL_VELOCITY);
                moveYHandleCollision(velocityY);
            } else {
                playerState = PlayerState.WALK;
                moveXHandleCollision(walkSpeed);
            }
        } else for(PlayerListener listener : playerListeners) {
            listener.onLevelCompleted();
        }
    }

    private void applyGravity(float maxFallVelocity) {
        if(velocityY < maxFallVelocity) {
            velocityY += gravity;
        }
    }

    public void hurtPlayer(MapEntity mapEntity) {
        switch(mapEntity.getCollisionType()) {
            case DAMAGE -> PLAYER_HEALTH -= 1;
            case INSTANT_DEATH -> PLAYER_HEALTH = 0;
            case PREVENT_JUMP -> preventJump(5000);
            case DEFAULT -> {}
        }
    }

    public void preventJump(int time) {
        jumpDelay.setWaitTime(JUMP_DELAY);
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

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        if(hasCollided) {
            if(direction == Direction.DOWN) {
                inAir = false;
            }
            velocityY = 0;
            handleCollision(MapTileCollisionHandler.lastCollidedTileY);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if(hasCollided) {
            handleCollision(MapTileCollisionHandler.lastCollidedTileX);
            if(playerState == PlayerState.WALK) {
                playerState = PlayerState.STAND;
            }
        }
    }

    private void handleCollision(MapTile tile) {
        if(tile != null && tile.getTileType() == TileType.LETHAL) {
            levelState = LevelState.DEAD;
        }
    }

}
