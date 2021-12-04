package Engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KeyLocker {
    private final Set<KeyboardAction> lockedAdapters = new HashSet<>();

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

    public boolean isActionUnlocked(KeyboardAction keyboardAction) {
        return !lockedAdapters.contains(keyboardAction);
    }

    public void clear() {
        lockedAdapters.clear();
    }
}
