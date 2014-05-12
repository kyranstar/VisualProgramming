package core.ui.programming.piece;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Output {
	int ports;
	Input[] connections;
	int[] connectionIndex;
	Piece piece;
	private static final int THICKNESS = 1;
	
	public Output(final int ports, final Piece piece){
		this.piece = piece;
		this.ports = ports;
		connections = new Input[ports];
		connectionIndex = new int[ports];
		for(int i = 0; i < ports; i++) {
			connectionIndex[i] = -1;
		}
	}
	public Output(final Output output) {
		this(output.ports, output.piece);
	}
	public final void connect(final Piece other, final int outputPort, final int inputPort){
		connections[outputPort] = other.input;
		connectionIndex[outputPort] = inputPort;
		other.input.connections[inputPort] = this;
	}
	public final void disconnect(final int outputPort) {
		if(connections[outputPort] == null)
		 {
			return; //already disconnected
		}
		connections[outputPort].connections[connectionIndex[outputPort]] = null;
		connectionIndex[outputPort] = -1;
		connections[outputPort] = null;
	}
	public final void draw(final Graphics2D g) {
		g.setStroke(new BasicStroke(THICKNESS));
		g.setColor(Color.GREEN);
		for (int i = 0; i < ports; i++){
			int x = piece.x + piece.width - 12;
			int y = piece.y + ((piece.height / (ports + 1)) * (i + 1)) - 5; //centers them and 5 is height/2
			if(connections[i] == null) {
				g.drawOval(x, y, 10, 10);
			} else {
				g.fillOval(x, y, 10, 10);
			}
		}
		
	}
	public final void drawConnections(final Graphics2D g) {
		g.setColor(Color.RED);
		for(int i = 0; i < connections.length; i++){
			Input con = connections[i];
			if(con == null) {
				continue;
			}
			int x1 = piece.x + piece.width - 5;
			int y1 = piece.y + ((piece.height / (ports + 1)) * (i + 1));
			int x2 = con.piece.x + 5;
			int y2 =  con.piece.y + ((con.piece.height / (con.ports + 1)) * (connectionIndex[i] + 1));
			g.drawLine(x1, y1, x2, y2);
		}
	}
	public final void update() {
		for(int i = 0; i < connections.length; i++){
			Input con = connections[i];
			if(con == null) {
				continue;
			}
			con.recieve(connectionIndex[i], piece.send(i));
			con.piece.update();
		}
	}
	public final boolean contains(final Point p) {
		return (piece.x  + piece.width - 12< p.getX() && 
				piece.y < p.getY() &&
		        piece.x + piece.width > p.getX()  &&
		        piece.y + piece.height > p.getY());
	}
	public final Integer getOuputFromY(final int y) {
		for(int i = 0; i < ports; i++){
			int yOfPort = piece.y + ((piece.height / (ports + 1)) * (i + 1)) - 5;
			if (y > yOfPort && y < yOfPort + 10) {
				return i;
			}
		}
		return null;
	 }
	public final Point getPointFromPort(final int i) {
		return new Point(piece.x + piece.width - 7, piece.y + ((piece.height / (ports + 1)) * (i + 1)));
	}

}
