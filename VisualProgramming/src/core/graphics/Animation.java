package core.graphics;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	private int numFrames;
	
	private int count;
	private int delay;
	
	private int timesPlayed;
	
	public Animation() {
		timesPlayed = 0;
	}
	
	public Animation setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		delay = 1;
		numFrames = frames.length;
		return this;
	}
	
	public Animation setDelay(int i) { delay = i; return this;}
	public Animation setFrame(int i) { currentFrame = i; return this;}
	public Animation setNumFrames(int i) { numFrames = i; return this;}
	
	public void update() {
		if(delay <= 0) return;
		
		count++;
		
		if(count == delay) {
			currentFrame++;
			count = 0;
		}
		if(currentFrame == numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public int getCount() { return count; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
	
}