package Utils;

public class GameTimer extends TimeParser {

    private long startTime, endTime, addTime;
    private boolean running;

    public GameTimer() {
        startTime = 0;
        endTime = 0;
        addTime = 0;
        running = false;
    }

    public void start() {
        addTime = getElapsed();
        if(!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        endTime = System.currentTimeMillis();
        running = false;
    }

    public void reset() {
        addTime = 0;
        startTime = System.currentTimeMillis();
    }

    public long getElapsed() {
        return (running ? System.currentTimeMillis() : endTime ) + addTime - startTime;
    }

    public long getTime() {
        return getElapsed();
    }
}
