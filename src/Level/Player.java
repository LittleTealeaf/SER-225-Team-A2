package Level;

import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.Stopwatch;

public abstract class Player extends GameObject {

    protected float walkSpeed, maxWalkSpeed, minWalkSpeed, walkAcceleration, gravity, jumpHeight, jumpDegrade, terminalVelocityX;

    public static int PLAYER_HEALTH = 3;

    protected Stopwatch attackDelay, jumpDelay;

    protected PlayerState playerState;

    public Player(SpriteSheet spriteSheet, float x,float y, String startingAnimationName) {
        super(spriteSheet,x,y,startingAnimationName);
        playerState = PlayerState.STANDING;
        attackDelay = new Stopwatch();
        jumpDelay = new Stopwatch();
    }


    public void update() {
        switch(playerState) {
            case DYING -> updateDying();
            case WINNING -> updateWinning();
            default -> updatePlaying();
        }
    }

    public void updatePlaying() {

    }

    public void updateDying() {

    }

    public void updateWinning() {

    }
}
