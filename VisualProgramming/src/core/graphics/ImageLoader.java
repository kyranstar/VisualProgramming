package core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import core.graphics.imageutil.GraphicsUtils;

public final class ImageLoader { // TODO implement image buffer
    private ImageLoader() {
    }

    private static Map<String, BufferedImage> buffer = new HashMap<String, BufferedImage>();
    private static int imagesInBuffer = 0;
    private static final int MAX_IMAGES_IN_BUFFER = 50;

    public static BufferedImage loadAndBufferImage(final String filename) throws IOException {
        final BufferedImage inBuffer = buffer.get(filename);
        if (inBuffer != null) return inBuffer;

        final BufferedImage loaded = ImageIO.read(ImageLoader.class.getResourceAsStream(filename));
        final BufferedImage image = GraphicsUtils.toCompatibleImage(loaded);
        if (imagesInBuffer < MAX_IMAGES_IN_BUFFER) {
            imagesInBuffer++;
            buffer.put(filename, image);
        }
        return image;
    }

    public static BufferedImage[][] loadTilesheet(final String filename, final int spriteWidth, final int spriteHeight)
            throws IOException {
        final BufferedImage spritesheet = loadAndBufferImage(filename);
        final int cols = spritesheet.getWidth() / spriteWidth;
        final int rows = spritesheet.getHeight() / spriteHeight;
        final BufferedImage[][] tiles = new BufferedImage[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                tiles[x][y] = spritesheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return tiles;
    }
}
