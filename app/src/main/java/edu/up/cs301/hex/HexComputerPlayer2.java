package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.hex.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second. This computer player does, however, have an option to
 * display the game as it is progressing, so if there is no human player on the
 * device, this player will display a GUI that shows the value of the counter
 * as the game is being played.
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



/**
 * External Citation
 * Date: 23 February 2024
 * Problem: Could not find out a algorithm for smart ai
 * Resource:
 * <a href="https://stackoverflow.com/questions/75662140/fastest-way-to-find-a-winner-in-the-game-of-hex">...</a>
 * Solution: I used the example code from this post as the basis for the code below
 *  change is the evaulate move and find strategic move
 */

public class HexComputerPlayer2 extends GameComputerPlayer {

	private Random random;

	/**
	 * Constructor for objects of class HexComputerPlayer2
	 *
	 * @param name the player's name
	 */
	public HexComputerPlayer2(String name) {
		super(name);
		random = new Random();
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
					return;
				}
			}


		}
	}

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
	}


	private double evaluateMove(HexState state, int x, int y, int playerNum) {
		double score = 0;
		int gridSize = state.gridSize;

		if (playerNum == 1) { // Red plays horizontally
			score -= Math.min(x, gridSize - 1 - x);
		} else { // Blue plays vertically
			score -= Math.min(y, gridSize - 1 - y);
		}

		int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
		for (int[] dir : directions) {
			int nx = x + dir[0];
			int ny = y + dir[1];
			if (state.isValid(nx, ny) && state.getPiece(nx, ny) == (playerNum == 1 ? HexState.RED : HexState.BLUE)) {
				score += 5;
			}
		}

		return score;
	}}