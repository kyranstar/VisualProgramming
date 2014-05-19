package core.ui.programming.pieces.gates.arithmetic;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueDouble;

public class Subtract extends Piece{
	private static final long serialVersionUID = -33456651151976283L;
	
	//v1 - v2 = v
	private ValueDouble v1;
	private ValueDouble v2;
	private ValueDouble v;
	public Subtract(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 2, 1, space);
		v1 = new ValueDouble(0);
		v = new ValueDouble(0);
		v2 = new ValueDouble(0);
	}

	@Override
	public final void draw(final Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		if(v != null) {
			g.drawString(v.toString(), x + 10, y + 20);
		}
	}

	@Override
	public final Value send(final int outputPort) {
		return v;
	}

	@Override
	public final void recieve(final int inputPort, final Value v) {
		if(!(v instanceof ValueDouble)) {
			return;
		}
		
		if(inputPort == 0) {
			v1 = (ValueDouble) v;
		} else if (inputPort == 1) {
			v2 = (ValueDouble) v;
		}
		this.v = new ValueDouble(v1.subtract(v2));
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}
}
