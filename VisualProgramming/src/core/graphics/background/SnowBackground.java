package core.graphics.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.entity.particle.CircleParticle;
import core.entity.particle.Particle;
import core.level.AbstractLevel;
import core.math.MathUtils;
import core.math.Vec2D;

public class SnowBackground extends AbstractBackground {

    private final int frequency;
    private int lastParticleTime;

    private final List<CircleParticle> particles = new ArrayList<>();
    private final AbstractLevel level;

    public SnowBackground(final AbstractLevel level, final int width, final int height, final int distance,
            final int frequency) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.frequency = frequency;
        this.distance = distance;
    }

    @Override
    public void draw(final Graphics2D g) {
        for (final Particle p : particles) {
            p.draw(g);
        }
    }

    @Override
    public void update() {
        final Iterator<CircleParticle> it = particles.iterator();
        while (it.hasNext()) {
            final CircleParticle p = it.next();
            p.update();
            p.setOpacity(Math.min(255, p.getLife()) / 255);
            if (p.isDead()) {
                it.remove();
            }
        }
        if (lastParticleTime <= 0) {
            final int leftScreen = (int) level.getMapViewport().getX();
            final int rightScreen = (int) (level.getMapViewport().getX() + level.getMapViewport().getRect2D()
                    .getWidth());

            final int size = MathUtils.getRandomInt(10, 20);

            final Color color = new Color(190, MathUtils.getRandomInt(190, 220), MathUtils.getRandomInt(190, 240));

            final CircleParticle p = new CircleParticle(level, MathUtils.getRandomInt(leftScreen - 100,
                    rightScreen + 100), -10, size, size, 255, color);

            p.setCollision(false);

            p.applyForce(new Vec2D(MathUtils.getRandomFloat(-1, 1), MathUtils.getRandomFloat(1, 3)));

            particles.add(p);

            lastParticleTime = frequency;
        } else {
            lastParticleTime--;
        }
    }
}
