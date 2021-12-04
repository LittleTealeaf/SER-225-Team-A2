package Utils;

public class GameTimer extends TimeParser {

    private long startTime, endTime;
    private boolean running;

    public GameTimer() {
        startTime = 0;
        endTime = 0;
        time = 0;
        running = false;
    }

    public void start() {
        if (!running) {
            time = getElapsed();
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public long getElapsed() {
        return (running ? System.currentTimeMillis() : endTime) + time - startTime;
    }

    public void stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
        }
    }

    public void reset() {
        time = 0;
        startTime = System.currentTimeMillis();
    }

    @Override
    public long getTime() {
        return getElapsed();
    }
}
