package core.world;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import core.level.LevelManager;
import core.ui.KeyControllable;
import core.ui.UserHud;

public class World implements KeyControllable{
	private LevelManager levelManager;
	
	public World(final int width, final int height, final UserHud userHud){
		this.levelManager = new LevelManager(width, height, userHud);
		levelManager.goToLevel(LevelManager.LEVEL.LEVEL_ONE);
	}
	public final void draw(final Graphics2D g){
		levelManager.draw(g);
	}
	public final void update(){
		levelManager.update();
	}
	@Override
	public final void keyPressed(final KeyEvent e) {
		levelManager.keyPressed(e);
	}
	@Override
	public final void keyReleased(final KeyEvent e) {
		levelManager.keyReleased(e);
		
	}
}
