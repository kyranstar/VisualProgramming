package core.math;

import java.util.Random;

public class MathUtils {
	private static final Random RAND = new Random();
	public static int getRandomInt(int min, int max){
		return RAND.nextInt(max - min) + min;
	}
	public static float getRandomFloat(int min, int max){
		return RAND.nextFloat() * (max - min) + min;
	}
}
