package core.ui.programming.pieces.gates.logical;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;

public class LogicalNot extends Piece{
	private static final long serialVersionUID = 2587447950396803241L;
	private ValueBoolean output;
	public LogicalNot(int x, int y) {
		super(x,y,150,75, 1, 1);
		output = new ValueBoolean(true);
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		if(output != null)
			g.drawString(output.toString(), x + 10, y + 20);
	}

	@Override
	public Value send(int outputPort) {
		return output;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		if(v instanceof ValueBoolean)
			this.output = new ValueBoolean(!((ValueBoolean)v).val);
	}

	@Override
	public void doubleClicked() {
		
	}
}
