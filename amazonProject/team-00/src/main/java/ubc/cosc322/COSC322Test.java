package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;


public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	private aiPlayer player = null;
    
    private String userName = null;
    private String passwd = null;
    private ChessBoard myBoard = null;
    
    private boolean moving = true;
    private int mycolor;
    private int oppcolor;
    private int count;
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and password 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	System.out.println("Congratulations!!! "
    			+ "I am called because the server indicated that the login is successful");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!"); 
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }
    

     // connect to gui

    public void play(int oriRow, int oriCol, int currRow, int currCol, int arrowRow, int arrowCol) {
    	ArrayList<Integer> queenStart = new ArrayList<Integer>();
    	queenStart.add(oriRow);
    	queenStart.add(oriCol);
    	ArrayList<Integer> queenEnd = new ArrayList<Integer>();
    	queenEnd.add(currRow);
    	queenEnd.add(currCol);
    	ArrayList<Integer> arrow = new ArrayList<Integer>();
    	arrow.add(arrowRow);
    	arrow.add(arrowCol);
    	
    	this.gameClient.sendMoveMessage(queenStart, queenEnd, arrow);
    	this.gamegui.updateGameState(queenStart, queenEnd, arrow);
    }


    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	if(messageType == GameMessage.GAME_STATE_BOARD) {
    		ArrayList<Integer> board = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		if(board==null) 
    			System.out.println("Board state is null");
    		myBoard = new ChessBoard();
    		count = 0;
			this.gamegui.setGameState(board);
    	}
    	else if(messageType == GameMessage.GAME_ACTION_START) {
    		ArrayList<Integer> board = myBoard.getBoard();
    		this.gamegui.setGameState(board);
    		System.out.println("Start");
    		if(board == null)
    			System.out.println("No board information");
    		else 
    			this.gamegui.setGameState(board);
    		
    		
    		String black = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
    		String white = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
    		
    		if(black.equals(userName)) {
    			this.mycolor = myBoard.POS_BLACK;
    			this.oppcolor = myBoard.POS_WHITE;
    			this.player = new aiPlayer(myBoard);
    			ArrayList<Integer> play = player.ai(mycolor, count);
    			ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(play.get(0), play.get(1)));
				ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(play.get(2), play.get(3)));
				ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(play.get(4), play.get(5)));
				myBoard.moveChess(oldQueen, newQueen, newArrow, mycolor);
				count++;
    			play(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
    			System.out.println(myBoard.toString());
    		}else {
    			this.mycolor = myBoard.POS_WHITE;
    			this.oppcolor = myBoard.POS_BLACK;
    		}
        }
    	else if(messageType == GameMessage.GAME_ACTION_MOVE) {
    		if (!myBoard.checkFin(mycolor)) {
    			if(this.moving) {
	    			ArrayList<Integer> queenCurr = 
	    					(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);    		
		    		ArrayList<Integer> queenNext = 
		    				(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
		    		ArrayList<Integer> arrow = 
		    				(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		    		System.out.println("\nAction move message");
		    		System.out.println("The opponent's starting position is: " + queenCurr);
		    		System.out.println("The opponent's new position is: " + queenNext);
		    		System.out.println("The opponent's blocking position is: " + arrow);
		    		myBoard.moveChess(queenCurr, queenNext, arrow, oppcolor);
		    		this.moving = false;
		    		this.gamegui.updateGameState(msgDetails);
	    		}
    		}
    		if (!myBoard.checkFin(oppcolor)) {
	    		this.player = new aiPlayer(myBoard);
		    	ArrayList<Integer> play = player.ai(mycolor, count);
		    	ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(play.get(0), play.get(1)));
				ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(play.get(2), play.get(3)));
				ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(play.get(4), play.get(5)));
				System.out.println("Action move message");
			   	System.out.println("Our starting position was: [" + play.get(0) + ", " + play.get(1) + "]");
			   	System.out.println("Our new position is: [" + play.get(2) + ", " + play.get(3) + "]");
			   	System.out.println("Our blocking position is: [" + play.get(4) + ", " + play.get(5) + "]");
			   	myBoard.moveChess(oldQueen, newQueen, newArrow, mycolor);
			   	count++;
		    	play(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
		    	System.out.println(myBoard.toString());
		    	this.moving = true;
    		}
    	}    	    	
    	return true;   	
    }
        
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return  this.gamegui;
	}

	@Override
	public void connect() {
		gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class