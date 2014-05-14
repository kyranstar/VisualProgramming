package core.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.entity.AbstractEntity;
import core.entity.neutral.BlockEntity;
import core.entity.player.PlayerEntity;
import core.graphics.Background;
import core.graphics.lighting.Light;
import core.graphics.lighting.LightMap;
import core.math.Vec2D;
import core.object.Tile;
import core.ui.KeyControllable;

public final class LevelOne extends AbstractLevel implements KeyControllable{
	private static final String LEVEL_FILE = "./res/maps/asd.tmx";
	private PlayerEntity player;
	private Background background;
	private LightMap lightMap;
	
	public LevelOne(final int width, final int height, final LevelManager levelManager) {
		super(LEVEL_FILE, width, height, levelManager);
	}
	@Override
	public void reset() {
		background = new Background("/backgrounds/background.png", 2);
		this.entities = new LinkedList<AbstractEntity>();
		controllableEntities = new ArrayList<KeyControllable>();
		
		this.ambientForce = new Vec2D(0, 0.6);
		player = new PlayerEntity(50, 50, this.map);
		entities.add(new BlockEntity(32,32,32,32, map));
		entities.add(player);
		this.controllableEntities.add(player);
		this.lightMap = new LightMap(map.getWidthInTiles() * Tile.SIZE, map.getHeightInTiles() * Tile.SIZE, 190);
		
		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(50,50, 900, Color.RED));
		lights.add(new Light(850,150, 300, Color.YELLOW));
		lights.add(new Light(650,20, 200, Color.YELLOW));
		
		this.lightMap.setLights(lights);
	}
	@Override
	public void draw(final Graphics2D g) {
		background.draw(g);
		g.translate(-mapViewport.getX(), -mapViewport.getY());
		this.mapViewport.draw(g, map);
		Iterator<AbstractEntity> it = this.entities.iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
		lightMap.draw(g);
		g.translate(mapViewport.getX(), mapViewport.getY());
	}
	
	@Override
	public void update() {
			Iterator<AbstractEntity> it = entities.iterator();
			while(it.hasNext()){
				AbstractEntity e = it.next();
				e.update();
				if(e.isAffectedByGravity()) {
					e.applyImpulse(ambientForce);
				}
				if(e.isDead()){
					it.remove();
				}
			}
			this.mapViewport.centerX(player.getX());
			this.mapViewport.lockFrame(map);
			background.setRelativePosition(mapViewport.getX(), background.getY());
	}
	@Override
	public void keyPressed(final KeyEvent e) {
		for(KeyControllable c : controllableEntities){
			c.keyPressed(e);
		}
	}
	@Override
	public void keyReleased(final KeyEvent e) {
		for(KeyControllable c : controllableEntities){
			c.keyReleased(e);
		}
	}
}
