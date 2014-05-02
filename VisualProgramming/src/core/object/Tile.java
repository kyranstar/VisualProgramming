package core.object;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import core.graphics.ImageLoader;

public enum Tile {
	
	AIR(0, -1, "transparent.png"),
	GRASS(1, 5, "grass.png", Attribute.SOLID);
	

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	private HashMap<Attribute, Boolean> attributes;
	private BufferedImage image;
	private int hardness;
	private int ID;
	
	private Tile(int ID, int hardness, String imageFile, Attribute... attributes){
		this.ID = ID;
		this.hardness = hardness;
		this.image = ImageLoader.loadImage(imageFile);
		
		this.attributes = new HashMap<Attribute, Boolean>();
		for(Attribute att : Attribute.values()) //initialize all to false
			this.attributes.put(att, false);
		
		for(Attribute att : attributes){
			this.attributes.put(att, true);
		}
	}
	public static Tile getByID(int ID){
		for(Tile t : values()){
			if(t.ID == ID){
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
