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
import java.util.List;

import javax.swing.SwingUtilities;

import machine.GamePanel;
import core.entity.AbstractEntity;
import core.ui.programming.piece.Piece;

public class ProgrammingSpaceInterface {	
	
	private ProgrammingSpace programmingSpace;
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
	private final int opacity;
	private final int INTERFACE_ROUNDNESS = 50;
	
	public ProgrammingSpaceInterface(final Rectangle pos, final int opacity, final GamePanel component) {
		this.component = component;
		programmingSpace = new ProgrammingSpace(this);
		this.location = new Rectangle(pos.x, pos.y, pos.width, pos.height);
		this.opacity = opacity;
		sidePanel = new SidePanel(this, pos.y);
	}

	public final void setPieces(final List<Piece> pieces){
		this.programmingSpace.setPieces(pieces);
	}
	
	public final void draw(final Graphics2D g) {
		
		g.setColor(new Color(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), opacity));
		g.fillRoundRect(location.x, location.y, location.width, location.height, INTERFACE_ROUNDNESS, INTERFACE_ROUNDNESS);
		
		g.setColor(new Color(BORDER_COLOR.getRed(), BORDER_COLOR.getGreen(), BORDER_COLOR.getBlue(), opacity));
		g.drawRoundRect(location.x - 1, location.y - 1, location.width + 1, location.height + 1, INTERFACE_ROUNDNESS, INTERFACE_ROUNDNESS);
		
		g.translate(location.x, location.y);
		g.setColor(Color.GREEN);
		g.drawString("X: " + x + " Y: " + y, 10, 30);

		Shape originalClip = g.getClip();
		g.clip(new RoundRectangle2D.Double(0,0, location.width,  location.height, INTERFACE_ROUNDNESS, INTERFACE_ROUNDNESS));
		
		g.setColor(new Color(LINE_COLOR.getRed(), LINE_COLOR.getGreen(), LINE_COLOR.getBlue(), opacity));
		for(int i = 0 - (x % LINE_SPACING); i < location.width ; i += LINE_SPACING){
			g.drawLine(i, 0, i , location.height);
		}
		for(int i = 0 - (y % LINE_SPACING); i < location.height ; i += LINE_SPACING){
			g.drawLine(0, i, location.width, i);
		}
		
		int noChangeX = x; 
		int noChangeY = y;
			g.translate(-noChangeX, -noChangeY);
			programmingSpace.draw(g);
			g.translate(noChangeX, noChangeY);

		sidePanel.draw(g);
		drawSelected(g);
		g.setClip(originalClip);
		g.translate(-location.x, -location.y);
	}

	public final void update() {
		programmingSpace.update();
	}

	private void drawSelected(final Graphics2D g) {
		if (portSelectedPiece == null) {
			return;
		}
		Point port = portSelectedPiece.getPointFromOutputPort(portSelected);
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		mouse.translate(-component.getLocationOnScreen().x, -component.getLocationOnScreen().y);

		g.drawLine(port.x - x, port.y - y, mouse.x  - this.location.x, mouse.y  - this.location.y);
	}

	private Piece selected;
	private Point relativeLocation;
	private Piece portSelectedPiece;
	private int portSelected;
	
	private Point relativeBackgroundLocation;

	public final void mouseDragged(final MouseEvent e) {
		e.translatePoint(-location.x, -location.y);
		sidePanel.mouseDragged(e);

		if(sidePanel.containsPoint(e.getPoint())){
			return;
		}		
		if (selected != null && relativeLocation != null){
			selected.setPosition(new Point(e.getPoint().x + relativeLocation.x, e.getPoint().y + relativeLocation.y));
			
			if(selected.getX() < -MAX_X){
				selected.setX(-MAX_X);
			}else if (selected.getX() + selected.getWidth() > MAX_X + this.getWidth()){
				selected.setX(MAX_X - selected.getWidth() + this.getWidth());
			}
			if(selected.getY() < -MAX_Y){
				selected.setY(-MAX_Y);
			}else if (selected.getY() + selected.getHeight() > MAX_Y + this.getHeight()){
				selected.setY(MAX_Y - selected.getHeight() + this.getHeight());
			}
			
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

	public final void mousePressed(final MouseEvent e) {
		e.translatePoint(-location.x, -location.y);
		sidePanel.mousePressed(e);
		
		if(sidePanel.containsPoint(e.getPoint())) {
			return;
		}
		Point point = e.getPoint();
		for (Piece p : programmingSpace.getPieces()) {
			if (p.contains(new Point(point.x + x, point.y + y))) {
				relativeLocation = new Point((int) (p.getX() - point.getX()), (int) (p.getY() - point.getY()));
				selected = p;
				relativeBackgroundLocation = null; //we selected something other than the background
				return;
			}
		}
		relativeBackgroundLocation = new Point(x + point.x, y + point.y);
	}

	public final void mouseReleased(final MouseEvent e) {
		selected = null;
		relativeLocation = null;
		sidePanel.mouseReleased(e);
	}

	public void keyPressed(final KeyEvent e) {

	}

	public void keyReleased(final KeyEvent e) {

	}

	public final void mouseClicked(final MouseEvent e) { // TODO fix so that connections can be made from inputs to outputs too
		e.translatePoint(-location.x, -location.y);
		sidePanel.mouseClicked(e);
		if(sidePanel.containsPoint(e.getPoint())) {
			return;
		}
		
		Point point = e.getPoint();
		for (Piece p : programmingSpace.getPieces()) {
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
	private final void handleLeftClick(final Piece p, final MouseEvent e){
		if (e.getClickCount() > 1) {
			p.doubleClicked();
			return;
		}
		if (p == portSelectedPiece) {
			return;
		}
		Integer port = p.getOutputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		if (portSelectedPiece != null && port == null) {
			port = p.getInputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		}
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
	private final void handleRightClick(final Piece p, final MouseEvent e){
		Integer port = p.getOutputPortFromPoint(new Point(e.getPoint().x + x, e.getPoint().y + y));
		if(port != null){
			p.disconnect(port);
			return;
		}
	}

	public final void addPiece(final Piece pieceCreated) {
		programmingSpace.addPiece(pieceCreated);
	}

	public final int getX() {
		return x;
	}
	public final int getY() {
		return y;
	}
	public final int getWidth() {
		return location.width;
	}
	public final int getHeight() {
		return location.height;
	}

	public final void setEntity(final AbstractEntity e) {
		this.programmingSpace.setEntity(e);
	}

	public final AbstractEntity getEntity() {
		return this.programmingSpace.getEntity();
	}

}
