package core.entity.neutral;

import java.awt.Color;
import java.awt.Graphics2D;

import core.entity.ai.AIUpAndDown;
import core.entity.programmable.ProgrammingSurface;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.programming.pieces.gates.arithmetic.Add;

public class BlockEntity extends NeutralEntity{

	public ProgrammingSurface surface;
	private AIUpAndDown artificialIntelligence;
	public BlockEntity(final int x, final int y, final double width, final double height, final GameMap map) {
		super(map);
		this.setRect(x, y, width, height);
		artificialIntelligence = new AIUpAndDown(y, y + 64, 5);
		this.affectedByGravity = false;
		this.setRestitution(0.1f);
		
		surface = new ProgrammingSurface(){
			@Override
			public void addAllPieces(){
				this.pieces.add(new Add(0, 0));
			}
		};
		surface.reset();
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

}
