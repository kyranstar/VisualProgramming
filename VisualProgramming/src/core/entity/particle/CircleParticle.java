package core.entity.particle;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import core.object.map.GameMap;

public class CircleParticle extends Particle {

	public CircleParticle(GameMap map, float x, float y, float width, float height, float life, Color color) {
		super(map, 1.0f, 1.0f, life, new Ellipse2D.Double(x,y,width,height), color);
	}

}
