package Level;

import GameObject.SpriteSheet;

/*
TODO: might delete cause isnt needed if powerup doesnt work as an enemy
 */
public class PowerUp extends MapEntity {

    public PowerUp(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public void initialize() {
        super.initialize();
    }

    public void update(Enemy enemy) {
        super.update();
        if (intersects(enemy)) {
            touchedEnemy(enemy);
        }
    }

    public void touchedEnemy(Enemy enemy) {
        enemy.hurtEnemy(this);
    }
}
