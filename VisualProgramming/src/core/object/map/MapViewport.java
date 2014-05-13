package core.object.map;

import java.awt.Graphics2D;

import core.entity.CollisionBox;
import core.object.Tile;

public class MapViewport {
	
	private CollisionBox bounds;
	
	public MapViewport(final double width, final double height){
		bounds = new CollisionBox(0, 0, width, height);
	}
	
	public final void draw(final Graphics2D g, final GameMap map){
		for(double x =  0; x < map.getWidthInTiles() * Tile.SIZE; x += Tile.SIZE){
			for(double y =  0; y < map.getHeightInTiles() * Tile.SIZE; y += Tile.SIZE){ 
				g.drawImage(map.getTileAt((int)(x/Tile.SIZE), (int) (y/Tile.SIZE)).getImage(), (int)x, (int)y, null);
			}
		}
	}
	
	public final void lockFrame(final GameMap map){
		if(this.getX() < 0) {
			this.bounds.setX(0);
		}
		if(this.getX() > map.getWidthInTiles() * Tile.SIZE - bounds.getWidth()) {
			this.bounds.setX(map.getWidthInTiles() * Tile.SIZE - bounds.getWidth());
		}
	}
	public final void centerX(final double x){
		this.bounds.setX(x - bounds.getWidth()/2);
	}
	public final void centerY(final double y){
		this.bounds.setY(y - bounds.getHeight()/2);
	}
	public final double getX() {
		return bounds.getUpperLeft().x;
	}
	public final double getY() {
		return bounds.getUpperLeft().y;
	}

	public final double getCenterX() {
		return this.bounds.getCenter().x;
	}
	public final double getCenterY() {
		return this.bounds.getCenter().y;
	}
}
