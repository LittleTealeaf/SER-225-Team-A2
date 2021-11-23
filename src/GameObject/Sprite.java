package GameObject;

import Engine.GraphicsHandler;
import Engine.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

// This class is for representing a Sprite, which is essentially a Rectangle with an image attached
// it also includes an attribute for "bounds", which can be thought of a sub rectangle on the image where it can be interacted with (like for collisions)
public class Sprite extends RectangleOld implements IntersectableRectangleOld {
	protected BufferedImage image;
    protected RectangleOld bounds;
    protected ImageEffect imageEffect;

    public Sprite (BufferedImage image, float scale, ImageEffect imageEffect) {
        super(0, 0, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new RectangleOld(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = imageEffect;
    }

    public Sprite(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(x, y, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new RectangleOld(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = imageEffect;
    }
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(String imageFileName) {
		image = ImageLoader.load(imageFileName);
	}

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ImageEffect getImageEffect() { return imageEffect; }

    public void setImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
    }

    public RectangleOld getBounds() {
        return new RectangleOld(getBoundsX1(), getBoundsY1(), bounds.getWidth(), bounds.getHeight(), scale);
    }

    public float getBoundsX1() {
        return x + bounds.getX1();
    }

    public float getBoundsX2() {
        return x + bounds.getX2();
    }

    public float getBoundsY1() {
        return y + bounds.getY1();
    }

    public float getBoundsY2() {
        return y + bounds.getY2();
    }

    public float getScaledBoundsX1() {
        return getX() + (bounds.getX1() * scale);
    }

    public float getScaledBoundsX2() {
        return getScaledBoundsX1() + bounds.getScaledWidth();
    }

    public float getScaledBoundsY1() {
        return getY() + (bounds.getY1() * scale);
    }

    public float getScaledBoundsY2() {
        return getScaledBoundsY1() + bounds.getScaledHeight();
    }

    public RectangleOld getScaledBounds() {
        return new RectangleOld(getScaledBoundsX1(), getScaledBoundsY1(), bounds.getScaledWidth(), bounds.getScaledHeight());
    }

    public void setBounds(RectangleOld bounds) {
        this.bounds = new RectangleOld(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), scale);
    }

    public void setBounds(float x, float y, int width, int height) {
        this.bounds = new RectangleOld(x, y, width, height, scale);
    }

    public RectangleOld getIntersectRectangle() {
        return getScaledBounds();
    }

    @Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawImage(image, Math.round(getX()), Math.round(getY()), getScaledWidth(), getScaledHeight(), imageEffect);
	}

	public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        RectangleOld scaledBounds = getScaledBounds();
        scaledBounds.setColor(color);
        scaledBounds.draw(graphicsHandler);
    }

    @Override
    public String toString() {
        return String.format("Sprite: x=%s y=%s width=%s height=%s bounds=(%s, %s, %s, %s)", getX(), getY(), getScaledWidth(), getScaledHeight(), getScaledBoundsX1(), getScaledBoundsY1(), getScaledBounds().getWidth(), getScaledBounds().getHeight());
    }
}
