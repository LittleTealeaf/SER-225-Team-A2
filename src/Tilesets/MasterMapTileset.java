package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

public class MasterMapTileset  extends Tileset {

    public MasterMapTileset() { super(ImageLoader.load("MasterMapTile.png"), 16, 16, 3); }
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

        //tree leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(0, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(treeTopLeavesFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTopLeavesTile);

        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(0, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(greyRockTile);

        // berry bush left
        Frame berryFrame = new FrameBuilder(getSubImage(0, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder berryTile = new MapTileBuilder(berryFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(berryTile);

        // berry bush right
        Frame berryRightFrame = new FrameBuilder(getSubImage(0, 4), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder berryRightTile = new MapTileBuilder(berryRightFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(berryRightTile);

        // background fence
        Frame backgroundFrame = new FrameBuilder(getSubImage(0,5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder backgroundFenceTile = new MapTileBuilder(backgroundFrame);

        mapTiles.add(backgroundFenceTile);

        // yellow flower
        Frame[] yellowFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 6), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 7), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 6), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 8), 500)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 9), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 10), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 9), 500)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 11), 500)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(treeTrunkFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(1, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // tree trunk hole top
        Frame treeTrunkHoleTopFrame = new FrameBuilder(getSubImage(1, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleTopTile = new MapTileBuilder(treeTrunkHoleTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkHoleTopTile);

        // tree trunk hole bottom
        Frame treeTrunkHoleBottomFrame = new FrameBuilder(getSubImage(1, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleBottomTile = new MapTileBuilder(treeTrunkHoleBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkHoleBottomTile);

        // tree berry leaves
        Frame treeBerryFrame = new FrameBuilder(getSubImage(1, 4), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder treeBerryTile = new MapTileBuilder(treeBerryFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeBerryTile);

        // wood block
        Frame woodFrame = new FrameBuilder(getSubImage(1,5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder woodTile = new MapTileBuilder(woodFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodTile);

        // wood hole
        Frame woodHoleFrame = new FrameBuilder(getSubImage(1, 6), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder woodHoleTile = new MapTileBuilder(woodHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodHoleTile);

        // fence body
        Frame fenceFrame = new FrameBuilder(getSubImage(1, 7), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder fenceTile = new MapTileBuilder(fenceFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fenceTile);

        // fence top
        Frame fenceTopFrame = new FrameBuilder(getSubImage(1, 8), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder fenceTopTile = new MapTileBuilder(fenceTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fenceTopTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(1, 9), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(middleBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(middleBranchTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(1, 10), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(leftEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(1, 10), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(rightEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightEndBranchTile);

        // glass
        Frame glassFrame = new FrameBuilder(getSubImage(1, 11), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder glassTile = new MapTileBuilder(glassFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(glassTile);

        // sun
        Frame[] sunFrames = new Frame[] {
                new FrameBuilder(getSubImage(2, 0), 400)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(2, 1), 400)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder sunTile = new MapTileBuilder(sunFrames);

        mapTiles.add(sunTile);

        // dirt
        Frame dirtFrame = new FrameBuilder(getSubImage(2, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(dirtTile);

        // sky
        Frame skyFrame = new FrameBuilder(getSubImage(2, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder skyTile = new MapTileBuilder(skyFrame);

        mapTiles.add(skyTile);

        // top water
        Frame topWaterFrame = new FrameBuilder(getSubImage(2, 4), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrame);

        mapTiles.add(topWaterTile);

        // water
        Frame waterFrame = new FrameBuilder(getSubImage(2, 5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder waterTile = new MapTileBuilder(waterFrame);

        mapTiles.add(waterTile);

        // gravel
        Frame gravelFrame = new FrameBuilder(getSubImage(2, 6), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder gravelTile = new MapTileBuilder(gravelFrame);

        mapTiles.add(gravelTile);

        // house wood
        Frame houseFrame = new FrameBuilder(getSubImage(2, 7), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder houseTile = new MapTileBuilder(houseFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseTile);

        // detailed house wood
        Frame houseTwoFrame = new FrameBuilder(getSubImage(2, 8), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder houseTwoTile = new MapTileBuilder(houseTwoFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseTwoTile);

        // house wood doorknob
        Frame doorKnobFrame = new FrameBuilder(getSubImage(2, 9), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder doorKnobTile = new MapTileBuilder(doorKnobFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorKnobTile);

        // roof
        Frame roofFrame = new FrameBuilder(getSubImage(2, 10), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder roofTile = new MapTileBuilder(roofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(roofTile);

        //wall
        Frame wallFrame = new FrameBuilder(getSubImage(2, 11), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder wallTile = new MapTileBuilder(wallFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(wallTile);

        //floor board
        Frame floorFrame = new FrameBuilder(getSubImage(3, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder floorTile = new MapTileBuilder(floorFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(floorTile);

        //window
        Frame windowFrame = new FrameBuilder(getSubImage(3, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder windowTile = new MapTileBuilder(windowFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowTile);

        //ceiling light
        Frame[] lightFrames = new Frame[] {
                new FrameBuilder(getSubImage(3, 2), 400)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(3, 3), 400)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder lightTile = new MapTileBuilder(lightFrames);

        mapTiles.add(lightTile);

        // middle table
        Frame middleTableFrame = new FrameBuilder(getSubImage(3, 5), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleTableTile = new MapTileBuilder(middleTableFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(middleTableTile);

        // table end
        Frame tableEndFrame = new FrameBuilder(getSubImage(3, 4), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder tableEndTile = new MapTileBuilder(tableEndFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(tableEndTile);

        // table pole
        Frame tablePoleFrame = new FrameBuilder(getSubImage(3, 6), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder tablePoleTile = new MapTileBuilder(tablePoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tablePoleTile);

        // scratching pole base
        Frame scratchPoleFrame = new FrameBuilder(getSubImage(3, 7), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder scratchPoleTile = new MapTileBuilder(scratchPoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(scratchPoleTile);

        // scratching pole middle arm
        Frame scratchMiddleFrame = new FrameBuilder(getSubImage(3, 9), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder scratchMiddleTile = new MapTileBuilder(scratchMiddleFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(scratchMiddleTile);

        // scratching pole left arm
        Frame leftScratchArmFrame = new FrameBuilder(getSubImage(3, 8), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftScratchArmTile = new MapTileBuilder(leftScratchArmFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftScratchArmTile);

        // scratching pole right arm
        Frame rightScratchArmFrame = new FrameBuilder(getSubImage(3, 8), 0)
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightScratchArmTile = new MapTileBuilder(rightScratchArmFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightScratchArmTile);

        //scratching pole hole
        Frame scratchHoleFrame = new FrameBuilder(getSubImage(3, 10), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder scratchHoleTile = new MapTileBuilder(scratchHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(scratchHoleTile);

        //bed footboard
        Frame footBoardFrame = new FrameBuilder(getSubImage(3, 11), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder footBoardTile = new MapTileBuilder(footBoardFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(footBoardTile);

        //bed middle
        Frame bedMiddleFrame = new FrameBuilder(getSubImage(4, 0), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder bedMiddleTile = new MapTileBuilder(bedMiddleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedMiddleTile);

        //bed pillow
        Frame bedPillowFrame = new FrameBuilder(getSubImage(4, 1), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder bedPillowTile = new MapTileBuilder(bedPillowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedPillowTile);

        //bed bottom
        Frame bedBottomFrame = new FrameBuilder(getSubImage(4, 2), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder bedBottomTile = new MapTileBuilder(bedBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedBottomTile);

        //bed bottom left leg
        Frame bedBottomLegLeftFrame = new FrameBuilder(getSubImage(4, 3), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder bedBottomLegLeftTile = new MapTileBuilder(bedBottomLegLeftFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedBottomLegLeftTile);

        //bed bottom right leg
        Frame bedBottomLegRightFrame = new FrameBuilder(getSubImage(4, 3), 0)
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder bedBottomLegRightTile = new MapTileBuilder(bedBottomLegRightFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedBottomLegRightTile);

        //door top
        Frame doorTopFrame = new FrameBuilder(getSubImage(4, 4), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder doorTopTile = new MapTileBuilder(doorTopFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorTopTile);

        //door bottom
        Frame doorBottomFrame = new FrameBuilder(getSubImage(4, 5), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder doorBottomTile = new MapTileBuilder(doorBottomFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorBottomTile);

        //tv
        Frame tvFrame = new FrameBuilder(getSubImage(4, 6), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder tvTile = new MapTileBuilder(tvFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tvTile);

        //tv table
        Frame tvTableFrame = new FrameBuilder(getSubImage(4, 7), 0)
                .withScale(tileScale)
                .build();

        MapTileBuilder tvTableTile = new MapTileBuilder(tvTableFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tvTableTile);



        return mapTiles;
    }
}
