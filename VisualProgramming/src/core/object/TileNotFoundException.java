package core.object;

public class TileNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -710367481963659500L;
	public TileNotFoundException(){
		super();
	}
	public TileNotFoundException(final String s){
		super(s);
	}
	public TileNotFoundException(final String s, TileNotFoundException e){
		super(s, e);
	}
}