package pieces.gates.memory;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import values.Value;
import values.ValueBoolean;

public class DFlipFlop extends Piece{
	private static final long serialVersionUID = 7285876124318708615L;
	//v1 + v2 = v
	private boolean writeIsOn;
	private ValueBoolean v;
	public DFlipFlop(int x, int y) {
		super(x,y,150,75, 2, 1);
		writeIsOn = false;
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
		if(!(v instanceof ValueBoolean))
			return;
		boolean val = ((ValueBoolean) v).val;
		if(inputPort == 0){
			writeIsOn = val;
		}else if (inputPort == 1 && writeIsOn && val == true){
			flip();
		}
	}
	private void flip(){
		this.v = new ValueBoolean(!this.v.val);
	}

	@Override
	public void doubleClicked() {
	}
}
