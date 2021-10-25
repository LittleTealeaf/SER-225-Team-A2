package Menu;

import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;

public class DebugScreen extends Menu {

    public DebugScreen(ScreenCoordinator screenCoordinator) {
        setMenuItemsAsGrid(new MenuOption[][]{
                new MenuOption[]{
                        new MenuOption("PLAY GAME", 80, 100, () -> screenCoordinator.setGameState(GameState.LEVEL)), new MenuOption("LEVEL SELECT",
                                                                                                                                    350, 100,
                                                                                                                                    () -> screenCoordinator.setGameState(
                                                                                                                                            GameState.LEVELSELECT)
                )
                }, new MenuOption[]{
                new MenuOption("CREDITS", 80, 200, () -> screenCoordinator.setGameState(GameState.CREDITS)), new MenuOption("NARRATIVE", 350, 200,
                                                                                                                            () -> screenCoordinator.setGameState(
                                                                                                                                    GameState.OPENING)
        )
        }, new MenuOption[]{
                new MenuOption("INSTRUCTIONS", 80, 300, () -> screenCoordinator.setGameState(GameState.INSTRUCTIONS)), new MenuOption("OPTIONS", 350,
                                                                                                                                      300,
                                                                                                                                      () -> screenCoordinator.setGameState(
                                                                                                                                              GameState.OPTIONS)
        )
        }
        });
        setBackground(new TitleScreenMap());
    }
}
