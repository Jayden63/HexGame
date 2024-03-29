package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;


/**
 * This contains the state for our Hex game. The state consist of who won the game and whos turn it is
 * 
 * @author Steven R. Vegdahl
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 * @version March 2024
 */
public class HexState extends GameState {

	// instance variables for our HexState
	private int playerTurn;
	private boolean hasWon;
	private HexBoard board;
	private String playerWinner;
	private Player player1;
	private Player player2;

	//For Undo:  save location of last move
	private int lastPlaceTileX;
	private int lastPlaceTileY;
	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 *
	 * @param
	 */
	public HexState() {
		this.playerTurn = 0;
		this.hasWon = false;
		this.board = new HexBoard(11);

		// Two players, red & blue players
		this.player1 = new Player("player 1", "red");
		this.player2 = new Player("player2", "blue");
		this.playerWinner = null;
	}

	/**
	 * copy constructor; makes a copy of the original object
	 *
	 * Implementing a copy constructor that makes a deep copy of HexState
	 *
	 *
	 * @param orig the object from which the copy should be made
	 */
	public HexState(HexState orig) {
		// set the counter to that of the original
		// Deep copy
		this.playerTurn = orig.playerTurn;
		this.hasWon = orig.hasWon;
		this.playerWinner = orig.playerWinner;
		this.board = new HexBoard(orig.board);
		this.player1 = new Player(orig.player1);
		this.player2 = new Player(orig.player2);
	}

	/**
	 * toString() method to the game state class that describes the state of the
	 * game as a string. This method prints the values of key variables in the Hex State
	 *
	 *
	 */
	@Override
	public String toString() {
		StringBuilder newString = new StringBuilder();
		newString.append("Game State:\n");
		newString.append("Player Turn: ").append(playerTurn == 0 ? "Player 1" : "Player 2").append("\n");
		newString.append("Has Won: ").append(hasWon ? "Yes" : "No").append("\n");
		if (hasWon) {
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

	/**
	 * Checks if a player is allowed to place a tile in a given location
	 *
	 * @param place
	 * @return
	 */
	public boolean placeTile(PlaceTile place) {
		// if the player's turn is correct, if the tile placement is valid, and if the space is empty,
		// then update the state of the game

		/*if (place.getPlayer() instanceof HexHumanPlayer) {
			int playerId = -1;
			playerId = ((HexHumanPlayer) place.getPlayer()).getPlayerId();
		}*/

		// checks if it is the first player's turn and if the spot where the tile is being placed is empty
		if ((this.playerTurn == 0) && (this.board.getGrid()[place.getX()][place.getY()] == board.EMPTY_COLOR))  {

			//player0 places the tile in the empty tile
			this.board.getGrid()[place.getX()][place.getY()] = board.RED_COLOR;

			//updates the location of latest placed tile
			lastPlaceTileX = place.getX();
			lastPlaceTileY = place.getY();

			//player1's turn
			this.playerTurn = 1;

			return true;
		}
		// checks if it is the second player's turn and if the spot where the tile is being placed is empty
		else if ((this.playerTurn == 1) && (this.board.getGrid()[place.getX()][place.getY()] == board.EMPTY_COLOR)) {
			//player1 places the tile in the empty tile
			this.board.getGrid()[place.getX()][place.getY()] = board.BLUE_COLOR;

			//updates the location of latest placed tile
			lastPlaceTileX = place.getX();
			lastPlaceTileY = place.getY();

			//player0's turn
			this.playerTurn = 0;

			return true;
		}
		return false;
	}

	/**
	 * Checks if the player can undo a move
	 *
	 * @param undo
	 * @return
	 */
	public boolean undoMove(UndoMove undo) {
		//Make sure it is NOT the undo-er's turn
		int playerId = -1;
		if (undo.getPlayer() instanceof HexHumanPlayer) {
			playerId = ((HexHumanPlayer) undo.getPlayer()).getPlayerId();
			if (playerId == this.playerTurn) {
				return false;
			}
			// finds the place in the 2d array that was last placed, removes it, then makes the player turn to who undid it
			lastPlaceTileX = -1;
			lastPlaceTileY = -1;
			this.board.getGrid()[lastPlaceTileX][lastPlaceTileY] = board.EMPTY_COLOR;
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Checks if the player can create a new game
	 *
	 * @param newGame
	 * @return
	 */
	public boolean newGameMove(NewGameMove newGame) {
			if (this.playerTurn == 1) {
				this.playerTurn = 0; //resets to player 1 turn
			}


			if (this.hasWon) {
				this.hasWon = false; //resets to no one has won
			}


			if ("red".equals(this.playerWinner) || "blue".equals(this.playerWinner)) {
				this.playerWinner = null; //resets the winner to no one
			}

			this.board = new HexBoard(11); // don't know if this correct

			return true;
		}


	public boolean isGameOver() {
		return this.hasWon = true;
	}

	public int getLastPlaceTileX() {
		return this.lastPlaceTileX;
	}
	public int getLastPlaceTileY() {
		return this.lastPlaceTileY;
	}

}


