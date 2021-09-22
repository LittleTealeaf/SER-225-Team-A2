package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

// This class is for the black bug enemy
// enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
// if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
public class Mouse extends Enemy {

	private float gravity = 1f;
	private float movementSpeed = 1f;
	private Direction startFacingDirection;
	private Direction facingDirection;
	private AirGroundState airGroundState;

	public Mouse(Point location, Direction facingDirection) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Mouse.png"), 23, 24), "WALK_LEFT");
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
		airGroundState = AirGroundState.GROUND;
	}

	@Override
	public void update(Player player) {
		float moveAmountX = 0;
		float moveAmountY = 0;

		// add gravity (if in air, this will cause bug to fall)
		moveAmountY += gravity;

		// if on ground, walk forward based on facing direction
		if (airGroundState == AirGroundState.GROUND) {
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
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3).withBounds(8, 8, 11, 12)
										.build(),
								new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3).withBounds(8, 8, 11, 12)
										.build() });

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build(),
						new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build() });
			}
		};
	}
}
