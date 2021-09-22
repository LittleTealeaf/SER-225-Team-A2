package MapEditor;

import Level.Map;
import Utils.Colors;

import javax.swing.*;

public class MapBuilder extends JPanel {
    private Map map;
    private JScrollPane tileBuilderScroll;
    private TileBuilder tileBuilder;
    private JLabel mapWidthLabel;
    private JLabel mapHeightLabel;
    private JLabel hoveredTileIndexLabel;

    public MapBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        //TODO: MAPEDITOR WINDOW SIZE: change this
        setSize(1175, 1160);
        setLayout(null);

        mapWidthLabel = new JLabel("Width: ");
        mapWidthLabel.setSize(70, 20);
        //TODO: MAPEDITOR WINDOW SIZE: change this
        mapWidthLabel.setLocation(2, 1140);
        add(mapWidthLabel);
        mapHeightLabel = new JLabel("Height: ");
        mapHeightLabel.setSize(70, 20);
        //TODO: MAPEDITOR WINDOW SIZE: change this
        mapHeightLabel.setLocation(76, 1140);
        add(mapHeightLabel);
        hoveredTileIndexLabel = new JLabel("X: , Y:");
        hoveredTileIndexLabel.setSize(140, 20);
        //TODO: MAPEDITOR WINDOW SIZE: change this
        hoveredTileIndexLabel.setLocation(152, 1140);
        add(hoveredTileIndexLabel);

        tileBuilder = new TileBuilder(controlPanelHolder, hoveredTileIndexLabel);
        tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        tileBuilderScroll.setLocation(0, 0);
        //TODO: MAPEDITOR WINDOW SIZE: change this
        tileBuilderScroll.setSize(1175, 1144);
        add(tileBuilderScroll);
    }

    public void setMap(Map map) {
        this.map = map;
        refreshTileBuilder();
    }

    public void refreshTileBuilder() {
        tileBuilder.setMap(map);
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        mapWidthLabel.setText("Width: " + map.getWidth());
        mapHeightLabel.setText("Height: " + map.getHeight());
    }
}
