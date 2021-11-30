package Flags;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Flag;
import Level.Player;
import Utils.Point;
import java.util.HashMap;

// This class is for the checkpoint flag
// It is placed at a location in the map and when the player touches it, saves their spawn point if they die
public class CheckpointFlag extends Flag {

    // Location of the flag
    protected Point location;

    // can be either PASSED or NOT_PASSED based on if the player got the checkpoint
    protected checkpointState checkpointState;

    public CheckpointFlag(Point location) {
        super(location.getX(), location.getY(), new SpriteSheet(ImageLoader.load("CheckpointFlag.png"), 17, 32), "NOT PASSED");
        this.location = location;
        checkpointState = checkpointState.NOT_PASSED;
    }

    @Override
    public void update(Player player) {
    	if(intersects(player)) {
    		checkpointState = checkpointState.PASSED;  
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
		                    .withBounds(0, 0, 7, 22)
		                    .build()
            });

            put("PASSED", new Frame[]{
            		new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
		                    .withScale(2)
		                    .withBounds(0, 0, 17, 31)
		                    .build()
            });
        }};
    }

    public enum checkpointState {
        PASSED, NOT_PASSED
    }
}
