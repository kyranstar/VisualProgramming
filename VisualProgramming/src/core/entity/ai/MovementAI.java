package core.entity.ai;

import core.entity.AbstractEntity;
import core.math.Vec2D;

public interface MovementAI extends ArtificialIntelligence{
	Vec2D getNextImpulse(AbstractEntity entity);
}
