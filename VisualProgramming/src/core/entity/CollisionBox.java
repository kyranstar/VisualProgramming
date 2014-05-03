package core.entity;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
	public Point2D getUpperLeft(){
		return new Point2D.Double(rect.getX(), rect.getY());
	}
	public Point2D getCenter(){
		return new Point2D.Double(rect.getCenterX(), rect.getCenterY());
	}

}
