package core.ui.programming.pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueDouble;


public class ConstantNumber extends Piece{
	private static final long serialVersionUID = 4696555063901811023L;
	private ValueDouble output;
	public ConstantNumber(final int x, final int y, final ProgrammingSpaceInterface space){
		super(x,y,150,75, 0, 5, space);
		this.output = new ValueDouble(0);
	}

	@Override
	public final Value send(final int outputPort) {
		return output;
	}

	@Override
	public void recieve(final int inputPort, final Value v) {
		
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
	public final void doubleClicked() {
		String result = JOptionPane.showInputDialog("Value: ");
		if(result == null) {
			return;
		}
		
		output = new ValueDouble(Double.parseDouble(result));
		update();
	}
}
