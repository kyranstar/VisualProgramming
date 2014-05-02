package core.object.map;

import core.object.Tile;

/*
 * 
 */
public class Map {
	private int width, height;
	
	private Tile[][] tiles;
	public Map(Tile[][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}
	public Tile getTileAt(int x, int y){
		return tiles[x][y];
	}
}
