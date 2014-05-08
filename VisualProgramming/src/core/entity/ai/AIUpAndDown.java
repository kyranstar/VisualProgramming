package core.entity.ai;

import core.entity.AbstractEntity;
import core.math.Vec2D;

public class AIUpAndDown implements ArtificialIntelligence{
	int top;
	int bottom;
	public AIUpAndDown(int top, int bottom){
		this.top = top;
		this.bottom = bottom;
	}
	@Override
	public Vec2D getNextAIImpulse(AbstractEntity entity) {
		Vec2D velocity = new Vec2D(0,0);
		if(entity.getY() <= this.bottom)
			velocity.y = 1;
		if(entity.getY() >= this.top)
			velocity.y = -1;
		return velocity;
	}
}
