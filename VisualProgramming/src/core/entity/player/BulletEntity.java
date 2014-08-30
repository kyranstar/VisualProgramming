package core.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;

public class BulletEntity extends AbstractEntity {

    private static final int SIZE = 50;

    public BulletEntity(final double d, final double e, final AbstractLevel level) {
        super(level);
        setRect(d, e, SIZE, SIZE);
        setAffectedByGravity(true);
    }

    @Override
    public void draw(final Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), (int) getSize().x, (int) getSize().y);
        drawCollisionBox(g);
    }

    @Override
    public void update() {
        setPosition(getNextPosition());
        checkAsleep();
        if (isOffScreen()) {
            kill();
        }
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        if (!(other instanceof PlayerEntity) && !(other instanceof BulletEntity)) {
            kill();
        }
    }
}
