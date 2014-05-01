package pieces.gates.logical;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import piece.Title;
import values.Value;
import values.ValueBoolean;

public class LogicalOr extends Piece{
	private static final long serialVersionUID = -530611072578480391L;
	private ValueBoolean v1;
	private ValueBoolean v2;
	private ValueBoolean vOut;
	public LogicalOr(int x, int y) {
		super(x,y,150,75, 2, 1);
		title = new Title("Logical Or", this);
		v1 = new ValueBoolean(false);
		v2 = new ValueBoolean(false);
		vOut = new ValueBoolean(false);
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		if(vOut != null)
			g.drawString(vOut.toString(), x + 10, y + 20);
	}

	@Override
	public Value send(int outputPort) {
		return vOut;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		if(!(v instanceof ValueBoolean))
			return;
		if(inputPort == 0)
			v1 = (ValueBoolean) v;
		else
			v2 = (ValueBoolean) v;
		
		vOut = new ValueBoolean(v1.val || v2.val);
	}

	@Override
	public void doubleClicked() {
		
	}
}
