package core.ui.programming.pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import core.ui.programming.ProgrammingSpaceInterface;
import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Updatable;
import core.ui.programming.values.Value;
import core.ui.programming.values.ValueBoolean;


public class Timer extends Piece implements Updatable{
	private static final long serialVersionUID = 1791084491119694444L;
	private boolean running;
	private long last;
	private long current;
	private float segment;
	private boolean outputting;
	private boolean waiting;
	private static final int WAITING_SEGMENT = 100; //outputs true for 100 millis
	public Timer(final int x, final int y, final ProgrammingSpaceInterface space){
		super(x,y,150,75, 0, 1, space);
		this.current = 0;
		running = false;
	}

	
	
	@Override
	public final Value send(final int outputPort) {
		if(outputting) {
			return new ValueBoolean(true);
		} else {
			return new ValueBoolean(false);
		}
	}

	@Override
	public void recieve(final int inputPort, final Value v) {
		
	}

	@Override
	public final void draw(final Graphics2D g) {
		drawBackground(g);
		title.draw(g);
		g.setColor(Color.BLUE);
		g.drawString((segment - current) + "", x + 10, y + 20);
	}

	@Override
	public final void doubleClicked() {
		String s = JOptionPane.showInputDialog("Value in milliseconds: ");
		if(s == null) {
			return;
		}
		
		segment = Integer.parseInt(s);
		update();
		running = true;
	}

	@Override
	public final void updatePiece() {
		if(!running) {
			return;
		}
		if(waiting){
			long millis = System.currentTimeMillis();
			current += (millis - last);
			last = millis;
			if(current > WAITING_SEGMENT){
				current = 0;
				outputting = false;
				waiting = false;
				update();
			}
			return;
		}
		long millis = System.currentTimeMillis();
		current += (millis - last);
		last = millis;
		if(current > segment){ //reset
			current = 0;
			outputting = true;
			update();
			waiting = true;
		}
	}
}
