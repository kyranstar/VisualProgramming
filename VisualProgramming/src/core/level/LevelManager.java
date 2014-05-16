package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import core.ui.KeyControllable;
import core.ui.UserHud;

public final class LevelManager implements KeyControllable{
	
	private LevelList levels;
	private int currentLevel;
	private final UserHud userHud;	
	
	public LevelManager(final int width, final int height, final UserHud userHud){
		levels = new LevelList(width, height, this);
		this.userHud = userHud;
		currentLevel = 0;
	}
	public void goToLevel(final LEVEL level){
		levels.getLevel(currentLevel).reset();
		for(int i = 0; i < LEVEL.values().length; i++){
			if(LEVEL.values()[i] == level){
				currentLevel = i;
			}
		}
	}
	public void draw(final Graphics2D g){
		levels.getLevel(currentLevel).draw(g);
	}
	public void update(){
		levels.getLevel(currentLevel).update();
	}
	@Override
	public void keyPressed(final KeyEvent e) {
		if(levels.getLevel(currentLevel) instanceof KeyControllable) {
			((KeyControllable) levels.getLevel(currentLevel)).keyPressed(e);
		}
	}
	@Override
	public void keyReleased(final KeyEvent e) {
		if(levels.getLevel(currentLevel) instanceof KeyControllable) {
			((KeyControllable) levels.getLevel(currentLevel)).keyReleased(e);
		}
	}
	public UserHud getUserHud() {
		return this.userHud;
	}
	public enum LEVEL{
		LEVEL_ONE(LevelOne.class);
		
		Constructor<? extends AbstractLevel> level;
		private LEVEL(final Class<? extends AbstractLevel> level){
			try {
				this.level = level.getDeclaredConstructor(int.class, int.class, LevelManager.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	private static class LevelList{
			private AbstractLevel[] levels;
			
			public LevelList(final int width, final int height, final LevelManager levelManager){
				LEVEL[] levelList = LEVEL.values();
				levels = new AbstractLevel[levelList.length];
				for(int i = 0; i < levelList.length; i++){
					try {
						levels[i] = levelList[i].level.newInstance(width, height, levelManager);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}			
			}
			public AbstractLevel getLevel(final int i){
				return levels[i];
			}
	}
}
