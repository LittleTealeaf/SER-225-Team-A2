package Level;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Game.GameState;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.ArrayList;

import Projectiles.Fireball;
import Projectiles.LazerBeam;
import Enemies.DinosaurEnemy.DinosaurState;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected float maxWalkSpeed = 0;
    protected float minWalkSpeed = 0;
    protected float walkAcceleration = 0;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityX = 0;
    
    // Number of times a player can be hit by a projectile before dying
    public static int playerHealth = 3;
    
    protected float momentumXIncrease = 0;

    // values used to handle player movement
    protected float jumpForce = 0;
    protected float momentumX = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;
    
    
    // values used to handle player movement
    protected float momentumY = 0;
    protected float moveAmountX, moveAmountY;
    
    protected Stopwatch attackCooldown = new Stopwatch();

    // values used to keep track of player's current state
    protected GameState levelTwo;
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected LevelState levelState;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key JUMP_KEY = Key.UP;
    protected Key MOVE_LEFT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_KEY = Key.RIGHT;
    protected Key CROUCH_KEY = Key.DOWN;
    protected Key rightKey = Key.D;
    protected Key leftKey = Key.A;
    protected Key upKey = Key.W;
    protected Key downKey = Key.S;
    protected Key spaceKey = Key.SPACE;
    protected Key attackKey = Key.E;
    protected Key shiftKey = Key.SHIFT;

    // if true, player cannot be hurt by enemies (good for testing)
    protected boolean isInvincible = false;
    // added 11/19
	protected PlayerAttack currentProjectile;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        levelState = LevelState.RUNNING;
        this.x =  x;
        this.y = y;
        
        // 11/19
        currentProjectile = null;
    }

    public void update() {
        moveAmountX = 0;
        moveAmountY = 0;

        // if player is currently playing through level (has not won or lost)
        if (levelState == LevelState.RUNNING) {
            applyGravity();

            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            previousAirGroundState = airGroundState;

            // update player's animation
            super.update();

            // move player with respect to map collisions based on how much player needs to move this frame
            super.moveYHandleCollision(moveAmountY);
            super.moveXHandleCollision(moveAmountX);

            updateLockedKeys();
        }

        // if player has beaten level
        else if (levelState == LevelState.LEVEL_COMPLETED) {
            updateLevelCompleted();
        }

        // if player has lost level
        else if (levelState == LevelState.PLAYER_DEAD) {
            updatePlayerDead();
        }
    }

    // add gravity to player, which is a downward force
    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case CROUCHING:
                playerCrouching();
                break;
            case JUMPING:
                playerJumping();
                break;
            // 11/19    
            case ATTACKING:
            	playerAttacking();
            	break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // sets animation to a STAND animation based on which way player is facing
        currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";

        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(rightKey) || Keyboard.isKeyDown(leftKey)) {
            playerState = PlayerState.WALKING;
        }

        // if jump key is pressed, player enters JUMPING state
        else if ((Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) || (Keyboard.isKeyDown(upKey) && !keyLocker.isKeyLocked(upKey)) || (Keyboard.isKeyDown(spaceKey) && !keyLocker.isKeyLocked(spaceKey))) {
            keyLocker.lockKey(JUMP_KEY);
            keyLocker.lockKey(upKey);
            keyLocker.lockKey(spaceKey);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed, player enters CROUCHING state
        else if (Keyboard.isKeyDown(CROUCH_KEY) || Keyboard.isKeyDown(downKey)) {
            playerState = PlayerState.CROUCHING;
        }
        
        // enter the attacking state if the attack key is pressed and the attack cooldown is up
        else if(Keyboard.isKeyDown(attackKey) && attackCooldown.isTimeUp()) {
        	//keyLocker.lockKey(attackKey);
        	playerState = PlayerState.ATTACKING;
        	//System.out.println(previousPlayerState.toString());
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        // sets animation to a WALK animation based on which way player is facing
        currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";

        // if the running key, shift, is held down acceleration the walk speed until the character reaches the max speed
        if (Keyboard.isKeyDown(shiftKey))
        {
        	if (walkSpeed < maxWalkSpeed)
        	{
        		walkSpeed = walkSpeed * walkAcceleration;
        	}
        	
        }
        // once the user lets go of the running key reset the walk speed
        else if (Keyboard.isKeyUp(shiftKey))
        {
        	walkSpeed = minWalkSpeed;
        }  
        
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(leftKey)) {
        	//System.out.println("s");
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
        }
        
        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(rightKey)) {
        	//System.out.println("d");
        	moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
        } else if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(rightKey) && Keyboard.isKeyUp(leftKey)) {
        	playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if ((Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) || (Keyboard.isKeyDown(upKey) && !keyLocker.isKeyLocked(upKey)) || (Keyboard.isKeyDown(spaceKey) && !keyLocker.isKeyLocked(spaceKey))) {
            keyLocker.lockKey(JUMP_KEY);
            keyLocker.lockKey(upKey);
            keyLocker.lockKey(spaceKey);
            //System.out.println("w");
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed,
        else if (Keyboard.isKeyDown(CROUCH_KEY) || Keyboard.isKeyDown(downKey)) {
            playerState = PlayerState.CROUCHING;
        }
        
        // enter the attacking state if the attack key is pressed and the attack cooldown is up
        else if(Keyboard.isKeyDown(attackKey) && attackCooldown.isTimeUp()) {
        	keyLocker.lockKey(attackKey);
        	playerState = PlayerState.ATTACKING;
        	//System.out.println(previousPlayerState.toString());
        }
    }

    // player CROUCHING state logic
    protected void playerCrouching() {
        // sets animation to a CROUCH animation based on which way player is facing
        currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";

        // if crouch key is released, player enters STANDING state
        if (Keyboard.isKeyUp(CROUCH_KEY) && Keyboard.isKeyUp(downKey)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if ((Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) || (Keyboard.isKeyDown(upKey) && !keyLocker.isKeyLocked(upKey)) || (Keyboard.isKeyDown(spaceKey) && !keyLocker.isKeyLocked(spaceKey))) {
            keyLocker.lockKey(JUMP_KEY);
            keyLocker.lockKey(upKey);
            keyLocker.lockKey(spaceKey);
            playerState = PlayerState.JUMPING;
        }
    }

    // player JUMPING state logic
    protected void playerJumping() {
        // if last frame player was on ground and this frame player is still on ground, the jump needs to be setup
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {
            // sets animation to a JUMP animation based on which way player is facing
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";

            // player is set to be in air and then player is sent into the air
            airGroundState = AirGroundState.AIR;
            jumpForce = jumpHeight;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
        }
        // if the player is no longer holding any of the jump keys set the jump force to 0 to stop the jump this allows the player to control how high they want to jump
        if (!(Keyboard.isKeyDown(JUMP_KEY) || (Keyboard.isKeyDown(upKey)) || Keyboard.isKeyDown(spaceKey)))
        {
        	jumpForce = 0;
        }
        
        
        // if player is in air (currently in a jump) and has more jumpForce, continue sending player upwards
        if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            // if player is moving upwards, set player's animation to jump. if player moving downwards, set player's animation to fall
            if (previousY > Math.round(y)) {
                currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            }

            // allows you to move left and right while in the air
            if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(leftKey)) {
                moveAmountX -= walkSpeed;
                facingDirection = Direction.LEFT;
            }             
            
            else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(rightKey)) {
                moveAmountX += walkSpeed;
                facingDirection = Direction.RIGHT;
            }

            // if player is falling, increases momentum as player falls so it falls faster over time
            if (moveAmountY > 0) {
                increaseMomentum();
            }
        }

        // if player last frame was in air and this frame is now on ground, player enters STANDING state
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
            
        }
    }
    
    // 11/19
    public void playerAttacking() {
    	if (playerState == PlayerState.ATTACKING) {
    		
    		// this allows the player to still move left or right will in the attacking state
    		 if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(leftKey)) {
                 moveAmountX -= walkSpeed;
                 facingDirection = Direction.LEFT;
             } 
             else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(rightKey)) {
                 moveAmountX += walkSpeed;
                 facingDirection = Direction.RIGHT;
             }
    		 
    		 
				// define where projectile will spawn on map (x location) relative to cat's
				// location
				// and define its movement speed
				int attackX;
				float movementSpeed;
				if (facingDirection == Direction.RIGHT) {
					attackX = Math.round(getX()) + getScaledWidth() - 20;
					movementSpeed = 1.5f;
				} else {
					attackX = Math.round(getX());
					movementSpeed = -1.5f;
				}

				// define where projectile will spawn on the map (y location) relative to
				// dinosaur enemy's location
				int attackY = Math.round(getY()) + 10;

				// create projectile
				PlayerAttack projectile = new PlayerAttack(new Point(attackX, attackY), movementSpeed, 1000);
				currentProjectile = projectile;

				// add projectile enemy to the map for it to offically spawn in the level
				map.addEnemy(projectile);

				attackCooldown.setWaitTime(1500);
                
               // after an attack finished set the player to a standing state
                playerState = PlayerState.STANDING;
               
            }
    }

    // while player is in air, this is called, and will increase momentumY by a set amount until player reaches terminal velocity
    protected void increaseMomentum() {
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void increaseMomentumX() {
        momentumX += momentumXIncrease;
        if (momentumX > terminalVelocityX) {
            momentumX = terminalVelocityX;
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(JUMP_KEY) && Keyboard.isKeyUp(upKey) && Keyboard.isKeyUp(spaceKey)) {
            keyLocker.unlockKey(JUMP_KEY);
            keyLocker.unlockKey(upKey);
            keyLocker.unlockKey(spaceKey);
        }
        // 11/19
        else if (Keyboard.isKeyUp(attackKey)) {
        	keyLocker.unlockKey(attackKey);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
    	// if the player collides with the coordinates specified below, it either stops (beginning of level) or goes back to the start (end of level)
    	if (direction == Direction.LEFT || direction == Direction.RIGHT) {
    		if (x < 0) {
    			hasCollided = true;
    			momentumX = 0;
    			setX(0);
    		}
    		else if (x > 2500) {
    			hasCollided = true;
    			momentumX = 0;
    			setX(0);
    			setY(0);
    		}
    	}
    	if (hasCollided && MapTileCollisionHandler.lastCollidedTileX != null) {
    		if (MapTileCollisionHandler.lastCollidedTileX.getTileType() == TileType.LETHAL) {
    			levelState = LevelState.PLAYER_DEAD;
    		}
    	}
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                playerState = PlayerState.JUMPING;
                airGroundState = AirGroundState.AIR;
            }
        }

        // if player collides with map tile upwards, it means it was jumping and then hit into a ceiling -- immediately stop upwards jump velocity
        else if (direction == Direction.UP) {
            if (hasCollided) {
                jumpForce = 0;
            }
        }
        if (hasCollided && MapTileCollisionHandler.lastCollidedTileY != null) {
    		if (MapTileCollisionHandler.lastCollidedTileY.getTileType() == TileType.LETHAL) {
    			levelState = LevelState.PLAYER_DEAD;
    		}
    	}
    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {
        System.out.println("Player Hurt by " + mapEntity.getClass());
        if (!isInvincible) {
            // if map entity is an enemy, kill player on touch
            if (mapEntity instanceof Enemy) {
            	playerHealth = 0;
            }
            if (mapEntity instanceof Projectile) {
            	playerHealth -= 1;
            }
            if (playerHealth <= 0) {
            	levelState = LevelState.PLAYER_DEAD;
           }
        }
    }

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
     
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        // if player is not on ground, player should fall until it touches the ground
        if (airGroundState != AirGroundState.GROUND && map.getCamera().containsDraw(this)) {
            currentAnimationName = "FALL_RIGHT";
            applyGravity();
            increaseMomentum();
            super.update();
            moveYHandleCollision(moveAmountY);
        }
        // move player to the right until it walks off screen
        else if (map.getCamera().containsDraw(this)) {
            currentAnimationName = "WALK_RIGHT";
            super.update();
            moveXHandleCollision(walkSpeed);
        } else {
            // tell all player listeners that the player has finished the level
            for (PlayerListener listener : listeners) {
                listener.onLevelCompleted();
            }
        }
    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {
        // change player animation to DEATH
        if (!currentAnimationName.startsWith("DEATH")) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "DEATH_RIGHT";
            } else {
                currentAnimationName = "DEATH_LEFT";
            }
            super.update();
        }
        // if death animation not on last frame yet, continue to play out death animation
        else if (currentFrameIndex != getCurrentAnimation().length - 1) {
          super.update();
        }
        // if death animation on last frame (it is set up not to loop back to start), player should continually fall until it goes off screen
        else if (currentFrameIndex == getCurrentAnimation().length - 1) {
            if (map.getCamera().containsDraw(this)) {
                moveY(3);
            } else {
                // tell all player listeners that the player has died in the level
                for (PlayerListener listener : listeners) {
                    listener.onDeath();
                }
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public AirGroundState getAirGroundState() {
        return airGroundState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }     
}


