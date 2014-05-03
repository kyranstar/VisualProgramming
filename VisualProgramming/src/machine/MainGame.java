package machine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import core.ui.KeyControllable;
import core.world.World;

public class MainGame implements KeyControllable{
	private final int ON_SCREEN_X;
	private final int ON_SCREEN_Y;
	public static final int WIDTH = GamePanel.WIDTH;
	public static final int HEIGHT = GamePanel.HEIGHT-ProgrammingSpace.HEIGHT;
	
	private World world;
	
	public MainGame(int onScreenX, int onScreenY){
		this.ON_SCREEN_X = onScreenX;
		this.ON_SCREEN_Y = onScreenY;
		world = new World(WIDTH, HEIGHT);
	}
	public void draw(Graphics2D g){
		g.translate(ON_SCREEN_X, ON_SCREEN_Y);
		world.draw(g);
		g.translate(-ON_SCREEN_X, -ON_SCREEN_Y);
	}
	public void update(){
		world.update();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		world.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		world.keyReleased(e);
	}
}
