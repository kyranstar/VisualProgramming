package core.ui.programming.pieces.gates.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueDouble;

public class ScaleSize extends Piece{

	private static final long serialVersionUID = 3531182461040466558L;

	public ScaleSize(final int x, final int y, final ProgrammingSpaceInterface space) {
		super(x,y,150,75, 1, 0, space);
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
	}

	@Override
	public Value send(int outputPort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recieve(int inputPort, Value v) {
		if(v instanceof ValueDouble){
			double val = ((ValueDouble) v).val;
			
			if(val == 0){
				return;
			}else if (val > 0){
				this.space.getEntity().setSize(space.getEntity().getSize().multiply(val));
			}else{
				this.space.getEntity().setSize(space.getEntity().getSize().multiply(1f / Math.abs(val)));
			}
		}
	}

	@Override
	public void doubleClicked() {
		// TODO Auto-generated method stub
		
	}

}
