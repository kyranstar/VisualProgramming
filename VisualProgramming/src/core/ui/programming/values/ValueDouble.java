package core.ui.programming.values;

public class ValueDouble implements Value{
	private static final long serialVersionUID = 1926165270679508796L;
	
	public final Double val;
	public ValueDouble(final double i){
		this.val = i;
	}
	public final String toString(){
		return val.toString();
	}
	public final double add(final ValueDouble v2) {
		return val + v2.val;
	}
	public final double subtract(final ValueDouble v2) {
		return val - v2.val;
	}
	public final double divide(final ValueDouble v2) {
		if(v2.val == 0) {
			return 0;
		}
		return val / v2.val;
	}
	public final double multiply(final ValueDouble v2) {
		return val * v2.val;
	}
	public final boolean lessThan(final ValueDouble v2) {
		return val < v2.val;
	}
	public final boolean greaterThan(final ValueDouble v2) {
		return val > v2.val;
	}
}
