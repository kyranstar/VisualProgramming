package core.graphics.background;

import java.awt.Graphics2D;

public abstract class AbstractBackground {

    protected double x;
    protected double y;
    protected double distance;

    protected int width;
    protected int height;

    public abstract void draw(final Graphics2D g);

    public abstract void update();

    public void viewportMoved(final double x, final double y) {
        this.x = (-x / distance) % width;
        this.y = (-y / distance) % height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
