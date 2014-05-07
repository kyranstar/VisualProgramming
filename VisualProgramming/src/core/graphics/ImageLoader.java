package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public final class ImageLoader { //TODO implement image buffer  
	private ImageLoader(){}
	
	private static Map<String, BufferedImage> buffer = new HashMap<String, BufferedImage>();
	private static int imagesInBuffer = 0;
	private static final int MAX_IMAGES_IN_BUFFER = 50;
	
	public static BufferedImage loadImage(String filename) throws IOException{
		BufferedImage inBuffer = buffer.get(filename);
		if(inBuffer != null) //if it is in the buffer
			return inBuffer;
		
		BufferedImage loaded = ImageIO.read(ImageLoader.class.getResourceAsStream(filename));
		if(imagesInBuffer < MAX_IMAGES_IN_BUFFER){
			imagesInBuffer++;
			buffer.put(filename, loaded);
		}
		return loaded;
	}
	public static BufferedImage[][] loadTilesheet(String filename, int spriteWidth, int spriteHeight) throws IOException{
		final BufferedImage spritesheet = loadImage(filename);
		final int cols = spritesheet.getWidth() / spriteWidth;
		final int rows = spritesheet.getHeight() / spriteHeight;
		BufferedImage[][] tiles = new BufferedImage[cols][rows];
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				tiles[x][y] = spritesheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
			}
		}
		return tiles;
	}
}
