package Game;

/**
 * Runs the base loops of the game, including updates and renders. Multiple updates may happen between frames if it takes too long, meaning the
 * game will catch up once it gets too far ahead.
 * @author Thomas Kwashnak
 */
public class GameThread implements Runnable {

    /**
     * Static number of ms between updates
     */
    private static final long UPDATE_FIXED_MS = 12;

    /**
     * Main thread to run the game on
     */
    private final Thread thread;
    /**
     * Methods to run for rendering and updating
     */
    private final Runnable render, update;
    /**
     * Provides a means of ending the loop manually
     */
    private boolean running = true;


    /**
     * Creates a new game thread
     * @param render Runnable called when a render is needed
     * @param update Runnable called when an update is needed
     */
    public GameThread(Runnable render, Runnable update) {
        thread = new Thread(this);
        this.render = render;
        this.update = update;
    }

    /**
     * Starts the thread
     */
    public void start() {
        thread.start();
    }

    /**
     * Stops the thread
     */
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        //Sets the next update to the current time
        long nextUpdateTime = System.currentTimeMillis();
        long currentTime;

        //Loop until running is false
        while(running) {
            //Update current time
            currentTime = System.currentTimeMillis();

            //Repeat until the update time is after (in the future) current time
            while(currentTime > nextUpdateTime) {
                update.run();
                nextUpdateTime += UPDATE_FIXED_MS;

                //Run a render
                render.run();
            }


        }
        //Reset running once it's set to false
        running = true;
    }
}

