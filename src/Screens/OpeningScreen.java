package Screens;

import java.awt.Color;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

public class OpeningScreen extends Screen {
	
	
	private AnimatedSprite cat;
	private Stopwatch catFirstMovement;
	private ScreenCoordinator screenCoordinator;
	private SpriteFont story;
	private SpriteFont story1;
	private SpriteFont story2;
	private SpriteFont story3;
	private SpriteFont story4;
	private SpriteFont story5;
	private SpriteFont story6;
	
	
	
	public OpeningScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

	@Override
	public void initialize() {
		SpriteSheet catSpriteSheet = new SpriteSheet(ImageLoader.load("Cat.png"), 24, 24);
		
		story = new SpriteFont("CAT NEEDS YOUR HELP!", 55, 100, "Comic Sans", 60, new Color(255, 0, 0));
		story.setOutlineColor(Color.black);
        story.setOutlineThickness(3);
        
        story1 = new SpriteFont("HELP! I was catnapped and I am trying", 170, 140, "Comic Sans", 25, new Color(255, 215, 0));
		story1.setOutlineColor(Color.black);
        story1.setOutlineThickness(3);
        
        story2 = new SpriteFont("to get back to my cat family. The catnappers", 170, 180, "Comic Sans", 25, new Color(255, 215, 0));
		story2.setOutlineColor(Color.black);
        story2.setOutlineThickness(3);
        
        story3 = new SpriteFont("brought me across all these obstacles that I", 170, 220, "Comic Sans", 25, new Color(255, 215, 0));
		story3.setOutlineColor(Color.black);
        story3.setOutlineThickness(3);
        
        story4 = new SpriteFont("need to cross by myself to get back. I need", 170, 260, "Comic Sans", 25, new Color(255, 215, 0));
      	story4.setOutlineColor(Color.black);
        story4.setOutlineThickness(3);
        
        story5 = new SpriteFont("your assistance to get back to the rest", 170, 300, "Comic Sans", 25, new Color(255, 215, 0));
      	story5.setOutlineColor(Color.black);
        story5.setOutlineThickness(3);
        
        story6 = new SpriteFont("of my CAT FAMILY!", 230, 340, "Comic Sans", 25, new Color(255, 215, 0));
      	story6.setOutlineColor(Color.black);
        story6.setOutlineThickness(3);
        
		cat = new AnimatedSprite(0, 0, getCatAnimations(catSpriteSheet), "STAND_RIGHT");
		catFirstMovement = new Stopwatch();
		catFirstMovement.setWaitTime(10000);
		cat.setCurrentAnimationName("WALK_RIGHT");
				
	}

	@Override
	public void update() {
		if(catFirstMovement.isTimeUp()) {
			cat.setCurrentAnimationName("STAND_RIGHT");
			screenCoordinator.setGameState(GameState.MENU);
		}
		else {
			cat.moveRight(1);
		}
		cat.update();

		
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// TODO Auto-generated method stub
		cat.draw(graphicsHandler);
		story.draw(graphicsHandler);
		story1.draw(graphicsHandler);
		story2.draw(graphicsHandler);
		story3.draw(graphicsHandler);
		story4.draw(graphicsHandler);
		story5.draw(graphicsHandler);
		story6.draw(graphicsHandler);
		
		
	}
	
	
	
	public HashMap<String, Frame[]> getCatAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });
            
            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 200)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 200)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 0)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 0)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 0)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 0)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0), 0)
                            .withScale(3)
                            .withBounds(8, 12, 8, 6)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0), 0)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 12, 8, 6)
                            .build()
            });

            put("DEATH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(5, 0), 100)
                            .withScale(3)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 1), 100)
                            .withScale(3)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                            .withScale(3)
                            .build()
            });

            put("DEATH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(5, 0), 100)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 1), 100)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
        }};
    }


}
