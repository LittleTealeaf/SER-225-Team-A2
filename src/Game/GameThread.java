package Game;

public class GameThread implements Runnable {

    private static double delta_time;

    private static final long UPDATE_FIXED_MS;

    public static final float UPDATE_FACTOR;

    static {
        UPDATE_FIXED_MS = 5;
        UPDATE_FACTOR = UPDATE_FIXED_MS / 15.0f;
    }

    private final Thread thread;
    private final Runnable render, update;
    private boolean running = true;


    public GameThread(Runnable render, Runnable update) {
        thread = new Thread(this);
        this.render = render;
        this.update = update;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
//        lastTime = System.currentTimeMillis();
        long nextUpdateTime = System.currentTimeMillis();
        long currentTime;
        while(running) {
            currentTime = System.currentTimeMillis();
            while(currentTime > nextUpdateTime) {
                update.run();
                nextUpdateTime += UPDATE_FIXED_MS;
            }
            render.run();

//            delta_time = ((double) timePassed) / 1000;
//            while(timePassed >= UPDATE_FIXED_MS) {
//                timePassed -= UPDATE_FIXED_MS;
//                update.run();
//            }
//
//            render.run();
//            currentTime = System.currentTimeMillis();
//            timePassed += currentTime - lastTime;
//            lastTime = currentTime;
        }
        running = true;
    }
}
