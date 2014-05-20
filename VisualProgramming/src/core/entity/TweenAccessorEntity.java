package core.entity;

import aurelienribon.tweenengine.TweenAccessor;
import core.math.Vec2D;

public class TweenAccessorEntity implements TweenAccessor<AbstractEntity>{

	public static final int SIZE_XY = 0;
	public static final int SIZE_X = 1;
	public static final int SIZE_Y = 2;
    public static final int POSITION_XY = 3;

    // TweenAccessor implementation

    @Override
    public int getValues(AbstractEntity target, int tweenType, float[] returnValues) {
        switch (tweenType) {
        	case SIZE_Y: returnValues[0] = (float)target.getSize().y; return 1;
        	case SIZE_X: returnValues[0] = (float)target.getSize().x; return 1;
        	case SIZE_XY:
                returnValues[0] = (float) target.getSize().x;
                returnValues[1] = (float) target.getSize().y;
                return 2;
            case POSITION_XY:
                returnValues[0] = (float) target.getX();
                returnValues[1] = (float) target.getY();
                return 2;
            default: assert false; return -1;
        }
    }
    
    @Override
    public void setValues(AbstractEntity target, int tweenType, float[] newValues) {
        switch (tweenType) {
        	case SIZE_X:
        			target.setSize(new Vec2D(newValues[0], target.getSize().y));
        		break;
        	case SIZE_Y:
        			target.setSize(new Vec2D(target.getSize().x, newValues[0]));
        		break;
        	case SIZE_XY:
        		target.setSize(new Vec2D(newValues[0], newValues[1]));
            break;
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            default: assert false; break;
        }
    }

}
