package machine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import core.graphics.imageutil.GraphicsUtils;
import core.level.LevelManager;
import core.level.LevelManager.LEVEL;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
    public static final double MAX_FPS = 60;
    private int fps;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 800;
    // image
    private BufferedImage image;
    private Graphics2D g;
    private final Thread thread;

    private final LevelManager game;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        thread = new Thread(this);
        game = new LevelManager(WIDTH, HEIGHT);
        game.goToLevel(LEVEL.LEVEL_ONE);

        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        setFocusable(true);
    }

    @Override
    public final void run() {
        long lastTime = System.nanoTime();
        final double nsPerTick = 1000000000D / MAX_FPS;

        int ticks = 0; // Every time it runs the game

        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        init();
        // Game loop
        while (true) {

            final long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;
            while (delta >= 1) {
                ticks++;
                update();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (final InterruptedException e) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, e);
            }
            if (shouldRender) {
                draw();
                drawToScreen();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                fps = ticks;
                ticks = 0;
            }
        }
    }

    private void init() {
        image = GraphicsUtils.createImage(WIDTH, HEIGHT, BufferedImage.OPAQUE);
        g = (Graphics2D) image.getGraphics();
        GraphicsUtils.prettyGraphics(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    private void draw() {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        game.draw(g, image);
        if (Debug.ON) {
            g.setColor(Color.GREEN);
            g.drawString(fps + " fps", 10, 10);
        }
    }

    private void update() {
        game.update();
    }

    private void drawToScreen() {
        final Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public void startThread() {
        thread.start();
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        game.mouseDragged(e);
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        game.mouseClicked(e);
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        game.mousePressed(e);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        game.mouseReleased(e);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        game.keyPressed(e);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        game.keyReleased(e);
    }

    @Override
    public void keyTyped(final KeyEvent arg0) {
    }
}
