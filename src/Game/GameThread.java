package Game;

import java.awt.*;

public class GameThread extends ThreadManager {


    private static GameThread instance;

    private static final long RENDER_DELAY_MS;

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
        RENDER_DELAY_MS = 1000 / highestRefreshRate;
    }

    public GameThread(Runnable runnable) {
        super(runnable,5);
        //Somehow setting tickDelay to 0 causes the game to break in some way?
        instance = this;
    }

    public static float getScale() {
        return ((float) instance.elapsedTick) / 15;
    }

}
