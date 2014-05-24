package core.entity.particle;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import core.graphics.imageutil.ColorUtils;
import core.math.MathUtils;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.programming.piece.Piece;

public class FireParticleSystem extends ParticleSystem{
	private static final int MIN_WAIT = 1;
	private static final int MAX_WAIT = 3;
	private int nextParticle;
	
	public FireParticleSystem(GameMap map) {
		super(map);
	}
	
	@Override
	public void update(){
		if(nextParticle <= 0){
			nextParticle = MathUtils.getRandomInt(MIN_WAIT, MAX_WAIT);
			float angle = (float) Math.toRadians(MathUtils.getRandomFloat(0, 360));
			float distance = MathUtils.getRandomFloat(0, 30);
			float x = (float) (this.getX() + (distance * Math.cos(angle)));
			float y = (float) (this.getY() + (distance * Math.sin(angle)));
			
			float size = MathUtils.getRandomFloat(10, 20);
			
			Particle p = new CircleParticle(map, x, y, size, size, MathUtils.getRandomInt(100, 255), ColorUtils.generateRandomColor(Color.RED));
			p.applyImpulse(new Vec2D(((float)MathUtils.getRandomFloat(-2, 2)) / 2, 0));
			this.addParticle(p);
		}
		nextParticle--;
		final Iterator<Particle> iterator = particles.iterator();
		while(iterator.hasNext()){
			Particle p = iterator.next();
			p.update();
			
			if(p.isDead()){
				iterator.remove();
			}
			p.setOpacity(p.getLife() / 255);
		}
	}
	@Override
	public void applyImpulse(Vec2D force){
		super.applyImpulse(force.multiply(-0.03));
	}

	@Override
	public List<Class<? extends Piece>> getProgrammingPieces() {
		return null;
	}
}
