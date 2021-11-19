package Game;

public class GameThread extends ThreadManager {

    public GameThread(Runnable runnable) {
        super(runnable,0);
    }
}
