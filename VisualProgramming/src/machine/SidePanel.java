package machine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import piece.Piece;
import piece.PieceGroup;

public final class SidePanel{ //TODO open close w/ animation
	
	private static final int WIDTH = 200;
	private static final int BOX_HEIGHT = 25;
	private static final int VERTICAL_SPACING = 2;
	private static final int OPACITY = 155;
	private static final int OPEN_POSITION_X = ProgrammingSpace.WIDTH - WIDTH- 20;
	private static final int CLOSED_POSITION_X = ProgrammingSpace.WIDTH;
	private static final Color TEXT_COLOR = new Color(200,200,200);
	private static float topBound;
	private static float bottomBound;
	private float positionY;
	private float positionX;
	private final ProgrammingSpace space;
	private List<Piece> pieces;
	private List<Color> colors;
	public SidePanel(final ProgrammingSpace space){
		super();
		this.space = space;
		positionY = 0;
		
		pieces = new ArrayList<Piece>();
		colors = new ArrayList<Color>();
		for(PieceGroup group : PieceGroup.getGroups()){
			for(Class<? extends Piece> p : group.getClasses()){
				pieces.add(PieceGroup.getInstanceOf(p, this.space));
				colors.add(group.getColor());
			}
		}
		topBound = VERTICAL_SPACING - 100;
		bottomBound = (BOX_HEIGHT + VERTICAL_SPACING) * pieces.size() - ProgrammingSpace.HEIGHT + 100;
	}
	public void draw(Graphics2D g){
		for(int i = 0; i < pieces.size(); i++){
			if(i*BOX_HEIGHT + i*VERTICAL_SPACING - positionY> ProgrammingSpace.HEIGHT)
				return;

			Piece p = pieces.get(i);
			int rgb = colors.get(i).getRGB();
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			g.setColor(new Color(red, green, blue, OPACITY));
			g.fillRoundRect(OPEN_POSITION_X, (int) (-positionY + i*BOX_HEIGHT + i*VERTICAL_SPACING), WIDTH , BOX_HEIGHT, 6, 6);
			g.setColor(TEXT_COLOR);
			int x = (WIDTH - (int) g.getFontMetrics().getStringBounds(p.toString(), g).getWidth()) / 2;
			g.drawString(p.toString(), x + OPEN_POSITION_X, (int) (-positionY + i*BOX_HEIGHT + i*VERTICAL_SPACING) + BOX_HEIGHT/2);
		}	
	}
	public void mouseClicked(MouseEvent e){
		Point point = e.getPoint();
		Piece p = getPieceAt(point);
		if( p == null)
			return;
		 Piece pieceCreated = PieceGroup.getInstanceOf(((Piece) p).getClass(), space);
		 space.addPiece(pieceCreated);
	}
	private Piece getPieceAt(Point point){
		if(point.x < OPEN_POSITION_X || point.x > OPEN_POSITION_X + WIDTH)
			return null;
		int y = point.y;
		for(int i = 0; i < pieces.size(); i++){
			if(y > (-positionY + i*BOX_HEIGHT + i*VERTICAL_SPACING) && y < (-positionY + i*BOX_HEIGHT + i*VERTICAL_SPACING) + BOX_HEIGHT)
				return pieces.get(i);
		}
		return null;
	}
	public boolean containsPoint(Point point){
		return getPieceAt(point) != null;
	}
	Point pressedLocation;
	//dragging methods
	public void mouseDragged(MouseEvent e){
		if(pressedLocation != null){
			this.positionY = pressedLocation.y - e.getPoint().y;
			if(this.positionY < topBound)
				this.positionY = topBound;
			else if(this.positionY > bottomBound)
				this.positionY = bottomBound;
		}
	}
	public void mousePressed(MouseEvent e){
		if(getPieceAt(e.getPoint()) != null){
			pressedLocation = new Point(e.getPoint().x, (int) (positionY + e.getPoint().y));
		}
	}
	public void mouseReleased(MouseEvent e){
		pressedLocation = null;
	}
}
