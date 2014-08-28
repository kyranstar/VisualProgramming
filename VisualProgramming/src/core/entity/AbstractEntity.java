package core.entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import core.level.AbstractLevel;
import core.math.Vec2D;
import core.object.Tile;
import core.object.Tile.Attribute;
import core.object.map.GameMap;

public abstract class AbstractEntity {
    private final MoveData moveData;
    private boolean dead;
    protected AbstractLevel parentLevel;
    protected int framesSinceLastBottomCollision;
    protected int framesSinceLastTopCollision;
    protected int framesSinceLastLeftCollision;
    protected int framesSinceLastRightCollision;

    /*
     * This boolean tells the collision checker whether this entity should check
     * collision with other entities. Basically, it means that this entity is
     * still.
     */
    private boolean collisionAsleep;
    private boolean velocityWasZeroLastFrame;

    private boolean affectedByGravity;
    private final TweenManager tweenManager;

    static {
        Tween.registerAccessor(AbstractEntity.class, new TweenAccessorEntity());
    }

    public AbstractEntity(final AbstractLevel level) {
        parentLevel = level;
        moveData = new MoveData();
        dead = false;
        setAffectedByGravity(false);
        tweenManager = new TweenManager();
    }

    public abstract void draw(Graphics2D g);

    public abstract void update();

    public final TweenManager getTweenManager() {
        return tweenManager;
    }

    protected void checkAsleep() {
        final double ASLEEP_VEL = 0.00001;

        if (getVelocity().x < ASLEEP_VEL && getVelocity().x > -ASLEEP_VEL && getVelocity().y > -ASLEEP_VEL
                && getVelocity().y < ASLEEP_VEL) {
            if (velocityWasZeroLastFrame) {
                setCollisionAsleep(true);

                getVelocity().x = 0;
                getVelocity().y = 0;
            } else {
                velocityWasZeroLastFrame = true;
            }
        } else {
            velocityWasZeroLastFrame = false;
            setCollisionAsleep(false);
        }
    }

    public boolean isOffScreen() {
        return !parentLevel.getMapViewport().intersects(getRect2D());
    }

    public boolean isCollisionAsleep() {
        return collisionAsleep;
    }

    public void setCollisionAsleep(final boolean collisionAsleep) {
        this.collisionAsleep = collisionAsleep;
    }

    public final boolean isDead() {
        return dead;
    }

    public final void kill() {
        dead = true;
    }

    public void applyForce(final Vec2D accel) {
        setVelocity(getVelocity().add(accel));
    }

    public final double getX() {
        return moveData.collisionBox.getUpperLeft().x;
    }

    public final double getY() {
        return moveData.collisionBox.getUpperLeft().y;
    }

    public final Vec2D getPosition() {
        return moveData.collisionBox.getUpperLeft();
    }

    public final Vec2D getSize() {
        return new Vec2D(moveData.collisionBox.getHeight(), moveData.collisionBox.getWidth());
    }

    public final void setSize(final Vec2D size) {
        moveData.collisionBox.setSize(size);
    }

    protected final void setRect(final double x, final double y, final double width, final double height) {
        moveData.collisionBox = new CollisionBox(x, y, width, height);
    }

    public final void setPosition(final Vec2D position) {
        moveData.collisionBox.setPosition(position);
    }

    public final void setCenter(final Vec2D position) {
        moveData.collisionBox.setCenter(position);
    }

    public final Vec2D getVelocity() {
        return moveData.velocity;
    }

    public final void setVelocity(final Vec2D velocity) {
        moveData.velocity = velocity;
    }

    public boolean isColliding(final AbstractEntity other) {
        return moveData.collisionBox.isColliding(other.moveData.collisionBox);
    }

    public boolean isColliding(final Line2D other) {
        return moveData.collisionBox.isColliding(other);
    }

    public final Vec2D getCenter() {
        return moveData.collisionBox.getCenter();
    }

    public final Rectangle2D getRect2D() {
        return moveData.collisionBox.getRect2D();
    }

    public final Corners getCornersAreSolid(final double x, final double y) {
        final int leftTile = (int) (x / Tile.SIZE);
        final int rightTile = (int) ((x + moveData.collisionBox.getWidth()) / Tile.SIZE);
        final int topTile = (int) (y / Tile.SIZE);
        final int bottomTile = (int) ((y + moveData.collisionBox.getHeight()) / Tile.SIZE);

        final boolean topLeft = hasAttribute(parentLevel.getMap(), Attribute.SOLID, topTile, leftTile);
        final boolean topRight = hasAttribute(parentLevel.getMap(), Attribute.SOLID, topTile, rightTile);
        final boolean bottomLeft = hasAttribute(parentLevel.getMap(), Attribute.SOLID, bottomTile, leftTile);
        final boolean bottomRight = hasAttribute(parentLevel.getMap(), Attribute.SOLID, bottomTile, rightTile);

        final Corners solidCorners = new Corners();
        solidCorners.topLeft = topLeft;
        solidCorners.topRight = topRight;
        solidCorners.bottomRight = bottomRight;
        solidCorners.bottomLeft = bottomLeft;
        return solidCorners;
    }

    private boolean hasAttribute(final GameMap map, final Attribute attribute, final int tileY, final int tileX) {
        boolean result = false;

        if (tileX >= 0 && tileX < map.getWidthInTiles() && tileY >= 0 && tileY < map.getHeightInTiles()) {
            result = map.getTileAt(tileX, tileY).getAttribute(attribute);
        }
        return result;
    }

    public final Vec2D getNextPosition() {

        final int currCol = (int) (getX() / Tile.SIZE);
        final int currRow = (int) (getY() / Tile.SIZE);

        final double destX = getX() + moveData.velocity.x;
        final double destY = getY() + moveData.velocity.y;

        double tempX = getX();
        double tempY = getY();

        Corners solidCorners = getCornersAreSolid(getX(), destY);
        boolean topLeft = solidCorners.topLeft;
        boolean topRight = solidCorners.topRight;
        boolean bottomLeft = solidCorners.bottomLeft;
        boolean bottomRight = solidCorners.bottomRight;

        framesSinceLastTopCollision += 1;
        framesSinceLastBottomCollision += 1;
        framesSinceLastLeftCollision += 1;
        framesSinceLastRightCollision += 1;
        if (moveData.velocity.y < 0) {
            if (topLeft || topRight) {
                moveData.velocity.y = 0;
                tempY = currRow * Tile.SIZE;
                framesSinceLastTopCollision = 0;
            } else {
                tempY += moveData.velocity.y;
            }
        } else if (moveData.velocity.y > 0) {
            if (bottomLeft || bottomRight) {
                moveData.velocity.y = 0;
                tempY = (currRow + 1) * Tile.SIZE - moveData.collisionBox.getHeight() % Tile.SIZE - 1;
                framesSinceLastBottomCollision = 0;
            } else {
                tempY += moveData.velocity.y;
            }
        }

        solidCorners = getCornersAreSolid(destX, getY());
        topLeft = solidCorners.topLeft;
        topRight = solidCorners.topRight;
        bottomLeft = solidCorners.bottomLeft;
        bottomRight = solidCorners.bottomRight;
        if (moveData.velocity.x < 0) {
            if (topLeft || bottomLeft) {
                moveData.velocity.x = 0;
                tempX = currCol * Tile.SIZE;
                framesSinceLastLeftCollision = 0;
            } else {
                tempX += moveData.velocity.x;
            }
        }
        if (moveData.velocity.x > 0) {
            if (topRight || bottomRight) {
                moveData.velocity.x = 0;
                tempX = (currCol + 1) * Tile.SIZE - moveData.collisionBox.getWidth() % Tile.SIZE - 1;
                framesSinceLastRightCollision = 0;
            } else {
                tempX += moveData.velocity.x;
            }
        }
        return new Vec2D(tempX, tempY);
    }

    public abstract void collisionWith(AbstractEntity other);

    private static class Corners {
        public boolean topLeft, topRight;
        public boolean bottomLeft, bottomRight;

        public Corners() {
            topLeft = false;
            topRight = false;
            bottomLeft = false;
            bottomRight = false;
        }
    }

    public final CollisionBox getCollisionBox() {
        return moveData.collisionBox;
    }

    public final boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    protected final void drawCollisionBox(final Graphics2D g) {
        moveData.collisionBox.draw(g);
    }

    public final void setX(final double x) {
        moveData.collisionBox.setX(x);
    }

    public final void setY(final double y) {
        moveData.collisionBox.setY(y);
    }

    public void setAffectedByGravity(final boolean affectedByGravity) {
        this.affectedByGravity = affectedByGravity;
    }

    public static enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
}
