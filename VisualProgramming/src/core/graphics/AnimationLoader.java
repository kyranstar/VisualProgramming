package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class AnimationLoader {
	private AnimationLoader(){}

	private static Map<String, BufferedImage[]> buffer = new HashMap<String, BufferedImage[]>();
	private static int animationsInBuffer = 0;
	private static final int MAX_ANIMATIONS_IN_BUFFER = 20;

	public static Animation getFromSpritesheet(final String spritesheet, final int spriteWidth) throws IOException {
		BufferedImage[] inBuffer = buffer.get(spritesheet);
		if(inBuffer != null){ //if we already have it in the buffer
			return new Animation(inBuffer);
		}
		Animation loaded = loadAnimationFromSpritesheet(spritesheet, spriteWidth);
		if(animationsInBuffer < MAX_ANIMATIONS_IN_BUFFER){
			buffer.put(spritesheet, loaded.getFrames());
			animationsInBuffer++;
		}
		return loaded;
	}
	private static Animation loadAnimationFromSpritesheet(final String spritesheet, final int spriteWidth) throws IOException{
		BufferedImage sheetImage = ImageLoader.loadImage(spritesheet);
		BufferedImage[] images = new BufferedImage[sheetImage.getWidth() / spriteWidth];
		for(int i = 0; i < images.length; i++) {
			images[i] = sheetImage.getSubimage(i * spriteWidth, 0, spriteWidth, sheetImage.getHeight());
		}
		Animation a = new Animation();
		a.setFrames(images);
		return a;
	}

	public static void resetBuffer(){
		buffer = new HashMap<String, BufferedImage[]>();
		animationsInBuffer = 0;
	}
}
