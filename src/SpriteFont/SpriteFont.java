package SpriteFont;

import Engine.Drawable;
import Engine.GraphicsHandler;

import java.awt.*;
import java.awt.font.FontRenderContext;

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

	public SpriteFont(String text, float x, float y, String fontName, int fontSize, Color color) {
		this.text = text;
		font = new Font(fontName, Font.PLAIN, fontSize);
		this.x = x;
		this.y = y;
		this.color = color;
		updateDimensions();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateDimensions();
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
		if (outlineColor != null && !outlineColor.equals(color)) {
			graphicsHandler.drawStringWithOutline(text, Math.round(x), Math.round(y), font, color, outlineColor, outlineThickness);
		} else {
			graphicsHandler.drawString(text, Math.round(x), Math.round(y), font, color);
		}
	}

	// this can be called instead of regular draw to have the text drop to the next line in graphics space on a new line character
	public void drawWithParsedNewLines(GraphicsHandler graphicsHandler) {

		/*
		Updates calculated height + width
		https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
		 */
		if(width == -1) {
			FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics(font);
			height = metrics.getHeight();
			width = metrics.stringWidth(text);
		}


		int drawLocationY = Math.round(this.y);
		for (String line: text.split("\n")) {
			if (outlineColor != null && !outlineColor.equals(color)) {
				graphicsHandler.drawStringWithOutline(line, Math.round(x), drawLocationY, font, color, outlineColor, outlineThickness);
			} else {
				graphicsHandler.drawString(line, Math.round(x), drawLocationY, font, color);
			}
			drawLocationY += font.getSize();
		}

	}

	public void updateDimensions() {
		width = -1;
		height = -1;
	}

	public boolean contains(Point point) {
		return point.x > x && point.y > y && point.x < x + width && point.y < y + height;
	}
}
