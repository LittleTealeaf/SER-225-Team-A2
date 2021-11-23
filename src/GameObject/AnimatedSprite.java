package GameObject;

import Engine.GraphicsHandler;
import Engine.Vector;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents an animated sprite<br>
 * Animations can either be passed in directly or loaded automatically in a subclass by overriding the getAnimations method<br>
 * This class contains logic for transitioning animations as well as playing out the frames in an animation in a loop<br>
 * Subclasses need to call down to this class's update method in order for animation logic to be performed<br>
 * While this calls does not extend from Sprite, it is set up in a way where it is still treated by other classes as if it is a singular sprite
 * (based on value of currentFrame)<br>
 */
public class AnimatedSprite implements Intersectable, Overlappable {

    /**
     * Position of the Animated Sprite
     */
    protected Vector pos;

    /**
     * maps animation name to an array of Frames representing one animation
     */
    protected HashMap<String, Frame[]> animations;

    /**
     * keeps track of current animation the sprite is using
     */
    protected String currentAnimationName = "";
    protected String previousAnimationName = "";

    /**
     * keeps track of current frame number in an animation the sprite is using
     */
    protected int currentFrameIndex;

    /**
     * if an animation has looped, this is set to true
     */
    protected boolean hasAnimationLooped;

    /**
     * current Frame object the animation is using based on currentAnimationName and currentFrameIndex
     * this is essential for the class, as it uses this to be treated as "one sprite"
     */
    protected Frame currentFrame;

    /**
     * times frame delay before transitioning into the next frame of an animation
     */
    private Stopwatch frameTimer = new Stopwatch();

    public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        pos = new Vector(x, y);
        this.animations = getAnimations(spriteSheet);
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    /**
     * Subclasses can override this method in order to add their own animations, which will be loaded in at initialization time
     *
     * @param spriteSheet
     *
     * @return
     */
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return null;
    }

    /**
     * currentFrame is essentially a sprite, so each game loop cycle<br>
     * the sprite needs to have its current state updated based on animation logic,<br>
     * and location updated to match any changes to the animated sprite class
     */
    protected void updateCurrentFrame() {
        currentFrame = getCurrentFrame();
        currentFrame.setLocationReference(pos);
    }

    /**
     * gets the frame from current animation that the animated sprite class is currently using
     *
     * @return
     */
    protected Frame getCurrentFrame() {
        return animations.get(currentAnimationName)[currentFrameIndex];
    }

    public AnimatedSprite(float x, float y, HashMap<String, Frame[]> animations, String startingAnimationName) {
        pos = new Vector(x, y);
        this.animations = animations;
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    public AnimatedSprite(BufferedImage image, float x, float y, String startingAnimationName) {
        pos = new Vector(x, y);
        SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = getAnimations(spriteSheet);
        this.currentAnimationName = startingAnimationName;
        updateCurrentFrame();
    }

    public AnimatedSprite(float x, float y) {
        pos = new Vector(x, y);
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

    /**
     * gets the animation that the animated sprite class is currently using
     *
     * @return
     */
    protected Frame[] getCurrentAnimation() {
        return animations.get(currentAnimationName);
    }

    public void setAnimations(HashMap<String, Frame[]> animations) {
        this.animations = animations;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        currentFrame.draw(graphicsHandler);
    }

    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        currentFrame.drawBounds(graphicsHandler, color);
    }

    public float getX() {
        return currentFrame.getBounds().getX1();
    }

    public void setX(float x) {
        pos.setX(x);
    }

    public float getY() {
        return currentFrame.getBounds().getY1();
    }

    public void setY(float y) {
        pos.setY(y);
    }

    public float getX1() {
        return currentFrame.getBounds().getX1();
    }

    public float getY1() {
        return currentFrame.getBounds().getY1();
    }

    public float getX2() {
        return currentFrame.getBounds().getX2();
    }

    public float getScaledX2() {
        return currentFrame.getScaledBounds().getX2();
    }

    public float getY2() {
        return currentFrame.getBounds().getY2();
    }

    public float getScaledY2() {
        return currentFrame.getScaledBounds().getY2();
    }

    public void setLocation(Vector location) {
        pos.set(location);
    }

    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void moveRight(float dx) {
        moveX(dx);
    }

    public void moveX(float dx) {
        move(new Vector(dx, 0));
    }

    public void move(Vector vector) {
        pos.add(vector);
    }

    public void moveLeft(float dx) {
        moveX(-dx);
    }

    public void moveDown(float dy) {
        moveY(dy);
    }

    public void moveY(float dy) {
        this.pos.addY(dy);
        currentFrame.move(new Vector(0, dy));
    }

    public void moveUp(float dy) {
        moveY(-dy);
    }

    public float getScale() {
        return currentFrame.getScale();
    }

    public void setScale(float scale) {
        currentFrame.setScale(scale);
    }

    @Deprecated
    public float getWidth() {
        return currentFrame.getWidth();
    }

    @Deprecated
    public void setWidth(float width) {
        currentFrame.setWidth(width);
    }

    @Deprecated
    public float getHeight() {
        return currentFrame.getHeight();
    }

    @Deprecated
    public void setHeight(float height) {
        currentFrame.setHeight(height);
    }

    public Rectangle getBounds() {
        return currentFrame.getBounds();
    }

    public void setBounds(Rectangle bounds) {
        currentFrame.setBounds(bounds);
    }

    public float getBoundsX1() {
        return currentFrame.getBounds().getX1();
    }

    public float getBoundsX2() {
        return currentFrame.getBounds().getX2();
    }

    public float getScaledBoundsX2() {
        return currentFrame.getBounds().getScaledX2();
    }

    public float getBoundsY1() {
        return currentFrame.getBounds().getY1();
    }

    public float getBoundsY2() {
        return currentFrame.getBounds().getY2();
    }

    public float getScaledBoundsY2() {
        return currentFrame.getBounds().getScaledY2();
    }

    @Override
    public boolean intersects(Intersectable other) {
        return currentFrame.intersects(other);
    }

    @Override
    public boolean overlaps(Overlappable other) {
        return currentFrame.overlaps(other);
    }

    @Override
    public String toString() {
        return String.format("Current Sprite: x=%s y=%s width=%s height=%s bounds=(%s, %s, %s, %s)", pos.getX(), pos.getY(), getScaledWidth(),
                             getScaledHeight(), getScaledBoundsX1(), getScaledBoundsY1(), getScaledBounds().getWidth(), getScaledBounds().getHeight()
                            );
    }

    public float getScaledWidth() {
        return currentFrame.getScaledBounds().getWidth();
    }

    public float getScaledHeight() {
        return currentFrame.getScaledBounds().getWidth();
    }

    public float getScaledBoundsX1() {
        return currentFrame.getScaledBounds().getX1();
    }

    public float getScaledBoundsY1() {
        return currentFrame.getScaledBounds().getY1();
    }

    public Rectangle getScaledBounds() {
        return currentFrame.getScaledBounds();
    }

    public void setCurrentAnimationName(String name) {
        this.currentAnimationName = name;
    }

    public Vector getPos() {
        return pos;
    }

    @Override
    public Vector getMinLocation() {
        return currentFrame.getMinLocation();
    }

    @Override
    public Vector getMaxLocation() {
        return currentFrame.getMaxLocation();
    }
}
