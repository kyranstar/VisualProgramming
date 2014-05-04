package core.entity;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import core.math.Vec2D;

public class CollisionBox {

	private Rectangle2D rect;
	
	public CollisionBox(double x, double y, double width, double height) {
		rect = new Rectangle2D.Double(x, y, width, height);
	}
	
	public boolean isColliding(CollisionBox other){
		return rect.intersects(other.rect);
	}
	public boolean isColliding(Line2D line){
		return line.intersects(rect);
	}
	public Vec2D getUpperLeft(){
		return new Vec2D(rect.getX(), rect.getY());
	}
	public Vec2D getCenter(){
		return new Vec2D(rect.getCenterX(), rect.getCenterY());
	}
	public void setPosition(Vec2D pos){
		this.rect.setRect(pos.x, pos.y, rect.getWidth(), rect.getHeight());
	}

}
