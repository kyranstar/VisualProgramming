package core.ui.programming.pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;


public class ConstantBoolean extends Piece{
	private static final long serialVersionUID = 8843262932473240600L;
	
	private ValueBoolean output;
	public ConstantBoolean(int x, int y){
		super(x,y,150,75, 0, 5);
		this.output = new ValueBoolean(false);
	}

	@Override
	public Value send(int outputPort) {
		return output;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		
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
	public void doubleClicked() {
		String s = JOptionPane.showInputDialog("Value: ");
		if(s == null)
			return;
		
		output = new ValueBoolean(Boolean.parseBoolean(s));
		update();
	}
}
