package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import core.graphics.imageutil.GraphicsUtils;

public final class AnimationLoader {
    private AnimationLoader() {
    }

    public static Animation getFromSpritesheet(final String spritesheet, final int spriteWidth) throws IOException {
        final BufferedImage sheetImage = ImageLoader.loadAndBufferImage(spritesheet);
        final BufferedImage[] images = new BufferedImage[sheetImage.getWidth() / spriteWidth];
        for (int i = 0; i < images.length; i++) {
            images[i] = GraphicsUtils.toCompatibleImage(sheetImage.getSubimage(i * spriteWidth, 0, spriteWidth,
                    sheetImage.getHeight()));
        }
        final Animation a = new Animation();
        a.setFrames(images);
        return a;
    }
}
