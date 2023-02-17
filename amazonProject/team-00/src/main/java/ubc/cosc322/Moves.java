package ubc.cosc322;

import java.util.ArrayList;
import java.util.Collections;


public class Moves {		//check the moves of the chess
	
	// Constants
	public static final int N = 0;
	public static final int NE = 1;
	public static final int E = 2;
	public static final int SE = 3;
	public static final int S = 4;
	public static final int SW = 5;
	public static final int W = 6;
	public static final int NW = 7;
	
	protected ArrayList<Integer> valid;
	
	// Constructor - creates a move object that holds an empty array of moves
	public Moves() {
		valid = new ArrayList<Integer>(Collections.nCopies(8, 0));
	}
	
   // returns an array with chessboard and position

	public ArrayList<Integer> getMoves(ChessBoard chessboard, ArrayList<Integer> pos) {
		valid.set(N, moveN(chessboard, pos));
		valid.set(NE, moveNE(chessboard, pos));
		valid.set(E, moveE(chessboard, pos));
		valid.set(SE, moveSE(chessboard, pos));
		valid.set(S, moveS(chessboard, pos));
		valid.set(SW, moveSW(chessboard, pos));
		valid.set(W, moveW(chessboard, pos));
		valid.set(NW, moveNW(chessboard, pos));
		return valid;
	}
	

	
   // return how many spaces can be moved of north direction

	public int moveN(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNhelp(chessboard, row, col, 0);
	}	
	 // helper method	
	public int moveNhelp(ChessBoard chessboard, int row, int col, int val) {
		row -= 1;
		if(row <= 0 || chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of north east direction

	public int moveNE(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNEhelp(chessboard, row, col, 0);
	}
	 // helper method	
	public int moveNEhelp(ChessBoard chessboard, int row, int col, int val) {
		row -= 1;
		col += 1;
		if(row <= 0 || col > 10)
			return val;
		if(chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNEhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of east direction
	public int moveE(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveEhelp(chessboard, row, col, 0);
	}
	 // helper method		
	public int moveEhelp(ChessBoard chessboard, int row, int col, int val) {
		col += 1;
		if(col > 10)
			return val;
		if(chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveEhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of south east direction
	
	public int moveSE(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSEhelp(chessboard, row, col, 0);
	}
	 // helper method	
	public int moveSEhelp(ChessBoard chessboard, int row, int col, int val) {
		row += 1;
		col += 1;
		if(row > 10 || col > 10)
			return val;
		if(chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveSEhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of south direction
		
	public int moveS(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveShelp(chessboard, row, col, 0);
	}
	 // helper method	
	public int moveShelp(ChessBoard chessboard, int row, int col, int val) {
		row += 1;
		if(row > 10)
			return val;
		if(chessboard.getTile(row, col) != 0)
			return val;		
		else {
			val += 1;
			return moveShelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of south west direction
	
	public int moveSW(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSWhelp(chessboard, row, col, 0);
	}
	 // helper method	
	public int moveSWhelp(ChessBoard chessboard, int row, int col, int val) {
		row += 1;
		col -= 1;
		if(row > 10 || col <= 0)
			return val;
		if(chessboard.getTile(row, col) != 0)
			return val;		
		else {
			val += 1;
			return moveSWhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of west direction
	
	public int moveW(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveWhelp(chessboard, row, col, 0);
	}
	 // helper method	
	public int moveWhelp(ChessBoard chessboard, int row, int col, int val) {
		col -= 1;
		if(col <= 0 || chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveWhelp(chessboard, row, col, val);
		}
	}
	
   // return how many spaces can be moved of north west direction

	public int moveNW(ChessBoard chessboard, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNWhelp(chessboard, row, col, 0);
	}
   // helper method	
	public int moveNWhelp(ChessBoard chessboard, int row, int col, int val) {
		row -= 1;
		col -= 1;
		if(row <= 0 || col <= 0 || chessboard.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNWhelp(chessboard, row, col, val);
		}
	}
	
	
}