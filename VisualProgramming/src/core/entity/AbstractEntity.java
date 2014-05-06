package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import core.math.Vec2D;
import core.object.Tile;
import core.object.Tile.Attribute;
import core.object.map.GameMap;

public abstract class AbstractEntity {

	private static final double COEF_FRICTION = 0.5;
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
	public boolean[] getCornersAreSolid(double x, double y) {
		int leftTile = (int)(x / Tile.SIZE);
		int rightTile = (int)((x + this.collisionBox.getWidth()) / Tile.SIZE);
		int topTile = (int)(y / Tile.SIZE);
		int bottomTile = (int)((y + this.collisionBox.getHeight()) / Tile.SIZE);

		boolean topLeft;
		boolean topRight;
		boolean bottomLeft;
		boolean bottomRight;
		
		if(leftTile < 0 || leftTile >= map.getWidth() || topTile < 0 || topTile >= map.getHeight())
			topLeft = false;
		else
			topLeft = map.getTileAt(leftTile, topTile).getAttribute(Attribute.SOLID);
		if(rightTile < 0 || rightTile >= map.getWidth() || topTile < 0 || topTile >= map.getHeight())
			topRight = false;
		else
			topRight = map.getTileAt(rightTile, topTile).getAttribute(Attribute.SOLID);
		if(leftTile < 0 || leftTile >= map.getWidth() || bottomTile < 0 || bottomTile >= map.getHeight())
			bottomLeft = false;
		else
			bottomLeft  = map.getTileAt(leftTile, bottomTile).getAttribute(Attribute.SOLID);
		if(rightTile < 0 || rightTile >= map.getWidth() || bottomTile < 0 || bottomTile >= map.getHeight())
			bottomRight = false;
		else
			bottomRight = map.getTileAt(rightTile, bottomTile).getAttribute(Attribute.SOLID);
		
		return new boolean[]{topLeft, topRight, bottomLeft, bottomRight};
	}
	/*
	 * @return next position
	 */
	public Vec2D getNextPosition() {
		
		int currCol = (int)getX() / Tile.SIZE;
		int currRow = (int)getY() / Tile.SIZE;
		
		double xdest = getX() + this.velocity.x;
		double ydest = getY() + this.velocity.y;
		
		double xtemp = getX();
		double ytemp = getY();
		
		boolean[] corners = getCornersAreSolid(getX(), ydest);
		boolean topLeft = corners[0];
		boolean topRight = corners[1];
		boolean bottomLeft = corners[2];
		boolean bottomRight = corners[3];
		
		if(this.velocity.y < 0) {
			if(topLeft || topRight) {
				this.velocity.y = 0;
				ytemp = currRow * Tile.SIZE;
			}
			else {
				ytemp += this.velocity.y;
			}
		}
		else if(this.velocity.y > 0) {
			if(bottomLeft || bottomRight) {
				this.velocity.y = 0;
				ytemp = (currRow + 1) * Tile.SIZE - this.collisionBox.getHeight() % Tile.SIZE - 1 ;
			}
			else {
				ytemp += this.velocity.y;
			}
		}
		
		corners = getCornersAreSolid(xdest, getY());
		topLeft = corners[0];
		topRight = corners[1];
		bottomLeft = corners[2];
		bottomRight = corners[3];
		if(this.velocity.x < 0) {
			if(topLeft || bottomLeft) {
				this.velocity.x = 0;
				xtemp = currCol * Tile.SIZE;
			}
			else {
				xtemp += this.velocity.x;
			}
		}
		if(this.velocity.x > 0) {
			if(topRight || bottomRight) {
				this.velocity.x = 0;
				xtemp = (currCol + 1) * Tile.SIZE - this.collisionBox.getWidth() % Tile.SIZE -1 ;
			}
			else {
				xtemp += this.velocity.x;
			}
		}
		return new Vec2D(xtemp, ytemp);
	}
}
