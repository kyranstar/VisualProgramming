package core.ui.programming.pieces.gates.comparison;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;
import core.ui.programming.values.ValueDouble;

public class GreaterThanOrEqual extends Piece{
	private static final long serialVersionUID = -6307575190171955440L;
	//v1 + v2 = v
	private ValueDouble v1;
	private ValueDouble v2;
	private ValueBoolean v;
	public GreaterThanOrEqual(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 2, 1, space);
		v1 = new ValueDouble(0);
		v = new ValueBoolean(false);
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
		this.v = new ValueBoolean(v1.val >= v2.val);
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}
}
