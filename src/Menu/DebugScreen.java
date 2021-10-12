package Menu;

import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;

public class DebugScreen extends Menu {
    public DebugScreen(ScreenCoordinator screenCoordinator) {
        setMenuItemsAsGrid(new MenuItem[][] {
                new MenuItem[] {
                        new MenuItem("PLAY GAME",80,100, () -> screenCoordinator.setGameState(GameState.LEVEL)),
                        new MenuItem("LEVEL SELECT",350,100, () -> screenCoordinator.setGameState(GameState.LEVELSELECT))
                }, new MenuItem[] {
                    new MenuItem("CREDITS",80,200, () -> screenCoordinator.setGameState(GameState.CREDITS)),
                    new MenuItem("NARRATIVE",350,200, () -> screenCoordinator.setGameState(GameState.OPENING))


                }, new MenuItem[] {
                        new MenuItem("INSTRUCTIONS",80,300, () -> screenCoordinator.setGameState(GameState.INSTRUCTIONS)),
                    new MenuItem("OPTIONS",350,300, () -> screenCoordinator.setGameState(GameState.OPTIONS))
            }
        });
        setBackground(new TitleScreenMap());
    }
}
