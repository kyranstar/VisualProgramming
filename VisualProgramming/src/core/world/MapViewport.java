package core.world;

import java.awt.Graphics2D;

import core.entity.CollisionBox;
import core.object.Tile;
import core.object.map.GameMap;

public class MapViewport {
	CollisionBox bounds;
	public MapViewport(double width, double height){
		bounds = new CollisionBox(0, 0, width, height);
	}
	
	public void draw(Graphics2D g, GameMap map){
		
		for(double x =  0; x < map.getWidthInTiles() * Tile.SIZE; x += Tile.SIZE){
			for(double y =  0; y < map.getHeightInTiles() * Tile.SIZE; y += Tile.SIZE){ 
				g.drawImage(map.getTileAt((int)(x/Tile.SIZE), (int) (y/Tile.SIZE)).getImage(), (int)x, (int)y, null);
			}
		}
	}
	public void lockFrame(GameMap map){
		if(this.getX() < 0)
		this.bounds.setX(0);
		if(this.getX() > map.getWidthInTiles() * Tile.SIZE - bounds.getWidth())
			this.bounds.setX(map.getWidthInTiles() * Tile.SIZE - bounds.getWidth());
	}
	public void setPosition(double x, double y){
		bounds.setPosition(x, y);
	}
	public void centerOn(double x, double y){
		bounds.setPosition(x - bounds.getWidth()/2, y - bounds.getWidth()/2);
	}
	public void centerX(double x){
		bounds.setX(x - bounds.getWidth()/2);
	}

	public double getX() {
		return bounds.getUpperLeft().x;
	}
	public double getY() {
		return bounds.getUpperLeft().y;
	}
}
