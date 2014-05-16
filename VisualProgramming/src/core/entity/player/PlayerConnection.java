package core.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import core.entity.AbstractEntity;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.programming.piece.Piece;


public class PlayerConnection extends AbstractEntity{

	private static final int SIZE = 50;
	
	public PlayerConnection(final double d, final double e, GameMap map) {
		super(map);
		this.setRect(d, e, SIZE, SIZE);
		this.affectedByGravity = false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int)getY(), (int)getSize().x, (int) this.getSize().y);
		this.drawCollisionBox(g);
	}

	@Override
	public void update() {
		this.setPosition(this.getNextPosition());
	}

	@Override
	public void applyImpulse(Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

	@Override
	public List<Piece> getProgrammingPieces() {
		throw new RuntimeException("Should not be asking connector for pieces");
	}
}
