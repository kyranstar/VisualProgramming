package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import core.entity.AbstractEntity;
import core.entity.player.PlayerEntity;
import core.math.Vec2D;
import core.ui.KeyControllable;

public final class LevelOne extends AbstractLevel implements KeyControllable{
	private static final String LEVEL_FILE = "./res/maps/asd.tmx";
	PlayerEntity player;
	
	public LevelOne(int width, int height) {
		super(LEVEL_FILE, width, height);
		reset();
	}
	@Override
	public void reset() {
		super.reset();
		this.ambientForce = new Vec2D(0, 0.3);
		player = new PlayerEntity(50, 50, this.map);
		this.entities.add(player);
		this.controllableEntities.add(player);
	}
	@Override
	public void draw(Graphics2D g) {
		this.mapViewport.draw(g, map);
		for(AbstractEntity e : this.entities){
			e.draw(g);
		}
	}
	@Override
	public void update() {
		System.out.println(entities.size());
		for(int i = 0; i < entities.size(); i++){
			AbstractEntity e = entities.get(i);
			e.update();
			e.applyAcceleration(ambientForce);
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
