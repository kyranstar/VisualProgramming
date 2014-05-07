package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import core.ui.KeyControllable;

public final class LevelManager implements KeyControllable{
	
	private LevelList levels;
	private int currentLevel;
		
	public LevelManager(int width, int height){
		levels = new LevelList(width, height);
		currentLevel = 0;
	}
	public void goToLevel(LEVEL level){
		levels.getLevel(currentLevel).reset();
		for(int i = 0; i < LEVEL.values().length; i++){
			if(LEVEL.values()[i] == level){
				currentLevel = i;
			}
		}
	}
	public void draw(Graphics2D g){
		levels.getLevel(currentLevel).draw(g);
	}
	public void update(){
		levels.getLevel(currentLevel).update();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(levels.getLevel(currentLevel) instanceof KeyControllable)
			((KeyControllable) levels.getLevel(currentLevel)).keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(levels.getLevel(currentLevel) instanceof KeyControllable)
			((KeyControllable) levels.getLevel(currentLevel)).keyReleased(e);
	}
	public enum LEVEL{
		LEVEL_ONE(LevelOne.class);
		
		Constructor<? extends AbstractLevel> level;
		private LEVEL(Class<? extends AbstractLevel> level){
			try {
				this.level = level.getDeclaredConstructor(int.class, int.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	private static class LevelList{
			private AbstractLevel[] levels;
			
			public LevelList(int width, int height){
				LEVEL[] levelList = LEVEL.values();
				levels = new AbstractLevel[levelList.length];
				for(int i = 0; i < levelList.length; i++){
					try {
						levels[i] = levelList[i].level.newInstance(width, height);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}			
			}
			public AbstractLevel getLevel(int i){
				return levels[i];
			}
	}
}
