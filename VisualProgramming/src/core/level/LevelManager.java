package core.level;

import java.awt.Graphics2D;

public class LevelManager {
	private static Level[] levels;
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
	private int currentLevel;
	
	public LevelManager(){
	}
	public void loadLevel(int level){
		
	}
	public void goToLevel(int level){
		currentLevel = level;
	}
	public void draw(Graphics2D g){
		levels[currentLevel].draw(g);
	}
	public void update(){
		levels[currentLevel].update();
	}
}
