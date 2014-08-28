package core.math;

import java.awt.Point;

public class Vec2D {
    public double x, y;

    public Vec2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(final Point ropePoint) {
        x = ropePoint.getX();
        y = ropePoint.getY();
    }

    public final Vec2D add(final Vec2D other) {
        return new Vec2D(x + other.x, y + other.y);
    }

    public final Vec2D subtract(final Vec2D other) {
        return new Vec2D(x - other.x, y - other.y);
    }

    public final Vec2D multiply(final Vec2D other) {
        return new Vec2D(x * other.x, y * other.y);
    }

    public final Vec2D multiply(final double constant) {
        return new Vec2D(x * constant, y * constant);
    }

    public final Vec2D divide(final Vec2D other) {
        return new Vec2D(x / other.x, y / other.y);
    }

    public final Vec2D divide(final double constant) {
        return new Vec2D(x / constant, y / constant);
    }

    public final double dotProduct(final Vec2D other) {
        return x * other.x + y * other.y;
    }

    public final double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public final double magnitudeSquared() {
        return dotProduct(this);
    }

    @Override
    public final String toString() {
        return "(" + x + ", " + y + ")";
    }

    public final Object copy() {
        return new Vec2D(x, y);
    }

    public final Vec2D unit() {
        final Vec2D vec = new Vec2D(x, y);
        final double length = magnitude();
        if (length != 0) {
            vec.x /= length;
            vec.y /= length;
        } else {
            vec.x = vec.y = 0;
        }
        return vec;
    }

    public final double getAngle(final Vec2D v) {
        Vec2D norm1 = ((Vec2D) copy()).unit();
        Vec2D norm2 = ((Vec2D) v.copy()).unit();

        norm1 = norm1.unit();
        norm2 = norm2.unit();

        return Math.acos(norm1.dotProduct(norm2));
    }

    public final Vec2D turnLeft() {
        return new Vec2D(-y, x);
    }

    public final Vec2D turnRight() {
        return new Vec2D(y, -x);
    }

    @Override
    public final int hashCode() {
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
    public final boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Vec2D other = (Vec2D) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
        return true;
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

}
