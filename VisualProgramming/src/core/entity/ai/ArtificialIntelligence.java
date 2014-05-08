package core.entity.ai;

import core.entity.AbstractEntity;
import core.math.Vec2D;

public interface ArtificialIntelligence {
	public Vec2D getNextAIImpulse(AbstractEntity entity);
}
