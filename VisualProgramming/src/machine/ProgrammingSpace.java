package machine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Updatable;

public class ProgrammingSpace {	
	public static final int WIDTH = GamePanel.WIDTH;
	public static final int HEIGHT = 200;
	
	private List<Piece> pieces;
	private GamePanel component;
	private SidePanel sidePanel;
	
	int x, y;
	private static final int LINE_SPACING = 80;
	
	public ProgrammingSpace(GamePanel component) {
		this.component = component;
		pieces = new ArrayList<Piece>();

		sidePanel = new SidePanel(this);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawString("X: " + x + " Y: " + y, 10, 30);
		
		g.setColor(Color.LIGHT_GRAY);
		for(int i = 0 - (x % LINE_SPACING); i < WIDTH ; i += LINE_SPACING){
			g.drawLine(i, 0, i , HEIGHT);
		}
		for(int i = 0 - (y % LINE_SPACING); i <HEIGHT ; i += LINE_SPACING){
			g.drawLine(0, i, WIDTH, i);
		}
		
		int noChangeX = x; 
		int noChangeY = y;
		synchronized(pieces){
			g.translate(-noChangeX, -noChangeY);
			g.setColor(Color.GREEN);
			for (Piece p : pieces) {
				p.draw(g);
			}
			for (Piece p : pieces) {
				p.drawConnections(g);
			}
			drawSelected(g);
			g.translate(noChangeX, noChangeY);
		}

		sidePanel.draw(g);
	}

	public void update() {
		synchronized(pieces){
		for(Piece p : pieces){
			if(p instanceof Updatable)
				((Updatable) p).updatePiece();
		}
		}
	}

	private void drawSelected(Graphics2D g) {
		if (portSelectedPiece == null)
			return;
		Point port = portSelectedPiece.getPointFromOutputPort(portSelected);
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		mouse.translate(-component.getLocationOnScreen().x, -component.getLocationOnScreen().y);

		g.drawLine(port.x, port.y, mouse.x + x, mouse.y + y);
	}

	private Piece selected;
	private Point relativeLocation;
	private Piece portSelectedPiece;
	private int portSelected;
	
	private Point relativeBackgroundLocation;

	public void mouseDragged(MouseEvent e) {
		sidePanel.mouseDragged(e);

		if(sidePanel.containsPoint(e.getPoint()))
			return;
		
		if (selected != null)
			selected.setPosition(new Point(e.getPoint().x + relativeLocation.x, e.getPoint().y + relativeLocation.y));
		if(relativeBackgroundLocation != null){
			this.x = relativeBackgroundLocation.x - e.getPoint().x;
			this.y = relativeBackgroundLocation.y - e.getPoint().y;
		}
	}

	public void mousePressed(MouseEvent e) {
		sidePanel.mousePressed(e);
		
		if(sidePanel.containsPoint(e.getPoint()))
			return;
		Point point = e.getPoint();
		for (Piece p : pieces) {
			if (p.contains(new Point(point.x + x, point.y + y))) {
				relativeLocation = new Point((int) (p.getX() - point.getX()), (int) (p.getY() - point.getY()));
				selected = p;
				relativeBackgroundLocation = null; //we selected something other than the background
				return;
			}
		}
		relativeBackgroundLocation = new Point(x + point.x, y + point.y);
	}

	public void mouseReleased(MouseEvent e) {
		selected = null;
		relativeLocation = null;
		sidePanel.mouseReleased(e);
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) { // TODO fix so that connections can be made from inputs to outputs too
		sidePanel.mouseClicked(e);
		if(sidePanel.containsPoint(e.getPoint()))
			return;
		
		Point point = e.getPoint();
		for (Piece p : pieces) {
			if (p.contains(new Point(point.x + x, point.y + y))) {
				if (SwingUtilities.isRightMouseButton(e)) {
					if(portSelectedPiece != null){
						portSelectedPiece = null;
						portSelected = -1;
						return;
					}
					handleRightClick(p, e);
					return;
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					handleLeftClick(p, e);
					return;
				}
			}
		}
	}
	public void handleLeftClick(Piece p, MouseEvent e){
		if (e.getClickCount() > 1) {
			p.doubleClicked();
			return;
		}
		if (p == portSelectedPiece)
			return;
		Integer port = p.getOutputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		if (portSelectedPiece != null && port == null)
			port = p.getInputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		if (port != null) {
			if (portSelectedPiece != null) {
				portSelectedPiece.disconnect(portSelected);
				portSelectedPiece.connect(p, portSelected, port);
				portSelectedPiece = null;
				portSelected = -1;
			} else {
				portSelectedPiece = p;
				portSelected = port;
			}
		}
		return;
	}
	public void handleRightClick(Piece p, MouseEvent e){
		Integer port = p.getOutputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		if(port != null){
			p.disconnect(port);
			return;
		}
	}

	public void addPiece(Piece pieceCreated) {
		synchronized(pieces){
			pieces.add(pieceCreated);
		}
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public static int getWidth() {
		return WIDTH;
	}
	public static int getHeight() {
		return HEIGHT;
	}
}
