package core.entity.neutral;

import java.awt.Color;
import java.awt.Graphics2D;

import core.entity.AbstractEntity;
import core.math.Vec2D;
import core.object.map.GameMap;

public class BlockEntity extends AbstractEntity{

	public BlockEntity(int x, int y, double width, double height, GameMap map) {
		super(map);
		this.setRect(x, y, width, height);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)this.getX(), (int) this.getY(), (int)this.getSize().x, (int) this.getSize().y);
	}

	@Override
	public void update() {
		this.setSize(this.getSize().add(new Vec2D(0.1, 0.1)));
		this.setPosition(this.getNextPosition());
	}

	@Override
	public void applyImpulse(Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

}
