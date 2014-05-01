package values;

public class ValueBoolean implements Value{
	public final Boolean val;
	public ValueBoolean(boolean v){
		this.val = v;
	}
	public String toString(){
		return val.toString();
	}
}
