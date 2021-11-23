package GameObject;

import Builders.FrameBuilder;
import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.Vector;
import Game.GameThread;
import Game.GameThreadDeprecated;
import Level.Map;
import Level.MapTile;
import Level.MapTileCollisionHandler;
import Level.TileType;
import Utils.Direction;
import Utils.MathUtils;
import Utils.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

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
	protected boolean inAir;

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

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, RectangleOld bounds) {
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

	public void moveHandleCollision(Vector providedVelocity) {
		Vector velocity = providedVelocity.getMultiplied(GameThread.UPDATE_FACTOR);
		Vector originalVelocity = velocity.clone();
		Vector unit = velocity.getUnit();

		Vector[] startingPoints = new Vector[] {
				pos, pos.getAdd(new Vector(getBounds().getWidth(),0)), pos.getAdd(new Vector(0,getBounds().getHeight())),
				pos.getAdd(new Vector(getBounds().getWidth(),getBounds().getHeight()))
		};


		Point tileIndex = getMap().getTileIndexByPosition(getScaledBounds().getPos1());

//		int rangeWidth = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth() + 3;
//		int rangeHeight = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight() + 3;

		float boundingVelocity = (float) Math.max(velocity.getMagnitude(),getBounds().getWidth() * 1.5);

//		int rangeHeight = (int) ( (boundingVelocity + getBounds().getHeight()) / map.getTileset().getScaledSpriteHeight()) * 2;
//		int rangeWidth = (int) ( (boundingVelocity + getBounds().getWidth()) / map.getTileset().getScaledSpriteWidth()) * 2;

		List<MapTile> tiles = map.getMapTilesInRange(getCenter(), boundingVelocity);

		//this doesn't seem to work

		System.out.println("Velocity: " + velocity);

		for(MapTile mapTile : tiles) {
			checkMapTile(startingPoints,velocity,mapTile);
		}
//
//		for(MapTile mapTile : map.getEnhancedMapTiles()) {
//			checkMapTile(startingPoints,velocity,mapTile);
//		}

		System.out.println("Update Velocity: " + velocity);
		move(velocity);
		inAir = velocity.getY() != 0;
	}
//
//	/**
//	 *
//	 * @param startPosition
//	 * @param velocity
//	 * @param mapTile
//	 * @return
//	 */
//	private boolean rayIntersects(Vector startPosition, Vector velocity, MapTile mapTile) {

//		float ySlope = velocity.getY() / velocity.getX(), xSlope = velocity.getX() / velocity.getY();
//
//		//Check left bound
//		float y = ySlope * (x1 - startPosition.getX()) + startPosition.getY();
//		boolean intersectsLeft = y <= y2 && y >= y1;
//		y = ySlope * (x2 - startPosition.getX()) + startPosition.getY();
//		boolean intersectsRight = y <= y2 && y >= y1;
//
//		return false;
//	}

	private void checkMapTile(Vector[] startingPoints, Vector velocity, MapTile mapTile) {
		if(mapTile != null && (mapTile.getTileType() == TileType.NOT_PASSABLE || (mapTile.getTileType() == TileType.JUMP_THROUGH_PLATFORM && velocity.getY() > 0))) {
			updateVelocityOld(startingPoints, velocity, mapTile);
		}
	}

	private void updateVelocity(Vector[] startingPoints, Vector velocity, MapTile mapTile) {

//		//x1 x2 y1 y2 are ordered values where x1 < x2 and y1 < y2. These indicate the x and y values of the edges of the bounding box
//		float x1 = mapTile.getPos().getX(), x2 = mapTile.getPos().getX() + mapTile.getBounds().getWidth();
//		float y1 = mapTile.getPos().getY(), y2 = mapTile.getPos().getY() + mapTile.getBounds().getHeight();
//		/*
//		xTest and yTest are the two faces that we will test intersections with (aka: the two faces that the velocity will hit first)
//		xTestObj and yTestObj are the same thing except for this GameObject
//		 */
//		float xTest = x2, yTest = y2, xTestObj = pos.getX(), yTestObj = pos.getY();
//		if(velocity.getX() > 0) {
//			xTest = x1;
//			xTestObj += getBounds().getWidth();
//		}
//		if(velocity.getY() > 0) {
//			yTest = y1;
//			yTestObj += getBounds().getHeight();
//		}




	}

	@Deprecated
	private void updateVelocityOld(Vector[] startingPoints, Vector velocity, MapTile mapTile) {
		float x1 = mapTile.getPos().getX(), x2 = mapTile.getPos().getX() + mapTile.getBounds().getWidth();
		float y1 = mapTile.getPos().getY(), y2 = mapTile.getPos().getY() + mapTile.getBounds().getHeight();

		for(Vector start : startingPoints) {
			System.out.print(velocity + " " + start + " " + x1 + " " + x2 + " " + y1 + " " + y2);
			float xLambda = Math.min(findCoefficient(start, velocity, x1, y1, y2), findCoefficient(start, velocity, x2, y1, y2));
			float yLambda = Math.min(
					findCoefficient(start.getFlipped(), velocity.getFlipped(), y1, x1, x2),
					findCoefficient(start.getFlipped(), velocity.getFlipped(), y2, x1, x2));

			if(xLambda > yLambda) {
				velocity.multiplyY(yLambda);
				velocity.multiplyX( Math.min(findCoefficient(start, velocity, x1, y1, y2), findCoefficient(start, velocity, x2, y1, y2)));
			} else {
				velocity.multiplyX(xLambda);
				velocity.multiplyY(Math.min(
						findCoefficient(start.getFlipped(), velocity.getFlipped(), y1, x1, x2),
						findCoefficient(start.getFlipped(), velocity.getFlipped(), y2, x1, x2)));
			}

			System.out.println(" " + xLambda + " " + yLambda);
		}
	}



	private float findCoefficient(Vector startPosition, Vector velocity, float v, float lowerBound, float upperBound) {

		if(velocity.getX() == 0) {
			return 1;
		}

		float lambda = (v - startPosition.getX()) / velocity.getX();
		float y = velocity.getY() * lambda + startPosition.getY();
		//return lambda only if it is less than 1 and the y position is within the bounds
		return (lambda >= 0 && lambda < 1 && (y < upperBound && y > lowerBound)) ? lambda : 1;
	}

	/**
	 * Moves the game object by a given velocity, barring impacts from collisions (within the map instance)
	 * @param providedVelocity Amount to move. Will not be modified during movement. Will scale down to the current scale from
	 * {@link GameThreadDeprecated#getScale()}
	 * @author Thomas Kwashnak
	 */
	@Deprecated
	public void moveHandleCollisionIterative(Vector providedVelocity) {
		//Copies the velocity scaled with current frame time
		final Vector velocity = providedVelocity.getMultiplied(GameThread.UPDATE_FACTOR);
		//Gets the unit vector, which is basically the unit-circle direction that the velocity is pointing towards
		final Vector unit = providedVelocity.getUnit();
		final Vector negativeUnit = unit.getNegative();

		System.out.println("Start Movement:\nVelocity = " + providedVelocity + ", Scaled = " + velocity);


		//Inch the object by integer increments to get closer to the position
		int intIterations = (int) velocity.getMagnitude();
		System.out.println(intIterations + " iterations");
		for(int i = 0; i < intIterations; i++) {
			//Moves a unit closer and reduces the "velocity left" by 1
			move(unit);
			velocity.add(negativeUnit);
			MapTile collision = getCollisionBounds(unit);
			if(collision != null) {
				//Move back and then break out of the loop
//				velocity.add(unit);
				velocity.set(unit);
				move(negativeUnit);

				break;
			}
		}
		System.out.println("Configure Velocity: " + velocity);
		move(velocity);
		if((lastCollided = getCollisionBounds(unit)) != null) {
			float xScale = 0, yScale = 0;

			if(velocity.getX() != 0) { //Preventing div by 0
				float xCol, xObj;
				if(velocity.getX() > 0) {
					xCol = lastCollided.getPos().getX();
					xObj = pos.getX() + getBounds().getWidth();
				} else {
					xCol = lastCollided.getPos().getX() + lastCollided.getBounds().getWidth();
					xObj = pos.getX();
				}
				if(xObj > xCol) {
					xScale = (xObj - xCol) / unit.getX();
				}
			}

			if(velocity.getY() != 0) {
				float yCol, yObj;
				if(velocity.getY() > 0) {
					yCol = lastCollided.getPos().getY();
					yObj = pos.getY() + getBounds().getHeight();
				} else {
					yCol = lastCollided.getPos().getY() + lastCollided.getBounds().getHeight();
					yObj = pos.getY();
				}
				if(yObj > yCol) {
					yScale = (yObj - yCol) / unit.getY();
				}
				System.out.println(yCol + "  " + yObj);
			}
			inAir = xScale > yScale;

			move(negativeUnit.getMultiplied(Math.max(xScale,yScale)));
		}
	}

	/**
	 * Checks if the game object has collided with any map tiles, and returns the collided map-tile if it has. Otherwise, returns null
	 *
	 * Copied from CollisionHandler
	 * @param velocity
	 * @return
	 */
	@Deprecated
	private MapTile getCollisionBounds(Vector velocity) {
		//TODO modify / recreate this code to iterate through all neighbor blocks only in the half direction of the velocity
//		int numberOfTilesToCheck = Math.max(getScaledBounds().getWidth() / getMap().getTileset().getScaledSpriteWidth() + 2, 3);

		Point tileIndex = getMap().getTileIndexByPosition(getScaledBounds().getPos1());

		int rangeWidth = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth() + 3;
		int rangeHeight = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight() + 3;

		MapTile[] tiles = map.getTilesInIndexBounds((int) tileIndex.x - 1, (int) tileIndex.y - 1, rangeWidth, rangeHeight);

		for(MapTile tile : tiles) {
			if(checkCollisionBounds(tile, velocity)) {
				return tile;
			}
		}

		for(MapTile enhancedTile : map.getEnhancedMapTiles()) {
			if(checkCollisionBounds(enhancedTile, velocity)) {
				return enhancedTile;
			}
		}

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
	@Deprecated
	private boolean checkCollisionBounds(MapTile mapTile, Vector velocity) {
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
	public RectangleOld getCalibratedScaledBounds() {
		if (map != null) {
			RectangleOld scaledBounds = getScaledBounds();
			return new RectangleOld(
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
			RectangleOld scaledCalibratedBounds = getCalibratedScaledBounds();
			scaledCalibratedBounds.setColor(color);
			scaledCalibratedBounds.draw(graphicsHandler);
		} else {
			super.drawBounds(graphicsHandler, color);
		}
	}

	public Map getMap() {
		return map;
	}

	public Vector getCenter() {
		return pos.getAdd(new Vector(getBounds().getWidth() / 2f, getBounds().getHeight() / 2f));
	}

}
