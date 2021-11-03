package Engine;

import java.util.HashSet;

// This class can be used to keep track of "locked" and "unlocked" keys based on the class
// For example, it's often useful to "lock" a key if pressed down until its been released, since holding down a key will continually count as a "key press".
// This way, that "key press" will only be counted once per press.
// This class does NOT do anything to the keyboard class to prevent a key from actually being detected -- that is not advisable as multiple classes may be detecting key presses separately
public class KeyLocker {
    private HashSet<Key> lockedKeys = new HashSet<>();
    private HashSet<KeyboardAction> lockedAdapters = new HashSet<>();

    // lock a key
    public void lockKey(Key... keys) {
        for(Key key : keys) {
            lockedKeys.add(key);
        }
    }

    // unlock a key
    public void unlockKey(Key... keys) {
        for(Key key : keys) {
            lockedKeys.remove(key);
        }
    }

    public void setKeys(boolean locked, KeyboardAction keyChannel) {
        setKeys(locked,keyChannel.getKeys());
    }

    public void setKeys(KeyboardAction... adapters) {
        for(KeyboardAction adapter : adapters) {
            lockedAdapters.add(adapter);
        }
    }

    public void setKeys(boolean locked, Key... keys) {
        if(locked) {
            lockKey(keys);
        } else {
            unlockKey(keys);
        }
    }

    public void setAction(KeyboardAction keyboardAction) {
        if(keyboardAction.isDown() && !isActionLocked(keyboardAction)) {
            lockAction(keyboardAction);
        } else {
            unlockAction(keyboardAction);
        }
    }

    public void unlockAction(KeyboardAction keyboardAction) {
        lockedAdapters.remove(keyboardAction);
    }

    public void lockAction(KeyboardAction keyboardAction) {
        lockedAdapters.add(keyboardAction);
    }

    public boolean isActionLocked(KeyboardAction keyboardAction) {
        return lockedAdapters.contains(keyboardAction);
    }



    // check if a key is currently locked
    public boolean isActionLocked(Key key) {
        return lockedKeys.contains(key);
    }

    public void clear() {
        lockedKeys.clear();
    }
}
