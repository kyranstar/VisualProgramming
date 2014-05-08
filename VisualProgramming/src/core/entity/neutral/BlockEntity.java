package core.entity.neutral;

import java.awt.Color;
import java.awt.Graphics2D;

import core.entity.AbstractEntity;
import core.entity.ai.AIUpAndDown;
import core.math.Vec2D;
import core.object.map.GameMap;

public class BlockEntity extends AbstractEntity{

	AIUpAndDown artificialIntelligence;
	public BlockEntity(int x, int y, double width, double height, GameMap map) {
		super(map);
		this.setRect(x, y, width, height);
		artificialIntelligence = new AIUpAndDown(y + 100, y - 100);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)this.getX(), (int) this.getY(), (int)this.getSize().x, (int) this.getSize().y);
	}

	@Override
	public void update() {
		this.setPosition(this.getNextPosition());
		this.applyImpulse(this.artificialIntelligence.getNextAIImpulse(this));
	}

	@Override
	public void applyImpulse(Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

}
