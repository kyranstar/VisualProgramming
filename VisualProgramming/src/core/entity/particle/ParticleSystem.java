package core.entity.particle;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.entity.AbstractEntity;
import core.math.Vec2D;
import core.object.map.GameMap;

public abstract class ParticleSystem extends AbstractEntity{
	protected final List<Particle> particles;
	
	public ParticleSystem(GameMap map){
		super(map);
		particles = new LinkedList<Particle>();
	}
	protected void addParticle(Particle p){
		particles.add(p);
	}
	public void update(){
		Iterator<Particle> it = particles.iterator();
		while(it.hasNext()){
			Particle p = it.next();
			p.update();
			
			if(p.isDead()){
				it.remove();
			}
		}
	}
	public void draw(Graphics2D g){
		for(Particle p : particles){
			p.draw(g);
		}
	}
	public void applyImpulse(Vec2D ambientForce) {
		for(Particle p : particles){
			p.applyImpulse(ambientForce);
		}
	}
}
