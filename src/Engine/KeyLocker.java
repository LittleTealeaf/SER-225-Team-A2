package Engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// This class can be used to keep track of "locked" and "unlocked" keys based on the class
// For example, it's often useful to "lock" a key if pressed down until its been released, since holding down a key will continually count as a "key press".
// This way, that "key press" will only be counted once per press.
// This class does NOT do anything to the keyboard class to prevent a key from actually being detected -- that is not advisable as multiple classes may be detecting key presses separately
public class KeyLocker {
    private Set<KeyboardAction> lockedAdapters = new HashSet<>();

    public void setAction(KeyboardAction... keyboardActions) {
        for(KeyboardAction keyboardAction : keyboardActions) {
            if(keyboardAction.isDown()) {
                lockedAdapters.add(keyboardAction);
            } else {
                lockedAdapters.remove(keyboardAction);
            }
        }
    }

    public void unlockAction(KeyboardAction... keyboardActions) {
        for(KeyboardAction keyboardAction : keyboardActions) {
            lockedAdapters.remove(keyboardAction);
        }
    }

    public void lockAction(KeyboardAction... keyboardActions) {
        Collections.addAll(lockedAdapters, keyboardActions);
    }

    public boolean isActionLocked(KeyboardAction keyboardAction) {
        return lockedAdapters.contains(keyboardAction);
    }

    public void clear() {
        lockedAdapters.clear();
    }
}
