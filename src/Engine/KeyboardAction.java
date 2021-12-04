package Engine;

/**
 * List of "categories" and functions to ask whether any key in that selection is pressed down. This allows for a very centralized place to
 * configure keybinds and the likes. Customizable Keys can be easily implemented by changing this from an enum to a class that has default
 * parameters, and allows for modifying the keys (or, simply by allowing the user to edit the keys. make keys not final in this instance)
 *
 * @author Thomas Kwashnak
 */
public enum KeyboardAction {
    MENU_ENTER(Key.ENTER, Key.SPACE),
    MENU_UP(Key.UP, Key.W),
    MENU_DOWN(Key.DOWN, Key.S),
    MENU_LEFT(Key.LEFT, Key.A),
    MENU_RIGHT(Key.RIGHT, Key.D),
    MENU_ESCAPE(Key.ESC),
    GAME_PAUSE(Key.P, Key.ESC),
    GAME_MOVE_LEFT(Key.A, Key.LEFT),
    GAME_MOVE_RIGHT(Key.D, Key.RIGHT),
    GAME_JUMP(Key.W, Key.SPACE, Key.UP),
    GAME_INSTRUCTIONS(Key.X),
    GAME_INTERACT(Key.SPACE),
    GAME_CROUCH(Key.S, Key.DOWN, Key.CTRL),
    GAME_ATTACK(Key.E),
    GAME_SPRINT(Key.SHIFT),
    GAME_RESPAWN(Key.SPACE);

    final int[] keys;

    /**
     * Converts the keys from {@link Key} to integer values, and then stores in keys
     *
     * @param keys List of {@link Key} objects to convert and store as integers
     */
    KeyboardAction(Key... keys) {
        this.keys = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            this.keys[i] = keys[i].getKeyCode();
        }
    }

    KeyboardAction(int... keys) {
        this.keys = keys;
    }

    public int[] getKeys() {
        return keys;
    }

    /**
     * @return {@code true} if any of the provided keys are down
     */
    public boolean isDown() {
        return Keyboard.isKeyDown(keys);
    }
}
