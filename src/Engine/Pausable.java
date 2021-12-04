package Engine;

/**
 * Indicates that a class can be paused and resumed. Pretty basic.
 *
 * @author Thomas Kwashnak
 */
public interface Pausable {

    void resume();

    void pause();
}
