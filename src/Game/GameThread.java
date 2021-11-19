package Game;

public class GameThread extends ThreadManager {

    public GameThread(Runnable runnable) {
        super(runnable,0);
    }

    public void run() {
        super.run();
    }

    public float getScaledMovement() {
        return (float) elapsedTick / 1000;
    }
}
