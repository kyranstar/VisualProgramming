	package core.graphics;
	
	import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
	
	
	public class AnimationSet {

	    private Animation currentAnimation;
	    private final Map<String, Animation> animations;

	    public AnimationSet() {
	        animations = new HashMap<>();
	    }
	    public void addAnimation(String title, Animation animation){
	        animations.put(title, animation);
	    }
	    public void goToAnimation(String title){
	        currentAnimation = animations.get(title);
	    }
	    public void removeAnimation(String title){
	        animations.remove(title);
	    }
	    public void draw(Graphics2D g, double d, double e){
	        g.drawImage(currentAnimation.getImage(), (int)d, (int)e, null);
	    }
	    public void update(){
	        currentAnimation.update();
	    }        
	}
