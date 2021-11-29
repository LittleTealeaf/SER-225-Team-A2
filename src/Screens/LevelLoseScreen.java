package Screens;

import Engine.Drawable;
import Engine.GamePanel;
import Engine.KeyboardAction;
import Level.Player;
import Level.PlayerAttack;
import Menu.Menu;
import SpriteFont.SpriteFont;

import java.awt.*;

public class LevelLoseScreen extends Menu {

    private final PlayLevelScreen playLevelScreen;

    public LevelLoseScreen(PlayLevelScreen playLevelScreen) {
        super();
        this.playLevelScreen = playLevelScreen;
        setDrawables(new Drawable[]{
                new SpriteFont("You lose!", 350, 270, "Comic Sans", 30, Color.white),
                new SpriteFont("Press Space to try again or Escape to go back to the main menu", 120, 300, "Comic Sans", 20, Color.white)
        });
    }

    public void update() {
        super.update();
        if (KeyboardAction.GAME_RESPAWN.isDown()) {
        	// if the player is in hardcore difficulty restart them at the first level otherwise restart them on the current level
        	if (GamePanel.getDifficultyHolder().getDifficulty() == 1)
        	{
        		playLevelScreen.loadMap(0);
        	}
        	else
        	{
        		playLevelScreen.resetLevel();
        	}
            Player.PLAYER_HEALTH = GamePanel.getDifficultyHolder().getDifficulty();
            PlayerAttack.dogHealth = 8;
        }
    }
}
