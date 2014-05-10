package core.ui.programming.pieces.gates.arithmetic;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueInteger;

public class Modulo extends Piece{
	private static final long serialVersionUID = -4313345156578419935L;
	//v1 % v2 = v
	private ValueInteger v1;
	private ValueInteger v2;
	private ValueInteger v;
	public Modulo(int x, int y) {
		super(x,y,150,75, 2, 1);
		v1 = new ValueInteger(0);
		v = new ValueInteger(0);
		v2 = new ValueInteger(0);
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
	public Value send(int outputPort) {
		return v;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		if(!(v instanceof ValueInteger))
			return;
		
		if(inputPort == 0)
			v1 = (ValueInteger) v;
		else if (inputPort == 1)
			v2 = (ValueInteger) v;
		this.v = new ValueInteger(v1.val % v2.val);
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}

}
