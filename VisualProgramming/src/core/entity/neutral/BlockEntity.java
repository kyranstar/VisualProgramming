package core.entity.neutral;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.entity.ai.AIUpAndDown;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.programming.piece.Piece;
import core.ui.programming.pieces.gates.entity.ScaleSize;

public final class BlockEntity extends NeutralEntity{

	private AIUpAndDown artificialIntelligence;
	private List<Class<? extends Piece>> pieces;
	public BlockEntity(final int x, final int y, final double width, final double height, final GameMap map) {
		super(map);
		this.setRect(x, y, width, height);
		artificialIntelligence = new AIUpAndDown(y, y + 64, 5);
		this.affectedByGravity = false;
		this.setRestitution(0.1f);
		this.pieces = new ArrayList<Class<? extends Piece>>();
		addAllPieces();
	}
	public void addAllPieces(){
		pieces.add(ScaleSize.class);
	}
	@Override
	public final void draw(final Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)this.getX(), (int) this.getY(), (int)this.getSize().x, (int) this.getSize().y);
		this.drawCollisionBox(g);
	}

	@Override
	public final void update() {
		this.setPosition(this.getNextPosition());
		this.applyImpulse(this.artificialIntelligence.getNextImpulse(this));
	}

	@Override
	public final void applyImpulse(final Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

	@Override
	public List<Class<? extends Piece>> getProgrammingPieces() {
		return pieces;
	}

}
