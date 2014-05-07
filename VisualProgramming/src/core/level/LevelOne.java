package core.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.entity.AbstractEntity;
import core.entity.neutral.BlockEntity;
import core.entity.player.PlayerEntity;
import core.math.Vec2D;
import core.ui.KeyControllable;

public final class LevelOne extends AbstractLevel implements KeyControllable{
	private static final String LEVEL_FILE = "./res/maps/asd.tmx";
	private PlayerEntity player;
	
	public LevelOne(int width, int height) {
		super(LEVEL_FILE, width, height);
	}
	@Override
	public void reset() {
		this.entities = new ArrayList<AbstractEntity>();
		controllableEntities = new ArrayList<KeyControllable>();
		
		this.ambientForce = new Vec2D(0, 0.6);
		player = new PlayerEntity(50, 50, this.map);
		this.entities.add(new BlockEntity(32,32,32,32, map));
		this.entities.add(player);
		this.controllableEntities.add(player);
	}
	@Override
	public void draw(Graphics2D g) {
		this.mapViewport.lockFrame(map);
		g.translate(-mapViewport.getX(), -mapViewport.getY());
		this.mapViewport.draw(g, map);
		for(AbstractEntity e : this.entities){
			e.draw(g);
		}
		g.translate(mapViewport.getX(), mapViewport.getY());
	}
	@Override
	public void update() {
		for(int i = 0; i < entities.size(); i++){
			AbstractEntity e = entities.get(i);
			e.update();
			e.applyAcceleration(ambientForce);
			if(e.isDead()){
				entities.remove(i);
				i--;
			}
		}
		this.mapViewport.centerX(player.getX());
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
