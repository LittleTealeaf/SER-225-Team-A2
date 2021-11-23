package Maps;

import Enemies.BugEnemy;
import Enemies.CyborgEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.TileType;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap7 extends Map {

    public TestMap7() {
        super("test_map7.txt", new CommonTileset(), new Point(1, 11));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(15, 11), Direction.LEFT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(16, 7), getPositionByTileIndex(17, 7), Direction.RIGHT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(16, 9), getPositionByTileIndex(17, 9), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(16, 2), getPositionByTileIndex(19, 2), Direction.RIGHT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(29, 9), getPositionByTileIndex(31, 9), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(34, 12), Direction.LEFT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(35, 9), getPositionByTileIndex(37, 9), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(40, 12), Direction.LEFT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(41, 9), getPositionByTileIndex(43, 9), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(49, 12), Direction.LEFT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(5, 9),
                getPositionByTileIndex(7, 9),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6, 16, 4),
                Direction.RIGHT
        ));
        
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(23, 9),
                getPositionByTileIndex(26, 9),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6, 16, 4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(48, 9)
        ));

        return enhancedMapTiles;
    }

}
