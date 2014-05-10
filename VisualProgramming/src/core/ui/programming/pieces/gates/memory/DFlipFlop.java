package core.ui.programming.pieces.gates.memory;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;

public class DFlipFlop extends Piece{
	private static final long serialVersionUID = 7285876124318708615L;
	//v1 + v2 = v
	private boolean writeIsOn;
	private ValueBoolean output;
	public DFlipFlop(int x, int y) {
		super(x,y,150,75, 2, 1);
		writeIsOn = false;
		output = new ValueBoolean(false);
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
	public Value send(int outputPort) {
		return output;
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
		this.output = new ValueBoolean(!this.output.val);
	}

	@Override
	public void doubleClicked() {
	}
}
