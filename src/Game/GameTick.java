package Game;

import java.awt.*;

/**
 * Responsible for the looping of the class and containing values such as time since last tick, etc.
 * @author Thomas Kwashnak
 */
public class GameTick implements Runnable {

    /**
     * May not need refresh rate and only refresh time ms
     */
    private static final int SCREEN_REFRESH_RATE;
    private static final long REFRESH_TIME_MS;

    static {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //        Sets the highest refresh rate to be the highest display rate
        int highestRefreshRate = 0;
        for(GraphicsDevice device : ge.getScreenDevices()) {
            int refreshRate = device.getDisplayMode().getRefreshRate();
            System.out.println(refreshRate);
            if(refreshRate > highestRefreshRate) {
                highestRefreshRate = refreshRate;
            }
        }
        SCREEN_REFRESH_RATE = highestRefreshRate;
        REFRESH_TIME_MS = 1000 / SCREEN_REFRESH_RATE;
    }

    /*
    http://www.java2s.com/Code/Java/2D-Graphics-GUI/GettingtheCurrentScreenRefreshRateandNumberofColors.htm
     */

    private Thread thread;
    private Runnable runnable;
    private boolean runCode = true;
    private long lastTime;
    private long timeSincePing;

    public GameTick(Runnable runnable) {
        thread = new Thread(this);
        this.runnable = runnable;
    }

    public void start() {
        lastTime = System.currentTimeMillis();
        timeSincePing = 0;
        thread.start();
    }

    public void end() {
        runCode = false;
    }

    public void run() {
        while(runCode) {

            if(timeSincePing > REFRESH_TIME_MS) {
                runnable.run();
                timeSincePing = 0;
            }

            long currentTime = System.currentTimeMillis();
            timeSincePing += currentTime - lastTime;
            lastTime = currentTime;
        }
        runCode = true;
    }


}
