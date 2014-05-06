package core.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.List;

import core.math.Vec2D;
import core.object.Tile;
import core.object.Tile.Attribute;
import core.object.map.GameMap;

public abstract class AbstractEntity {

	private CollisionBox collisionBox;
	protected Vec2D velocity;
	protected boolean isDead;
	protected GameMap map;
	
	public AbstractEntity(int x, int y, int width, int height, GameMap map){
		this.map = map;
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
	public Vec2D getSize(){
		return new Vec2D(collisionBox.getRect2D().getHeight(), collisionBox.getRect2D().getWidth());
	}
	public void setPosition(Vec2D position){
		this.collisionBox.setPosition(position);
	}
	public Vec2D getVelocity(){
		return this.velocity;
	}
	public void setVelocity(Vec2D velocity){
		this.velocity = velocity;
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

	public Rectangle2D getRect2D() {
		return collisionBox.getRect2D();
	}
	public boolean isUpperRightTileSolid(){
		int tileX = (int) (this.collisionBox.getUpperRight().x / Tile.SIZE);
		int tileY = (int) (this.collisionBox.getUpperRight().y / Tile.SIZE);
		return this.collisionBox.isColliding(map.getCollisionBoxAt(tileX, tileY));
	}
	public boolean isBottomRightTileSolid(){
		int tileX = (int) (this.collisionBox.getBottomRight().x / Tile.SIZE);
		int tileY = (int) (this.collisionBox.getBottomRight().y / Tile.SIZE);
		return this.collisionBox.isColliding(map.getCollisionBoxAt(tileX, tileY));
	}
	public boolean isUpperLeftTileSolid(){
		int tileX = (int) (this.collisionBox.getUpperLeft().x / Tile.SIZE);
		int tileY = (int) (this.collisionBox.getUpperLeft().y / Tile.SIZE);
		return this.collisionBox.isColliding(map.getCollisionBoxAt(tileX, tileY));
	}
	public boolean isBottomLeftTileSolid(){
		int tileX = (int) (this.collisionBox.getBottomLeft().x / Tile.SIZE);
		int tileY = (int) (this.collisionBox.getBottomLeft().y / Tile.SIZE);
		return this.collisionBox.isColliding(map.getCollisionBoxAt(tileX, tileY));
	}
	public void fixCollisions(GameMap map){
		
	}	
}
