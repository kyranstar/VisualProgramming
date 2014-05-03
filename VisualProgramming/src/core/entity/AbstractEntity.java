package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import core.math.Vec2D;

public abstract class AbstractEntity {

	private CollisionBox collisionBox;
	protected Vec2D velocity;
	protected boolean isDead;
	
	public AbstractEntity(int x, int y, int width, int height){
		collisionBox = new CollisionBox(x, y, width, height);
		velocity = new Vec2D(0,0);
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	public boolean isDead() {
		return isDead;
	}
	public abstract void applyAcceleration(Vec2D accel);
	
	public double getX(){
		return collisionBox.getUpperLeft().getX();
	}
	public double getY(){
		return collisionBox.getUpperLeft().getY();
	}
	public boolean isColliding(AbstractEntity other){
		return collisionBox.isColliding(other.collisionBox);
	}
	public boolean isColliding(Line2D other){
		return collisionBox.isColliding(other);
	}
	public Point2D getCenter(){
		return collisionBox.getCenter();
	}
}
