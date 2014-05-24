package core.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.List;

import core.entity.AbstractEntity;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.ui.programming.piece.Piece;

public abstract class Particle extends AbstractEntity {
	private float life;
	private Shape shape;
	private Color color;
	private float size;
	private float opacity;
	
	public Particle(GameMap map, float size, float opacity, float life, Shape shape, Color color) {
		super(map);
		this.life = life;
		this.shape = shape;
		this.opacity = opacity;
		setColor(color);
		this.size = size;
		this.setSize(new Vec2D(shape.getBounds().width, shape.getBounds().height));
	}

	@Override
	public final void draw(Graphics2D g) {
		g.setColor(color);
		
		g.translate(getX(), getY());
		g.scale(size, size);
		g.fill(shape);
		g.scale(1/size, 1/size);
		g.translate(-getX(), -getY());
	}

	@Override
	public void update() {
		life--;
		if(life <= 0){
			this.isDead = true;
		}
		this.setPosition(this.getNextPosition());
	}
	protected final void setColor(Color col){
		this.color = new Color(((float)col.getRed()) / 255, ((float)col.getGreen()) / 255, ((float)col.getBlue()) / 255, opacity);
	}
	protected final void setOpacity(float op){
		this.opacity = op;
		this.color = new Color(((float)color.getRed()) / 255, ((float)color.getGreen()) / 255, ((float)color.getBlue()) / 255, opacity);
	}

	@Override
	public final void applyImpulse(Vec2D accel) {
		this.setVelocity(this.getVelocity().add(accel));
	}

	@Override
	public final List<Class<? extends Piece>> getProgrammingPieces() {
		return null;
	}

	public float getLife() {
		return life;
	}

}
