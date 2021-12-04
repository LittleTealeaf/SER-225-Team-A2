package MapEditor;

import Level.Map;
import Maps.*;

import java.util.ArrayList;

public class EditorMaps {

    public static ArrayList<String> getMapNames() {
        return new ArrayList<>() {{
            add("TestMap");
            add("TitleScreen");
            add("LevelSelect");
            add("TestTutorial");
            add("TestMap2");
            add("TestMap3");
            add("TestMap4");
            add("TestMap5");
            add("TestMap6");
            add("TestMap7");
            add("BossBattle");
        }};
    }

    public static Map getMapByName(String mapName) {
        return switch (mapName) {
            case "TestMap" -> new TestMap();
            case "TitleScreen" -> new TitleScreenMap();
            case "TestTutorial" -> new TestTutorial();
            case "TestMap2" -> new TestMap2();
            case "TestMap3" -> new TestMap3();
            case "TestMap4" -> new TestMap4();
            case "TestMap5" -> new TestMap5();
            case "TestMap6" -> new TestMap6();
            case "TestMap7" -> new TestMap7();
            case "LevelSelect" -> new LevelSelectMap();
            case "BossBattle" -> new BossBattle();
            default -> throw new RuntimeException("Unrecognized map name");
        };
    }
}
