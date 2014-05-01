package pieces.gates.logical;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import values.Value;
import values.ValueBoolean;

public class LogicalNot extends Piece{
	private static final long serialVersionUID = 2587447950396803241L;
	private ValueBoolean vOut;
	public LogicalNot(int x, int y) {
		super(x,y,150,75, 1, 1);
		vOut = new ValueBoolean(true);
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
		if(v instanceof ValueBoolean)
			this.vOut = new ValueBoolean(!((ValueBoolean)v).val);
	}

	@Override
	public void doubleClicked() {
		
	}
}
