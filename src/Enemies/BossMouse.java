package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy.DinosaurState;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

// This class is for the black bug enemy
// enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
// if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
public class BossMouse extends Enemy {

	// timer is used to determine when a fireball is to be shot out
	protected Stopwatch shootTimer = new Stopwatch();

	// can be either WALK or SHOOT based on what the enemy is currently set to do
	protected DinosaurState dinosaurState;
	protected DinosaurState previousDinosaurState;
	private float gravity = 1f;
	private float movementSpeed = 1f;
	private Direction startFacingDirection;
	private Direction facingDirection;
	private AirGroundState airGroundState;

	public BossMouse(Point location, Direction facingDirection) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Mouse.png"), 23, 24), "WALK_LEFT");
		this.startFacingDirection = facingDirection;
		this.initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		dinosaurState = DinosaurState.WALK;
		facingDirection = startFacingDirection;
		if (facingDirection == Direction.RIGHT) {
			currentAnimationName = "WALK_RIGHT";
		} else if (facingDirection == Direction.LEFT) {
			currentAnimationName = "WALK_LEFT";
		}
		airGroundState = AirGroundState.GROUND;
		shootTimer.setWaitTime(50);
	}

	@Override
	public void update(Player player) {
		float moveAmountX = 0;
		float moveAmountY = 0;

		if (shootTimer.isTimeUp() && dinosaurState != DinosaurState.SHOOT) {
			dinosaurState = DinosaurState.SHOOT;
		}

		// add gravity (if in air, this will cause bug to fall)
		moveAmountY += gravity;

		// if on ground, walk forward based on facing direction
		if (airGroundState == AirGroundState.GROUND) {
			if (facingDirection == Direction.RIGHT) {
				moveAmountX += movementSpeed;
			} else {
				moveAmountX -= movementSpeed;
			}

			// move bug
			moveYHandleCollision(moveAmountY);
			moveXHandleCollision(moveAmountX);

			super.update(player);

		}
		if (dinosaurState == DinosaurState.SHOOT) {
			if (previousDinosaurState == DinosaurState.WALK) {
				shootTimer.setWaitTime(100);
				currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
			} else if (shootTimer.isTimeUp()) {

				// define where fireball will spawn on map (x location) relative to dinosaur
				// enemy's location
				// and define its movement speed
				int cheeseX;
				float movementSpeed;
				if (facingDirection == Direction.RIGHT) {
					cheeseX = Math.round(getX()) + getScaledWidth();
					movementSpeed = 5f;
				} else {
					cheeseX = Math.round(getX());
					movementSpeed = -5f;
				}

				// define where fireball will spawn on the map (y location) relative to dinosaur
				// enemy's location
				int cheeseY = Math.round(getY()) + 120;

				// create Fireball enemy
				Cheese cheese = new Cheese(new Point(cheeseX, cheeseY), movementSpeed, 10000);

				// add fireball enemy to the map for it to offically spawn in the level
				map.addEnemy(cheese);

				// change dinosaur back to its WALK state after shooting, reset shootTimer to
				// wait another 2 seconds before shooting again
				dinosaurState = DinosaurState.WALK;
				shootTimer.setWaitTime(700);
			}
		}
		previousDinosaurState = dinosaurState;
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
		// if it is not colliding with the ground, it means that it's currently in the
		// air, so its air ground state is changed to AIR
		if (direction == Direction.DOWN) {
			if (hasCollided) {
				airGroundState = AirGroundState.GROUND;
			} else {
				airGroundState = AirGroundState.AIR;
			}
		}
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("WALK_LEFT",
						new Frame[] {
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(10)
										.withBounds(8, 8, 11, 12).build(),
								new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(10)
										.withBounds(8, 8, 11, 12).build() });

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(10)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build(),
						new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(10)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build() });
			}
		};
	}
}
