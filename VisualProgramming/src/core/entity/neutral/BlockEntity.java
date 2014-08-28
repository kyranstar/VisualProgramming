package core.entity.neutral;

import java.awt.Color;
import java.awt.Graphics2D;

import machine.GamePanel;
import core.entity.AbstractEntity;
import core.entity.ai.AIUpAndDown;
import core.level.AbstractLevel;

public final class BlockEntity extends NeutralEntity {

    private final AIUpAndDown artificialIntelligence;

    public BlockEntity(final int x, final int y, final double width, final double height, final AbstractLevel level) {
        super(level);
        setRect(x, y, width, height);
        artificialIntelligence = new AIUpAndDown(y, y + 64, 5);
        setAffectedByGravity(false);
    }

    @Override
    public final void draw(final Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), (int) getSize().x, (int) getSize().y);
        drawCollisionBox(g);
    }

    @Override
    public final void update() {
        setPosition(getNextPosition());
        applyForce(artificialIntelligence.getNextImpulse(this));
        getTweenManager().update((float) (1f / GamePanel.MAX_FPS));
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        // TODO Auto-generated method stub

    }
}
