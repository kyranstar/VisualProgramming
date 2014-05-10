package core.ui.programming.values;

public class ValueInteger implements Value{
	private static final long serialVersionUID = 1926165270679508796L;
	
	public final Integer val;
	public ValueInteger(int i){
		this.val = i;
	}
	public String toString(){
		return val.toString();
	}
	public int add(ValueInteger v2) {
		return val + v2.val;
	}
	public int subtract(ValueInteger v2) {
		return val - v2.val;
	}
	public int divide(ValueInteger v2) {
		if(v2.val == 0)
			return 0;
		return val / v2.val;
	}
	public int multiply(ValueInteger v2) {
		return val * v2.val;
	}
	public boolean lessThan(ValueInteger v2) {
		return val < v2.val;
	}
	public boolean greaterThan(ValueInteger v2) {
		return val > v2.val;
	}
}
