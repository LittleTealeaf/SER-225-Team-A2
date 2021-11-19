package Game;

/**
 * @author Thomas Kwashnak
 */
public class ThreadManager implements Runnable {

    private Thread thread;
    private long tickDelay, lastTick, elapsedTick;
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
                elapsedTick = 0;
                runnable.run();
            }

            long currentTick = System.currentTimeMillis();
            elapsedTick += currentTick - lastTick;
            lastTick = currentTick;
        }
        running = true;
    }
}
