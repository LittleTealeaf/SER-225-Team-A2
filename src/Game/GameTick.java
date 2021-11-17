package Game;

import java.awt.*;

/**
 * Responsible for the looping of the class and containing values such as time since last tick, etc.
 * @author Thomas Kwashnak
 */
public class GameTick {

    private static final int SCREEN_REFRESH_RATE;

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
    }

    /*
    http://www.java2s.com/Code/Java/2D-Graphics-GUI/GettingtheCurrentScreenRefreshRateandNumberofColors.htm
     */
}
