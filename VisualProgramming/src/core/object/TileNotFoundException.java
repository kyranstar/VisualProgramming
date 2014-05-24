package core.object;


public class TileNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -710367481963659500L;
	public TileNotFoundException(){
		super();
	}
	public TileNotFoundException(final String message){
		super(message);
	}
	
	public TileNotFoundException(final String message, final Exception exception) {
		super(message, exception);
	}
}