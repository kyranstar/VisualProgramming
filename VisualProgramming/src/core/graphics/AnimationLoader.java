package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

public final class AnimationLoader {
	private AnimationLoader(){}

	public static Animation getFromSpritesheet(final String spritesheet, final int spriteWidth) throws IOException {
		BufferedImage sheetImage = ImageLoader.loadAndBufferImage(spritesheet);
		BufferedImage[] images = new BufferedImage[sheetImage.getWidth() / spriteWidth];
		for(int i = 0; i < images.length; i++) {
			images[i] = sheetImage.getSubimage(i * spriteWidth, 0, spriteWidth, sheetImage.getHeight());
		}
		Animation a = new Animation();
		a.setFrames(images);
		return a;
	}
}
