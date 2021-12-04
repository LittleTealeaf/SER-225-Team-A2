package Level;

import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all flags in the game -- all flags should extend from it
public class Flag extends MapEntity {

    public Flag(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public Flag(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Flag(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public Flag(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public Flag(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public Flag(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public Flag(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(Player player) {
        super.update();
        if (intersects(player)) {
            touchedPlayer();
        }
    }

    // A subclass can override this method to specify what it does when it touches the player
    public void touchedPlayer() {

    }
}
