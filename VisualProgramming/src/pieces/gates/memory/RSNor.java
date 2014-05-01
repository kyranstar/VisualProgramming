package pieces.gates.memory;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import values.Value;
import values.ValueBoolean;

public class RSNor extends Piece{
	private static final long serialVersionUID = 7313371345155882797L;
	//v1 + v2 = v
	private ValueBoolean v;
	public RSNor(int x, int y) {
		super(x,y,150,75, 2, 1);
		v = new ValueBoolean(false);
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
		if(inputPort == 0){
			this.v = new ValueBoolean(true);
		}
		else
			this.v = new ValueBoolean(false);
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}

}
