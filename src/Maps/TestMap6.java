package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.CyborgEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap6 extends Map {

    public TestMap6() {
        super("test_map6.txt", new CommonTileset(), new Point(1, 15));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(5, 15), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(26, 16), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(4, 9), getPositionByTileIndex(7, 9), Direction.RIGHT));
        enemies.add(new CyborgEnemy(getPositionByTileIndex(31, 5), getPositionByTileIndex(35, 5), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(40, 1), getPositionByTileIndex(43, 1), Direction.RIGHT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(9, 8),
                getPositionByTileIndex(11, 8),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(12, 5),
                getPositionByTileIndex(14, 5),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.LEFT
        ));
        
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(36, 5),
                getPositionByTileIndex(38, 5),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.LEFT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(48, 9)
        ));

        return enhancedMapTiles;
    }

}
