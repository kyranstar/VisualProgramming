package core.object.map;

public class PropertyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4060131633079251198L;
	public PropertyNotFoundException(){
		super();
	}
	public PropertyNotFoundException(String s){
		super(s	);
	}
	public PropertyNotFoundException(String s, Exception e){
		super(s, e);
	}
}
