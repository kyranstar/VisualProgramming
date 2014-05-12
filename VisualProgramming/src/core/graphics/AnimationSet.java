	package core.graphics;
	
	import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
	
	
	public class AnimationSet {

	    private Animation currentAnimation;
	    private final Map<String, Animation> animations = new HashMap<>();

	    public AnimationSet() {
	    }
	    public final void addAnimation(final String title, final Animation animation){
	        animations.put(title, animation);
	    }
	    public final void goToAnimation(final String title){
	        currentAnimation = animations.get(title);
	    }
	    public final void removeAnimation(final String title){
	        animations.remove(title);
	    }
	    public final void draw(final Graphics2D g, final double d, final double e){
	        g.drawImage(currentAnimation.getImage(), (int)d, (int)e, null);
	    }
	    public final void update(){
	        currentAnimation.update();
	    }
		public final double getCurrentWidth() {
			return currentAnimation.getImage().getWidth();
		}
		public final double getCurrentHeight() {
			return currentAnimation.getImage().getHeight();
		}        
	}
