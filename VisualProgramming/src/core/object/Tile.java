package core.object;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.graphics.ImageLoader;

public enum Tile {
	
	AIR(42, -1, "/tiles/transparent.png"),
	DIRT(10, 5, "/tiles/dirt.png", Attribute.SOLID);
	

	public static final int SIZE = 32;
	
	private final Map<Attribute, Boolean> attributes;
	private final BufferedImage image;
	private final int hardness;
	private final int gid;
	
	private Tile(final int gid, final int hardness, final String imageFile, final Attribute... attributes){
		this.gid = gid;
		this.hardness = hardness;
		try {
			this.image = ImageLoader.loadAndBufferImage(imageFile);
		} catch (IOException e) {
			throw new TileNotFoundException("Tile image not found: " + imageFile, e);
		} catch(IllegalArgumentException e){
			throw new IllegalArgumentException(imageFile, e);
		}
		
		this.attributes = new HashMap<Attribute, Boolean>();
		for(final Attribute att : Attribute.values()){ //initialize all attributes to false
			this.attributes.put(att, false);
		}		
		for(final Attribute att : attributes){
			this.attributes.put(att, true);
		}
	}
	public static Tile getByID(final int gid){
		for(final Tile t : values()){
			if(t.gid == gid){
				return t;
			}
		}
		throw new TileNotFoundException("Tile not found by id: " + gid);
	}
	public int getHardness(){
		return hardness;
	}
	public boolean getAttribute(final Attribute attribute){
		return attributes.get(attribute);
	}
	public BufferedImage getImage(){
		return this.image;
	}
	public static enum Attribute{
		SOLID; //entities collide with this
	}
}

