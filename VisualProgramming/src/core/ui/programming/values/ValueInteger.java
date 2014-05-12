package core.ui.programming.values;

public class ValueInteger implements Value{
	private static final long serialVersionUID = 1926165270679508796L;
	
	public final Integer val;
	public ValueInteger(final int i){
		this.val = i;
	}
	public final String toString(){
		return val.toString();
	}
	public final int add(final ValueInteger v2) {
		return val + v2.val;
	}
	public final int subtract(final ValueInteger v2) {
		return val - v2.val;
	}
	public final int divide(final ValueInteger v2) {
		if(v2.val == 0) {
			return 0;
		}
		return val / v2.val;
	}
	public final int multiply(final ValueInteger v2) {
		return val * v2.val;
	}
	public final boolean lessThan(final ValueInteger v2) {
		return val < v2.val;
	}
	public final boolean greaterThan(final ValueInteger v2) {
		return val > v2.val;
	}
}
