package core.level;

import java.awt.Graphics2D;

import machine.MainGame;

public final class LevelManager {
	private static AbstractLevel[] levels;
	private int currentLevel;
	
	private static int width = MainGame.WIDTH, height = MainGame.HEIGHT;
	
	public LevelManager(){
		currentLevel = 0;
	}
	public void goToLevel(int level){
		levels[currentLevel].reset();
		currentLevel = level;
	}
	public void draw(Graphics2D g){
		levels[currentLevel].draw(g);
	}
	public void update(){
		levels[currentLevel].update();
	}
	
	enum LEVEL{
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
