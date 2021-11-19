package Game;

import java.awt.*;

/**
 * Responsible for the looping of the class and containing values such as time since last tick, etc.
 * @author Thomas Kwashnak
 */
@Deprecated
public class RenderThread extends ThreadManager {

    /**
     * May not need refresh rate and only refresh time ms
     */
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
        REFRESH_TIME_MS = 1000 / highestRefreshRate;
    }

    public RenderThread(Runnable runnable) {
        super(runnable, REFRESH_TIME_MS);
    }
}
