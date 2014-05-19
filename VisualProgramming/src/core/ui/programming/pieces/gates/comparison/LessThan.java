package core.ui.programming.pieces.gates.comparison;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;
import core.ui.programming.values.ValueDouble;

public class LessThan extends Piece{
	private static final long serialVersionUID = -3523105030746146586L;
	//v1 + v2 = v
	private ValueDouble input1;
	private ValueDouble input2;
	private ValueBoolean output;
	public LessThan(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 2, 1, space);
		input1 = new ValueDouble(0);
		output = new ValueBoolean(false);
		input2 = new ValueDouble(0);
	}

	@Override
	public final void draw(final Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		if(output != null) {
			g.drawString(output.toString(), x + 10, y + 20);
		}
	}

	@Override
	public final Value send(final int outputPort) {
		return output;
	}

	@Override
	public final void recieve(final int inputPort, final Value v) {
		if(!(v instanceof ValueDouble)) {
			return;
		}
		
		if(inputPort == 0) {
			input1 = (ValueDouble) v;
		} else if (inputPort == 1) {
			input2 = (ValueDouble) v;
		}
		this.output = new ValueBoolean(input1.val < input2.val);
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}
}
