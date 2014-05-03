package core.object;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import core.graphics.ImageLoader;

public enum Tile {
	
	AIR(0, -1, "transparent.png"),
	GRASS(1, 5, "grass.png", Attribute.SOLID);
	

	public static final int SIZE = 32;
	
	private Map<Attribute, Boolean> attributes;
	private BufferedImage image;
	private int hardness;
	private int blockID;
	
	private Tile(int blockID, int hardness, String imageFile, Attribute... attributes){
		this.blockID = blockID;
		this.hardness = hardness;
		this.image = ImageLoader.loadImage(imageFile);
		
		this.attributes = new HashMap<Attribute, Boolean>();
		for(Attribute att : Attribute.values()) //initialize all to false
			this.attributes.put(att, false);
		
		for(Attribute att : attributes){
			this.attributes.put(att, true);
		}
	}
	public static Tile getByID(int blockID){
		for(Tile t : values()){
			if(t.blockID == blockID){
				return t;
			}
		}
		return null;
	}
	public int getHardness(){
		return hardness;
	}
	public boolean getAttribute(Attribute at){
		return attributes.get(at);
	}
	public BufferedImage getImage(){
		return this.image;
	}
	enum Attribute{
		SOLID;
	}
}
