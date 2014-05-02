package core.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
	List<Level> levels;
	int currentLevel;
	
	public LevelManager(){
		levels = new ArrayList<Level>();
	}
	public void addLevel(Level level){
		levels.add(level);
	}
	public void goToLevel(int level){
		currentLevel = level;
	}
	public void draw(Graphics2D g){
		levels.get(currentLevel).draw(g);
	}
	public void update(){
		levels.get(currentLevel).update();
	}
}
