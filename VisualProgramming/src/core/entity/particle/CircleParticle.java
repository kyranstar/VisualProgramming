package core.entity.particle;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;
import core.math.Vec2D;

public class CircleParticle extends Particle {

    @Override
    public void applyForce(final Vec2D accel) {
        super.applyForce(accel);
    }

    public CircleParticle(final AbstractLevel level, final float x, final float y, final float width,
            final float height, final float life, final Color color) {
        super(level, 1.0f, 1.0f, life, new Ellipse2D.Double(x, y, width, height), color);
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        // TODO Auto-generated method stub

    }

}
