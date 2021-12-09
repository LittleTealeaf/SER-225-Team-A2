package Utils;

public class TimeParser {

    private static final long MS_PER_SECOND, SECOND_PER_MINUTE, MINUTE_PER_HOUR, MS_PER_HOUR, MS_PER_MINUTE;

    static {
        MS_PER_SECOND = 1000;
        SECOND_PER_MINUTE = 60;
        MINUTE_PER_HOUR = 60;
        MS_PER_MINUTE = MS_PER_SECOND * SECOND_PER_MINUTE;
        MS_PER_HOUR = MS_PER_MINUTE * MINUTE_PER_HOUR;
    }

    protected long time;

    public TimeParser() {
        this(0);
    }

    public TimeParser(long time) {
        this.time = time;
    }

    /**
     * Adds time to the TimeParser
     * @param time Time, in milliseconds, to add to the timer
     */
    public void addTime(long time) {
        this.time += time;
    }

    /**
     * Removes time from the TimeParser
     * @param time Time, in milliseconds, to remove from the timer
     */
    public void subtractTime(long time) {
        this.time -= time;
    }

    /**
     * Converts the time to a readable string. Uses {@link #getTime()} rather than specifically looking at the time object, such that overriding
     * the {@link #getTime()} method will modify the time that this method displays.
     *
     * @return Readable String representing the time.
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

        StringBuilder stringBuilder = new StringBuilder();

        if (hours > 0) {
            if (hours > 9) {
                stringBuilder.append((char) ('0' + hours / 10));
            }
            stringBuilder.append((char) ('0' + hours % 10)).append(':').append((char) ('0' + minutes / 10)).append((char) ('0' + minutes % 10))
                         .append(':');
            stringBuilder.append((char) ('0' + seconds / 10));
        } else if (minutes > 0) {
            if (minutes > 9) {
                stringBuilder.append((char) ('0' + minutes / 10));
            }
            stringBuilder.append((char) ('0' + minutes % 10)).append(':');
            stringBuilder.append((char) ('0' + seconds / 10));
        } else if (seconds > 9) {
            stringBuilder.append((char) ('0' + seconds / 10));
        }
        stringBuilder.append((char) ('0' + seconds % 10)).append('.');
        stringBuilder.append((char) ('0' + elapsed / 100)).append((char) ('0' + (elapsed / 10) % 10));

        return stringBuilder.toString();
    }

    /**
     * Gets the current time. Override this method in order to modify what the {@link #toString()} method outputs
     * @return Time, in milliseconds, to display
     */
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
