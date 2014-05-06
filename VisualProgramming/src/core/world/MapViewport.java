package core.world;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import core.object.Tile;
import core.object.map.GameMap;

public class MapViewport {
	Rectangle2D bounds;
	public MapViewport(double width, double height){
		bounds = new Rectangle2D.Double(0, 0, width, height);
	}
	
	public void draw(Graphics2D g, GameMap map){
		lockFrame(map);
		
		int width = map.getWidth() * Tile.SIZE;
		int height = map.getHeight() * Tile.SIZE;
		
		for(double x =  0; x < bounds.getWidth(); x += Tile.SIZE){
			for(double y =  0; y < bounds.getHeight(); y += Tile.SIZE){ 
				if(x < 0 || x >= width || y < 0 || y >= height)
					continue;
				g.drawImage(map.getTileAt((int)(x/Tile.SIZE), (int) (y/Tile.SIZE)).getImage(), (int)x, (int)y, null);
			}
		}
	}
	private void lockFrame(GameMap map){
		if(this.getX() < 0)
			this.bounds.setRect(0, bounds.getY(), bounds.getWidth(), bounds.getHeight());
		if(this.getX() > map.getWidth() * Tile.SIZE - bounds.getWidth())
			this.bounds.setRect(map.getWidth() * Tile.SIZE - bounds.getWidth(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		if(this.getY() < 0)
			this.bounds.setRect(bounds.getX(), 0, bounds.getWidth(), bounds.getHeight());
		if(this.getY() > map.getHeight() * Tile.SIZE - bounds.getHeight())
			this.bounds.setRect(bounds.getX(), map.getHeight() * Tile.SIZE - bounds.getHeight(), bounds.getWidth(), bounds.getHeight());
	}
	public void setPosition(double x, double y){
		bounds.setFrame(x, y, bounds.getWidth(), bounds.getHeight());
	}
	public void centerOn(double x, double y){
		bounds.setFrame(x - bounds.getWidth()/2, y - bounds.getWidth()/2, bounds.getWidth(), bounds.getHeight());
	}
	public void centerX(double x){
		bounds.setFrame(x - bounds.getWidth()/2, bounds.getY(), bounds.getWidth(), bounds.getHeight());
	}

	public double getX() {
		return bounds.getX();
	}
	public double getY() {
		return bounds.getY();
	}
}
