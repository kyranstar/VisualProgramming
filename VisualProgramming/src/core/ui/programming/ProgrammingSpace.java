package core.ui.programming;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import machine.GamePanel;
import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Updatable;

public class ProgrammingSpace {	
	
	private List<Piece> pieces;
	private GamePanel component;
	private SidePanel sidePanel;

	private Rectangle location;
	private int x, y;
	private static final int LINE_SPACING = 80;
	
	private static final int MAX_X = 200;
	private static final int MAX_Y = 200;
	
	private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
	private static final Color BORDER_COLOR = Color.CYAN;
	private static final Color LINE_COLOR = Color.LIGHT_GRAY;
	private int opacity;
	
	public ProgrammingSpace(Rectangle pos, int opacity, GamePanel component) {
		this.component = component;
		pieces = new ArrayList<Piece>();
		this.location = new Rectangle(pos.x, pos.y, pos.width, pos.height);
		this.opacity = opacity;
		sidePanel = new SidePanel(this, pos.y);
	}

	public void draw(Graphics2D g) {
		final int roundness = 50;
		
		g.setColor(new Color(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), opacity));
		g.fillRoundRect(location.x, location.y, location.width, location.height, roundness, roundness);
		
		g.setColor(new Color(BORDER_COLOR.getRed(), BORDER_COLOR.getGreen(), BORDER_COLOR.getBlue(), opacity));
		g.drawRoundRect(location.x - 1, location.y - 1, location.width + 1, location.height + 1, roundness, roundness);
		
		g.translate(location.x, location.y);
		g.setColor(Color.GREEN);
		g.drawString("X: " + x + " Y: " + y, 10, 30);

		Shape originalClip = g.getClip();
		g.clip(new RoundRectangle2D.Double(0,0, location.width,  location.height, roundness, roundness));
		
		g.setColor(new Color(LINE_COLOR.getRed(), LINE_COLOR.getGreen(), LINE_COLOR.getBlue(), opacity));
		for(int i = 0 - (x % LINE_SPACING); i < location.width ; i += LINE_SPACING){
			g.drawLine(i, 0, i , location.height);
		}
		for(int i = 0 - (y % LINE_SPACING); i < location.height ; i += LINE_SPACING){
			g.drawLine(0, i, location.width, i);
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

		g.setClip(originalClip);
		g.translate(-location.x, -location.y);
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

		g.drawLine(port.x, port.y, mouse.x + x - this.location.x, mouse.y + y - this.location.y);
	}

	private Piece selected;
	private Point relativeLocation;
	private Piece portSelectedPiece;
	private int portSelected;
	
	private Point relativeBackgroundLocation;

	public void mouseDragged(MouseEvent e) {
		e.translatePoint(-location.x, -location.y);
		sidePanel.mouseDragged(e);

		if(sidePanel.containsPoint(e.getPoint())){
			return;
		}		
		if (selected != null && relativeLocation != null){
			selected.setPosition(new Point(e.getPoint().x + relativeLocation.x, e.getPoint().y + relativeLocation.y));
		}
		if(relativeBackgroundLocation != null){
			this.x = relativeBackgroundLocation.x - e.getPoint().x;
			this.y = relativeBackgroundLocation.y - e.getPoint().y;
			
			if(this.x > MAX_X){
				this.x = MAX_X;
			}else if(this.x < -MAX_X){
				this.x = -MAX_X;
			}
			
			if(this.y > MAX_Y){
				this.y = MAX_Y;
			}else if(this.y < -MAX_Y){
				this.y = -MAX_Y;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		e.translatePoint(-location.x, -location.y);
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
		e.translatePoint(-location.x, -location.y);
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
	public int getWidth() {
		return location.width;
	}
	public int getHeight() {
		return location.height;
	}
}
