package core.entity;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import core.math.Vec2D;

public class CollisionBox {

	private Rectangle2D rect;
	
	public CollisionBox(double x, double y, double width, double height) {
		rect = new Rectangle2D.Double(x, y, width, height);
	}
	
	public CollisionBox(Rectangle2D rect) {
		this.rect = rect;
	}

	public boolean isColliding(CollisionBox other){
		if(other == null)
			return false;
		return rect.intersects(other.rect);
	}
	public boolean isColliding(Line2D line){
		if(line == null)
			return false;
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

	public Rectangle2D getRect2D() {
		return rect;
	}

	public Vec2D getBottomRight() {
		return new Vec2D(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
	}

	public Vec2D getUpperRight() {
		return new Vec2D(rect.getX() + rect.getWidth(), rect.getY());
	}
	public Vec2D getBottomLeft() {
		return new Vec2D(rect.getX(), rect.getY() + rect.getHeight());
	}

	public void setY(double y) {
		this.rect.setFrame(rect.getX(), y, rect.getWidth(), rect.getHeight());
	}
	public void setX(double x) {
		this.rect.setFrame(x, rect.getY(), rect.getWidth(), rect.getHeight());
	}

}
