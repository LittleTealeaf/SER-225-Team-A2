package Level;

import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.AirGroundState;
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

    public static int PLAYER_HEALTH = 3;

    protected Stopwatch attackDelay, jumpDelay;

    protected PlayerState playerState;
    protected boolean inAir, invincible;

    private List<PlayerListener> playerListeners = new ArrayList<>();

    public Player(SpriteSheet spriteSheet, float x,float y, String startingAnimationName) {
        super(spriteSheet,x,y,startingAnimationName);
        playerState = PlayerState.STANDING;
        attackDelay = new Stopwatch();
        jumpDelay = new Stopwatch();
    }


    public void update() {
//        Is the super.update() ordering specific? Unsure?
        super.update();
        switch(playerState) {
            case DYING -> updateDying();
            case WINNING -> updateWinning();
            default -> updatePlaying();
        }

    }



    private void updatePlaying() {

    }

    private void updateDying() {

    }

    private void updateWinning() {

    }

    public void hurtPlayer(MapEntity mapEntity) {
        if(!invincible) {
            switch(mapEntity.getCollisionType()) {

            }
        }
    }

    public void completeLevel() {
        playerState = PlayerState.WINNING;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void addListener(PlayerListener listener) {
        playerListeners.add(listener);
    }
}
