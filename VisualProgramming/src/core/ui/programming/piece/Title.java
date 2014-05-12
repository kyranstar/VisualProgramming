package core.ui.programming.piece;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Title {
	private static final int SIZE = 15;
	
	Piece piece;
	String type;
	public Title(final String type, final Piece piece){
		this.type = type;
		this.piece = piece;
		
	}
	public Title(final Title title, final Piece p) {
		this.type = title.type;
		this.piece = p;
	}
	public final void draw(final Graphics2D g){
		g.setFont(new Font(g.getFont().getPSName(), Font.BOLD, SIZE)); 
		int width = g.getFontMetrics().stringWidth(type) + 10;
		int height = g.getFontMetrics().getHeight();
		int x = piece.x + (piece.width/2) - (width/2) - 10;
		int y = piece.y + 3;

		g.setColor(new Color(170, 170, 170));
		g.fillRoundRect(x, y - height, width, height, 5, 5);

		g.setColor(Color.GREEN);
		g.drawString(type, x + 5, y - 5);
	}
}
