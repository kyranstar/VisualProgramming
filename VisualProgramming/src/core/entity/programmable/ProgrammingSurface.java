package core.entity.programmable;

import java.util.ArrayList;
import java.util.List;

import core.ui.programming.piece.Piece;

public class ProgrammingSurface {
	protected List<Piece> pieces;
	public ProgrammingSurface(){
		pieces = new ArrayList<Piece>();
	}
	
	public final void reset(){
		pieces = new ArrayList<Piece>();
		addAllPieces();
	}
	//Override this method
	public void addAllPieces(){}
	
	public final List<Piece> getPieces(){
		return pieces;
	}
}
