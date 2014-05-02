package machine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import piece.Piece;
import piece.PieceGroup;

public final class SidePanel{
	
	private static final int WIDTH = 200;
	private static final int BOX_HEIGHT = 50;
	private static final int VERTICAL_SPACING = 25;
	private static final int POSITION_X = GamePanel.WIDTH - WIDTH- 50;
	private static final Color BACKGROUND_COLOR = new Color(200,50,50);
	private static final Color TEXT_COLOR = new Color(200,200,200);
	private boolean visible;
	private float position;
	private final ProgrammingSpace space;
	private List<Piece> pieces;
	public SidePanel(final ProgrammingSpace game){
		super();
		this.space = game;
		
		visible = true;
		position = 0;
		
		pieces = createList();
	}
	public void draw(Graphics2D g){
		if(visible){
			float drawingPos = -position;
			for(int i = 0; i < pieces.size(); i++){
				Piece p = pieces.get(i);
				g.setColor(BACKGROUND_COLOR);
				g.fillRoundRect(POSITION_X, (int) (drawingPos + i*BOX_HEIGHT + i*VERTICAL_SPACING), WIDTH , BOX_HEIGHT, 6, 6);
				g.setColor(TEXT_COLOR);
				int width = g.getFontMetrics().stringWidth(p.toString());
				g.drawString(p.toString(), POSITION_X + width/2, (int) (drawingPos + i*BOX_HEIGHT + i*VERTICAL_SPACING) + BOX_HEIGHT/2);
			}
		}
		else{
			
		}
			
	}
	public void mouseClicked(MouseEvent e){
		Point point = e.getPoint();
		Piece p = getPieceAt(point);
		if( p == null)
			return;
		System.out.println("contians");
		 Piece pieceCreated = PieceGroup.getInstanceOf(((Piece) p).getClass(), space);
		 space.addPiece(pieceCreated);
	}
	private Piece getPieceAt(Point point){
		if(point.x < POSITION_X || point.x > POSITION_X + WIDTH)
			return null;
		int y = point.y;
		for(int i = 0; i < pieces.size(); i++){
			if(y > (-position + i*BOX_HEIGHT + i*VERTICAL_SPACING) && y < (-position + i*BOX_HEIGHT + i*VERTICAL_SPACING) + BOX_HEIGHT)
				return pieces.get(i);
		}
		return null;
	}
	
	//dragging methods
	public void mouseDragged(MouseEvent e){
		
	}
	public void mousePressed(MouseEvent e){
		
	}
	public void mouseReleased(MouseEvent e){
		
	}
	private List<Piece> createList(){
		List<Piece> pieces = new ArrayList<Piece>();
		//create the child nodes
			for(PieceGroup group : PieceGroup.getGroups()){
				for(Class<? extends Piece> p : group.getClasses()){
					pieces.add(PieceGroup.getInstanceOf(p, space));
				}
			}
		return pieces;
	}
}
