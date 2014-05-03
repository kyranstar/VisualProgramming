package core.level;

import java.awt.Graphics2D;
import java.util.List;

import core.entity.Entity;
import core.object.map.Map;
import core.object.map.MapViewport;

public abstract class Level {
	protected List<Entity> entities;
	protected MapViewport mapViewport;
	protected Map map;

	public void draw(Graphics2D g) {
		mapViewport.draw(g, map);
		for(Entity e : entities)
			e.draw(g);
	}

	public void update() {
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			e.update();
			if(e.isDead()){
				entities.remove(i);
				i--;
			}
		}
		
	}

	public abstract void reset();
}
