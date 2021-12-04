package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import EnhancedMapTiles.EndLevelBox;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap2 extends Map {

    public TestMap2() {
        super("Level 2", "test_map_2.txt", new CommonTileset(), new Point(1, 11));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(25, 6).addY(1), getPositionByTileIndex(28, 6).addY(1), Direction.RIGHT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
//
//        //enhancedMapTiles.add(new HorizontalMovingPlatform(
//                ImageLoader.load("GreenPlatform.png"),
//                getPositionByTileIndex(24, 6),
//                getPositionByTileIndex(27, 6),
//                TileType.JUMP_THROUGH_PLATFORM,
//                3,
//                new Rectangle(0, 6,16,4),
//                Direction.RIGHT
//       // ));

        enhancedMapTiles.add(new EndLevelBox(getPositionByTileIndex(32, 7)));

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {

        //npcs.add(new Walrus(getPositionByTileIndex(20, 10).subtract(new Point(0, 13)), this));

        return new ArrayList<>();
    }
}
