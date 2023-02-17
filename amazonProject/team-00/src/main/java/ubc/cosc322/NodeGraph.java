package ubc.cosc322;

import java.util.ArrayList;

public class NodeGraph extends ChessBoard{
	
	// attributes
    public ArrayList<Integer> oldQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newQueen = new ArrayList<Integer>(); 
    public ArrayList<Integer> newArrow= new ArrayList<Integer>(); 
    public ArrayList<NodeGraph> children = null;
    public double value;
    public int value1;
    public int value2;
    public int depth;
    
    // default constructor
    public NodeGraph() {
    	
    }
    
    
    // constructor with given board
    public NodeGraph(ChessBoard board) {
        super(board);
    }

    // constructor with given value 1, 2
    public NodeGraph(int val1, int val2) {
    	super();
    	this.value1 = val1;
    	this.value2 = val2;
    	this.value = val1 + val2;
    }
    
    public NodeGraph(double val) {
    	super();
    	this.value = val;
    }
    //constructor with given scale, old queen position, new queen/arrow position, value 1, 2, board, depth
    public NodeGraph(ArrayList<Integer> oldq, ArrayList<Integer> q, ArrayList<Integer> a, int val1, int val2, ChessBoard b, int d, double sc) {
        super();
        this.oldQueen = oldq;   //old position
    	this.newQueen = q;		// new position
    	this.newArrow = a;		// new arrow position
    	this.value1 = val1;		
    	this.value2 = val2;
    	this.value = (val1 * sc) + (val2 * (1-sc));
    	super.board = b.board;
    	this.depth = d; 
    }

    // add child to the graph
    public void addChild(NodeGraph newChild) {
    	if(children == null) {
    		children = new ArrayList<NodeGraph>();
    		children.add(newChild);
    	}else {
    		children.add(newChild);
    	}
    }

    // add multiple child to the graph
    public void addChildren(ArrayList<NodeGraph> newChildren) {
    	if(children == null) {
    		children = newChildren;
    	} else {
    		for(int i = 0; i < newChildren.size(); i++) {
    			children.add(newChildren.get(i));
    		}
    	}
    }
    
    //return value
    public double getValue() {
    	return value;
    }
}