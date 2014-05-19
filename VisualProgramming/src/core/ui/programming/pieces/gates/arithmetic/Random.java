package core.ui.programming.pieces.gates.arithmetic;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;
import core.ui.programming.values.ValueDouble;

public class Random extends Piece{
	private static final long serialVersionUID = -8074729307443999281L;

	private static java.util.Random rand = new java.util.Random();
	
	private ValueDouble max;
	private ValueDouble[] last;
	public Random(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 2, 5, space);
		max = new ValueDouble(0);
		last = new ValueDouble[5];
		for(int i = 0; i < last.length; i++) {
			last[i] = new ValueDouble(0);
		}
	}

	@Override
	public final void draw(final Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		g.drawString("update", input.getPointFromPort(0).x + 15, input.getPointFromPort(0).y + 9);
		g.drawString("max", input.getPointFromPort(1).x + 15, input.getPointFromPort(1).y + 9);
	}

	@Override
	public final Value send(final int outputPort) {
		return last[outputPort];
	}

	@Override
	public final void recieve(final int inputPort, final Value v) {
		if(inputPort == 0){
			if(v instanceof ValueBoolean && ((ValueBoolean)v).val == true){
				if(max.val != 0) {
					for(int i = 0; i < last.length; i++)
						last[i] = new ValueDouble(rand.nextDouble() * max.val);
				}
				update();
			}
		}
		else if (inputPort == 1){
			if(!(v instanceof ValueDouble)) {
				return;
			}
			
			this.max = (ValueDouble) v;
		}
	}

	@Override
	public final void doubleClicked() {
		update();
	}

}
