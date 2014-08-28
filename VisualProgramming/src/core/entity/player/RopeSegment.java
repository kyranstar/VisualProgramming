package core.entity.player;

import java.awt.Graphics2D;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;
import core.math.Vec2D;

public class RopeSegment extends AbstractEntity {

    private final Rope parent;
    private final int place;

    public RopeSegment(final AbstractLevel level, final double x, final double y, final Rope parent, final int place) {
        super(level);
        setRect(x, y, Rope.MAX_DIST, Rope.MAX_DIST);
        this.parent = parent;
        this.place = place;
    }

    @Override
    public void draw(final Graphics2D g) {
        g.fillOval((int) getX(), (int) getY(), Rope.MAX_DIST, Rope.MAX_DIST);
    }

    @Override
    public void applyForce(final Vec2D accel) {
        super.applyForce(accel);
        // a goes toward top, b goes toward bottom
        int i = 1;
        for (int a = place - 1, b = place + 1; a >= 0 || b <= parent.getSegments().size() - 1; a--, b++) {
            if (a >= 0) {
                final RopeSegment aSeg = parent.getSegments().get(a);
                aSeg.setVelocity(aSeg.getVelocity().add(accel.divide(parent.getSegments().size())));
            }
            if (b <= parent.getSegments().size() - 1) {
                final RopeSegment bSeg = parent.getSegments().get(b);
                bSeg.setVelocity(bSeg.getVelocity().add(accel.divide(parent.getSegments().size())));
            }
            i++;
        }
    }

    @Override
    public void update() {
        setPosition(getNextPosition());
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        // TODO Auto-generated method stub

    }

}
