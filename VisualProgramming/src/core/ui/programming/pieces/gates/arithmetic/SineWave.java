package core.ui.programming.pieces.gates.arithmetic;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Updatable;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueDouble;

public class SineWave extends Piece implements Updatable{
	
	ValueDouble val;
	int time = 0;
	int value = 0;

	public SineWave(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 0, 3, space);
		val = new ValueDouble(0);
	}

	@Override
	public final void draw(final Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
	}

	@Override
	public final Value send(final int outputPort) {
		return val;
	}

	@Override
	public final void recieve(final int inputPort, final Value v) {

	}

	@Override
	public final void doubleClicked() {
		update();
	}

	@Override
	public void updatePiece() {
		time++;
		if(time % 10 == 0){
			value++;
			if(value > 90)
				value = -90;

			val = new ValueDouble(Math.sin(Math.toRadians(value)));
			update();
		}
	}

}
