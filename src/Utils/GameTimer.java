package Utils;

/**
 * Represents a Game Timer that can track the elapsed time between two individual times. Whenever it stops, and is restarted, it will simply just
 * add on to the current elapsed time rather than reset the elapsed time.
 * @author Thomas Kwashnak
 */
public class GameTimer extends TimeParser {

    private long startTime, endTime;
    private boolean running;

    /**
     * Creates a new Game Timer starting at 0 seconds
     */
    public GameTimer() {
        startTime = 0;
        endTime = 0;
        time = 0;
        running = false;
    }

    /**
     * Starts the timer only if it is not running. If there is elapsed time, it will set the current time to the current elapsed time (in essence
     * preserving the previous elapsed time)
     */
    public void start() {
        if (!running) {
            time = getElapsed();
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    /**
     * Calculates and returns the elapsed time. If the timer is currently running, it will use the current point in time to calculate the total
     * time. If the timer is stopped, then it will use the endTime variable. It adds this difference to the time variable, which contains the
     * elapsed time of previous stretches.
     * @return The current elapsed time of the Game Timer
     */
    public long getElapsed() {
        return (running ? System.currentTimeMillis() : endTime) + time - startTime;
    }

    /**
     * Stops the game timer and marks the timer as stopped
     */
    public void stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
        }
    }

    /**
     * Resets the timer. Can be used whether the timer is actively running
     */
    public void reset() {
        time = 0;
        startTime = System.currentTimeMillis();
    }

    /**
     * Overrides the getTime method in order to use the {@link #getElapsed()} method to grab the time, instead of the general time, in parsing
     * @return Total elapsed time of the GameTimer
     */
    @Override
    public long getTime() {
        return getElapsed();
    }
}
