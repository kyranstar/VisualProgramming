package core.ui.programming.pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueInteger;


public class ConstantNumber extends Piece{
	private static final long serialVersionUID = 4696555063901811023L;
	private ValueInteger output;
	public ConstantNumber(final int x, final int y){
		super(x,y,150,75, 0, 5);
		this.output = new ValueInteger(0);
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
		
		output = new ValueInteger(Integer.parseInt(result));
		update();
	}
}
