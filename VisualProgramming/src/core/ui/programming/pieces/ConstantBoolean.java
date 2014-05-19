package core.ui.programming.pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;


public class ConstantBoolean extends Piece{
	private static final long serialVersionUID = 8843262932473240600L;
	
	private ValueBoolean output;
	public ConstantBoolean(final int x, final int y, final ProgrammingSpaceInterface space){
		super(x,y,150,75, 0, 5, space);
		this.output = new ValueBoolean(false);
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
		String s = JOptionPane.showInputDialog("Value: ");
		if(s == null) {
			return;
		}
		
		output = new ValueBoolean(Boolean.parseBoolean(s));
		update();
	}
}
