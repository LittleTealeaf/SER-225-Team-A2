package SpriteFont;

import Engine.Drawable;
import Engine.GraphicsHandler;

import java.awt.*;

// This class represents a sprite font, which is graphic text (text drawn to the screen as if it were an image)

public class SpriteFont implements Drawable {

    protected String text;
    protected Font font;
    protected float x;
    protected float y;
    protected float width, height;
    protected Color color;
    protected Color outlineColor;
    protected float outlineThickness = 1f;
    /**
     * Whether to automatically multi-line the text if `\n` is present
     */
    protected boolean multiLine = false, containsNewLines;

    public SpriteFont(String text, float x, float y, String fontName, int fontSize, Color color) {
        this(text, x, y, new Font(fontName, Font.PLAIN, fontSize), color);
    }

    public SpriteFont(String text, float x, float y, Font font, Color color) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        this.color = color;
        updateDimensions();
        updateContainsNewLines();
    }

    public void updateDimensions() {
        width = -1;
        height = -1;
    }

    public void updateContainsNewLines() {
        containsNewLines = text.contains("\n");
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFontName(String fontName) {
        this.font = new Font(fontName, this.font.getStyle(), this.font.getSize());
        updateDimensions();
    }

    public void setFontStyle(int fontStyle) {
        this.font = new Font(font.getFontName(), fontStyle, this.font.getSize());
        updateDimensions();
    }

    public void setFontSize(int size) {
        this.font = new Font(font.getFontName(), this.font.getStyle(), size);
        updateDimensions();
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public void setOutlineThickness(float outlineThickness) {
        this.outlineThickness = outlineThickness;
        updateDimensions();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveX(float dx) {
        x += dx;
    }

    public void moveY(float dy) {
        y += dy;
    }

    public void moveRight(float dx) {
        x += dx;
    }

    public void moveLeft(float dx) {
        x -= dx;
    }

    public void moveDown(float dy) {
        y += dy;
    }

    public void moveUp(float dy) {
        y -= dy;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (multiLine && containsNewLines) {
            drawWithParsedNewLines(graphicsHandler);
        } else {
            if (outlineColor != null && !outlineColor.equals(color)) {
                graphicsHandler.drawStringWithOutline(getText(), Math.round(x), Math.round(y), font, color, outlineColor, outlineThickness);
            } else {
                graphicsHandler.drawString(getText(), Math.round(x), Math.round(y), font, color);
            }
        }
    }

    // this can be called instead of regular draw to have the text drop to the next line in graphics space on a new line character
    public void drawWithParsedNewLines(GraphicsHandler graphicsHandler) {

		/*
		Updates calculated height + width
		https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
		 */
        if (width == -1) {
            FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics(font);
            height = metrics.getHeight();
            width = metrics.stringWidth(getText());
        }

        int drawLocationY = Math.round(this.y);
        for (String line : text.split("\n")) {
            if (outlineColor != null && !outlineColor.equals(color)) {
                graphicsHandler.drawStringWithOutline(line, Math.round(x), drawLocationY, font, color, outlineColor, outlineThickness);
            } else {
                graphicsHandler.drawString(line, Math.round(x), drawLocationY, font, color);
            }
            drawLocationY += font.getSize();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateDimensions();
        updateContainsNewLines();
    }

    public boolean isMultiLine() {
        return multiLine;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public boolean contains(Point point) {
        return point.x > x && point.y > y && point.x < x + width && point.y < y + height;
    }
}
