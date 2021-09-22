package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.LawnMowerOfDeath;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import Tilesets.MasterMapTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

public class LevelThree extends Map {
    public LevelThree() {
        super("level_three.txt", new MasterMapTileset(), new Point(1, 44));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        // Lawnmowers
        enemies.add(new LawnMowerOfDeath(getPositionByTileIndex(15, 42), Direction.LEFT));
        enemies.add(new LawnMowerOfDeath(getPositionByTileIndex(26, 43), Direction.LEFT));
        enemies.add(new LawnMowerOfDeath(getPositionByTileIndex(37, 32), Direction.LEFT));

        // bug enemies
        enemies.add(new BugEnemy(getPositionByTileIndex(30, 45), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(53, 39), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(78, 16), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(74, 8), Direction.RIGHT));
        enemies.add(new BugEnemy(getPositionByTileIndex(84, 8), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(89, 10), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(106, 48), Direction.LEFT));
        // dragon enemies
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(7, 43).addY(2), getPositionByTileIndex(14, 43).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(68, 35).addY(2), getPositionByTileIndex(72, 35).addY(2), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(54, 30).addY(2), getPositionByTileIndex(58, 30).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(67, 26).addY(2), getPositionByTileIndex(72, 26).addY(2), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(55, 22).addY(2), getPositionByTileIndex(59, 22).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(90, 34).addY(2), getPositionByTileIndex(92, 34).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(93, 42).addY(2), getPositionByTileIndex(95, 42).addY(2), Direction.RIGHT));

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(59, 40),
                getPositionByTileIndex(65, 40),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(63, 37),
                getPositionByTileIndex(65, 37),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(60, 33),
                getPositionByTileIndex(66, 33),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(60, 29),
                getPositionByTileIndex(66, 29),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(63, 25),
                getPositionByTileIndex(65, 25),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(61, 21),
                getPositionByTileIndex(64, 21),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(103, 43),
                getPositionByTileIndex(105, 43),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(112, 41)
        ));

        return enhancedMapTiles;
    }
}
