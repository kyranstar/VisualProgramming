package machine;

import java.awt.Graphics2D;

import core.world.World;

public class MainGame {
	private final int ON_SCREEN_X;
	private final int ON_SCREEN_Y;
	private final int WIDTH;
	private final int HEIGHT;
	
	World world;
	
	public MainGame(int onScreenX, int onScreenY, int width, int height){
		this.ON_SCREEN_X = onScreenX;
		this.ON_SCREEN_Y = onScreenY;
		this.WIDTH = width;
		this.HEIGHT = height;
		world = new World(WIDTH, HEIGHT);
	}
	public void draw(Graphics2D g){
		g.translate(ON_SCREEN_X, ON_SCREEN_Y);
		world.draw(g);
		g.translate(-ON_SCREEN_X, -ON_SCREEN_Y);
	}
	public void update(){
		
	}
}
