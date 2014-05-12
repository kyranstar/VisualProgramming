package core.ui.programming.piece;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import core.ui.programming.values.Value;

public class Input {
	int ports;
	Output[] connections;
	Piece piece;
	private static final int THICKNESS = 1;
	
	public Input(final int ports, final Piece piece){
		this.piece = piece;
		this.ports = ports;
		connections = new Output[ports];
	}
	public final void connect(final Piece other, final int outputPort, final int inputPort){
		connections[inputPort] = other.output;
		other.output.connections[outputPort] = this;
	}
	public final void draw(final Graphics2D g) {
		g.setStroke(new BasicStroke(THICKNESS));
		g.setColor(Color.RED);
		for (int i = 0; i < ports; i++){
			int x = piece.x + 2;
			int y = piece.y + ((piece.height / (ports + 1)) * (i + 1)) - 5; //centers them and 5 is height/2
			if(connections[i] == null) {
				g.drawOval(x, y, 10, 10);
			} else {
				g.fillOval(x, y, 10, 10);
			}
		}
	}
	public final void recieve(final int inputPort, final Value v){
		piece.recieve(inputPort, v);
	}
	public final boolean contains(final Point p){
		return (piece.x < p.getX() && 
				piece.y < p.getY() &&
		        piece.x + 12 > p.getX()  &&
		        piece.y + piece.height > p.getY());
	}
	public final Integer getInputFromY(final int y) {
		for(int i = 0; i < ports; i++){
			int yOfPort = piece.y + ((piece.height / (ports + 1)) * (i + 1)) - 5;
			if (y > yOfPort && y < yOfPort + 10) {
				return i;
			}
		}
		return null;
	 }
	public final Point getPointFromPort(final int i) {
		return new Point(piece.x + 2, piece.y + ((piece.height / (ports + 1)) * (i + 1)) - 5);
	}
}
