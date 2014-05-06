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

	private static final float WALK_SPEED = 3;
	private static final float COEF_FRIC = 0.9f;
	AnimationSet animations;
	private boolean movingLeft, movingRight;
	
	public PlayerEntity(int x, int y, GameMap map) {
		super(map);
		animations = new AnimationSet();
		try {
			animations.addAnimation("test", AnimationLoader.getFromSpritesheet("/sprites/test.png", 100).setDelay(6));
		} catch (IOException e) {
			e.printStackTrace();
		}
		animations.goToAnimation("test");
		this.setRect(x, y, animations.getCurrentWidth(), animations.getCurrentHeight());
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawRect((int)this.getX(), (int) this.getY(), (int)animations.getCurrentWidth(), (int) animations.getCurrentHeight());
		this.animations.draw(g, this.getX(), this.getY());
	}

	@Override
	public void update() {
		this.setPosition(this.getNextPosition());
		if(this.movingLeft)
			this.velocity.x = -WALK_SPEED;
		if(!this.movingLeft && this.velocity.x < 0)
			this.velocity.x *= COEF_FRIC;
		
		if(this.movingRight)
			this.velocity.x = WALK_SPEED;
		if(!this.movingRight && this.velocity.x > 0)
			this.velocity.x *= COEF_FRIC;
		
		animations.update();
	}

	@Override
	public void applyAcceleration(Vec2D accel) {
		this.velocity = this.velocity.add(accel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_A:
				this.movingLeft = true;
				break;
			case KeyEvent.VK_D:
				this.movingRight = true;
				break;
			case KeyEvent.VK_W:
				this.velocity.y = -5;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_A:
				this.movingLeft = false;
				break;
			case KeyEvent.VK_D:
				this.movingRight = false;
				break;
		}
	}

}
