package core.level;

import java.awt.Graphics2D;

public class LevelManager {
	private static Level[] levels;
	private int currentLevel;
	
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
		LevelOne(new LevelOne());
		
		Level level;
		private LEVEL(Level level){
			this.level = level;
		}
	}
	
	static{
		final LEVEL[] LEVELS = LEVEL.values();
		for(int i = 0; i < LEVELS.length; i++){
			levels[i] = LEVELS[i].level;
		}			
	}
}
