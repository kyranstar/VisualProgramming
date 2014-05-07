package core.entity;

import core.math.Vec2D;

public class MoveData {
	public CollisionBox collisionBox;
	public Vec2D velocity;
	public MoveData(){
		this.collisionBox = new CollisionBox(0,0,0,0);
		this.velocity = new Vec2D(0,0);
	}
}
