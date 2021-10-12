package Engine;

import java.util.HashSet;

// This class can be used to keep track of "locked" and "unlocked" keys based on the class
// For example, it's often useful to "lock" a key if pressed down until its been released, since holding down a key will continually count as a "key press".
// This way, that "key press" will only be counted once per press.
// This class does NOT do anything to the keyboard class to prevent a key from actually being detected -- that is not advisable as multiple classes may be detecting key presses separately
public class KeyLocker {
    private HashSet<Key> lockedKeys = new HashSet<>();

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

    public void setKeys(boolean locked, KeyboardAdapter keyChannel) {
        setKeys(locked,keyChannel.getKeys());
    }

    public void setKeys(KeyboardAdapter... adapters) {
        for(KeyboardAdapter adapter : adapters) {
            setKeys(adapter.isDown(),adapter.getKeys());
        }
    }

    public boolean isKeyLocked(KeyboardAdapter adapter) {
        return isKeyLocked(adapter.getKeys()[0]);
    }

    public void setKeys(boolean locked, Key... keys) {
        if(locked) {
            lockKey(keys);
        } else {
            unlockKey(keys);
        }
    }



    // check if a key is currently locked
    public boolean isKeyLocked(Key key) {
        return lockedKeys.contains(key);
    }

    public void clear() {
        lockedKeys.clear();
    }
}
