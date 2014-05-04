package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

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
		return collisionBox.getUpperLeft().x;
	}
	public double getY(){
		return collisionBox.getUpperLeft().y;
	}
	public Vec2D getPosition(){
		return collisionBox.getUpperLeft();
	}
	public void setPosition(Vec2D position){
		this.collisionBox.setPosition(position);
	}
	public boolean isColliding(AbstractEntity other){
		return collisionBox.isColliding(other.collisionBox);
	}
	public boolean isColliding(Line2D other){
		return collisionBox.isColliding(other);
	}
	public Vec2D getCenter(){
		return collisionBox.getCenter();
	}
}
