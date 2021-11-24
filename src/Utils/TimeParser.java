package Utils;

public class TimeParser {
    private static long MS_PER_SECOND, SECOND_PER_MINUTE, MINUTE_PER_HOUR, MS_PER_HOUR, MS_PER_MINUTE;

    private long time;

    static {
        MS_PER_SECOND = 1000;
        SECOND_PER_MINUTE = 60;
        MINUTE_PER_HOUR = 60;
        MS_PER_MINUTE = MS_PER_SECOND * SECOND_PER_MINUTE;
        MS_PER_HOUR = MS_PER_MINUTE * MINUTE_PER_HOUR;
    }

    public TimeParser() {
        this(0);
    }

    public TimeParser(long time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void addTime(long time) {
        this.time += time;
    }

    public void subtractTime(long time) {
        this.time -= time;
    }

    public String toString() {
        long elapsed = getTime();
        long hours = elapsed / MS_PER_HOUR;
        long minutes = (elapsed %= MS_PER_HOUR) / MS_PER_MINUTE;
        long seconds = (elapsed %= MS_PER_MINUTE) / MS_PER_SECOND;
        elapsed %= MS_PER_SECOND;

        if(hours > 0) {
            return String.format("%s:%s:%s.%s",hours,minutes,seconds,elapsed/10);
        } else if(minutes > 0) {
            return String.format("%s:%s.%s",minutes,seconds,elapsed/10);
        } else {
            return String.format("%s.%s",seconds,elapsed/10);
        }
    }
}
