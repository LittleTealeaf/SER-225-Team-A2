package Builders;

import GameObject.Frame;
import GameObject.GameObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.IntFunction;

// Builder class to instantiate a GameObject class
public class GameObjectBuilder {

    protected final HashMap<String, Frame[]> animations = new HashMap<>();
    protected String startingAnimationName = "DEFAULT";

    public GameObjectBuilder() {}

    public GameObjectBuilder(Frame frame) {
        addDefaultAnimation(frame);
    }

    public GameObjectBuilder addDefaultAnimation(Frame frame) {
        animations.put("DEFAULT", new Frame[]{frame});
        return this;
    }

    public GameObjectBuilder(Frame[] frames) {
        addDefaultAnimation(frames);
    }

    public GameObjectBuilder addDefaultAnimation(Frame[] frames) {
        animations.put("DEFAULT", frames);
        return this;
    }

    public GameObjectBuilder addAnimation(String animationName, Frame[] frames) {
        animations.put(animationName, frames);
        return this;
    }

    public GameObjectBuilder addAnimation(String animationName, Frame frame) {
        animations.put(animationName, new Frame[]{frame});
        return this;
    }

    public GameObjectBuilder withStartingAnimation(String startingAnimationName) {
        this.startingAnimationName = startingAnimationName;
        return this;
    }

    public GameObject build(float x, float y) {
        return new GameObject(x, y, cloneAnimations(), startingAnimationName);
    }

    public HashMap<String, Frame[]> cloneAnimations() {
        HashMap<String, Frame[]> animationsCopy = new HashMap<>();
        for (String key : animations.keySet()) {
            Frame[] frames = animations.get(key);
            animationsCopy.put(key, Arrays.stream(frames).map(frame -> frame.copy()).toArray((IntFunction<Frame[]>) Frame[]::new));
        }
        return animationsCopy;
    }
}