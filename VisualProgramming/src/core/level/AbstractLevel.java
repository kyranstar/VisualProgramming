package core.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import machine.GamePanel;
import machine.MainGame;
import core.entity.AbstractEntity;
import core.object.map.GameMap;
import core.object.map.MapLoader;
import core.object.map.MapViewport;

public abstract class AbstractLevel {
	protected List<AbstractEntity> entities;
	protected MapViewport mapViewport;
	protected GameMap map;
	
	protected AbstractLevel(String filename, int width, int height){
		entities = new ArrayList<AbstractEntity>();
		map = MapLoader.loadMap(filename);
		mapViewport = new MapViewport(width, height);
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();

	public abstract void reset();
}
