package pieces.gates.memory;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import values.Value;
import values.ValueBoolean;

public class TFlipFlop extends Piece{
	private static final long serialVersionUID = 7336110108397567541L;
	//v1 + v2 = v
	private ValueBoolean v;
	public TFlipFlop(int x, int y) {
		super(x,y,150,75, 1, 1);
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
		if(v instanceof ValueBoolean)
			if(((ValueBoolean)v).val == true)
				flip();
	}
	private void flip(){
		this.v = new ValueBoolean(!this.v.val);
	}

	@Override
	public void doubleClicked() {
		flip();
	}
}
