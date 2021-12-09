package Engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/*
 * This class is used throughout the engine for detecting keyboard state
 * This includes if a key is pressed, if a key is not pressed, and if multiple keys are pressed/not pressed at the same time
 */
public class Keyboard extends KeyAdapter {

    private static final Set<Integer> keysDown;

    static {
        keysDown = new HashSet<>();
    }

    /**
     * Returns if one of the keys is down
     *
     * @param keyCode Integer code of the key
     *
     * @return True if at least one of the keys is down
     */
    public static boolean isKeyDown(int... keyCode) {
        for (Integer i : keyCode) {
            if (keysDown.contains(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all keys currently listed in the set
     */
    public static void clear() {
        keysDown.clear();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keysDown.add(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keysDown.remove(keyEvent.getKeyCode());
    }
}
