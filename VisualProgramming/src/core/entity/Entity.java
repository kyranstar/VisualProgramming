package core.entity;

import java.awt.Graphics2D;

public abstract class Entity {

	protected boolean isDead;
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	public boolean isDead() {
		return isDead;
	}

}
