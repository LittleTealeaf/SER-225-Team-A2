package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.*;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;
import SpriteFont.*;

import java.awt.*;
import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestTutorial extends Map {

    public TestTutorial() {
        super("test_tutorial.txt", new CommonTileset(), new Point(1, 8));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(15, 10), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 9).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(24, 6),
                getPositionByTileIndex(27, 6),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(32, 7)
        ));

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        //npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13)), this));

        return npcs;
    }

    private static final SpriteFont SPRITE_FONT_DIRECTIONS = new SpriteFont("Press 'X' for controls", 15, 35, "Times New Roman", 30, Color.white);
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        SPRITE_FONT_DIRECTIONS.draw(graphicsHandler);
    }
}
