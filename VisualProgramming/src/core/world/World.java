package core.world;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import core.level.LevelManager;
import core.ui.KeyControllable;

public class World implements KeyControllable{
	LevelManager levelManager;
	
	
	public World(int width, int height){
		this.levelManager = new LevelManager(width, height);
		levelManager.goToLevel(LevelManager.LEVEL.LEVEL_ONE);
	}
	public void draw(Graphics2D g){
		levelManager.draw(g);
	}
	public void update(){
		levelManager.update();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		levelManager.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		levelManager.keyReleased(e);
		
	}
}
