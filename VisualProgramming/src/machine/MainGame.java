package machine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.ui.KeyControllable;
import core.ui.UserHud;
import core.world.World;

public class MainGame implements KeyControllable{
	private final int ON_SCREEN_X;
	private final int ON_SCREEN_Y;
	public static final int WIDTH = GamePanel.WIDTH;
	public static final int HEIGHT = GamePanel.HEIGHT;//TODO 200
	UserHud userInterface;
	
	private World world;
	
	public MainGame(int onScreenX, int onScreenY, GamePanel gamePanel){
		this.ON_SCREEN_X = onScreenX;
		this.ON_SCREEN_Y = onScreenY;
		world = new World(WIDTH, HEIGHT);
		userInterface = new UserHud(new Rectangle(0, HEIGHT- 200, 500,200), gamePanel);
	}
	public void draw(Graphics2D g){
		g.translate(ON_SCREEN_X, ON_SCREEN_Y);
		world.draw(g);
		g.translate(-ON_SCREEN_X, -ON_SCREEN_Y);
		userInterface.draw(g);
	}
	public void update(){
		world.update();
		userInterface.update();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		world.keyPressed(e);
		userInterface.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		world.keyReleased(e);
		userInterface.keyReleased(e);
	}
	public void mouseClicked(MouseEvent e) {
		userInterface.mouseClicked(e);
	}
	public void mousePressed(MouseEvent e) {
		userInterface.mousePressed(e);
	}
	public void mouseDragged(MouseEvent e) {
		userInterface.mouseDragged(e);
	}
	public void mouseReleased(MouseEvent e) {
		userInterface.mouseReleased(e);
	}
}
