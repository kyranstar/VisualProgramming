package core.ui.programming.pieces.gates.comparison;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;
import core.ui.programming.values.ValueInteger;

public class Equals extends Piece{
	private static final long serialVersionUID = -297758686217039922L;
	private ValueInteger input1;
	private ValueInteger input2;
	private ValueBoolean output;
	public Equals(final int x, final int y) {
		super(x,y,150,75, 2, 1);
		input1 = new ValueInteger(0);
		output = new ValueBoolean(false);
		input2 = new ValueInteger(0);
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
		if(!(v instanceof ValueInteger)) {
			return;
		}
		
		if(inputPort == 0) {
			input1 = (ValueInteger) v;
		} else if (inputPort == 1) {
			input2 = (ValueInteger) v;
		}
		this.output = new ValueBoolean(input1.val.intValue()  == input2.val.intValue());
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}
}
