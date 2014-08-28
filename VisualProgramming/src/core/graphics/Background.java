package core.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import machine.GamePanel;

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double distance;

    public Background(final String s, final double distance) {
        try {
            image = ImageLoader.loadAndBufferImage(s);
            this.distance = distance;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Background(final String filename, final double ms, final int x, final int y, final int w, final int h) {
        try {
            image = ImageLoader.loadAndBufferImage(filename);
            image = image.getSubimage(x, y, w, h);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void setRelativePosition(final double x, final double y) {
        this.x = (-x / distance) % image.getWidth();
        this.y = (-y / distance) % image.getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw(final Graphics2D g) {
        for (double x = this.x - image.getWidth(); x < GamePanel.WIDTH; x += image.getWidth()) {
            for (double y = this.y - image.getHeight(); y < GamePanel.HEIGHT; y += image.getHeight()) {
                g.drawImage(image, (int) x, (int) y, null);
            }
        }
    }

}
