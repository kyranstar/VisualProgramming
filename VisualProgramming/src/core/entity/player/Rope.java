package core.entity.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;
import core.math.Vec2D;

public class Rope extends AbstractEntity {

    private final Vec2D lockPosition = new Vec2D(300, 0);
    public static final int MAX_DIST = 5;
    private final List<RopeSegment> segments = new ArrayList<RopeSegment>();

    public Rope(final AbstractLevel level, final int maxRadius) {
        super(level);
        setAffectedByGravity(true);
        for (int i = 0; i < maxRadius; i += MAX_DIST) {
            getSegments().add(new RopeSegment(level, getLockposition().x + i, getLockposition().y, this, i / MAX_DIST));
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        RopeSegment prev = null;
        int i = 0;
        for (final RopeSegment seg : getSegments()) {
            if (prev != null) {
                g.setColor(new Color(i, 0, i));
                g.setStroke(new BasicStroke(5));
                g.drawLine((int) prev.getCenter().x, (int) prev.getCenter().y, (int) seg.getCenter().x,
                        (int) seg.getCenter().y);
                g.fillOval((int) seg.getX(), (int) seg.getY(), (int) seg.getSize().x, (int) seg.getSize().y);
            }
            prev = seg;
            i = Math.min(255, i + 20);
        }
    }

    @Override
    public void update() {

        for (int i = 0; i < getSegments().size(); i++) {
            RopeSegment previous = null;
            RopeSegment next = null;

            if (i != 0) {
                previous = getSegments().get(i - 1);
            }
            if (i != getSegments().size() - 1) {
                next = getSegments().get(i + 1);
            }
            final RopeSegment seg = getSegments().get(i);

            // do collision detection
            seg.update();
            // if we are not the first (position locked) segment
            if (next != null) {
                // if we are out of range of the previous segment
                if (seg.getCenter().toPoint().distanceSq(next.getCenter().toPoint()) > MAX_DIST * MAX_DIST) {
                    // pull us back in
                    final Vec2D nextCenter = next.getPosition();
                    seg.applyForce(nextCenter.subtract(seg.getPosition()).multiply(0.1));
                    // seg.setPosition(nextCenter.add(seg.getPosition().subtract(nextCenter).unit().multiply(MAX_DIST)));
                }
            }
            if (previous != null) {
                // if we are out of range of the previous segment
                if (seg.getCenter().toPoint().distanceSq(previous.getCenter().toPoint()) > MAX_DIST * MAX_DIST) {
                    // pull us back in
                    final Vec2D previousCenter = previous.getPosition();
                    // seg.applyForce(previousCenter.subtract(seg.getPosition()).multiply(0.01));
                    seg.setPosition(previousCenter.add(seg.getPosition().subtract(previousCenter).unit()
                            .multiply(MAX_DIST)));
                }
            }

        }
        // lock position of first segment
        getSegments().get(0).setPosition(getLockposition());
        getSegments().get(0).setVelocity(new Vec2D(0, 0));
    }

    public RopeSegment getLastSegment() {
        return getSegments().get(getSegments().size() - 1);
    }

    @Override
    public void applyForce(final Vec2D accel) {
        for (final RopeSegment seg : getSegments()) {
            seg.setVelocity(seg.getVelocity().add(accel));
        }
    }

    @Override
    public boolean isColliding(final AbstractEntity other) {
        for (final RopeSegment seg : getSegments()) {
            if (seg.isColliding(other)) return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(final Line2D other) {
        for (final RopeSegment seg : getSegments()) {
            if (seg.isColliding(other)) return true;
        }
        return false;
    }

    @Override
    public boolean isCollisionAsleep() {
        return false;
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        // TODO Auto-generated method stub

    }

    public Vec2D getLockposition() {
        return lockPosition;
    }

    public List<RopeSegment> getSegments() {
        return segments;
    }

}
