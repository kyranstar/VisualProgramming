package core.ui.programming.pieces.gates.memory;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;

public class TFlipFlop extends Piece{
	private static final long serialVersionUID = 7336110108397567541L;
	//v1 + v2 = v
	private ValueBoolean output;
	public TFlipFlop(final int x, final int y) {
		super(x,y,150,75, 1, 1);
		output = new ValueBoolean(false);
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
		if(v instanceof ValueBoolean) {
			if(((ValueBoolean)v).val == true) {
				flip();
			}
		}
	}
	private void flip(){
		this.output = new ValueBoolean(!this.output.val);
	}

	@Override
	public final void doubleClicked() {
		flip();
	}
}
