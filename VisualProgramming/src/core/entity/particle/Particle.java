package core.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;
import core.math.Vec2D;

public abstract class Particle extends AbstractEntity {

    private float life;
    private final Shape shape;
    private Color color;
    private final float size;
    private float opacity;
    private boolean collision = true;

    public Particle(final AbstractLevel level, final float size, final float opacity, final float life,
            final Shape shape, final Color color) {
        super(level);
        this.life = life;
        this.shape = shape;
        this.opacity = opacity;
        setColor(color);
        this.size = size;
        setSize(new Vec2D(shape.getBounds().width, shape.getBounds().height));
    }

    @Override
    public final void draw(final Graphics2D g) {
        g.setColor(color);

        g.translate(getX(), getY());
        g.scale(size, size);
        g.fill(shape);
        g.scale(1 / size, 1 / size);
        g.translate(-getX(), -getY());
    }

    @Override
    public void update() {
        life--;
        if (life <= 0) {
            kill();
        }
        if (collision) {
            setPosition(getNextPosition());
        } else {
            setPosition(getPosition().add(getVelocity()));
        }
    }

    public final void setColor(final Color col) {
        color = new Color(((float) col.getRed()) / 255, ((float) col.getGreen()) / 255, ((float) col.getBlue()) / 255,
                opacity);
    }

    public final void setOpacity(final float op) {
        opacity = op;
        color = new Color(((float) color.getRed()) / 255, ((float) color.getGreen()) / 255,
                ((float) color.getBlue()) / 255, opacity);
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(final boolean collision) {
        this.collision = collision;
    }

    public float getLife() {
        return life;
    }

}
