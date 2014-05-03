package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import core.entity.AbstractEntity;
import core.entity.player.PlayerEntity;
import core.ui.KeyControllable;

public final class LevelOne extends AbstractLevel implements KeyControllable{
	private static final String LEVEL_FILE = "/maps/levelone.map";
	
	public LevelOne(int width, int height) {
		super(LEVEL_FILE, width, height);
		reset();
	}
	@Override
	public void reset() {
		PlayerEntity e = new PlayerEntity(0, 0);
		this.entities.add(e);
		this.controllableEntities.add(e);
	}
	@Override
	public void draw(Graphics2D g) {
		for(int i = 0; i < entities.size(); i++){
			AbstractEntity e = entities.get(i);
			e.draw(g);
		}
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
	@Override
	public void keyPressed(KeyEvent e) {
		for(KeyControllable c : controllableEntities){
			c.keyPressed(e);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		for(KeyControllable c : controllableEntities){
			c.keyReleased(e);
		}
	}
}
