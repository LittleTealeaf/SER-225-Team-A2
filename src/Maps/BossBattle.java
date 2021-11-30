package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.Dog;
import Enemies.CyborgEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import Flags.CheckpointFlag;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Screens.PlayLevelScreen;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;
import java.util.ArrayList;

// Represents a test map to be used in a level
public class BossBattle extends Map {

    public BossBattle() {
        super("BossBattle.txt", new CommonTileset(), new Point(35, 5));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new CyborgEnemy(getPositionByTileIndex(34, 13), getPositionByTileIndex(38, 13), Direction.RIGHT));
    	enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 5), getPositionByTileIndex(28, 5), Direction.RIGHT));
    	enemies.add(new DinosaurEnemy(getPositionByTileIndex(33, 5), getPositionByTileIndex(44, 5), Direction.RIGHT));
    	enemies.add(new CyborgEnemy(getPositionByTileIndex(16, 5), getPositionByTileIndex(17, 5), Direction.RIGHT));
    	enemies.add(new CyborgEnemy(getPositionByTileIndex(2, 5), getPositionByTileIndex(3, 5), Direction.RIGHT));
    	enemies.add(new Dog(getPositionByTileIndex(4, 5), getPositionByTileIndex(15, 5), Direction.RIGHT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(39, 13),
                getPositionByTileIndex(42, 13),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        
        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(0, 0)
        ));

        return enhancedMapTiles;
    }
    
    @Override
    public ArrayList<Flag> loadFlags() {
        ArrayList<Flag> flags = new ArrayList<>();

        flags.add(new CheckpointFlag(
        		getPositionByTileIndex(24, 5)
        ));

        return flags;
    }

}
