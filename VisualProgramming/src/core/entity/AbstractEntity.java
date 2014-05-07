package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import core.math.Vec2D;
import core.object.Tile;
import core.object.Tile.Attribute;
import core.object.map.GameMap;

public abstract class AbstractEntity {

	private MoveData moveData;
	protected boolean isDead;
	protected GameMap map;
	
	public AbstractEntity(GameMap map){
		this.map = map;
		this.moveData = new MoveData();
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	public boolean isDead() {
		return isDead;
	}
	public abstract void applyAcceleration(Vec2D accel);
	
	public double getX(){
		return moveData.collisionBox.getUpperLeft().x;
	}
	public double getY(){
		return moveData.collisionBox.getUpperLeft().y;
	}
	public Vec2D getPosition(){
		return moveData.collisionBox.getUpperLeft();
	}
	public Vec2D getSize(){
		return new Vec2D(moveData.collisionBox.getHeight(), moveData.collisionBox.getWidth());
	}
	public void setRect(double x, double y, double width, double height){
		moveData.collisionBox = new CollisionBox(x,y,width,height);
	}
	public void setPosition(Vec2D position){
		moveData.collisionBox.setPosition(position);
	}
	public Vec2D getVelocity(){
		return moveData.velocity;
	}
	public void setVelocity(Vec2D velocity){
		moveData.velocity = velocity;
	}
	public boolean isColliding(AbstractEntity other){
		return moveData.collisionBox.isColliding(other.moveData.collisionBox);
	}
	public boolean isColliding(Line2D other){
		return moveData.collisionBox.isColliding(other);
	}
	public Vec2D getCenter(){
		return moveData.collisionBox.getCenter();
	}

	public Rectangle2D getRect2D() {
		return moveData.collisionBox.getRect2D();
	}
	public boolean[] getCornersAreSolid(double x, double y) {
		int leftTile = (int)(x / Tile.SIZE);
		int rightTile = (int)((x + moveData.collisionBox.getWidth()) / Tile.SIZE);
		int topTile = (int)(y / Tile.SIZE);
		int bottomTile = (int)((y + moveData.collisionBox.getHeight()) / Tile.SIZE);
		
		boolean topLeft = hasAttribute(map, Attribute.SOLID, topTile, leftTile);
		boolean topRight = hasAttribute(map, Attribute.SOLID, topTile, rightTile);
		boolean bottomLeft = hasAttribute(map, Attribute.SOLID, bottomTile, leftTile);
		boolean bottomRight = hasAttribute(map, Attribute.SOLID, bottomTile, rightTile);
		
		return new boolean[]{topLeft, topRight, bottomLeft, bottomRight};
	}
	private boolean hasAttribute(GameMap map, Attribute attribute, int tileY, int tileX) {
		  boolean result = false;

		  if (tileX >= 0 && tileX < map.getWidthInTiles() && tileY >= 0 && tileY < map.getHeightInTiles()) {
		    result = map.getTileAt(tileX, tileY).getAttribute(attribute);
		  }

		  return result;
		}
	public Vec2D getNextPosition() {
		
		int currCol = (int) (getX() / Tile.SIZE);
		int currRow = (int) (getY() / Tile.SIZE);
		
		double destX = getX() + moveData.velocity.x;
		double destY = getY() + moveData.velocity.y;
		
		double tempX = getX();
		double tempY = getY();
		
		boolean[] corners = getCornersAreSolid(getX(), destY);
		boolean topLeft = corners[0];
		boolean topRight = corners[1];
		boolean bottomLeft = corners[2];
		boolean bottomRight = corners[3];
		
		if(moveData.velocity.y < 0) {
			if(topLeft || topRight) {
				moveData.velocity.y = 0;
				tempY = currRow * Tile.SIZE;
			}
			else {
				tempY += moveData.velocity.y;
			}
		}
		else if(moveData.velocity.y > 0) {
			if(bottomLeft || bottomRight) {
				moveData.velocity.y = 0;
				tempY = (currRow + 1) * Tile.SIZE - moveData.collisionBox.getHeight() % Tile.SIZE - 1 ;
			}
			else {
				tempY += moveData.velocity.y;
			}
		}
		
		corners = getCornersAreSolid(destX, getY());
		topLeft = corners[0];
		topRight = corners[1];
		bottomLeft = corners[2];
		bottomRight = corners[3];
		if(moveData.velocity.x < 0) {
			if(topLeft || bottomLeft) {
				moveData.velocity.x = 0;
				tempX = currCol * Tile.SIZE;
			}
			else {
				tempX += moveData.velocity.x;
			}
		}
		if(moveData.velocity.x > 0) {
			if(topRight || bottomRight) {
				moveData.velocity.x = 0;
				tempX = (currCol + 1) * Tile.SIZE - moveData.collisionBox.getWidth() % Tile.SIZE -1 ;
			}
			else {
				tempX += moveData.velocity.x;
			}
		}
		return new Vec2D(tempX, tempY);
	}
}
