package core.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.entity.AbstractEntity;
import core.object.map.GameMap;
import core.object.map.MapLoader;
import core.object.map.MapViewport;
import core.ui.KeyControllable;

public abstract class AbstractLevel {
	protected List<AbstractEntity> entities;
	protected List<KeyControllable> controllableEntities;
	protected MapViewport mapViewport;
	protected GameMap map;
	
	protected AbstractLevel(String filename, int width, int height){
		entities = new ArrayList<AbstractEntity>();
		controllableEntities = new ArrayList<KeyControllable>();
		map = MapLoader.loadMap(filename);
		mapViewport = new MapViewport(width, height);
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();

	public abstract void reset();
}
