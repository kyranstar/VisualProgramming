package core.graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
	
	public Animation(BufferedImage[] frames) {
		timesPlayed = 0;
		setFrames(frames);
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
	
	public Animation getAnimationFlippedOnY(){
		BufferedImage[] frames = new BufferedImage[this.frames.length];
		for(int i = 0; i < this.frames.length; i++){
			AffineTransform tx=AffineTransform.getScaleInstance(1.0,-1.0);  //scaling
		    tx.translate(0,-this.frames[i].getHeight());  //translating
		    AffineTransformOp tr=new AffineTransformOp(tx,null);  //transforming
			frames[i] = tr.filter(this.frames[i], null);
		}
		Animation a = new Animation();
		a.setFrames(frames);
		a.setDelay(this.delay);
		return a;
	}
	public Animation getAnimationFlippedOnX(){
		BufferedImage[] frames = new BufferedImage[this.frames.length];
		for(int i = 0; i < this.frames.length; i++){
			AffineTransform tx=AffineTransform.getScaleInstance(-1.0,1.0);  //scaling
			tx.translate(-this.frames[i].getWidth(),0);  //translating
			AffineTransformOp tr=new AffineTransformOp(tx,null);  //transforming
			frames[i] = tr.filter(this.frames[i], null);
		}
		Animation a = new Animation();
		a.setFrames(frames);
		a.setDelay(this.delay);
		return a;
	}

	public BufferedImage[] getFrames() {
		return this.frames;
	}
	
}