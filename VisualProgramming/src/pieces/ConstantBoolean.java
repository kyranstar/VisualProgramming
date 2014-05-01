package pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import piece.Piece;
import values.Value;
import values.ValueBoolean;


public class ConstantBoolean extends Piece{
	private static final long serialVersionUID = 8843262932473240600L;
	
	private ValueBoolean v;
	public ConstantBoolean(int x, int y){
		super(x,y,150,75, 0, 5);
		this.v = new ValueBoolean(false);
	}

	@Override
	public Value send(int outputPort) {
		return v;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		if(v != null)
			g.drawString(v.toString(), x + 10, y + 20);
	}

	@Override
	public void doubleClicked() {
		String s = JOptionPane.showInputDialog("Value: ");
		if(s == null)
			return;
		
		v = new ValueBoolean(Boolean.parseBoolean(s));
		update();
	}
}
