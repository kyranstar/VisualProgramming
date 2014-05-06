package core.object.map;

import core.entity.CollisionBox;
import core.object.Tile;

public class GameMap {
	private final int width, height;
	
	private CollisionBox[][] collisionMap;
	private Tile[][] tiles;
	public GameMap(Tile[][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		
		collisionMap = new CollisionBox[width][height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(tiles[x][y].getAttribute(Tile.Attribute.SOLID)){
					collisionMap[x][y] = new CollisionBox(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
				}
			}
		}
	}	
	
	
	public Tile getTileAt(int x, int y){
		return tiles[x][y];
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public CollisionBox getCollisionBoxAt(int x, int y) {
		return collisionMap[x][y];
	}
}
