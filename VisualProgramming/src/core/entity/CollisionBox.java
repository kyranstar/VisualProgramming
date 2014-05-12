package core.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import core.math.Vec2D;

public class CollisionBox {

	private Rectangle2D rect;
	
	public CollisionBox(final double x, final double y, final double width, final double height) {
		rect = new Rectangle2D.Double(x, y, width, height);
	}
	
	public CollisionBox(final Rectangle2D rect) {
		this.rect = rect;
	}

	public final boolean isColliding(final CollisionBox other){
		if(other == null) {
			return false;
		}
		return rect.intersects(other.rect);
	}
	public final boolean isColliding(final Line2D line){
		if(line == null) {
			return false;
		}
		return line.intersects(rect);
	}
	public final Vec2D getUpperLeft(){
		return new Vec2D(rect.getX(), rect.getY());
	}
	public final Vec2D getCenter(){
		return new Vec2D(rect.getCenterX(), rect.getCenterY());
	}
	public final void setPosition(final Vec2D pos){
		this.rect.setRect(pos.x, pos.y, rect.getWidth(), rect.getHeight());
	}

	public final Rectangle2D getRect2D() {
		return rect;
	}

	public final Vec2D getBottomRight() {
		return new Vec2D(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
	}

	public final Vec2D getUpperRight() {
		return new Vec2D(rect.getX() + rect.getWidth(), rect.getY());
	}
	public final Vec2D getBottomLeft() {
		return new Vec2D(rect.getX(), rect.getY() + rect.getHeight());
	}

	public final void setY(final double y) {
		this.rect.setFrame(rect.getX(), y, rect.getWidth(), rect.getHeight());
	}
	public final void setX(final double x) {
		this.rect.setFrame(x, rect.getY(), rect.getWidth(), rect.getHeight());
	}

	public final double getWidth() {
		return rect.getWidth();
	}
	public final double getHeight(){
		return rect.getHeight();
	}

	public final void setPosition(final double x, final double y) {
		rect.setRect(x, y, rect.getWidth(), rect.getHeight());
	}

	public final void setSize(final Vec2D size) {
		this.rect.setFrame(rect.getX(), rect.getY(), size.x, size.y);
	}

	public final void draw(final Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	}

}
