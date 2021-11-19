package Game;

public class GameThread extends ThreadManager {


    private static GameThread instance;

    public GameThread(Runnable runnable) {
        super(runnable,0);
        instance = this;
    }

    public static float getScale() {
        return ((float) instance.elapsedTick) / 15;
    }

}
