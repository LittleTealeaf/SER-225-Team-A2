package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

public class LevelTwoTileset extends Tileset {
    public LevelTwoTileset() { super(ImageLoader.load("LevelTwoTile.png"), 16, 16, 3); }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // grass
        Frame grassFrame = new FrameBuilder(getSubImage(0, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(grassTile);

        // sky
        Frame skyFrame = new FrameBuilder(getSubImage(2, 5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder skyTile = new MapTileBuilder(skyFrame);

        mapTiles.add(skyTile);

        // dirt
        Frame dirtFrame = new FrameBuilder(getSubImage(3, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame);

        mapTiles.add(dirtTile);

        // sun
        Frame sunFrame = new FrameBuilder(getSubImage(2, 0), 0)
                        .withScale(tileScale)
                        .build();

        MapTileBuilder sunTile = new MapTileBuilder(sunFrame);

        mapTiles.add(sunTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(1, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(1, 5), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(leftEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(1, 5), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(rightEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightEndBranchTile);

        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(treeTrunkFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkTile);

        // tree top leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(0, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(treeTopLeavesFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTopLeavesTile);

        // yellow flower changed from 1 to 0
        Frame yellowFlowerFrame = new FrameBuilder(getSubImage(0, 4), 0)
                        .withScale(tileScale)
                        .build();

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrame);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame purpleFlowerFrame = new FrameBuilder(getSubImage(0, 3), 0)
                        .withScale(tileScale)
                        .build();

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrame);

        mapTiles.add(purpleFlowerTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(1, 4), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(middleBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(middleBranchTile);

        // top water
        Frame topWaterFrame = new FrameBuilder(getSubImage(3, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrame);

        mapTiles.add(topWaterTile);

        // water
        Frame waterFrame = new FrameBuilder(getSubImage(3, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder waterTile = new MapTileBuilder(waterFrame);

        mapTiles.add(waterTile);

        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(0, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(greyRockTile);

        // fence body
        Frame fenceFrame = new FrameBuilder(getSubImage(1, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder fenceTile = new MapTileBuilder(fenceFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fenceTile);

        // fence top
        Frame fenceTopFrame = new FrameBuilder(getSubImage(1, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder fenceTopTile = new MapTileBuilder(fenceTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fenceTopTile);

        // background fence
        Frame backgroundFrame = new FrameBuilder(getSubImage(0,5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder backgroundFenceTile = new MapTileBuilder(backgroundFrame);

        mapTiles.add(backgroundFenceTile);

        // wood block
        Frame woodFrame = new FrameBuilder(getSubImage(2,1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder woodTile = new MapTileBuilder(woodFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodTile);

        // wood hole
        Frame woodHoleFrame = new FrameBuilder(getSubImage(2, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder woodHoleTile = new MapTileBuilder(woodHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodHoleTile);

        // berry bush left
        Frame berryFrame = new FrameBuilder(getSubImage(2, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder berryTile = new MapTileBuilder(berryFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(berryTile);

        // berry bush right
        Frame berryRightFrame = new FrameBuilder(getSubImage(2, 4), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder berryRightTile = new MapTileBuilder(berryRightFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(berryRightTile);

        return mapTiles;
    }
}
