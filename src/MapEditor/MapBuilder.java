package MapEditor;

import Level.Map;
import Utils.Colors;

import javax.swing.*;

public class MapBuilder extends JPanel {

    private final JScrollPane tileBuilderScroll;
    private final TileBuilder tileBuilder;
    private final JLabel mapWidthLabel;
    private final JLabel mapHeightLabel;
    private Map map;

    public MapBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        setSize(585, 562);
        setLayout(null);

        mapWidthLabel = new JLabel("Width: ");
        mapWidthLabel.setSize(70, 20);
        mapWidthLabel.setLocation(2, 544);
        add(mapWidthLabel);
        mapHeightLabel = new JLabel("Height: ");
        mapHeightLabel.setSize(70, 20);
        mapHeightLabel.setLocation(76, 544);
        add(mapHeightLabel);
        JLabel hoveredTileIndexLabel = new JLabel("X: , Y:");
        hoveredTileIndexLabel.setSize(140, 20);
        hoveredTileIndexLabel.setLocation(152, 544);
        add(hoveredTileIndexLabel);

        tileBuilder = new TileBuilder(controlPanelHolder, hoveredTileIndexLabel);
        tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        tileBuilderScroll.setLocation(0, 0);
        tileBuilderScroll.setSize(585, 546);
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
