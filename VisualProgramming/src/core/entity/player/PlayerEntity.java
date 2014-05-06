package core.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import core.entity.AbstractEntity;
import core.graphics.AnimationLoader;
import core.graphics.AnimationSet;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.KeyControllable;

public class PlayerEntity extends AbstractEntity implements KeyControllable{

	private static final int WIDTH = 100, HEIGHT = 100;
	AnimationSet animations;
	
	public PlayerEntity(int x, int y, GameMap map) {
		super(x, y, WIDTH, HEIGHT, map);
		animations = new AnimationSet();
		try {
			animations.addAnimation("test", AnimationLoader.getFromSpritesheet("/sprites/test.png", 100).setDelay(6));
		} catch (IOException e) {
			e.printStackTrace();
		}
		animations.goToAnimation("test");
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawRect((int)this.getX(), (int) this.getY(), this.WIDTH, this.HEIGHT);
		this.animations.draw(g, this.getX(), this.getY());
	}

	@Override
	public void update() {
		this.setPosition(this.checkTileMapCollision());
		animations.update();
	}

	@Override
	public void applyAcceleration(Vec2D accel) {
		this.velocity = this.velocity.add(accel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D){
			this.velocity.x = 1;
		}else if(e.getKeyCode() == KeyEvent.VK_A){
			this.velocity.x = -1;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
