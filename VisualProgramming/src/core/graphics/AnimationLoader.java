package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

public final class AnimationLoader {
	private AnimationLoader(){}
	/*
	 * Takes in 1 dimensional images with back to back sprites
	 */
	public static Animation getFromSpritesheet(String spritesheet, int spriteWidth) throws IOException {
		BufferedImage sheetImage = ImageLoader.loadImage(spritesheet);
		BufferedImage[] images = new BufferedImage[sheetImage.getWidth() / spriteWidth];
		for(int i = 0; i < images.length; i++)
			images[i] = sheetImage.getSubimage(i * spriteWidth, 0, spriteWidth, sheetImage.getHeight());
		Animation a = new Animation();
		a.setFrames(images);
		return a;
	}
}
