package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import android.util.Log;

import java.io.Serializable;

/**
 * A class that represents the state of a game. In our hex game
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
public class HexLocalGame extends LocalGame implements Serializable {

	// serial ID
	public static final long serialVersionUID = 202442384845L;

	// the game's state
	private final HexState gameState;

	/**
	 * can this player move
	 *
	 * @return
	 * 		true, because all player are always allowed to move at all times,
	 * 		as this is a fully asynchronous game
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return gameState.getPlayerTurnID() == playerIdx;
	}

	/**
	 * This constructor should be called when a new hex game is started
	 */
	public HexLocalGame(GameState state) {
		// initialize the Hext Game State
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
			HexMoveAction ppa = (HexMoveAction) action;
			int row = ppa.getRow();
			int col = ppa.getCol();

			return gameState.placeTileAction(row,col);

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

			return "BLUE WINS! ";

		} else if (gameState.redWins()) {

			return "RED WINS! ";
		}

		return null;
	}//checkIfGameOver
}