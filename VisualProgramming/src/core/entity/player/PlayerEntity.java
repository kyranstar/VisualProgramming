package core.entity.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import core.entity.AbstractEntity;
import core.graphics.AnimationLoader;
import core.graphics.AnimationSet;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.KeyControllable;
import core.ui.UserHud;
import core.ui.programming.piece.Piece;

public class PlayerEntity extends AbstractEntity implements KeyControllable{

	private static final float WALK_SPEED = 3;
	private static final float JUMP_SPEED = 12;
	private static final float COEF_FRIC = 0.1f;
	
	
	private AnimationSet animations;
	private boolean movingLeft, movingRight;
	private PlayerConnection connection;
	private List<AbstractEntity> programmableEntities;
	private UserHud userHud;
	
	public PlayerEntity(final int x, final int y, List<AbstractEntity> programmableEntities, UserHud userHud,final GameMap map) {
		super(map);
		animations = new AnimationSet();
		try {
			animations.addAnimation("moveRight", AnimationLoader.getFromSpritesheet("/sprites/test.png", 64).setDelay(6));
			animations.addAnimation("moveLeft", AnimationLoader.getFromSpritesheet("/sprites/test.png", 64).setDelay(6).getAnimationFlippedOnX());
		} catch (IOException e) {
			Logger.getLogger(PlayerEntity.class.getName()).log(Level.SEVERE, null, e);
		}
		animations.goToAnimation("moveRight");
		this.userHud = userHud;
		this.programmableEntities = programmableEntities;
		this.setRect(x, y, animations.getCurrentWidth(), animations.getCurrentHeight());
		this.affectedByGravity = true;
		this.setRestitution(500f);
	}
	@Override
	public final void draw(final Graphics2D g) {
		this.drawCollisionBox(g);
		this.animations.draw(g, this.getX(), this.getY());
		if(connection != null)
			connection.draw(g);
	}

	@Override
	public final void update() {
		this.setPosition(this.getNextPosition());
		if(this.movingLeft){
			this.getVelocity().x = -WALK_SPEED;
		}
		else if (this.getVelocity().x < 0){
			this.getVelocity().x *= COEF_FRIC;
		}
		
		if(this.movingRight){
			this.getVelocity().x = WALK_SPEED;
		}
		else if(this.getVelocity().x > 0){
			this.getVelocity().x *= COEF_FRIC;
		}
		
		animations.update();
		if(connection != null){
			connection.update();
			if(connection.isDead()){
				connection = null;
				return;
			}
			for(AbstractEntity e : programmableEntities){
				if(e.equals(this))
					continue;
				if(connection.getCollisionBox().isColliding(e.getCollisionBox())){
					connection.kill();
					userHud.getProgrammingSpaceInterface().setPieces(e.getProgrammingPieces());
				}					
			}
		}
	}

	@Override
	public final void applyImpulse(final Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

	@Override
	public final void keyPressed(final KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_A:
				this.movingLeft = true;
				this.animations.goToAnimation("moveLeft");
				break;
			case KeyEvent.VK_D:
				this.movingRight = true;
				this.animations.goToAnimation("moveRight");
				break;
			case KeyEvent.VK_W:
				if(this.framesSinceLastBottomCollision <= 5) {
					this.getVelocity().y = -JUMP_SPEED;
				}
				break;
			case KeyEvent.VK_SPACE:
				connection = new PlayerConnection(this.getX(), this.getY(), map);
				if(this.movingLeft)
					connection.applyImpulse(new Vec2D(-4, 0));
				else if(this.movingRight)
					connection.applyImpulse(new Vec2D(4, 0));
				break;
			default:
				break;
		}
	}

	@Override
	public final void keyReleased(final KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_A:
				this.movingLeft = false;
				break;
			case KeyEvent.VK_D:
				this.movingRight = false;
				break;
			case KeyEvent.VK_W:
				break;
		default:
			break;
		}
	}
	@Override
	public List<Piece> getProgrammingPieces() {
		throw new RuntimeException("Should not be asking player for pieces");
	}
}
