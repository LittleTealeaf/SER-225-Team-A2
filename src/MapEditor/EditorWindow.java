package MapEditor;

import javax.swing.*;

public class EditorWindow {
    private JFrame editorWindow;
    private EditorMainPanel editorMainPanel;

    public EditorWindow() {
        editorWindow = new JFrame("Map Editor");
        editorMainPanel = new EditorMainPanel(editorWindow);
        editorWindow.setContentPane(editorMainPanel);
        editorWindow.setResizable(false);
        //TODO: MAPEDITOR WINDOW SIZE: change this first then change the rest to match it
        editorWindow.setSize(1400, 1200);
        editorWindow.setLocationRelativeTo(null);
        editorWindow.setVisible(true);
        editorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
    }

}
