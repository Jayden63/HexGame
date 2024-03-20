package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
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
	private HexTile[][] placedLocation;
	private boolean hasWon;
	private HexBoard board;
	private String playerWinner;
	private Player player1;
	private Player player2;

	private PlaceTile hexPlaceTile;


	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 *
	 * @param
	 */
	public HexState() {
		this.playerTurn = 0;
		this.hasWon = false;
		this.board = new HexBoard(11);
		this.player1 = new Player("player 1", "red");
		this.player2 = new Player("player2", "red");
		this.playerWinner = null;
	}

	/**
	 * copy constructor; makes a copy of the original object
	 *
	 * @param orig the object from which the copy should be made
	 */
	public HexState(HexState orig) {
		// set the counter to that of the original
		this.playerTurn = orig.playerTurn;
		this.hasWon = orig.hasWon;
		this.playerWinner = orig.playerWinner;
		this.board = new HexBoard(orig.board);
		this.player1 = new Player(orig.player1);
		this.player2 = new Player(orig.player2);
	}

	//toString for HexState
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
		if (this.board.getGrid()[place.getX()][place.getY()] == null) {
			//placedlocation keeps track of the current placed location x, y
			for (int x = 0; x < placedLocation.length; x++) {
				for (int y = 0; y < placedLocation[x].length; y++) {
					placedLocation[x][y] = board.getGrid()[place.getX()][place.getY()];
				}
			}
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
		// finds the place in the 2d array that was last placed, removes it, then makes the player turn to who undid it
		if (this.placeTile(hexPlaceTile)) {
			//currently placed location set to empty
			placedLocation[hexPlaceTile.getX()][hexPlaceTile.getY()] = null;
			return true;
		}
		return false;
	}

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
}


