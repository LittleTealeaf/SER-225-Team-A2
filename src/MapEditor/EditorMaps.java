package MapEditor;

import Level.Map;
import Maps.*;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("LevelOne");
            add("TitleScreen");
            add("LevelTwo");
            add("LevelThree");
            add("LevelFour");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "LevelOne":
                return new LevelOne();
            case "TitleScreen":
                return new TitleScreenMap();
            case "LevelTwo":
                return new LevelTwo();
            case "LevelThree":
                return new LevelThree();
            case "LevelFour":
                return new LevelFour();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
