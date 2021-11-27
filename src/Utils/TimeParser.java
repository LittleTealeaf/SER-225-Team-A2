package Utils;

public class TimeParser {
    private static final long MS_PER_SECOND, SECOND_PER_MINUTE, MINUTE_PER_HOUR, MS_PER_HOUR, MS_PER_MINUTE;

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

    /**
     * Converts the time to a readable string
     * @return
     */
    public String toString() {
        /*
        Implementation Note:
        While yes, there is a more efficient method... I did it this way
        Refrain from changing as the ' ' characters are important for rendering in the right spot
         */
        long elapsed = getTime();
        long hours = elapsed / MS_PER_HOUR;
        long minutes = (elapsed %= MS_PER_HOUR) / MS_PER_MINUTE;
        long seconds = (elapsed %= MS_PER_MINUTE) / MS_PER_SECOND;
        elapsed %= MS_PER_SECOND;

        char[] chs = new char[hours > 0 ? 11 : minutes > 0 ? 8 : 5];

        //milliseconds
        chs[chs.length - 3] = '.';
        chs[chs.length - 2] = (char) ('0' + (elapsed / 100));
        chs[chs.length - 1] = (char) ('0' + (elapsed / 10) % 10);
        //seconds
        chs[chs.length - 5] = (char) ('0' + (seconds / 10));
        if(minutes == 0 && hours == 0 && chs[chs.length - 5] == '0') {
            chs[chs.length - 5] = ' ';
        }
        chs[chs.length - 4] = (char) ('0' + (seconds % 10));

        if(minutes > 0 || hours > 0) {
            chs[chs.length - 6] = ':';
            chs[chs.length - 7] = (char) ('0' + (minutes % 10));
            chs[chs.length - 8] = (char) ('0' + (minutes / 10));
            if(chs[chs.length - 8] == '0' && hours == 0) {
                chs[chs.length - 8] = ' ';
            }

            if(hours > 0) {
                chs[0] = (char) ('0' + (hours / 10) % 10);
                if(chs[0] == '0') {
                    chs[0] = ' ';
                }
                chs[1] = (char) ('0' + hours % 10);
                chs[2] = ':';
            }
        }

        return new String(chs);
    }
}
