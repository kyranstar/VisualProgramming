package core.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import machine.Debug;
import core.entity.AbstractEntity;
import core.entity.neutral.BlockEntity;
import core.entity.particle.FireParticleSystem;
import core.entity.player.PlayerEntity;
import core.entity.player.Rope;
import core.graphics.Background;
import core.graphics.imageutil.GraphicsUtils;
import core.graphics.lighting.LightMap;
import core.input.KeyControllable;
import core.input.MouseControllable;
import core.level.LevelManager.LEVEL;
import core.math.Vec2D;
import core.object.Tile;

public final class LevelOne extends AbstractLevel {
    private static final String LEVEL_FILE = "/maps/asd.tmx";
    private PlayerEntity player;
    private Background background;
    private LightMap lightMap;

    public LevelOne(final int width, final int height, final LevelManager levelManager) {
        super(LEVEL_FILE, width, height, levelManager);
    }

    @Override
    public void reset() {

        resetEntities();

        background = new Background("/backgrounds/background.png", 2);

        setGravityForce(new Vec2D(0, 0.6));
        player = new PlayerEntity(800, 00, this);

        addEntity(new FireParticleSystem(100, 200, this));
        addEntity(new BlockEntity(32, 32, 32, 32, this));
        addEntity(player);
        addEntity(new Rope(this, 300));
        lightMap = new LightMap(getMap().getWidthInTiles() * Tile.SIZE, getMap().getHeightInTiles() * Tile.SIZE,
                getMap().getAmbientLight(), getMap().getLights());
    }

    @Override
    public void draw(final Graphics2D g, final BufferedImage image) {

        background.draw(g);
        g.translate(-getMapViewport().getX(), -getMapViewport().getY());

        final long mapBefore = System.nanoTime();
        getMapViewport().draw(g, getMap(), getMapViewport().getRect2D());
        final long mapAfter = System.nanoTime() - mapBefore;

        final long entityBefore = System.nanoTime();
        synchronized (entities) {
            for (final AbstractEntity e : getAllEntities()) {
                e.draw(g);
            }
        }
        final long entityAfter = System.nanoTime() - entityBefore;

        g.translate(getMapViewport().getX(), getMapViewport().getY());

        final long lightmapBefore = System.nanoTime();
        lightMap.addDynamicLight(player.light);
        lightMap.draw(g, getMapViewport().getRect2D());
        final long lightmapAfter = System.nanoTime() - lightmapBefore;

        final long glowBefore = System.nanoTime();
        GraphicsUtils.glowFilter(image, 0.3f);
        final long glowAfter = System.nanoTime() - glowBefore;

        if (Debug.ON) {
            g.setColor(Color.GREEN);

            g.drawString("Entity Count: " + getAllEntities().size(), 10, 30);
            g.drawString("Map: " + mapAfter / 1000000, 100, 30);
            g.drawString("Entities: " + entityAfter / 1000000, 100, 50);
            g.drawString("Lightmap: " + lightmapAfter / 1000000, 100, 70);
            g.drawString("Glow: " + glowAfter / 1000000, 100, 90);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < getAllEntities().size(); i++) {
            final AbstractEntity e = getAllEntities().get(i);
            e.update();
            if (!e.isCollisionAsleep()) {
                for (int j = i + 1; j < getAllEntities().size(); j++) {
                    final AbstractEntity other = getAllEntities().get(j);
                    if (other != e) {
                        if (other.isColliding(e)) {
                            e.collisionWith(other);
                            other.collisionWith(e);
                        }
                    }
                }
            }
            if (e.isAffectedByGravity()) {
                if (!e.isCollisionAsleep()) {
                    e.applyForce(getGravityForce());
                }
            }
            if (e.isDead()) {
                getAllEntities().remove(i);
                i--;
            }
        }
        getMapViewport().centerX(player.getX());
        getMapViewport().lockFrame(getMap());
        background.setRelativePosition(getMapViewport().getX(), background.getY());
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_0) {
            levelManager.goToLevel(LEVEL.LEVEL_TWO);
        }

        for (final KeyControllable c : getKeyControllableEntities()) {
            c.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        for (final KeyControllable c : getKeyControllableEntities()) {
            c.keyReleased(e);
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        for (final MouseControllable c : getMouseControllableEntities()) {
            c.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        for (final MouseControllable c : getMouseControllableEntities()) {
            c.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        for (final MouseControllable c : getMouseControllableEntities()) {
            c.mouseDragged(e);
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        for (final MouseControllable c : getMouseControllableEntities()) {
            c.mouseReleased(e);
        }
    }
}
