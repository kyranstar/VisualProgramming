package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import machine.MainGame;
import core.ui.KeyControllable;

public final class LevelManager implements KeyControllable{
	private static AbstractLevel[] levels;
	private int currentLevel;
	
	private static int width = MainGame.WIDTH, height = MainGame.HEIGHT;
	
	public LevelManager(){
		currentLevel = 0;
	}
	public void goToLevel(LEVEL level){
		levels[currentLevel].reset();
		for(int i = 0; i < LEVEL.values().length; i++){
			if(LEVEL.values()[i] == level){
				currentLevel = i;
			}
		}
	}
	public void draw(Graphics2D g){
		levels[currentLevel].draw(g);
	}
	public void update(){
		levels[currentLevel].update();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(levels[currentLevel] instanceof KeyControllable)
			((KeyControllable) levels[currentLevel]).keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(levels[currentLevel] instanceof KeyControllable)
			((KeyControllable) levels[currentLevel]).keyReleased(e);
	}
	
	public enum LEVEL{
		LevelOne(new LevelOne(width, height));
		
		AbstractLevel level;
		private LEVEL(AbstractLevel level){
			this.level = level;
		}
	}
	
	static{
		final LEVEL[] LEVELS = LEVEL.values();
		levels = new AbstractLevel[LEVELS.length];
		
		for(int i = 0; i < LEVELS.length; i++){
			levels[i] = LEVELS[i].level;
		}			

	}
}
