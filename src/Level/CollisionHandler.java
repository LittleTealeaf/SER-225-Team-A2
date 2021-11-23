package Level;

import Engine.Vector;
import Game.GameThread;
import GameObject.GameObject;
import Utils.Point;

/**
 *
 */
@Deprecated
public class CollisionHandler {

    private GameObject gameObject;
    private MapTile lastCollidedTile;

    public CollisionHandler(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Map getMap() {
        return gameObject.getMap();
    }

    public Vector getAdjustedMovement(Vector velocity) {
        velocity.multiply(GameThread.UPDATE_FACTOR);
        Vector unit = velocity.getUnit(), cloned = velocity.clone();
        Vector originalPos = gameObject.getPos().clone();

        while(cloned.getMagnitude() > 1) {
            gameObject.move(unit);
            cloned.subtract(1);
            MapTile collision = getCollision(cloned);
            if(collision != null) {
                gameObject.move(unit.getNegative());
                cloned.add(1);
                lastCollidedTile = collision;
                return modifiedVelocity(collision,cloned);
            }
        }
        return velocity;
    }

    private Vector modifiedVelocity(MapTile collided, Vector velocity) {
        System.out.println(velocity);
        return null;
    }

    /**
     * Checks if the game object has collided with any map tiles, and returns the collided map-tile if it has. Otherwise, returns null
     * @param velocity
     * @return
     */
    private MapTile getCollision(Vector velocity) {
        int numberOfTilesToCheck = Math.max((int) gameObject.getScaledBounds().getWidth() / getMap().getTileset().getScaledSpriteWidth(),1);
        Point tileIndex = getMap().getTileIndexByPosition(gameObject.getPos());
        for(int i = -1;i < numberOfTilesToCheck + 1; i++) {
            for(int j = -1;j < numberOfTilesToCheck + 1; j++) {
                MapTile mapTile = getMap().getTileByIntPosition((int) tileIndex.x + i, (int) tileIndex.y + j);
                if(checkCollision(mapTile, velocity)) {
                    return mapTile;
                }
            }
        }

        return null;
    }

    public boolean checkCollision(MapTile mapTile, Vector velocity) {
        return mapTile != null && hasCollidedWithMapTile(gameObject,mapTile,velocity);
    }

    public static boolean hasCollidedWithMapTile(GameObject gameObject, MapTile mapTile, Vector velocity) {
        return switch (mapTile.getTileType()) {
            case NOT_PASSABLE, LETHAL -> gameObject.intersects(mapTile);
            case JUMP_THROUGH_PLATFORM -> velocity.getY() < 0 && gameObject.intersects(mapTile) && Math.round(gameObject.getScaledBoundsY2() -1) == Math.round(
                    mapTile.getScaledBoundsY1());
            default -> false;
        };
    }

    public MapTile getLastCollidedTile() {
        return lastCollidedTile;
    }

}
