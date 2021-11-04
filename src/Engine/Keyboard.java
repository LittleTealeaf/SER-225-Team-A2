package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/*
 * This class is used throughout the engine for detecting keyboard state
 * This includes if a key is pressed, if a key is not pressed, and if multiple keys are pressed/not pressed at the same time
 */
public class Keyboard {

	// hashmaps keep track of if a key is currently down or up
	private static final Set<Integer> keysDown = new HashSet<>();

	private static final KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
        	// when key is pressed, set its keyDown state to true and its keyUp state to false
            int keyCode = e.getKeyCode();
            keysDown.add(keyCode);
        }

        @Override
        public void keyReleased(KeyEvent e) {
			// when key is released, set its keyDown state to false and its keyUp state to true
			int keyCode = e.getKeyCode();
            keysDown.remove(keyCode);
        }
    };

	// prevents Keyboard from being instantiated -- it's my way of making a "static" class like C# has
	private Keyboard() { }
    
    public static KeyListener getKeyListener() {
    	return keyListener;
    }

    // returns if a key is currently being pressed
    public static boolean isKeyDown(Key key) {
    	return keysDown.contains(key.getKeyCode());
    }

    // returns if a key is currently not being pressed
    public static boolean isKeyUp(Key key) {
    	return !keysDown.contains(key.getKeyCode());
    }

    // checks if multiple keys are being pressed at the same time
    public static boolean areKeysDown(Key[] keys) {
    	for (Key key : keys) {
    		if(!keysDown.contains(key.getKeyCode())){
				return false;
			}
    	}
    	return true;
    }

	// checks if multiple keys are not being pressed at the same time
	public static boolean areKeysUp(Key[] keys) {
    	for (Key key : keys) {
    		if (keysDown.contains(key.getKeyCode())) {
    			return false;
    		}
    	}
    	return true;
    }

	/**
	 * Returns if one of the keys is down
	 * @param keyCode Integer code of the key
	 * @return True if at least one of the keys is down
	 */
	public static boolean isKeyDown(int... keyCode) {
		for(Integer i : keyCode) {
			if(keysDown.contains(i)) {
				return true;
			}
		}
		return false;
	}
}
