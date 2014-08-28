package core.graphics.imageutil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.Random;

public final class GraphicsUtils {
    private static final Random RAND = new Random();
    private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();

    public static Color getRandomSimilarColor(final Color c, final int similarity) {
        final float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        hsb[0] += RAND.nextFloat() * similarity;
        hsb[1] += RAND.nextFloat() * similarity;
        hsb[2] += RAND.nextFloat() * similarity;
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    public static Color generateRandomColor(final Color mix) {
        int red = RAND.nextInt(256);
        int green = RAND.nextInt(256);
        int blue = RAND.nextInt(256);

        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }

        return new Color(red, green, blue);
    }

    public static BufferedImage toCompatibleImage(final BufferedImage image) {
        // obtain the current system graphical settings
        final GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfx_config.getColorModel())) return image;

        // image is not optimized, so create a new image that is
        final BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(),
                image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

    public static BufferedImage createImage(final int width, final int height, final int transparency) {
        BufferedImage image = GFX_CONFIG.createCompatibleImage(width, height, transparency);
        if (image.getRaster().getDataBuffer().getDataType() != DataBuffer.TYPE_INT) {
            switch (transparency) {
            case BufferedImage.TRANSLUCENT:
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                break;
            case BufferedImage.OPAQUE:
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                break;
            case BufferedImage.BITMASK:
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
                break;
            default:
                break;
            }
        }
        return image;
    }

    public static BufferedImage copyImage(final BufferedImage bi) {
        final ColorModel cm = bi.getColorModel();
        final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        final WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void glowFilter(final BufferedImage src, final float amount) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        final int[] inPixels = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();

        final float a = amount * 8;
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int rgb1 = inPixels[index];
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;

                r1 = clampPixel((int) (r1 * a));
                g1 = clampPixel((int) (g1 * a));
                b1 = clampPixel((int) (b1 * a));

                inPixels[index] = (rgb1 & 0xff000000) | (r1 << 16) | (g1 << 8) | b1;
                index++;
            }
        }
    }

    public static int clampPixel(final int i) {
        if (i < 0) return 0;
        if (i > 255) return 255;
        return i;
    }

    public static void prettyGraphics(final Graphics2D g) {
        g.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
