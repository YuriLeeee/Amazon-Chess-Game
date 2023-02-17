package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class aiPlayer extends ChessBoard {

	// attributes
    public aiPlayer parent = null;
    public ChessBoard board = null;
    public NodeGraph graph = null;
    
    // constructor with given board
    public aiPlayer(ChessBoard chessboard) {
        super(chessboard);
        graph = new NodeGraph(chessboard);
    }
    
    // constructor with given parent and chessboard
    public aiPlayer(aiPlayer parent, ChessBoard chessboard) {
        super(parent);
        this.parent = parent;
    }

    // ai method that calls relevant algorithm depending on how far the game goes
    public ArrayList<Integer> ai(int colour, int counter) {
    	if (counter < 10) {
    		int depth = 1;
    		double scale = 1;
    		createNodes(colour, depth, scale);
    		NodeGraph moveNode = miniMax(graph, new NodeGraph(-Double.MAX_VALUE), new NodeGraph(Double.MAX_VALUE), true);

    		ArrayList<Integer> move = null;
    		if (!moveNode.oldQueen.isEmpty()) {
    			move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		} else if (moveNode.oldQueen.isEmpty()) {
    			move = graph.randomMove(colour);
    		}
        	return move; 
	
    	} else if (counter >= 10 && counter < 15) {
    		int depth = 1;
    		double scale = 0.8;
    		createNodes(colour, depth, scale);
    		NodeGraph moveNode = miniMax(graph, new NodeGraph(-Double.MAX_VALUE), new NodeGraph(Double.MAX_VALUE), true);
    		ArrayList<Integer> move = null;
    		if (!moveNode.oldQueen.isEmpty()) {
    			move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		} else if (moveNode.oldQueen.isEmpty()) {
    			move = graph.randomMove(colour);
    		}
        	return move;  
    	} else if (counter >= 15 && counter < 25) {
    		int depth = 1;
    		double scale = 0.5;
    		createNodes(colour, depth, scale);
    		NodeGraph moveNode = miniMax(graph, new NodeGraph(-Double.MAX_VALUE), new NodeGraph(Double.MAX_VALUE), true);
    		
    		ArrayList<Integer> move = null;
    		if (!moveNode.oldQueen.isEmpty()) {
    			move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		} else if (moveNode.oldQueen.isEmpty()) {
    			move = graph.randomMove(colour);
    		}
        	return move; 
    	}else if (counter >= 25) { 
    		int depth = 1;
    		double scale = 0.2;
    		createNodes(colour, depth, scale);
    		NodeGraph moveNode = miniMax(graph, new NodeGraph(-Double.MAX_VALUE), new NodeGraph(Double.MAX_VALUE), true);
    		ArrayList<Integer> move = null;
    		if (!moveNode.oldQueen.isEmpty()) {
    			move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		} else if (moveNode.oldQueen.isEmpty()) {
    			move = graph.randomMove(colour);
    		}
        	return move; 
    	}
		return null;
    }

    // miniMax algorithm to prioritize maximizing the moves by minimizing the opponent's available moves
    public NodeGraph miniMax(NodeGraph node, NodeGraph alpha, NodeGraph beta, boolean maximizingPlayer) {
    	if(node.children == null || node.children.size() == 0) {
    		return node;
    	}
    	if (maximizingPlayer && node.oldQueen == null) {
    		return new NodeGraph(-Double.MAX_VALUE);
    	}
    	if (!maximizingPlayer && node.oldQueen == null) {
    		return new NodeGraph(Double.MAX_VALUE);
    	}

    	
    	if(maximizingPlayer) {
    		NodeGraph best = new NodeGraph(Double.MAX_VALUE);
    		for(int i = 0; i < node.children.size(); i++) {
	    		NodeGraph val = miniMax(node.children.get(i), alpha, beta, false);
	    		if(val.value > best.value) best = val;
	    		if(best.value > alpha.value) alpha = best;
	    		if(best.value >= beta.value) {
	    			break;
	    		}
    		}
    		return best;
    	} else {
    		NodeGraph best = new NodeGraph(Double.MAX_VALUE);
    		for(int i = 0; i < node.children.size(); i++) {
	    		NodeGraph val = miniMax(node.children.get(i), alpha, beta, true);
	    		if(val.value < best.value) best = val;
	    		if(best.value < beta.value) beta = best;
	    		if(best.value <= alpha.value) break;

    		}
    		return best;
    	}
    }
    
    // create new nodes to the graph
    public void createNodes(int color, int depth, double scale) {
    	for(int i = 0; i < depth; i++) {
    		if(i > 0 && i % 2 == 1) {
    			addToGraph(getOppColor(color), graph, i, scale);
    		}
    		if (i % 2 == 0)
    			addToGraph(color, graph, i, scale);
    	}
    }
    
    // add children to graph
    public void addToGraph(int colour, NodeGraph node, int depth, double scale) {

    	for(int i = 0; i < node.children.size(); i++) {
    		addToGraph(colour, node.children.get(i), depth + 1, scale);
    	}
    }

   // return new position based on the old position
    public ArrayList<Integer> updatedPos(int direction, ArrayList<Integer> pos, int distance) {
    	ArrayList<Integer> newpos = new ArrayList<Integer>();
		if (direction == Moves.N) {
			newpos.add(pos.get(0)-distance); newpos.add(pos.get(1));
		}
		else if (direction == Moves.NE) {
			newpos.add(pos.get(0)-distance); newpos.add(pos.get(1)+distance);
		}
		else if (direction == Moves.E) {
			newpos.add(pos.get(0)); newpos.add(pos.get(1)+distance);
		}
		else if (direction == Moves.SE) {
			newpos.add(pos.get(0)+distance); newpos.add(pos.get(1)+distance);
			}
		else if (direction == Moves.S) {
			newpos.add(pos.get(0)+distance); newpos.add(pos.get(1));
		}
		else if (direction == Moves.SW) {
			newpos.add(pos.get(0)+distance); newpos.add(pos.get(1)-distance);
		}
		else if (direction == Moves.W) {
			newpos.add(pos.get(0)); newpos.add(pos.get(1)-distance);
		}
		else if (direction == Moves.NW) {
			newpos.add(pos.get(0)-distance); newpos.add(pos.get(1)-distance);
		}
		return newpos;
    }
    
    // return the opponent color
    public int getOppColor(int color) {
    	if (color==1) return 2;
    	else if (color==2) return 1;
    	return 0;
    }
    
    // return the opponent positions
    public ArrayList<ArrayList<Integer>> getOppPos(int colour) {
    	int oppc = 0;
    	if (colour == 1)
    		oppc = 2;
    	else
    		oppc = 1;
    	ArrayList<ArrayList<Integer>> opp = super.getPositions(oppc);
    	return opp;
    }


}