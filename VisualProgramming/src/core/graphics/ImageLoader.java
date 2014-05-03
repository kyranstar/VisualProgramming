package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ImageLoader { //TODO implement image buffer  
	private ImageLoader(){}
	
	public static BufferedImage loadImage(String filename) throws IOException{
		return ImageIO.read(ImageLoader.class.getResourceAsStream(filename));
	}
	public static BufferedImage[][] loadTilesheet(String filename, int spriteWidth, int spriteHeight) throws IOException{
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
