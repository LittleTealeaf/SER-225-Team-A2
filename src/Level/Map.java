package Level;

import Engine.*;
import Projectiles.Projectile;
import Utils.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    This class is for defining a map that is used for a specific level
    The map class handles/manages a lot of different things, including:
    1. tile map -- the map tiles that make up the map
    2. entities in the map -- this includes enemies, enhanced map tiles, and npcs
    3. the map's camera, which does a lot of work itself in the Camera class
    4. adjusting camera location based off of player location
    5. calculating which tile a game object is currently on based on its x and y location
*/

public abstract class Map implements Drawable {

    // the tileset this map uses for its map tiles
    protected final Tileset tileset;
    // the location of the "mid point" of the screen
    // this is what tells the game that the player has reached the center of the screen, therefore the camera should move instead of the player
    // this goes into creating that "map scrolling" effect
    protected final int xMidPoint;
    protected final int yMidPoint;
    // in pixels, this basically creates a rectangle defining how big the map is
    // startX and Y will always be 0, endX and Y is the number of tiles multiplied by the number of pixels each tile takes up
    protected final int startBoundX;
    protected final int startBoundY;
    protected final int endBoundX;
    protected final int endBoundY;
    // the name of the map text file that has the tile map information
    protected final String mapFileName;
    //right most bound
    private final int rightBound;
    protected String name;
    // the tile map (map tiles that make up the entire map image)
    protected MapTile[] mapTiles;
    // width and height of the map in terms of the number of tiles width-wise and height-wise
    protected int width;
    protected int height;
    // camera class that handles the viewable part of the map that is seen by the player of a game during a level
    protected Camera camera;
    // tile player should start on when this map is first loaded
    protected Point playerStartTile;
    protected Point scaledPlayerStartTile;
    // lists to hold map entities that are a part of the map
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Projectile> projectiles;
    protected ArrayList<EnhancedMapTile> enhancedMapTiles;
    protected ArrayList<NPC> npcs;
    protected ArrayList<Flag> flags;

    // if set to false, camera will not move as player moves
    protected boolean adjustCamera = true;

    public Map(String name, String mapFileName, Tileset tileset, Point playerStartTile) {
        this.name = name;
        this.mapFileName = mapFileName;
        this.tileset = tileset;
        setupMap();
        this.startBoundX = 0;
        this.startBoundY = 0;
        this.endBoundX = width * tileset.getScaledSpriteWidth();
        this.endBoundY = height * tileset.getScaledSpriteHeight();
        this.xMidPoint = ScreenManager.getScreenWidth() / 2;
        this.yMidPoint = (ScreenManager.getScreenHeight() / 2);
        this.playerStartTile = playerStartTile;
        rightBound = getWidthPixels() - getTileset().getScaledSpriteWidth();
    }

    // sets up map by reading in the map file to create the tile map
    // loads in enemies, enhanced map tiles, and npcs
    // and instantiates a Camera
    public void setupMap() {
        loadMapFile();

        this.enemies = loadEnemies();
        for (Enemy enemy : this.enemies) {
            enemy.setMap(this);
        }

        this.projectiles = loadProjectiles();
        for (Projectile projectile : this.projectiles) {
            projectile.setMap(this);
        }

        this.enhancedMapTiles = loadEnhancedMapTiles();
        for (EnhancedMapTile enhancedMapTile : this.enhancedMapTiles) {
            enhancedMapTile.setMap(this);
        }

        this.npcs = loadNPCs();
        for (NPC npc : this.npcs) {
            npc.setMap(this);
        }

        if (GamePanel.getDifficulty() != 1) {
            this.flags = loadFlags();
            for (Flag flags : this.flags) {
                flags.setMap(this);
            }
        }

        this.camera = new Camera(0, 0, tileset.getScaledSpriteWidth(), tileset.getScaledSpriteHeight(), this);
    }

    public int getWidthPixels() {
        return width * tileset.getScaledSpriteWidth();
    }

    public Tileset getTileset() {
        return tileset;
    }

    // reads in a map file to create the map's tilemap
    private void loadMapFile() {
        Scanner fileInput;
        try {
            // open map file that is located in the MAP_FILES_PATH directory
            fileInput = new Scanner(new File(Config.MAP_FILES_PATH + this.mapFileName));
        } catch (FileNotFoundException ex) {
            // if map file does not exist, create a new one for this map (the map editor uses this)
            System.out.println("Map file " + Config.MAP_FILES_PATH + this.mapFileName + " not found! Creating empty map file...");

            try {
                createEmptyMapFile();
                fileInput = new Scanner(new File(Config.MAP_FILES_PATH + this.mapFileName));
            } catch (IOException ex2) {
                ex2.printStackTrace();
                System.out.println("Failed to create an empty map file!");
                throw new RuntimeException();
            }
        }

        // read in map width and height from the first line of map file
        this.width = fileInput.nextInt();
        this.height = fileInput.nextInt();

        // define array size for map tiles, which is width * height (this is a standard array, NOT a 2D array)
        this.mapTiles = new MapTile[this.height * this.width];
        fileInput.nextLine();

        // read in each tile index from the map file, use the defined tileset to get the associated MapTile to that tileset, and place it in the array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tileIndex = fileInput.nextInt();
                int xLocation = j * tileset.getScaledSpriteWidth();
                int yLocation = i * tileset.getScaledSpriteHeight();
                MapTile tile = tileset.getTile(tileIndex).build(xLocation, yLocation);
                tile.setMap(this);
                setMapTile(j, i, tile);
            }
        }

        fileInput.close();
    }

    // list of enemies defined to be a part of the map, should be overridden in a subclass
    protected ArrayList<Enemy> loadEnemies() {
        return new ArrayList<>();
    }

    protected ArrayList<Projectile> loadProjectiles() {
        return new ArrayList<>();
    }

    // list of enhanced map tiles defined to be a part of the map, should be overridden in a subclass
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    // list of npcs defined to be a part of the map, should be overridden in a subclass
    protected ArrayList<NPC> loadNPCs() {
        return new ArrayList<>();
    }

    // list of flags defined to be a part of the map, should be overridden in a subclass
    protected ArrayList<Flag> loadFlags() {
        return new ArrayList<>();
    }

    // creates an empty map file for this map if one does not exist
    // defaults the map dimensions to 0x0
    private void createEmptyMapFile() throws IOException {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(Config.MAP_FILES_PATH + this.mapFileName);
        fileWriter.write("0 0\n");
        fileWriter.close();
    }

    // set specific map tile from tile map to a new map tile
    public void setMapTile(int x, int y, MapTile tile) {
        mapTiles[getConvertedIndex(x, y)] = tile;
    }

    // since tile map array is a standard (1D) array and not a 2D,
    // instead of doing [y][x] to get a value, instead the same can be achieved with x + width * y
    private int getConvertedIndex(int x, int y) {
        return x + width * y;
    }

    public void setPlayerStartTile(Point point) {
        playerStartTile = point;
        scaledPlayerStartTile = getPositionByTileIndex((int) point.x, (int) point.y);
    }

    // get position on the map based on a specfic tile index
    public Point getPositionByTileIndex(int xIndex, int yIndex) {
        return new Point(xIndex * tileset.getScaledSpriteWidth(), yIndex * tileset.getScaledSpriteHeight());
    }

    // gets player start position based on player start tile (basically the start tile's position on the map)
    public Point getPlayerStartPosition() {
        return scaledPlayerStartTile != null ? scaledPlayerStartTile : getPositionByTileIndex((int) playerStartTile.x, (int) playerStartTile.y);
    }

    public void setPlayerStartPosition(Point point) {
        scaledPlayerStartTile = point;
    }

    public String getMapFileName() {
        return mapFileName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeightPixels() {
        return height * tileset.getScaledSpriteHeight();
    }

    public MapTile[] getMapTiles() {
        return mapTiles;
    }

    public void setMapTiles(MapTile[] mapTiles) {
        this.mapTiles = mapTiles;
    }

    // returns a tile based on a position in the map
    public MapTile getTileByPosition(int xPosition, int yPosition) {
        Point tileIndex = getTileIndexByPosition(xPosition, yPosition);
        if (isInBounds(Math.round(tileIndex.x), Math.round(tileIndex.y))) {
            return getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y));
        } else {
            return null;
        }
    }

    // returns the index of a tile (x index and y index) based on a position in the map
    public Point getTileIndexByPosition(float xPosition, float yPosition) {
        int xIndex = Math.round(xPosition) / tileset.getScaledSpriteWidth();
        int yIndex = Math.round(yPosition) / tileset.getScaledSpriteHeight();
        return new Point(xIndex, yIndex);
    }

    // checks if map tile being requested is in bounds of the tile map array
    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // get specific map tile from tile map
    public MapTile getMapTile(int x, int y) {
        if (isInBounds(x, y)) {
            return mapTiles[getConvertedIndex(x, y)];
        } else {
            return null;
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<EnhancedMapTile> getEnhancedMapTiles() {
        return enhancedMapTiles;
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    // returns all active enemies (enemies that are a part of the current update cycle) -- this changes every frame by the Camera class
    public ArrayList<Enemy> getActiveEnemies() {
        return camera.getActiveEnemies();
    }

    // returns all active projectiles (projectiles that are a part of the current update cycle) -- this changes every frame by the Camera class
    public ArrayList<Projectile> getActiveProjectiles() {
        return camera.getActiveProjectiles();
    }

    // returns all active enhanced map tiles (enhanced map tiles that are a part of the current update cycle) -- this changes every frame by the
    // Camera class
    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return camera.getActiveEnhancedMapTiles();
    }

    // returns all active npcs (npcs that are a part of the current update cycle) -- this changes every frame by the Camera class
    public ArrayList<NPC> getActiveNPCs() {
        return camera.getActiveNPCs();
    }

    // returns all active flags (flags that are a part of the current update cycle) -- this changes every frame by the Camera class
    public ArrayList<Flag> getActiveFlags() {
        return camera.getActiveFlags();
    }

    // add an enemy to the map's list of enemies
    public void addEnemy(Enemy enemy) {
        enemy.setMap(this);
        this.enemies.add(enemy);
    }

    // add a projectile to the map's list of projectiles
    public void addProjectile(Projectile projectile) {
        projectile.setMap(this);
        this.projectiles.add(projectile);
    }

    // add an enhanced map tile to the map's list of enhanced map tiles
    public void addEnhancedMapTile(EnhancedMapTile enhancedMapTile) {
        enhancedMapTile.setMap(this);
        this.enhancedMapTiles.add(enhancedMapTile);
    }

    // add an npc to the map's list of npcs
    public void addNPC(NPC npc) {
        npc.setMap(this);
        this.npcs.add(npc);
    }

    // add an FLAG to the map's list of FLAGS
    public void addFlag(Flag flag) {
        flag.setMap(this);
        this.flags.add(flag);
    }

    public void setAdjustCamera(boolean adjustCamera) {
        this.adjustCamera = adjustCamera;
    }

    public void update(Player player) {
        if (adjustCamera) {
            adjustMovementY(player);
            adjustMovementX(player);
        }
        camera.update(player);
    }

    // based on the player's current Y position (which in a level can potentially be updated each frame),
    // adjust the player's and camera's positions accordingly in order to properly create the map "scrolling" effect
    private void adjustMovementY(Player player) {
        // if player goes past center screen (below) and there is more map to show below, push player back to center and move camera upward
        if (player.getCalibratedYLocation() > yMidPoint && camera.getEndBoundY() < endBoundY) {
            float yMidPointDifference = yMidPoint - player.getCalibratedYLocation();
            camera.moveY(-yMidPointDifference);

            // if camera moved past the bottom of the map as a result from the move above, move camera upwards and push player downwards
            if (camera.getEndBoundY() > endBoundY) {
                float cameraDifference = camera.getEndBoundY() - endBoundY;
                camera.moveY(-cameraDifference);
            }
        }
        // if player goes past center screen (above) and there is more map to show above, push player back to center and move camera upwards
        else if (player.getCalibratedYLocation() < yMidPoint && camera.getY() > startBoundY) {
            float yMidPointDifference = yMidPoint - player.getCalibratedYLocation();
            camera.moveY(-yMidPointDifference);

            // if camera moved past the top of the map as a result from the move above, move camera downwards and push player upwards
            if (camera.getY() < startBoundY) {
                float cameraDifference = startBoundY - camera.getY();
                camera.moveY(cameraDifference);
            }
        }
    }

    // based on the player's current X position (which in a level can potentially be updated each frame),
    // adjust the player's and camera's positions accordingly in order to properly create the map "scrolling" effect
    private void adjustMovementX(Player player) {
        // if player goes past center screen (on the right side) and there is more map to show on the right side, push player back to center and
        // move camera forward
        if (player.getCalibratedXLocation() > xMidPoint && camera.getEndBoundX() < endBoundX) {
            float xMidPointDifference = xMidPoint - player.getCalibratedXLocation();
            camera.moveX(-xMidPointDifference);

            // if camera moved past the right edge of the map as a result from the move above, move camera back and push player forward
            if (camera.getEndBoundX() > endBoundX) {
                float cameraDifference = camera.getEndBoundX() - endBoundX;
                camera.moveX(-cameraDifference);
            }
        }
        // if player goes past center screen (on the left side) and there is more map to show on the left side, push player back to center and move
        // camera backwards
        else if (player.getCalibratedXLocation() < xMidPoint && camera.getX() > startBoundX) {
            float xMidPointDifference = xMidPoint - player.getCalibratedXLocation();
            camera.moveX(-xMidPointDifference);

            // if camera moved past the left edge of the map as a result from the move above, move camera back and push player backward
            if (camera.getX() < startBoundX) {
                float cameraDifference = startBoundX - camera.getX();
                camera.moveX(cameraDifference);
            }
        }
    }

    public void reset() {
        setupMap();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        camera.draw(graphicsHandler);
    }

    public int getRightBound() {
        return rightBound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
