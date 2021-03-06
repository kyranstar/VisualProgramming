package core.entity.ai;

import core.entity.AbstractEntity;
import core.math.Vec2D;

public class AIUpAndDown implements MovementAI{
	
	int top;
	int bottom;
	double speed;
	public AIUpAndDown(final int top, final int bottom, final double speed){
		this.top = top;
		this.bottom = bottom;
		this.speed = speed;
	}
	@Override
	public final Vec2D getNextImpulse(final AbstractEntity entity) {
		Vec2D velocity = new Vec2D(0,0);
		
		if(entity.getY() <= this.top){
			velocity.y = speed;
		}
		if(entity.getY() >= this.bottom){
			velocity.y = -speed;
		}
		return velocity;
	}
}
