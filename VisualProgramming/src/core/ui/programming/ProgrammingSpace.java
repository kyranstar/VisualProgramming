package core.ui.programming;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.ui.programming.piece.Piece;
import core.ui.programming.piece.Updatable;

public class ProgrammingSpace {	
	
	private List<Piece>  pieces = new ArrayList<Piece>();
	
	public ProgrammingSpace() {
	}

	public void setPieces(List<Piece> pieces){
		this.pieces = pieces;
	}
	
	public void draw(Graphics2D g) {
		synchronized(pieces){
			g.setColor(Color.GREEN);
			for (Piece p : pieces) {
				p.draw(g);
			}
			for (Piece p : pieces) {
				p.drawConnections(g);
			}
		}
	}

	public void update() {
		synchronized(pieces){
		for(Piece p : pieces){
			if(p instanceof Updatable)
				((Updatable) p).updatePiece();
		}
		}
	}

	public void addPiece(Piece pieceCreated) {
		synchronized(pieces){
			pieces.add(pieceCreated);
		}
	}

	public List<Piece> getPieces() {
		return pieces;
	}
}
