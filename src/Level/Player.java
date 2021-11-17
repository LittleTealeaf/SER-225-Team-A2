package Level;

import Engine.KeyLocker;
import Engine.KeyboardAction;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.PlayerState.Facing;
import Utils.Direction;
import Utils.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public abstract class Player extends GameObject {

/*
Values set by the cat class
        gravity = .5f; -> amount that the velocity moves in the y direction
        terminalVelocityY = 6f;
        jumpHeight = 14.5f;
        jumpDegrade = .5f;
        walkSpeed = 2.1f;
        minWalkSpeed = 2.1f;
        maxWalkSpeed = 3.3f;
        walkAcceleration = 1.05f;
        momentumYIncrease = .5f;
 */

    /*
    Ideas for how to manage movement with minimal modifications:
    Point velocity -> current velocity, modified by gravity, etc.
    float gravity -> gravity force

     */

    protected float gravity;

    public static int PLAYER_HEALTH = 3;

    protected Stopwatch attackDelay, jumpDelay;
    private KeyLocker keyLocker = new KeyLocker();


    protected PlayerState playerState;
    protected Facing facing;
    protected LevelState levelState;

    protected boolean inAir, invincible;

    // Velocities
    protected float velocityX, velocityY;

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

        //List of keys that require to be tapped
        keyLocker.setAction(KeyboardAction.GAME_JUMP);
    }


    private void updatePlaying() {
        applyGravity();

    }

    private void updateDead() {

    }

    private void updateWin() {

    }

    private void applyGravity() {
        //Legacy code multiplies velocity by 2 beforehand
        velocityY *= 2;
        velocityY += gravity;
    }

    public void hurtPlayer(MapEntity mapEntity) {
//        Do we even still need the invincible value?
        if(!invincible) {
            switch(mapEntity.getCollisionType()) {
                case DAMAGE -> PLAYER_HEALTH -= 1;
                case INSTANT_DEATH -> PLAYER_HEALTH = 0;
                case PREVENT_JUMP -> preventJump(5000);
                case DEFAULT -> {}
            }
        }
    }

    /**
     * @return {@code True} when the player is allowed to jump, and is on the ground
     */
    public boolean canJump() {
        return jumpDelay.isTimeUp() && !isInAir();
    }

    public void preventJump(int time) {
        jumpDelay.setWaitTime(5000);
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

    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        if(direction == Direction.DOWN) {
            if(hasCollided) {
                velocityY = 0;
            }
        }
    }
}
