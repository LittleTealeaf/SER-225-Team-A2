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

    //Static Values
    private static final int ATTACK_DELAY = 1500, JUMP_DELAY = 5000;

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

    protected float gravity, jumpHeight;

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
        keepInBounds();

        playerState = PlayerState.STAND;

        //Update Player Action and Direction
        boolean move_leftDown = KeyboardAction.GAME_MOVE_LEFT.isDown();
        if(move_leftDown ^ KeyboardAction.GAME_MOVE_RIGHT.isDown()) {
            facing = move_leftDown ? Facing.LEFT : Facing.RIGHT;
            playerState = PlayerState.WALK;
        }

        //Update Player X Direction


        //Update Jump
        if(KeyboardAction.GAME_JUMP.isDown()) {
            jump();
        } else if(velocityY < 0) { //if the player releases while velocity is still up, cut short, remember that velocityY is inverse
            velocityY = 0;
        }

        //Update Attack
        if(KeyboardAction.GAME_ATTACK.isDown() && attackDelay.isTimeUp()) {
            attack();
        }

        //If the player is in the air, set its animation based on velocityY
        if(inAir) {
            playerState = velocityY > 0 ? PlayerState.JUMP : PlayerState.FALL;
        }

        //Updates player to death if their health hits 0
        if(PLAYER_HEALTH <= 0) {
            levelState = LevelState.DEAD;
        }

        super.moveYHandleCollision(velocityY);
        super.moveXHandleCollision(velocityX);
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

    private void jump() {
        if(canJump() && keyLocker.isActionUnlocked(KeyboardAction.GAME_JUMP)) {
            velocityY = -jumpHeight;
        }
    }

    private void updateDead() {
        playerState = PlayerState.DEATH;
    }

    private void updateWin() {

    }

    private void applyGravity() {
        //Legacy code multiplies velocity by 2 beforehand
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
        if(direction == Direction.DOWN) {
            inAir = !hasCollided;
        }
        if(hasCollided) {
            velocityY = 0;
            handleCollision(MapTileCollisionHandler.lastCollidedTileY);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if(hasCollided) {
            handleCollision(MapTileCollisionHandler.lastCollidedTileX);
        }
    }

    private void handleCollision(MapTile tile) {
        if(tile != null && tile.getTileType() == TileType.LETHAL) {
            levelState = LevelState.DEAD;
        }
    }

}
