package GameObject;

import Engine.Collidable;
import Engine.Drawable;
import Engine.GraphicsHandler;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents an animated sprite
 * Animations can either be passed in directly or loaded automatically in a subclass by overriding the getAnimations method
 * This class contains logic for transitioning animations as well as playing out the frames in an animation in a loop
 * Subclasses need to call down to this class's update method in order for animation logic to be performed
 * While this calls does not extend from Sprite, it is set up in a way where it is still treated by other classes as if it is a singular sprite
 * (based on value of currentFrame)
 */
public class AnimatedSprite implements Collidable, Drawable {

    // times frame delay before transitioning into the next frame of an animation
    private final Stopwatch frameTimer = new Stopwatch();
    // location of entity
    protected float x, y;
    // maps animation name to an array of Frames representing one animation
    protected HashMap<String, Frame[]> animations;
    // keeps track of current animation the sprite is using
    protected String currentAnimationName = "";
    protected String previousAnimationName = "";
    // keeps track of current frame number in an animation the sprite is using
    protected int currentFrameIndex;
    // if an animation has looped, this is set to true
    protected boolean hasAnimationLooped;
    // current Frame object the animation is using based on currentAnimationName and currentFrameIndex
    // this is essential for the class, as it uses this to be treated as "one sprite"
    protected Frame currentFrame;

    public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        this.x = x;
        this.y = y;
        this.animations = getAnimations(spriteSheet);
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    // Subclasses can override this method in order to add their own animations, which will be loaded in at initialization time
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return null;
    }

    /**
     * currentFrame is essentially a sprite, so each game loop cycle
     * the sprite needs to have its current state updated based on animation logic,
     * and location updated to match any changes to the animated sprite class
     */
    protected void updateCurrentFrame() {
        currentFrame = getCurrentFrame();
        currentFrame.setX(x);
        currentFrame.setY(y);
    }

    /**
     * gets the frame from current animation that the animated sprite class is currently using
     *
     * @return Frame from the current animation that the animated sprite class is currently using
     */
    protected Frame getCurrentFrame() {
        return animations.get(currentAnimationName)[currentFrameIndex];
    }

    public AnimatedSprite(float x, float y, HashMap<String, Frame[]> animations, String startingAnimationName) {
        this.x = x;
        this.y = y;
        this.animations = animations;
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    public AnimatedSprite(BufferedImage image, float x, float y, String startingAnimationName) {
        this.x = x;
        this.y = y;
        SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = getAnimations(spriteSheet);
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    public AnimatedSprite(float x, float y) {
        this.x = x;
        this.y = y;
        this.animations = new HashMap<>();
        this.currentAnimationName = "";
    }

    public void update() {
        // if animation name has been changed (previous no longer equals current), setup for the new animation and start using it
        if (!previousAnimationName.equals(currentAnimationName)) {
            currentFrameIndex = 0;
            updateCurrentFrame();
            frameTimer.setWaitTime(getCurrentFrame().getDelay());
            hasAnimationLooped = false;
        } else {
            // if animation has more than one frame, check if it's time to transition to a new frame based on that frame's delay
            if (getCurrentAnimation().length > 1 && currentFrame.getDelay() > 0) {

                // if enough time has passed based on current frame's delay and it's time to transition to a new frame,
                // update frame index to the next frame
                // It will also wrap around back to the first frame index if it was already on the last frame index (the animation will loop)
                if (frameTimer.isTimeUp()) {
                    currentFrameIndex++;
                    if (currentFrameIndex >= animations.get(currentAnimationName).length) {
                        currentFrameIndex = 0;
                        hasAnimationLooped = true;
                    }
                    frameTimer.setWaitTime(getCurrentFrame().getDelay());
                    updateCurrentFrame();
                }
            }
        }
        previousAnimationName = currentAnimationName;
    }

    // gets the animation that the animated sprite class is currently using
    protected Frame[] getCurrentAnimation() {return animations.get(currentAnimationName);}

    public void setAnimations(HashMap<String, Frame[]> animations) {
        this.animations = animations;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        currentFrame.draw(graphicsHandler);
    }

    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        currentFrame.drawBounds(graphicsHandler, color);
    }

    public float getX() {return currentFrame.getX();}

    public void setX(float x) {
        this.x = x;
        currentFrame.setX(x);
    }

    public float getY() {return currentFrame.getY();}

    public void setY(float y) {
        this.y = y;
        currentFrame.setY(y);
    }

    public float getX1() {return currentFrame.getX1();}

    public float getY1() {return currentFrame.getY1();}

    public float getX2() {return currentFrame.getX2();}

    public float getScaledX2() {return currentFrame.getScaledX2();}

    public float getY2() {return currentFrame.getY2();}

    public float getScaledY2() {return currentFrame.getScaledY2();}

    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void moveX(float dx) {
        this.x += dx;
        currentFrame.moveX(dx);
    }

    public void moveRight(float dx) {
        this.x += dx;
        currentFrame.moveRight(dx);
    }

    public void moveLeft(float dx) {
        this.x -= dx;
        currentFrame.moveLeft(dx);
    }

    public void moveY(float dy) {
        this.y += dy;
        currentFrame.moveY(dy);
    }

    public void moveDown(float dy) {
        this.y += dy;
        currentFrame.moveDown(dy);
    }

    public void moveUp(float dy) {
        this.y -= dy;
        currentFrame.moveUp(dy);
    }

    public float getScale() {
        return currentFrame.getScale();
    }

    public void setScale(float scale) {
        currentFrame.setScale(scale);
    }

    public int getWidth() {
        return currentFrame.getWidth();
    }

    public void setWidth(int width) {
        currentFrame.setWidth(width);
    }

    public int getHeight() {
        return currentFrame.getHeight();
    }

    public void setHeight(int height) {
        currentFrame.setHeight(height);
    }

    public Rectangle getBounds() {
        return currentFrame.getBounds();
    }

    public void setBounds(Rectangle bounds) {
        currentFrame.setBounds(bounds);
    }

    public float getBoundsX1() {
        return currentFrame.getBoundsX1();
    }

    public float getBoundsX2() {
        return currentFrame.getBoundsX2();
    }

    public float getScaledBoundsX2() {
        return currentFrame.getScaledBoundsX2();
    }

    public float getBoundsY1() {
        return currentFrame.getBoundsY1();
    }

    public float getBoundsY2() {
        return currentFrame.getBoundsY2();
    }

    public float getScaledBoundsY2() {
        return currentFrame.getScaledBoundsY2();
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return currentFrame.getIntersectRectangle();
    }

    public boolean intersects(Collidable other) {
        return currentFrame.intersects(other);
    }

    public boolean overlaps(Collidable other) {
        return currentFrame.overlaps(other);
    }

    @Override
    public String toString() {
        return String.format("Current Sprite: x=%s y=%s width=%s height=%s bounds=(%s, %s, %s, %s)", x, y, getScaledWidth(), getScaledHeight(),
                             getScaledBoundsX1(), getScaledBoundsY1(), getScaledBounds().getWidth(), getScaledBounds().getHeight()
                            );
    }

    public int getScaledWidth() {
        return currentFrame.getScaledWidth();
    }

    public int getScaledHeight() {
        return currentFrame.getScaledHeight();
    }

    public float getScaledBoundsX1() {
        return currentFrame.getScaledBoundsX1();
    }

    public float getScaledBoundsY1() {
        return currentFrame.getScaledBoundsY1();
    }

    public Rectangle getScaledBounds() {
        return currentFrame.getScaledBounds();
    }

    public void setCurrentAnimationName(String name) {
        this.currentAnimationName = name;
    }
}
