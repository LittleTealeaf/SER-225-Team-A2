package Level;

// Other classes can use this interface to listen for events from the Player class
public interface PlayerListener {

    /**
     * Whenever the player leaves the level in its completed state
     */
    void onLevelCompleted();
    void onDeath();

    /**
     * Whenever the player finishes the level by hitting the finish button
     */
    void onLevelFinished();
}
