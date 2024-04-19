package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.graphics.Color;
import android.util.Log;

/**
 * A class that represents the state of a game. In our counter game, the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
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
public class HexLocalGame extends LocalGame {


	// the game's state
	private HexState gameState;

	/**
	 * can this player move
	 *
	 * @return
	 * 		true, because all player are always allowed to move at all times,
	 * 		as this is a fully asynchronous game
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return true;
	}

	/**
	 * This ctor should be called when a new counter game is started
	 */
	public HexLocalGame(GameState state) {
		// initialize the game state, with the counter value starting at 0
		if (! (state instanceof HexState)) {
			state = new HexState();
		}
		this.gameState = (HexState)state;
		super.state = state;
	}



	/**
	 * The only type of GameAction that should be sent is HexMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		Log.i("action", action.getClass().toString());

		if (action instanceof HexMoveAction) {
			HexMoveAction placePieceAction = (HexMoveAction) action;

			// Check if the move is legal
			if (gameState.isLegalMove(placePieceAction.getRow(), placePieceAction.getCol())) {
				// Place the piece on the board
				gameState.placeTileAction(placePieceAction.getRow(), placePieceAction.getCol());

				gameState.switchTurns();
				return true; // Move was successfully processed

			} else {
				return false; // Move was not legal
			}
		}
		return false;

	}//makeMove



	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new HexState(this.gameState));

	}//sendUpdatedSate



	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 *
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		if (gameState.blueWins()) {
			return gameState.getPlayerTurnID() + " BLUE WINS! ";

		} else if (gameState.redWins()) {
			return gameState.getPlayerTurnID() + " RED WINS! ";
		}

		return null;


	}

}// class CounterLocalGame