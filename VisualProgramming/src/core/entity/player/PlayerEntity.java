package core.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import core.entity.AbstractEntity;
import core.graphics.AnimationLoader;
import core.graphics.AnimationSet;
import core.graphics.lighting.Light;
import core.input.KeyControllable;
import core.input.MouseControllable;
import core.level.AbstractLevel;
import core.math.Vec2D;

public class PlayerEntity extends AbstractEntity implements KeyControllable, MouseControllable {

    private static final float WALK_SPEED = 3;
    private static final float JUMP_SPEED = 12;
    private static final float COEF_FRIC = 0.1f;

    private final AnimationSet animations;
    private boolean moveKeyPressed;

    private Direction direction = Direction.LEFT;

    private final AbstractLevel level;

    private Rope rope;
    private int ropeTimer;
    public Light light;

    public PlayerEntity(final int x, final int y, final AbstractLevel level) {
        super(level);
        animations = new AnimationSet();
        try {
            animations.addAnimation("moveRight", AnimationLoader.getFromSpritesheet("/sprites/test.png", 64)
                    .setDelay(6));
            animations.addAnimation("moveLeft", AnimationLoader.getFromSpritesheet("/sprites/test.png", 64).setDelay(6)
                    .getAnimationFlippedOnX());
        } catch (final IOException e) {
            Logger.getLogger(PlayerEntity.class.getName()).log(Level.SEVERE, null, e);
        }
        animations.goToAnimation("moveRight");
        this.level = level;
        setRect(x, y, animations.getCurrentWidth(), animations.getCurrentHeight());
        setAffectedByGravity(true);
        light = Light.createRoundLight((int) getCenter().x, (int) getCenter().y, 100, new Color(150, 150, 00));
    }

    @Override
    public final void draw(final Graphics2D g) {
        drawCollisionBox(g);
        animations.draw(g, getX(), getY());
    }

    @Override
    public final void update() {
        setPosition(getNextPosition());
        if (direction == Direction.LEFT && moveKeyPressed) {
            getVelocity().x = -WALK_SPEED;

            if (rope != null) {
                getVelocity().x = -50;
            }

        } else if (getVelocity().x < 0) {
            getVelocity().x *= COEF_FRIC;
        }

        if (direction == Direction.RIGHT && moveKeyPressed) {
            getVelocity().x = WALK_SPEED;
            if (rope != null) {
                getVelocity().x = 50;
            }
        } else if (getVelocity().x > 0) {
            getVelocity().x *= COEF_FRIC;
        }

        checkAsleep();
        animations.update();

        if (rope != null) {
            if (rope.getLockposition().toPoint().distanceSq(getCenter().toPoint()) > Rope.MAX_DIST * Rope.MAX_DIST) {
                rope.getLastSegment().applyForce(getCenter().subtract(rope.getLastSegment().getCenter()));
                setCenter(rope.getLastSegment().getCenter());
            }
        }
        light.setCenterX((int) getCenter().x);
        light.setCenterY((int) getCenter().y);
        if (ropeTimer > 0) {
            ropeTimer--;
        }
    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
            moveKeyPressed = true;
            direction = Direction.LEFT;
            animations.goToAnimation("moveLeft");
            break;
        case KeyEvent.VK_D:
            moveKeyPressed = true;
            direction = Direction.RIGHT;
            animations.goToAnimation("moveRight");
            break;
        case KeyEvent.VK_W:
            if (framesSinceLastBottomCollision <= 5 || isCollisionAsleep()) {
                getVelocity().y = -JUMP_SPEED;
            }
            break;
        case KeyEvent.VK_SPACE:
            final BulletEntity bullet = new BulletEntity(getX(), getY(), level);
            if (direction == Direction.LEFT) {
                bullet.applyForce(new Vec2D(-4, 0));
            } else if (direction == Direction.RIGHT) {
                bullet.applyForce(new Vec2D(4, 0));
            }
            level.addEntity(bullet);
            if (rope != null) {
                ropeTimer = 50;
                rope = null;
                getVelocity().y = -10;
            }
            break;
        default:
            break;
        }
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
        case KeyEvent.VK_D:
            moveKeyPressed = false;
            break;
        case KeyEvent.VK_W:
            break;
        default:
            break;
        }
    }

    @Override
    public void collisionWith(final AbstractEntity other) {
        if (other instanceof Rope && ropeTimer <= 0) {
            rope = (Rope) other;
            rope.getLastSegment().setVelocity(getVelocity());
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            setVelocity(getVelocity().add(new Vec2D(0, 1)));
            setPosition(new Vec2D(e.getPoint()));
            return;
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
