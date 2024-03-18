package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for our Hex game. The state consist of who won the game and whos turn it is
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class HexState extends GameState {

	// instance variables for our HexState
	private int playerTurn = 0;
	private boolean hasWon;
	private HexBoard board;
	private String playerWinner;

	private Player player1;
	private Player player2;

	
	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 * 
	 * @param
	 */
	public HexState() {
		this.playerTurn = 0;
		this.hasWon = false;
		this.board = new HexBoard(11);
		this.player1 = new Player("player 1","red");
		this.player2 = new Player("player2","red");
		this.playerWinner = null;


	}
	
	/**
	 * copy constructor; makes a copy of the original object
	 * 
	 * @param orig
	 * 		the object from which the copy should be made
	 */
	public HexState(HexState orig) {
		// set the counter to that of the original
		this.playerTurn = orig.playerTurn;
		this.hasWon = orig.hasWon;
		this.playerWinner = orig.playerWinner;
		this.board = new HexBoard(orig.board);
		this.player1 = new Player(orig.player1);
		this.player2 = new Player(orig.player2);
		//this.board = orig.board;
		//this.player1 = orig.player1;
		//this.player2 = orig.player2;

	}
	//toString for HexState
	@Override
	public String toString() {
		StringBuilder newString = new StringBuilder();
		newString.append("Game State:\n");
		newString.append("Player Turn: ").append(playerTurn == 0 ? "Player 1" : "Player 2").append("\n");
		newString.append("Has Won: ").append(hasWon ? "Yes" : "No").append("\n");
		if(hasWon) {
			newString.append("Winner: ").append(playerWinner).append("\n");
		} else {
			newString.append("Winner: N/A\n");
		}

		// hexBoard and player classes have their toString methods
		newString.append("Board State:\n").append(board.toString()).append("\n");
		newString.append("Player 1: ").append(player1.toString()).append("\n");
		newString.append("Player 2: ").append(player2.toString()).append("\n");

		return newString.toString();
	}



}