package core.entity.particle;

import java.awt.Color;
import java.util.Iterator;

import core.entity.AbstractEntity;
import core.graphics.imageutil.GraphicsUtils;
import core.level.AbstractLevel;
import core.math.MathUtils;
import core.math.Vec2D;

public class FireParticleSystem extends ParticleSystem {
    private static final int MIN_WAIT = 1;
    private static final int MAX_WAIT = 3;
    private int nextParticle;

    public FireParticleSystem(final int x, final int y, final AbstractLevel level) {
        super(level);

        setPosition(new Vec2D(x, y));
    }

    @Override
    public void update() {
        if (nextParticle <= 0) {
            nextParticle = MathUtils.getRandomInt(MIN_WAIT, MAX_WAIT);
            final float angle = (float) Math.toRadians(MathUtils.getRandomFloat(0, 360));
            final float distance = MathUtils.getRandomFloat(0, 30);
            final float x = (float) (getX() + (distance * Math.cos(angle)));
            final float y = (float) (getY() + (distance * Math.sin(angle)));

            final float size = MathUtils.getRandomFloat(10, 20);

            final Particle p = new CircleParticle(parentLevel, x, y, size, size, MathUtils.getRandomInt(100, 255),
                    GraphicsUtils.generateRandomColor(Color.RED));
            p.setAffectedByGravity(false);
            p.applyForce(new Vec2D((MathUtils.getRandomFloat(-2, 2)) / 2, 0));
            addParticle(p);
        }
        nextParticle--;
        final Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            final Particle p = iterator.next();
            p.update();

            if (p.isDead()) {
                iterator.remove();
            }
            p.setOpacity(p.getLife() / 255);
        }
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        // TODO Auto-generated method stub

    }
}
