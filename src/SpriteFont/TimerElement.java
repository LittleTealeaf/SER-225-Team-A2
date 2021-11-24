package SpriteFont;

import Utils.TimeParser;

import java.awt.*;

public class TimerElement extends SpriteFont {

    private TimeParser timeParser;

    public TimerElement(String text, float x, float y, String fontName, int fontSize, Color color) {
        super(text, x, y, fontName, fontSize, color);
    }

    public TimerElement(TimeParser timeParser, float x, float y, String fontName, int fontSize, Color color) {
        super(null,x,y,fontName,fontSize,color);
        this.timeParser= timeParser;
    }

    public TimerElement(TimeParser timeParser, float x, float y, Font font, Color color) {
        super(null,x,y,font,color);
        this.timeParser = timeParser;
    }

    public TimeParser getGameTimer() {
        return timeParser;
    }

    public void setGameTimer(TimeParser timeParser) {
        this.timeParser = timeParser;
    }

    @Override
    public String getText() {
        return timeParser != null ? timeParser.toString() : "NO TIME SPECIFIED";
    }
}
