package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap3 extends Map {

    public TestMap3() {
        super("Level 3", "test_map3.txt", new CommonTileset(), new Point(1, 11));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(25, 11), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(
                new HorizontalMovingPlatform(ImageLoader.load("GreenPlatform.png"), getPositionByTileIndex(14, 10), getPositionByTileIndex(17, 10),
                                             TileType.JUMP_THROUGH_PLATFORM, 3, new Rectangle(0, 6, 16, 4), Direction.RIGHT
                ));

        enhancedMapTiles.add(new EndLevelBox(getPositionByTileIndex(32, 7)));

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {

        // npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13)), this));

        return new ArrayList<>();
    }
}
