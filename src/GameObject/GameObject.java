package GameObject;

import Builders.FrameBuilder;
import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.Vector;
import Game.GameThread;
import Level.Map;
import Level.MapTile;
import Level.MapTileCollisionHandler;
import Utils.Direction;
import Utils.MathUtils;
import Utils.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The all important GameObject class is what every "entity" used in this game should be based off of
 * 	It encapsulates all the other class logic in the GameObject package to be a "one stop shop" for all entity needs
 * 	This includes:
 * 	1. displaying an image (as a sprite) to represent the entity
 * 	2. animation logic for the sprite
 * 	3. collision detection with a map
 * 	4. performing proper draw logic based on camera movement
 *
 * @author Thomas Kwashnak
 * @author Alex Thimineur + Others
 */

/*
 * GOALS with a potential rewrite
 *  - Enable float-based locations -> more accurate positioning
 *  - Add "move velocity" to move a specific velocity, scale it with the current time difference, and check collisions
 */
public class GameObject extends AnimatedSprite implements Drawable {

	protected Vector startPosition, amountMoved;

	protected MapTile lastCollided;

	// the map instance this game object "belongs" to.
	protected Map map;

	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation) {
		super(spriteSheet, x, y, startingAnimation);
		startPosition = new Vector(x, y);
	}

	public GameObject(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(x, y, animations, startingAnimation);
		startPosition = new Vector(x, y);
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
		startPosition = new Vector(x, y);
	}

	public GameObject(BufferedImage image, float x, float y) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0).build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		startPosition = new Vector(x, y);
	}

	public GameObject(BufferedImage image, float x, float y, float scale) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		startPosition = new Vector(x, y);
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		startPosition = new Vector(x, y);
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[]{
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.withBounds(bounds)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		startPosition = new Vector(x, y);
	}

//	@Override
//	public void update() {
//		// call to animation logic
//		super.update();
//
//		// update previous position to be the current position
//
//	}

	/**
	 * Moves the game object by a given velocity, barring impacts from collisions (within the map instance)
	 * @param providedVelocity Amount to move. Will not be modified during movement. Will scale down to the current scale from
	 * {@link GameThread#getScale()}
	 * @author Thomas Kwashnak
	 */
	public void moveHandleCollision(Vector providedVelocity) {


		//Copies the velocity scaled with current frame time
		Vector velocity = providedVelocity.getMultiplied(GameThread.getScale());
		//Gets the unit vector, which is basically the unit-circle direction that the velocity is pointing towards
		final Vector unit = providedVelocity.getUnit();
		final Vector negativeUnit = unit.getNegative();


		//Inch the object by integer increments to get closer to the position
		int intIterations = (int) velocity.getMagnitude();
		for(int i = 0; i < intIterations; i++) {
			//Moves a unit closer and reduces the "velocity left" by 1
			move(unit);
			velocity.add(negativeUnit);
			MapTile collision = getCollision(unit);
			if(collision != null) {
				//Move back and then break out of the loop
				velocity.add(unit);
				move(negativeUnit);
				break;
			}
		}


	}

	/**
	 * Checks if the game object has collided with any map tiles, and returns the collided map-tile if it has. Otherwise, returns null
	 *
	 * Copied from CollisionHandler
	 * @param velocity
	 * @return
	 */
	private MapTile getCollision(Vector velocity) {
		//TODO modify / recreate this code to iterate through all neighbor blocks only in the half direction of the velocity
//		int numberOfTilesToCheck = Math.max(getScaledBounds().getWidth() / getMap().getTileset().getScaledSpriteWidth() + 2, 3);

		Point tileIndex = getMap().getTileIndexByPosition(getScaledBounds().getPos1());

		int rangeWidth = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth() + 3;
		int rangeHeight = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight() + 3;

		MapTile[] tiles = map.getTilesInBounds((int) tileIndex.x - 1, (int) tileIndex.y - 1, rangeWidth, rangeHeight);

		for(MapTile tile : tiles) {
			if(checkCollision(tile,velocity)) {
				return tile;
			}
		}

//		MapTile[] tiles = map.getRange((int) tileIndex.x - 1, (int) tileIndex.y - 1,
//									   getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth() + 2, )

//		for(int i = -1; i < numberOfTilesToCheck + 1; i++) {
//			for(int j = -1;j < numberOfTilesToCheck + 1; j++) {
//				MapTile mapTile = getMap().getTileByPosition((int) tileIndex.x + i, (int) tileIndex.y + j);
//				System.out.println((tileIndex.x + i) + " " + (tileIndex.y + j) + " " + mapTile.getTileIndex());
//				if(checkCollision(mapTile, velocity)) {
//					return mapTile;
//				}
//			}
//		}
		return null;
	}

//	/**
//	 * Copied from CollisionHandler
//	 * @param mapTile
//	 * @param velocity
//	 * @return
//	 */
//	private boolean checkCollision(MapTile mapTile, Vector velocity) {
//		return mapTile != null && hasCollidedWithMapTile(mapTile,velocity);
//	}

	/**
	 * Copied from CollisionHandler
	 * @param mapTile
	 * @param velocity
	 * @return
	 */
	private boolean checkCollision(MapTile mapTile, Vector velocity) {
		return mapTile != null && switch (mapTile.getTileType()) {
			case NOT_PASSABLE, LETHAL -> intersects(mapTile);
			case JUMP_THROUGH_PLATFORM -> velocity.getY() > 0 && intersects(mapTile) && Math.round(getScaledBoundsY2() -1) == Math.round(
					mapTile.getScaledBoundsY1());
			case PASSABLE -> false;
		};
	}

	// move game object along the x axis
	// will stop object from moving based on map collision logic (such as if it hits a solid tile)
	@Deprecated
	public void moveXHandleCollision(float dx) {
		if (map != null) {
			handleCollisionX(dx);
		} else {
			super.moveX(dx);
		}
	}

	// move game object along the y axis
	// will stop object from moving based on map collision logic (such as if it hits a solid tile)
	@Deprecated
	public void moveYHandleCollision(float dy) {
		if (map != null) {
			handleCollisionY(dy);
		} else {
			super.moveY(dy);
		}
	}

	@Deprecated
	private int magnitudeToInt(float magnitude) {
		return (int) magnitude;
	}

	// performs collision check logic for moving along the x axis against the map's tiles
	@Deprecated
	public float handleCollisionX(float moveAmountX) {
		// determines amount to move (whole number)
		int amountToMove = magnitudeToInt(moveAmountX);

		// gets decimal remainder from amount to move
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);

		// determines direction that will be moved in based on if moveAmountX is positive or negative
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;

		// moves game object one pixel at a time until total move amount is reached
		// if at any point a map tile collision is determined to have occurred from the move,
		// move player back to right in front of the "solid" map tile's position, and stop attempting to move further
		float amountMoved = 0;
		boolean hasCollided = false;
		for (int i = 0; i < amountToMove; i++) {
			moveX(direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setX(newLocation);
				break;
			}
			amountMoved = (i + 1) * direction.getVelocity();
		}

		// if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountXRemainder)
		// it starts by moving the game object by that decimal amount
		// it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
		// if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
		if (!hasCollided) {
			moveX(moveAmountXRemainder * direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setX(newLocation);
			}
		}

		// call this method which a game object subclass can override to listen for collision events and react accordingly
		onEndCollisionCheckX(hasCollided, direction);

		// returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
		return amountMoved + (moveAmountXRemainder * direction.getVelocity());
	}

	// performs collision check logic for moving along the y axis against the map's tiles
	@Deprecated
	public float handleCollisionY(float moveAmountY) {
		// determines amount to move (whole number)
		int amountToMove = magnitudeToInt(moveAmountY);

		// gets decimal remainder from amount to move
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);

		// determines direction that will be moved in based on if moveAmountY is positive or negative
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;

		// moves game object one pixel at a time until total move amount is reached
		// if at any point a map tile collision is determined to have occurred from the move,
		// move player back to right in front of the "solid" map tile's position, and stop attempting to move further
//		float amountMoved = 0;
//		boolean hasCollided = false;
//		for (int i = 0; i < amountToMove; i++) {
//			moveY(direction.getVelocity());
//			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
//			if (newLocation != 0) {
//				hasCollided = true;
//				setY(newLocation);
//				break;
//			}
//			amountMoved = (i + 1) * direction.getVelocity();
//		}
//
//		// if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountYRemainder)
//		// it starts by moving the game object by that decimal amount
//		// it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
//		// if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
//		if (!hasCollided) {
//			moveY(moveAmountYRemainder * direction.getVelocity());
//			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
//			if (newLocation != 0) {
//				hasCollided = true;
//				setY(newLocation);
//			}
//		}
		boolean hasCollided = false;


		setY(moveAmountY + pos.getY());
		float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this,map,direction);
		if(newLocation != 0) {
			setY(newLocation);
//			System.out.println(newLocation);
			hasCollided = true;
		}


		// call this method which a game object subclass can override to listen for collision events and react accordingly
		onEndCollisionCheckY(hasCollided, direction);

		// returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
		return (moveAmountYRemainder * direction.getVelocity());
	}

	// game object subclass can override this method to listen for x axis collision events and react accordingly after calling "moveXHandleCollision"
	@Deprecated
	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }

	// game object subclass can override this method to listen for y axis collision events and react accordingly after calling "moveYHandleCollision"
	@Deprecated
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }

	// gets x location taking into account map camera position
	@Deprecated
	//TODO change to vector
	public float getCalibratedXLocation() {
		if (map != null) {
			return pos.getX() - map.getCamera().getX();
		} else {
			return getX();
		}
	}

	// gets y location taking into account map camera position
	@Deprecated
	public float getCalibratedYLocation() {
		if (map != null) {
			return pos.getY() - map.getCamera().getY();
		} else {
			return getY();
		}
	}

	// gets scaled bounds taking into account map camera position
	public Rectangle getCalibratedScaledBounds() {
		if (map != null) {
			Rectangle scaledBounds = getScaledBounds();
			return new Rectangle(
					scaledBounds.getX1() - map.getCamera().getX(),
					scaledBounds.getY1() - map.getCamera().getY(),
					scaledBounds.getScaledWidth(),
					scaledBounds.getScaledHeight()
			);
		} else {
			return getScaledBounds();
		}
	}

	// set this game object's map to make it a "part of" the map, allowing calibrated positions and collision handling logic to work
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		if (map != null) {
			graphicsHandler.drawImage(
					currentFrame.getImage(),
					Math.round(getCalibratedXLocation()),
					Math.round(getCalibratedYLocation()),
					currentFrame.getScaledWidth(),
					currentFrame.getScaledHeight(),
					currentFrame.getImageEffect());
		} else {
			super.draw(graphicsHandler);
		}
	}

	@Override
	public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
		if (map != null) {
			Rectangle scaledCalibratedBounds = getCalibratedScaledBounds();
			scaledCalibratedBounds.setColor(color);
			scaledCalibratedBounds.draw(graphicsHandler);
		} else {
			super.drawBounds(graphicsHandler, color);
		}
	}

	public Map getMap() {
		return map;
	}

}
