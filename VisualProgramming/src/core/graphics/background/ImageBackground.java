package core.graphics.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import machine.GamePanel;
import core.graphics.ImageLoader;

public class ImageBackground extends AbstractBackground {

    private BufferedImage image;

    public ImageBackground(final String s, final double distance) {
        try {
            image = ImageLoader.loadAndBufferImage(s);
            this.distance = distance;
            width = image.getWidth();
            height = image.getHeight();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        for (double x = this.x - image.getWidth(); x < GamePanel.WIDTH; x += image.getWidth()) {
            for (double y = this.y - image.getHeight(); y < GamePanel.HEIGHT; y += image.getHeight()) {
                g.drawImage(image, (int) x, (int) y, null);
            }
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
