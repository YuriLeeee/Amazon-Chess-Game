package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class ChessBoard {
	
	// attributes
	//public static final int POS_AVAILABLE = 0;
	public static final int POS_BLACK = 1;
	public static final int POS_WHITE = 2;
	public static final int POS_ARROW = 3;
	public static final int N = 121;
	public static final ArrayList<Integer> W_POS = new ArrayList<>(Arrays.asList(15, 18, 45, 54));
	public static final ArrayList<Integer> B_POS = new ArrayList<>(Arrays.asList(78, 87, 114, 117));
	
	protected ArrayList<Integer> board;
	protected Moves move;
	
	
	// default constructor
	public ChessBoard() {
		board = new ArrayList<Integer>(Collections.nCopies(N,0));
		for (int i = 0; i < 4; i++) {
			board.set(B_POS.get(i), POS_BLACK);
			board.set(W_POS.get(i), POS_WHITE);
		}
		move = new Moves();
	}
	
   // Copying the same constructor
	public ChessBoard(ChessBoard parent) {
		this();
		Collections.copy(this.board, parent.board);
	}
	

   // return tile
	public int getTile(int row, int col) {
		return board.get(row * 11 + col);
	}
	

	
   // set tile with given row, column and value
	public void setTile(int row, int col, int value) {
		board.set(row*11+col, value);
	}
	
   // set tile with given index and value
	public void setTile(int index, int value) {
		board.set(index-1, value);
	}
	
   // get the board array
	public ArrayList<Integer> getBoard() {
		return this.board;
	}
	
   // check the game ends or not
	public boolean checkFin(int colour) {
		ArrayList<ArrayList<Integer>> pieces = this.getPositions(colour);
		
		for(int i = 0; i < 4; i++) {
			ArrayList<Integer> moves = move.getMoves(this, pieces.get(i));
			for (int j = 0; j < 8; j++) {
				if (moves.get(j) > 0)
					return false;
			}
		}
		return true;
	}
	
   // move chess with given position

	public void moveChess(ArrayList<Integer> queenStartLoc, ArrayList<Integer> queenFinLoc, ArrayList<Integer> arrowLoc, int colour) {
		ChessBoard temp = new ChessBoard(this);
		if (this.getTile(queenStartLoc.get(0), queenStartLoc.get(1)) == colour) {
			temp.setTile(queenStartLoc.get(0), queenStartLoc.get(1), 0);
			temp.setTile(queenFinLoc.get(0), queenFinLoc.get(1), colour);
			
			temp.setTile(arrowLoc.get(0), arrowLoc.get(1), POS_ARROW);
			Collections.copy(this.board, temp.board);
			
		}
	}
	
   // move black or white chess randomly
	public ArrayList<Integer> randomMove(int colour) {
		if(!this.checkFin(colour)) { //if game haven't finish
			int num = (int)Math.random()*4;
			ArrayList<ArrayList<Integer>> pos = this.getPositions(colour);
			int sum = 0;
			while(sum == 0) {
				ArrayList<Integer> moves = move.getMoves(this, pos.get(num));
				for (int j = 0; j < 8; j++) {
					sum += moves.get(j);
				}
				num = (int) Math.floor(Math.random()*4);
			}
			ChessBoard temp = new ChessBoard(this);
			ArrayList<Integer> queen = random(temp, colour, pos.get(num));
			temp.setTile(pos.get(num).get(0), pos.get(num).get(1), 0);
			temp.setTile(queen.get(0), queen.get(1), colour);
			ArrayList<Integer> arrow = random(temp, colour, queen);
			ArrayList<Integer> arr = new ArrayList<Integer>(6);
			arr.add(pos.get(num).get(0));
			arr.add(pos.get(num).get(1));
			arr.add(queen.get(0));
			arr.add(queen.get(1));
			arr.add(arrow.get(0));
			arr.add(arrow.get(1));
			return arr;
		}
		return null;
	}
	
   //  RandomMove helper method
	public ArrayList<Integer> random(ChessBoard board, int colour, ArrayList<Integer> pos) {
		ArrayList<Integer> valid = move.getMoves(board, pos);
		int distance = 0;
		int direction = 0;
		while (distance == 0) {
			direction = (int) Math.floor(Math.random()*8);
			distance = (int) Math.floor(Math.random()*(valid.get(direction)+1));
		}
		ArrayList<Integer> queen = new ArrayList<Integer>();
		if (direction == Moves.N) {queen.add(pos.get(0)-distance); queen.add(pos.get(1));
		}
		else if (direction == Moves.NE) {
			queen.add(pos.get(0)-distance); queen.add(pos.get(1)+distance);
		}
		else if (direction == Moves.E) {
			queen.add(pos.get(0)); queen.add(pos.get(1)+distance);
		}
		else if (direction == Moves.SE) {
			queen.add(pos.get(0)+distance); queen.add(pos.get(1)+distance);
		}
		else if (direction == Moves.S) {
			queen.add(pos.get(0)+distance); queen.add(pos.get(1));
		}
		else if (direction == Moves.SW) {
			queen.add(pos.get(0)+distance); queen.add(pos.get(1)-distance);
		}
		else if (direction == Moves.W) {
			queen.add(pos.get(0)); queen.add(pos.get(1)-distance);
		}
		else if (direction == Moves.NW) {
			queen.add(pos.get(0)-distance); queen.add(pos.get(1)-distance);
		}
		return queen;
	}
	
   // return position of given color queen
	public ArrayList<ArrayList<Integer>> getPositions(int colour) {
		ArrayList<ArrayList<Integer>> pos = new ArrayList<ArrayList<Integer>>();
		for (int i = 12; i < N; i++) {
			if (board.get(i) == colour) {
				int row = i/11; 
				int column = i%11;
				pos.add(new ArrayList<Integer>(Arrays.asList(i/11, i%11)));
			}
		}
		return pos;
	}


}