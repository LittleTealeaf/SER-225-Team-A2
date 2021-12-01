package Flags;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Flag;
import Level.Map;
import Level.Player;
import Screens.PlayLevelScreen;
import Utils.Point;

import java.util.HashMap;

// This class is for the checkpoint flag
// It is placed at a location in the map and when the player touches it, saves their spawn point if they die
public class CheckpointFlag extends Flag {

    // Location of the flag
    protected Point location;

    // can be either PASSED or NOT_PASSED based on if the player got the checkpoint
    protected checkpointState checkpointState;

    protected Map map;

    public CheckpointFlag(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("CheckpointFlag.png"), 16, 27), "NOT PASSED");
        this.location = location;
        checkpointState = checkpointState.NOT_PASSED;
    }

    @Override
    public void update(Player player) {
    	super.update(player);
    	if(intersects(player)) {
    		checkpointState = checkpointState.PASSED;
    		PlayLevelScreen.loadedMap.setPlayerStartPosition(location); 		
    	}
    	
    	if(checkpointState == checkpointState.PASSED) {
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
        return new HashMap<String, Frame[]>() {{
            put("NOT PASSED", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
		                    .withScale(2)
		                    .withBounds(0, 0, 17, 28)
		                    .build()
            });

            put("PASSED", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
		                    .withScale(2)
		                    .withBounds(0, 0, 17, 28)
		                    .build()
            });
        }};
    }

    public enum checkpointState {
        PASSED, NOT_PASSED
    }
}
