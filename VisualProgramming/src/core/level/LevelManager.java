package core.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import machine.Debug;
import core.input.KeyControllable;
import core.input.MouseControllable;

public final class LevelManager implements KeyControllable, MouseControllable {

    private final LevelList levels;
    private int currentLevel;

    private long lastUpdateMillis = 0;
    private long lastDrawMillis = 0;
    private int checkUpdateTimer = 0;
    private int checkDrawTimer = 0;

    public LevelManager(final int width, final int height) {
        levels = new LevelList(width, height, this);
        currentLevel = 0;
    }

    public void goToLevel(final LEVEL level) {
        levels.unloadLevel(currentLevel);
        for (int i = 0; i < LEVEL.values().length; i++) {
            if (LEVEL.values()[i] == level) {
                currentLevel = i;
            }
        }
        levels.getLevel(currentLevel).reset();
    }

    public void draw(final Graphics2D g, final BufferedImage image) {
        final long before = System.currentTimeMillis();
        levels.getLevel(currentLevel).draw(g, image);
        if (Debug.ON) {
            if (checkDrawTimer <= 0) {
                lastDrawMillis = System.currentTimeMillis() - before;
                checkDrawTimer = 20;
            } else {
                checkDrawTimer--;
            }
            g.setColor(Color.GREEN);
            g.drawString("Draw - " + lastDrawMillis + "      Update - " + lastUpdateMillis, 100, 10);
        }
    }

    public void update() {
        final long before = System.currentTimeMillis();
        levels.getLevel(currentLevel).update();
        if (checkUpdateTimer <= 0) {
            lastUpdateMillis = System.currentTimeMillis() - before;
            checkUpdateTimer = 20;
        } else {
            checkUpdateTimer--;
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        levels.getLevel(currentLevel).keyPressed(e);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        levels.getLevel(currentLevel).keyReleased(e);
    }

    public enum LEVEL {
        LEVEL_ONE(LevelOne.class), LEVEL_TWO(LevelOne.class);

        Constructor<? extends AbstractLevel> level;

        private LEVEL(final Class<? extends AbstractLevel> level) {
            try {
                this.level = level.getDeclaredConstructor(int.class, int.class, LevelManager.class);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private static class LevelList {
        private final AbstractLevel[] levels;

        final private int width, height;
        final private LevelManager levelManager;

        public LevelList(final int width, final int height, final LevelManager levelManager) {

            this.width = width;
            this.height = height;
            this.levelManager = levelManager;

            final LEVEL[] levelList = LEVEL.values();
            levels = new AbstractLevel[levelList.length];
        }

        public void unloadLevel(final int i) {
            levels[i] = null;
        }

        public AbstractLevel getLevel(final int i) {
            if (levels[i] == null) {
                try {
                    levels[i] = LEVEL.values()[i].level.newInstance(width, height, levelManager);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return levels[i];
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        levels.getLevel(currentLevel).mouseClicked(e);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        levels.getLevel(currentLevel).mousePressed(e);
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        levels.getLevel(currentLevel).mouseDragged(e);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        levels.getLevel(currentLevel).mouseReleased(e);
    }
}
