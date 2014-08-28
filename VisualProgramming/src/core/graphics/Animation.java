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

    public Animation(final BufferedImage[] frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public final Animation setFrames(final BufferedImage[] frames) {
        this.frames = frames.clone();
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 1;
        numFrames = frames.length;
        return this;
    }

    public final Animation setDelay(final int delay) {
        this.delay = delay;
        return this;
    }

    public final Animation setFrame(final int frame) {
        currentFrame = frame;
        return this;
    }

    public final Animation setNumFrames(final int numFrames) {
        this.numFrames = numFrames;
        return this;
    }

    public final void update() {
        if (delay <= 0) return;

        count++;

        if (count == delay) {
            currentFrame++;
            count = 0;
        }
        if (currentFrame == numFrames) {
            currentFrame = 0;
            timesPlayed++;
        }

    }

    public final int getFrame() {
        return currentFrame;
    }

    public final int getCount() {
        return count;
    }

    public final BufferedImage getImage() {
        return frames[currentFrame];
    }

    public final boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public final boolean hasPlayed(final int i) {
        return timesPlayed == i;
    }

    public final Animation getAnimationFlippedOnY() {
        final BufferedImage[] frames = new BufferedImage[this.frames.length];
        for (int i = 0; i < this.frames.length; i++) {
            final AffineTransform tx = AffineTransform.getScaleInstance(1.0, -1.0); // scaling
            tx.translate(0, -this.frames[i].getHeight()); // translating
            final AffineTransformOp tr = new AffineTransformOp(tx, null); // transforming
            frames[i] = tr.filter(this.frames[i], null);
        }
        final Animation a = new Animation();
        a.setFrames(frames);
        a.setDelay(delay);
        return a;
    }

    public final Animation getAnimationFlippedOnX() {
        final BufferedImage[] frames = new BufferedImage[this.frames.length];
        for (int i = 0; i < this.frames.length; i++) {
            final AffineTransform tx = AffineTransform.getScaleInstance(-1.0, 1.0); // scaling
            tx.translate(-this.frames[i].getWidth(), 0); // translating
            final AffineTransformOp tr = new AffineTransformOp(tx, null); // transforming
            frames[i] = tr.filter(this.frames[i], null);
        }
        final Animation a = new Animation();
        a.setFrames(frames);
        a.setDelay(delay);
        return a;
    }

    public final BufferedImage[] getFrames() {
        return frames.clone();
    }

}
