package Menu;

import Engine.Screen;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;
import jdk.nashorn.internal.runtime.Debug;

public class DebugScreen extends Menu {
    public DebugScreen(ScreenCoordinator screenCoordinator) {
        super(screenCoordinator);
        setMenuItemsAsGrid(new MenuItem[][] {
                new MenuItem[] {
                        new MenuItem("PLAY GAME",80,100),
                        new MenuItem("LEVEL SELECT",350,100)
                }, new MenuItem[] {
                    new MenuItem("CREDITS",80,200),
                    new MenuItem("NARRATIVE",350,200)


                }, new MenuItem[] {
                        new MenuItem("INSTRUCTIONS",80,300),
                    new MenuItem("OPTIONS",350,300)
            }
        });
        setBackground(new TitleScreenMap());
    }
}
