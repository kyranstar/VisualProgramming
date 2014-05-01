package pieces.gates.arithmetic;

import java.awt.Color;
import java.awt.Graphics2D;

import piece.Piece;
import values.Value;
import values.ValueBoolean;
import values.ValueInteger;

public class Random extends Piece{
	private static final long serialVersionUID = -8074729307443999281L;

	private static java.util.Random rand = new java.util.Random();
	
	private ValueInteger max;
	private ValueInteger[] last;
	public Random(int x, int y) {
		super(x,y,150,75, 2, 5);
		max = new ValueInteger(0);
		last = new ValueInteger[5];
		for(int i = 0; i < last.length; i++)
			last[i] = new ValueInteger(0);
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		g.drawString("update", input.getPointFromPort(0).x + 15, input.getPointFromPort(0).y + 9);
		g.drawString("max", input.getPointFromPort(1).x + 15, input.getPointFromPort(1).y + 9);
	}

	@Override
	public Value send(int outputPort) {
		return last[outputPort];
	}

	@Override
	public void recieve(int inputPort, Value v) {
		if(inputPort == 0){
			if(v instanceof ValueBoolean && ((ValueBoolean)v).val == true){
				if(max.val != 0)
					for(int i = 0; i < last.length; i++)
						last[i] = new ValueInteger(rand.nextInt(max.val));
				update();
			}
		}
		else
			this.max = (ValueInteger) v;
	}

	@Override
	public void doubleClicked() {
		update();
	}

}
