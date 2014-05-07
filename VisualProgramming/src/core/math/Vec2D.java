package core.math;

public class Vec2D {
	public double x, y;
	public Vec2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vec2D(){
		this.x = 0;
		this.y = 0;
	}
	public Vec2D add(Vec2D other){
		return new Vec2D(x + other.x, y + other.y);
	}
	public Vec2D subtract(Vec2D other){
		return new Vec2D(x - other.x, y - other.y);
	}
	public Vec2D multiply(Vec2D other){
		return new Vec2D(x * other.x, y * other.y);
	}
	public Vec2D multiply(double constant) {
		return new Vec2D(x * constant, y * constant);
	}
	public Vec2D divide(Vec2D other){
		return new Vec2D(x / other.x, y / other.y);
	}
	public Vec2D divide(double constant) {
		return new Vec2D(x / constant, y / constant);
	}
	public double dotProduct(Vec2D other){
        return this.x * other.x + this.y * other.y;
	} 
	public double magnitude() {
        return Math.sqrt(this.magnitudeSquared());
    }
	public double magnitudeSquared(){
		return this.dotProduct(this);
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
    }
	public Object copy(){
		return new Vec2D(x,y);		
	}
	public Vec2D unit(){
		Vec2D vec = new Vec2D(this.x, this.y);
	    double length = magnitude();
	     vec.x /= length;
	    vec.y /= length;
	    return vec;
	}
	public double getAngle(Vec2D v){
	        Vec2D norm1 = ((Vec2D) copy()).unit();
	        Vec2D norm2 = ((Vec2D) v.copy()).unit();

	        norm1 = norm1.unit();
	        norm2 = norm2.unit();

	        return Math.acos(norm1.dotProduct(norm2));
	    }
	 public Vec2D turnLeft(){
		 return new Vec2D(-this.y, this.x);
	 }
	 public Vec2D turnRight(){
		 return new Vec2D(this.y, -this.x);
	 }
	public Vec2D direction() {
        if (this.magnitude() == 0.0)
        	return new Vec2D(0,0);
        return this.multiply(1.0 / this.magnitude());
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec2D other = (Vec2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	
}
