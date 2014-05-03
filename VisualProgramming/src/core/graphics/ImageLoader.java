package core.graphics;

import java.awt.image.BufferedImage;

public final class ImageLoader { //TODO implement image buffer  
	private ImageLoader(){}
	
	public static BufferedImage loadImage(String filename){
		return null;
	}
	public static BufferedImage[][] loadTilesheet(String filename, int spriteWidth, int spriteHeight){
		BufferedImage spritesheet = loadImage(filename);
		int cols = spritesheet.getWidth() / spriteWidth;
		int rows = spritesheet.getHeight() / spriteHeight;
		BufferedImage[][] tiles = new BufferedImage[cols][rows];
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				tiles[x][y] = spritesheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
			}
		}
		return tiles;
	}
}
