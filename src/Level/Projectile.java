package Level;

import Engine.CollisionType;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.RectangleOld;
import GameObject.SpriteSheet;
import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all projectiles in the game -- all projectiles should extend from it
public class Projectile extends MapEntity {

    public Projectile(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public Projectile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Projectile(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public Projectile(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public Projectile(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public Projectile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public Projectile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, RectangleOld bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    @Override
    public void initialize() {
        super.initialize();
        collisionType = CollisionType.DAMAGE;
    }

    public void update(Player player) {
        super.update();
        if (intersects(player)) {
            touchedPlayer(player);
        }
    }

    // A subclass can override this method to specify what it does when it touches the player
    public void touchedPlayer(Player player) {
        player.hurtPlayer(this);
    }   
}
