package core.object.map;

import java.util.List;

import core.entity.CollisionBox;
import core.graphics.lighting.Light;
import core.object.Tile;

public class GameMap {
	private final int width, height;
	
	private List<Light> lights;
	private CollisionBox[][] collisionMap;
	private Tile[][] tiles;
	private int ambientLight;
	public GameMap(final Tile[][] tiles, final List<Light> lights, final int ambientLight){
		this.ambientLight = ambientLight;
		this.lights = lights;
		this.tiles = tiles.clone();
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
	
	
	public final Tile getTileAt(final int x, final int y){
		return tiles[x][y];
	}
	public final int getWidthInTiles() {
		return width;
	}
	public final int getHeightInTiles() {
		return height;
	}
	public final CollisionBox getCollisionBoxAt(final int x, final int y) {
		return collisionMap[x][y];
	}


	public List<Light> getLights() {
		return lights;
	}


	public int getAmbientLight() {
		return ambientLight;
	}
}
