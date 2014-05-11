package machine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import core.ui.programming.ProgrammingSpace;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	public static final double MAX_FPS = 60;
	private int fps;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 700;
	//image
	private BufferedImage image;
	private Graphics2D g;
	private Thread thread;

	private MainGame game;
	
	public GamePanel(){
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		thread = new Thread(this);
		game = new MainGame(0, 0, this);
		
		
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/MAX_FPS;
		
		int ticks = 0; //Every time it runs the game
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		init();
		//Game loop
		
		while (true){	
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while(delta >= 1){
				ticks++;
				update();
				delta-=1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, e);
			}
			if (shouldRender){
				draw();
				drawToScreen();
				}
				
				if (System.currentTimeMillis() - lastTimer >= 1000){
					lastTimer += 1000;
					fps = ticks;
					ticks = 0;
				}
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	private void draw() {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		game.draw(g);
		g.setColor(Color.GREEN);
		g.drawString(fps + " fps", 10, 10);
	}
	private void update(){
		game.update();
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}
	public void startThread(){
		this.thread.start();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		game.mouseDragged(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		game.mouseClicked(e); 
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		game.mousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {game.mouseReleased(e);}
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
