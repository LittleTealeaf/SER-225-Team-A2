package Level;

import GameObject.GameObject;
import Utils.Direction;
import Utils.Point;



/*
 * Why is this a static class
 */

/**
 * This class has methods to check if a game object has collided with a map tile
 *  it is used by the game object class to determine if a collision occurred
 */
@Deprecated
public class MapTileCollisionHandler {
	
	public static MapTile lastCollidedTileX, lastCollidedTileY;

    public static float getAdjustedPositionAfterCollisionCheckX(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = (int) Math.max(gameObject.getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
        float edgeBoundX = direction == Direction.LEFT ? gameObject.getScaledBounds().getX1() : gameObject.getScaledBounds().getX2();
        Point tileIndex = map.getTileIndexByPosition(edgeBoundX, gameObject.getScaledBounds().getY1());
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
            if (mapTile != null && hasCollidedWithMapTile(gameObject, mapTile, direction)) {
            	lastCollidedTileX = mapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getScaledX2() - gameObject.getScaledBoundsX2();
                    return mapTile.getScaledBoundsX1() - gameObject.getScaledWidth() + boundsDifference;
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getScaledBoundsX1() - gameObject.getX();
                    return mapTile.getScaledBoundsX2() - boundsDifference;
                }
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (hasCollidedWithMapTile(gameObject, enhancedMapTile, direction)) {
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getScaledX2() - gameObject.getScaledBoundsX2();
                    return enhancedMapTile.getScaledBoundsX1() - gameObject.getScaledWidth() + boundsDifference;
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getScaledBoundsX1() - gameObject.getX();
                    return enhancedMapTile.getScaledBoundsX2() - boundsDifference;
                }
            }
        }
        return 0;
    }

    /**
     * Firstly, totally going to be changing this in velocities
     *
     * Checks collisions between a list of tiles and enhanced tile-sets, returns 0 if no collision, or the new Y value if a collision does occur.
     * @param gameObject Object to check collision
     * @param map Map the object is on
     * @param direction Direction the object is moving in
     * @return
     */
    public static float getAdjustedPositionAfterCollisionCheckY(GameObject gameObject, Map map, Direction direction) {
        //Tiles to check? getting a list of tiles
        int numberOfTilesToCheck = (int) Math.max(gameObject.getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
        float edgeBoundY = direction == Direction.UP ? gameObject.getScaledBounds().getY1() : gameObject.getScaledBounds().getY2();
        /*Get the edge bounds... ok?*/
        Point tileIndex = map.getTileIndexByPosition(gameObject.getScaledBounds().getX1(), edgeBoundY); /*Gets the uh, bottom left item? */
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) { /*Get only immediately surrounding map tiles. AHA */
            /*
            So the bottom message rounds to a whole number to check the tile at that location
             */
            MapTile mapTile = map.getMapTile(/*converts / grabs based on array*/Math.round(tileIndex.x) + j, Math.round(tileIndex.y)); /*Grab the map
            tile relative to the gameObject position*/

            if (mapTile != null && hasCollidedWithMapTile(gameObject, mapTile, direction)) { /* Checks if there's a tile there, and if it's collided*/
            	lastCollidedTileY = mapTile; /*Update tile*/
                /*Normalizes the distance based on the direction + bounds of the block*/
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getScaledY2() - gameObject.getScaledBoundsY2();
                    return mapTile.getScaledBoundsY1() - gameObject.getScaledHeight() + boundsDifference;
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getScaledBoundsY1() - gameObject.getY();
                    return mapTile.getScaledBoundsY2() - boundsDifference;
                }
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (hasCollidedWithMapTile(gameObject, enhancedMapTile, direction)) {
                lastCollidedTileY = enhancedMapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getScaledY2() - gameObject.getScaledBoundsY2();
                    return enhancedMapTile.getScaledBoundsY1() - gameObject.getScaledHeight() + boundsDifference;
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getScaledBoundsY1() - gameObject.getY();
                    return enhancedMapTile.getScaledBoundsY2() - boundsDifference;
                }
            }
        }
        return 0;
    }

    // based on tile type, perform logic to determine if a collision did occur with an intersecting tile or not
    private static boolean hasCollidedWithMapTile(GameObject gameObject, MapTile mapTile, Direction direction) {
        return switch (mapTile.getTileType()) {
            case NOT_PASSABLE, LETHAL -> gameObject.intersects(mapTile);
            case JUMP_THROUGH_PLATFORM -> direction == Direction.DOWN && gameObject.intersects(mapTile) && Math.round(
                    gameObject.getScaledBoundsY2() - 1) == Math.round(mapTile.getScaledBoundsY1());
            default -> false;
        };
    }
}
