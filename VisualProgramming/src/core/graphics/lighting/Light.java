package core.graphics.lighting;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import core.graphics.imageutil.GraphicsUtils;

public final class Light {

    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final float[] DIST = { 0.05f, 1f };

    public BufferedImage image;
    private int x, y;

    private Light() {
    }

    public static Light createRoundLight(final int x, final int y, final int radius, final Color color) {
        final Light light = new Light();

        final Color[] colors = { color, TRANSPARENT };

        light.x = x - radius;
        light.y = y - radius;

        light.image = GraphicsUtils.createImage(radius * 2, radius * 2, BufferedImage.TRANSLUCENT);
        final Graphics2D g = light.image.createGraphics();
        GraphicsUtils.prettyGraphics(g);
        g.setPaint(new RadialGradientPaint(new Point2D.Float(radius, radius), radius, DIST, colors));
        g.fillRect(0, 0, light.image.getWidth(), light.image.getHeight());
        g.dispose();
        return light;
    }

    public static Light createPolygonLight(final Point2D lightEntrance, final Point2D lightExit, final int x,
            final int y, final List<Point> points, final Color color) {
        final Light light = new Light();
        light.x = x;
        light.y = y;

        int minX = 0, minY = 0;
        int maxX = 0, maxY = 0;
        for (final Point point : points) {
            if (point.x < minX) {
                minX = point.x;
            }
            if (point.y < minY) {
                minY = point.y;
            }
            if (point.x > maxX) {
                maxX = point.y;
            }
            if (point.y > maxY) {
                maxY = point.y;
            }
        }

        final Polygon p = new Polygon();
        for (final Point point : points) {
            // we want to set the origin to the upper left of the image, not the
            // first point, so we add the coordinates of the left most and top
            // most points
            p.addPoint(point.x + Math.abs(minX), point.y + Math.abs(minY));
        }

        light.image = GraphicsUtils
                .createImage(maxX + Math.abs(minX), maxY + Math.abs(minY), BufferedImage.TRANSLUCENT);
        final Graphics2D g = light.image.createGraphics();
        GraphicsUtils.prettyGraphics(g);
        g.setPaint(new GradientPaint(lightEntrance, color, lightExit, TRANSPARENT));
        g.fill(p);
        g.dispose();

        return light;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setCenterX(final int x2) {
        x = x2 - image.getWidth() / 2;
    }

    public void setCenterY(final int x2) {
        y = x2 - image.getHeight() / 2;
    }
}
