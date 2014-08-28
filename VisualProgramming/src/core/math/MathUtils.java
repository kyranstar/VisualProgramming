package core.math;

import java.util.Random;

public final class MathUtils {

    private MathUtils() {
    }

    private static final float EPSILON = 0.000001f;
    private static final Random RAND = new Random();

    public static int getRandomInt(final int min, final int max) {
        return RAND.nextInt(max - min) + min;
    }

    public static float getRandomFloat(final int min, final int max) {
        return RAND.nextFloat() * (max - min) + min;
    }

    public static int round(final double number, final int multiple) {
        return (int) (Math.round(number / multiple) * multiple);
    }

    public static boolean floatEquals(final float first, final float second) {
        return Math.abs(first - second) < EPSILON;
    }

    public static double floor(final double number, final int multiple) {
        return (int) (Math.floor(number / multiple) * multiple);
    }

    public static double ceil(final double number, final int multiple) {
        return (int) (Math.ceil(number / multiple) * multiple);
    }
}
