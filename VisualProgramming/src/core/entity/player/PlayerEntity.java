package core.entity.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import core.entity.AbstractEntity;
import core.graphics.Animation;
import core.graphics.AnimationLoader;
import core.graphics.AnimationSet;
import core.math.Vec2D;
import core.ui.KeyControllable;

public class PlayerEntity extends AbstractEntity implements KeyControllable{

	private static final int WIDTH = 50, HEIGHT = 100;
	AnimationSet animations;
	
	public PlayerEntity(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		animations = new AnimationSet();
		try {
			Animation test = AnimationLoader.getFromSpritesheet("/test.png", 128);
			test.setDelay(6);
			animations.addAnimation("test", test);
		} catch (IOException e) {
			e.printStackTrace();
		}
		animations.goToAnimation("test");
	}

	@Override
	public void draw(Graphics2D g) {
		this.animations.draw(g, this.getX(), this.getY());
	}

	@Override
	public void update() {
		animations.update();
	}

	@Override
	public void applyAcceleration(Vec2D accel) {
		this.velocity = this.velocity.add(accel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
