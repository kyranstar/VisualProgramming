package core.ui.programming.pieces.gates.logical;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Title;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;

public class LogicalOr extends Piece{
	private static final long serialVersionUID = -530611072578480391L;
	private ValueBoolean input1;
	private ValueBoolean input2;
	private ValueBoolean output;
	public LogicalOr(int x, int y) {
		super(x,y,150,75, 2, 1);
		title = new Title("Logical Or", this);
		input1 = new ValueBoolean(false);
		input2 = new ValueBoolean(false);
		output = new ValueBoolean(false);
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
		if(!(v instanceof ValueBoolean))
			return;
		if(inputPort == 0)
			input1 = (ValueBoolean) v;
		else
			input2 = (ValueBoolean) v;
		
		output = new ValueBoolean(input1.val || input2.val);
	}

	@Override
	public void doubleClicked() {
		
	}
}
