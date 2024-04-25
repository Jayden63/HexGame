package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import android.graphics.Color;
import java.io.Serializable;


/**
 * A computer-version of a hex-player
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 * @version March 2024
 */


public class HexComputerPlayer2 extends GameComputerPlayer implements Serializable {

	// serial ID
	public static final long serialVersionUID = 202442391220L;


	/**
	 * Constructor for objects of class HexComputerPlayer2
	 *
	 * @param name the player's name
	 */
	public HexComputerPlayer2(String name) {
		super(name);
	}


	/**
	 * Called when the game state updates
	 *
	 * @param info the game info, should be an instance of HexState
	 */
	protected void receiveInfo(GameInfo info) {
		if (info instanceof HexState) {
			HexState state = (HexState) info;
			if (state.getPlayerTurnID() != this.playerNum) {
				return; // Not this player's turn
			}
			try {
				Thread.sleep(250); // Wait for .25 seconds before making a move
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}

			// Find the best strategic move
			int[] move = findStrategicMove(state);
			if (move != null) {
				// check again to make sure the tile is still empty before making the move
				if (state.grid[move[0]][move[1]].getColor() == Color.WHITE) {
					// send a move action to the game
					game.sendAction(new HexMoveAction(this, move[0], move[1]));
					// after making a move, we should return to avoid making further moves
				}
			}
		}
	}//recieveInfo


	/**
	 * Finds a strategic move based on the current state and the player's number.
	 *
	 * @param state the current state of the Hex game
	 * @return coordinates for the move as [x, y], or null if no move is found
	 */
	private int[] findStrategicMove(HexState state) {
		int gridSize = state.gridSize;
		int[] bestMove = null;
		double bestScore = Double.NEGATIVE_INFINITY;

		// Loop through the entire grid to evaluate the best move
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				// Check if the current position is empty
				if (state.grid[x][y].getColor() == Color.WHITE) {
					// Evaluate the move based on the AI's strategy
					double score = evaluateMove(state, x, y, playerNum);
					if (score > bestScore) {
						bestScore = score;
						bestMove = new int[] {x, y};
					}
				}
			}
		}

		// If a move was found, return it; otherwise, return null
		return bestMove;

	}//findStrategicMove


	/**
	 * evaluateMove() evaluates the score of a game for the smart AI player
	 * @param state // The state of the game
	 * @param x // the hexTile x position in the hex grid array
	 * @param y // the hexTile y position in the hex grid array
	 * @param playerNum // To determine the AI's player color
	 * @return double // The score of the move
	 */
	private double evaluateMove(HexState state, int x, int y, int playerNum) {

		// The value to return
		double score = 0;

		// Gets the size of the grid
		int gridSize = state.gridSize;

		if (playerNum == 1) { // Red plays horizontally
			score -= Math.min(x, gridSize - 1 - x);
		} else { // Blue plays vertically
			score -= Math.min(y, gridSize - 1 - y);
		}

		// Defines the possible direction of movements in the grid
		int[][] directions =
				{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
		for (int[] dir : directions) {
			// To calculate the new coordinates
			int nx = x + dir[0];
			int ny = y + dir[1];

			// Plays move if valid move & tile in new coordinate is player color
			if (state.isValid(nx, ny) && state.getPiece(nx, ny) == (state.getPlayerColor() == HexState.BLUE ?
					HexState.RED : HexState.BLUE)) {
				score += 5;
			}
		}

		return score;
	}//evaluateMove
}