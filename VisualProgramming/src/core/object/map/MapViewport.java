package core.object.map;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import core.object.Tile;

public class MapViewport {
	Rectangle2D bounds;
	public MapViewport(double width, double height){
		bounds = new Rectangle2D.Double(0, 0, width, height);
	}
	
	public void draw(Graphics2D g, GameMap map){
		g.translate(-bounds.getX(), -bounds.getY());
		int width = map.getWidth() * Tile.SIZE;
		int height = map.getHeight() * Tile.SIZE;
		
		for(int x = (int) bounds.getX(); x < bounds.getX() + bounds.getWidth(); x += Tile.SIZE){
			for(int y = (int) bounds.getY(); y < bounds.getY() + bounds.getHeight(); y += Tile.SIZE){ 
				if(x < 0 || x >= width || y < 0 || y >= height)
					continue;
				g.drawImage(map.getTileAt(x/Tile.SIZE, y/Tile.SIZE).getImage(), x, y, null);
			}
		}
	}
	public void setPosition(double x, double y){
		bounds.setFrame(x, y, bounds.getWidth(), bounds.getHeight());
	}
}
