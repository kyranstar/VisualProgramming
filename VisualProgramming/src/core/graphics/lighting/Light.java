package core.graphics.lighting;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Light {
	private Point2D position;
	private int radius;
	private Color color;
	public Light(float x, float y, int radius, Color color){
		this.position = new Point2D.Float(x,y);
		this.radius = radius;
		this.color = color;
	}
	public Point2D getPosition() {
		return position;
	}
	public int getRadius() {
		return radius;
	}
	public Color getColor() {
		return color;
	}
}
