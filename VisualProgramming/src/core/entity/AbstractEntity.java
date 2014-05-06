package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

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
	}/*
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
	}*/
	public boolean[] getCornersAreSolid(double x, double y) {
		int leftTile = (int)(x / Tile.SIZE);
		int rightTile = (int)((x + this.collisionBox.getWidth()) / Tile.SIZE);
		int topTile = (int)(y / Tile.SIZE);
		int bottomTile = (int)((y + this.collisionBox.getHeight()) / Tile.SIZE);
		if(topTile < 0 || bottomTile >= map.getHeight() ||
			leftTile < 0 || rightTile >= map.getWidth()) {
			return new boolean[]{false, false, false ,false};
		}
		
		boolean topLeft = map.getTileAt(leftTile, topTile).getAttribute(Attribute.SOLID);
		boolean topRight = map.getTileAt(rightTile, topTile).getAttribute(Attribute.SOLID);
		boolean bottomLeft  = map.getTileAt(leftTile, bottomTile).getAttribute(Attribute.SOLID);
		boolean bottomRight = map.getTileAt(rightTile, bottomTile).getAttribute(Attribute.SOLID);
		
		return new boolean[]{topLeft, topRight, bottomLeft, bottomRight};
	}
	/*
	 * @return next position
	 */
	public Vec2D checkTileMapCollision() {
		
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
				System.out.println("top");
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
				System.out.println("bot");
				ytemp = currRow * Tile.SIZE + this.collisionBox.getHeight()/4;
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
				System.out.println("left");
				this.velocity.x = 0;
				xtemp = currCol * Tile.SIZE;
			}
			else {
				xtemp += this.velocity.x;
			}
		}
		if(this.velocity.x > 0) {
			if(topRight || bottomRight) {
				System.out.println("right");
				this.velocity.x = 0;
				xtemp = currCol * Tile.SIZE + this.collisionBox.getWidth()/4;
			}
			else {
				xtemp += this.velocity.x;
			}
		}
		return new Vec2D(xtemp, ytemp);
	}		
}
