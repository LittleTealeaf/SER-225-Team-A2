package Maps;

import java.util.ArrayList;

import Enemies.BossMouse;
import Level.Enemy;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

// Represents a test map to be used in a level
public class BossLevel extends Map {

	public BossLevel() {
		super("Boss_Map.txt", new CommonTileset(), new Point(1, 11));
	}

	@Override
	public ArrayList<Enemy> loadEnemies() {
		ArrayList<Enemy> enemies = new ArrayList<>();
		enemies.add(new BossMouse(getPositionByTileIndex(10, 8), Direction.LEFT));
		return enemies;
	}
}
