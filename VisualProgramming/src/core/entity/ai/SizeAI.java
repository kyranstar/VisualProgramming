package core.entity.ai;

import core.entity.AbstractEntity;
import core.math.Vec2D;

public interface SizeAI extends ArtificialIntelligence{
	Vec2D getNextSize(AbstractEntity entity);
}
