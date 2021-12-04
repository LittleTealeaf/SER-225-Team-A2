package Flags;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Flag;
import Level.Player;
import Screens.PlayLevelScreen;
import Utils.Point;

import java.util.HashMap;

// This class is for the checkpoint flag
// It is placed at a location in the map and when the player touches it, saves their spawn point if they die
public class CheckpointFlag extends Flag {

    // Location of the flag
    protected final Point location;

    // can be either PASSED or NOT_PASSED based on if the player got the checkpoint
    protected CheckpointState checkpointState;

    public CheckpointFlag(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("CheckpointFlag.png"), 16, 27), "NOT PASSED");
        this.location = location;
        checkpointState = CheckpointState.NOT_PASSED;
    }

    @Override
    public void update(Player player) {
    	super.update(player);
    	if(intersects(player)) {
    		checkpointState = CheckpointState.PASSED;
    		PlayLevelScreen.loadedMap.setPlayerStartPosition(location);
    	}
    	
    	if(checkpointState == CheckpointState.PASSED) {
        	currentAnimationName = "PASSED";
        }
        else {
        	currentAnimationName = "NOT PASSED";
        }
    }
    
    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<>() {{
            put("NOT PASSED", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200).withScale(2).withBounds(0, 0, 17, 28).build()
            });

            put("PASSED", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200).withScale(2).withBounds(0, 0, 17, 28).build()
            });
        }};
    }

    public enum CheckpointState {
        PASSED, NOT_PASSED
    }
}
