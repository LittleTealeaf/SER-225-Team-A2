package Engine;

/**
 * List of "categories" and functions to ask whether any key in that selection is pressed down
 */
public enum KeyboardAdapter {
    MENU_ENTER(Key.ENTER, Key.SPACE),
    MENU_UP(Key.UP,Key.W),
    MENU_DOWN(Key.DOWN,Key.S),
    MENU_LEFT(Key.LEFT,Key.A),
    MENU_RIGHT(Key.RIGHT,Key.D);

    Key[] keys;

    KeyboardAdapter(Key... keys) {
        this.keys = keys;
    }

    public Key[] getKeys() {
        return keys;
    }

    public boolean isDown() {
        for(Key key : keys) {
            if(Keyboard.isKeyDown(key)) {
                return true;
            }
        }
        return false;
    }


}
