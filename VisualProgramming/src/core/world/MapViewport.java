package core.world;

import java.awt.Graphics2D;

import core.entity.CollisionBox;
import core.object.Tile;
import core.object.map.GameMap;

public class MapViewport {
	CollisionBox bounds;
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
	public final void setPosition(final double x, final double y){
		bounds.setPosition(x, y);
	}
	public final void centerOn(final double x, final double y){
		bounds.setPosition(x - bounds.getWidth()/2, y - bounds.getWidth()/2);
	}
	public final void centerX(final double x){
		bounds.setX(x - bounds.getWidth()/2);
	}

	public final double getX() {
		return bounds.getUpperLeft().x;
	}
	public final double getY() {
		return bounds.getUpperLeft().y;
	}
}
