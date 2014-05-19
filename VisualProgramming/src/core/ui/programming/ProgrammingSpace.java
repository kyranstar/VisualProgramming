package core.ui.programming;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.entity.AbstractEntity;
import core.ui.programming.piece.Piece;
import core.ui.programming.piece.PieceGroup;
import core.ui.programming.piece.Updatable;

public class ProgrammingSpace {	
	
	private List<Piece>  pieces = new ArrayList<Piece>();
	private AbstractEntity entity;
	private ProgrammingSpaceInterface programmingInterface;
	
	public ProgrammingSpace(ProgrammingSpaceInterface programmingInterface) {
		this.programmingInterface = programmingInterface;
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
	public AbstractEntity getEntity(){
		return this.entity;
	}
	public void setEntity(final AbstractEntity entity) {
		this.entity = entity;
		pieces.clear();
		for(Class<? extends Piece> pieceClass : entity.getProgrammingPieces()){
			this.addPiece(PieceGroup.getInstanceOf(pieceClass, this.getInterface()));
		}
	}
	private ProgrammingSpaceInterface getInterface() {
		return this.programmingInterface;
	}

	public List<Piece> getPieces() {
		return pieces;
	}
}
 