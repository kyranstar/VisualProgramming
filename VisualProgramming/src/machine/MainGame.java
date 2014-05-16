package machine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.ui.KeyControllable;
import core.ui.UserHud;
import core.world.World;

public class MainGame implements KeyControllable{
	public static final int WIDTH = GamePanel.WIDTH;
	public static final int HEIGHT = GamePanel.HEIGHT;//TODO 200
	UserHud userInterface;
	
	private World world;
	
	public MainGame(final GamePanel gamePanel){

		userInterface = new UserHud(150, gamePanel);
		world = new World(WIDTH, HEIGHT, userInterface);
	}
	public final void draw(final Graphics2D g){
		world.draw(g);
		userInterface.draw(g);
	}
	public final void update(){
		world.update();
		userInterface.update();
	}
	@Override
	public final void keyPressed(final KeyEvent e) {
		world.keyPressed(e);
		userInterface.keyPressed(e);
	}
	@Override
	public final void keyReleased(final KeyEvent e) {
		world.keyReleased(e);
		userInterface.keyReleased(e);
	}
	public final void mouseClicked(final MouseEvent e) {
		userInterface.mouseClicked(e);
	}
	public final void mousePressed(final MouseEvent e) {
		userInterface.mousePressed(e);
	}
	public final void mouseDragged(final MouseEvent e) {
		userInterface.mouseDragged(e);
	}
	public final void mouseReleased(final MouseEvent e) {
		userInterface.mouseReleased(e);
	}
}
