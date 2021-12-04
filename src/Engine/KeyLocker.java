package Engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that allows for the locking of keyActions in order to prevent inputs from happening multiple times when a key is pressed down
 *
 * @author Alex Thimineur (and others)
 * @author Thomas Kwashnak (Updated)
 */
public class KeyLocker {

    private final Set<KeyboardAction> lockedAdapters = new HashSet<>();

    /**
     * Updates a current action with its current state. If an action is pressed, then it will lock that keyAction down. If that action is not
     * pressed, then it will remove that keyAction from the locked adapters
     *
     * @param keyboardActions List of keyboardActions to update
     */
    public void updateActions(KeyboardAction... keyboardActions) {
        for (KeyboardAction keyboardAction : keyboardActions) {
            if (keyboardAction.isDown()) {
                lockedAdapters.add(keyboardAction);
            } else {
                lockedAdapters.remove(keyboardAction);
            }
        }
    }

    /**
     * Unlocks one or more keyboard actions, removing them from the list
     *
     * @param keyboardActions Keyboard Actions to unlock
     */
    public void unlockAction(KeyboardAction... keyboardActions) {
        for (KeyboardAction keyboardAction : keyboardActions) {
            lockedAdapters.remove(keyboardAction);
        }
    }

    /**
     * Locks one or more keyboard actions, adding them to the list
     *
     * @param keyboardActions Keyboard Actions to lock
     */
    public void lockAction(KeyboardAction... keyboardActions) {
        Collections.addAll(lockedAdapters, keyboardActions);
    }

    /**
     * Checks if an action is locked
     *
     * @param keyboardAction Keyboard Action to check if it is locked
     *
     * @return {@code true} if that keyboard action is found in the locked keys list, {@code false} otherwise
     */
    public boolean isActionLocked(KeyboardAction keyboardAction) {
        return lockedAdapters.contains(keyboardAction);
    }

    /**
     * Checks if an action is unlocked
     *
     * @param keyboardAction Keyboard Action to check if it is unlocked
     *
     * @return {@code true} if that action is not in the locked keys list, {@code false} otherwise
     */
    public boolean isActionUnlocked(KeyboardAction keyboardAction) {
        return !lockedAdapters.contains(keyboardAction);
    }

    /**
     * Removes all keyboard actions from the list
     */
    public void clear() {
        lockedAdapters.clear();
    }
}
