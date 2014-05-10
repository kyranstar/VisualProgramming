package core.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.entity.AbstractEntity;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.object.map.MapLoader;
import core.ui.KeyControllable;
import core.world.MapViewport;

public abstract class AbstractLevel {
	protected Vec2D ambientForce; //Gravity
	protected List<AbstractEntity> entities;
	protected List<KeyControllable> controllableEntities;
	protected MapViewport mapViewport;
	protected GameMap map;
	
	protected AbstractLevel(String filename, int screenWidth, int screenHeight){
		try {
			map = MapLoader.loadMap(filename);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Logger.getLogger(AbstractLevel.class.getName()).log(Level.SEVERE, null, e);
		}
		mapViewport = new MapViewport(screenWidth, screenHeight);
		reset(); //not required, but safer
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	public abstract void reset();
}
