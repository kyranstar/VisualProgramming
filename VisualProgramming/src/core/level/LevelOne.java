package core.level;

import java.awt.Graphics2D;

import core.entity.AbstractEntity;

public class LevelOne extends AbstractLevel {
	private static final String LEVEL_FILE = "/maps/levelone.map";
	
	public LevelOne(int width, int height) {
		super(LEVEL_FILE, width, height);
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		for(int i = 0; i < entities.size(); i++){
			AbstractEntity e = entities.get(i);
			e.update();
			if(e.isDead()){
				entities.remove(i);
				i--;
			}
		}
	}

}
