package core.object.map;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import core.entity.CollisionBox;
import core.math.MathUtils;
import core.object.Tile;

public class MapViewport {

    private final CollisionBox bounds;

    public MapViewport(final double width, final double height) {
        bounds = new CollisionBox(0, 0, width, height);
    }

    public final void draw(final Graphics2D g, final GameMap map, final Rectangle2D view) {
        final int SIZE = Tile.SIZE;

        for (double x = MathUtils.floor(view.getX(), SIZE); x < MathUtils.ceil(view.getX() + view.getWidth(), SIZE); x += SIZE) {
            for (double y = MathUtils.floor(view.getY(), SIZE); y < MathUtils
                    .ceil(view.getY() + view.getHeight(), SIZE); y += SIZE) {

                g.drawImage(map.getTileAt((int) (x / SIZE), (int) (y / SIZE)).getImage(), (int) x, (int) y, null);
            }
        }
    }

    public final void lockFrame(final GameMap map) {
        if (getX() < 0) {
            bounds.setX(0);
        }
        if (getX() > map.getWidthInTiles() * Tile.SIZE - bounds.getWidth()) {
            bounds.setX(map.getWidthInTiles() * Tile.SIZE - bounds.getWidth());
        }
        if (getY() < 0) {
            bounds.setY(0);
        }
        if (getY() > map.getHeightInTiles() * Tile.SIZE - bounds.getHeight()) {
            bounds.setY(map.getHeightInTiles() * Tile.SIZE - bounds.getHeight());
        }
    }

    public final void centerX(final double x) {
        bounds.setX(x - bounds.getWidth() / 2);
    }

    public final void centerY(final double y) {
        bounds.setY(y - bounds.getHeight() / 2);
    }

    public final double getX() {
        return bounds.getUpperLeft().x;
    }

    public final double getY() {
        return bounds.getUpperLeft().y;
    }

    public final double getCenterX() {
        return bounds.getCenter().x;
    }

    public final double getCenterY() {
        return bounds.getCenter().y;
    }

    public boolean contains(final Rectangle2D rect2d) {
        return bounds.getRect2D().contains(rect2d);
    }

    public boolean intersects(final Rectangle2D rect2d) {
        return bounds.getRect2D().intersects(rect2d);
    }

    public Rectangle2D getRect2D() {
        return bounds.getRect2D();
    }
}
