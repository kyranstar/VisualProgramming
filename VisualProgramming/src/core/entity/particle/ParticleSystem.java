package core.entity.particle;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.entity.AbstractEntity;
import core.level.AbstractLevel;
import core.math.Vec2D;

public abstract class ParticleSystem extends AbstractEntity {
    protected final List<Particle> particles;

    public ParticleSystem(final AbstractLevel level) {
        super(level);
        particles = new LinkedList<Particle>();
        setAffectedByGravity(false);
    }

    protected void addParticle(final Particle p) {
        particles.add(p);
    }

    @Override
    public void update() {
        final Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            final Particle p = it.next();
            p.update();

            if (p.isDead()) {
                it.remove();
            }
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        for (final Particle p : particles) {
            p.draw(g);
        }
    }

    @Override
    public void applyForce(final Vec2D ambientForce) {
        for (final Particle p : particles) {
            // fire goes up with downward gravity
            p.applyForce(ambientForce.multiply(new Vec2D(1, -0.1)));
        }
    }
}
