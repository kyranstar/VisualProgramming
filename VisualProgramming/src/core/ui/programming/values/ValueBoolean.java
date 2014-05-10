package core.ui.programming.values;

public class ValueBoolean implements Value{
	private static final long serialVersionUID = -7417740274849279829L;
	
	public final Boolean val;
	public ValueBoolean(boolean v){
		this.val = v;
	}
	public String toString(){
		return val.toString();
	}
}
