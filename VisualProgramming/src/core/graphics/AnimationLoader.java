package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import core.graphics.imageutil.GraphicsUtils;

public final class AnimationLoader {
    private AnimationLoader() {
    }

    public static Animation getFromHorizontalSpritesheet(final String spritesheet, final int spriteWidth)
            throws IOException {
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

    public static Animation getFromVerticalSpritesheet(final String spritesheet, final int spriteHeight)
            throws IOException {
        final BufferedImage sheetImage = ImageLoader.loadAndBufferImage(spritesheet);
        final BufferedImage[] images = new BufferedImage[sheetImage.getHeight() / spriteHeight];
        for (int i = 0; i < images.length; i++) {
            images[i] = GraphicsUtils.toCompatibleImage(sheetImage.getSubimage(0, i * spriteHeight,
                    sheetImage.getWidth(), spriteHeight));
        }
        final Animation a = new Animation();
        a.setFrames(images);
        return a;
    }
}
