package MapEditor;

import javax.swing.*;
import java.awt.*;

public class EditorMainPanel extends JPanel {

    public EditorMainPanel(JFrame parent) {
        setLayout(null);
        setBackground(Color.BLACK);
        SelectedTileIndexHolder selectedTileIndexHolder = new SelectedTileIndexHolder();
        MapBuilder mapBuilder = new MapBuilder(selectedTileIndexHolder);
        add(mapBuilder);
        EditorControlPanel editorControlPanel = new EditorControlPanel(selectedTileIndexHolder, mapBuilder, parent);
        mapBuilder.setMap(editorControlPanel.getSelectedMap());
        add(editorControlPanel);
    }
}
