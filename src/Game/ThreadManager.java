package Game;

/**
 * @author Thomas Kwashnak
 */
public class ThreadManager implements Runnable {

    private Thread thread;
    protected long lastTick, elapsedTick;
    private final long tickDelay;
    private boolean running = true;
    private Runnable runnable;

    public ThreadManager(Runnable runnable, long tickDelay) {
        thread = new Thread(this);
        this.tickDelay = tickDelay;
        this.runnable = runnable;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        lastTick = System.currentTimeMillis();
        while(running) {
            if(elapsedTick > tickDelay) {
                runnable.run();
                elapsedTick = 0;
            }

            long currentTick = System.currentTimeMillis();
            elapsedTick += currentTick - lastTick;
            lastTick = currentTick;
        }
        running = true;
    }

    public static float getTimeScale() {
        return 0f;
    }
}
