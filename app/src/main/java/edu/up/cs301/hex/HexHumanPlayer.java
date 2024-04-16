package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.util.Log;
import edu.up.cs301.hex.R;
import android.widget.EditText;
/**
 * A GUI of a counter-player. The GUI displays the current value of the counter,
 * and allows the human player to press the '+' and '-' buttons in order to
 * send moves to the game.
 *
 * Just for fun, the GUI is implemented so that if the player presses either button
 * when the counter-value is zero, the screen flashes briefly, with the flash-color
 * being dependent on whether the player is player 0 or player 1.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 *  @version March 2024
 */
public class HexHumanPlayer extends GameHumanPlayer {

	/* instance variables */


	//Whose turn is it / winner notification text view
	TextView turnTV;

	// the most recent game state, as given to us by the CounterLocalGame
	private HexState gameState;

	// the android activity that we are running
	private GameMainActivity myActivity;
	private Hex_SurfaceView mySurfaceView;
	/**
	 * constructor
	 * @param name
	 * 		the player's name
	 */
	public HexHumanPlayer(String name, HexState gameState) {
		super(name);
		this.gameState = gameState;
	}


	//Why doesn't his already exist??
	public int getPlayerId() { return this.playerNum; }
	/**
	 * Returns the GUI's top view object
	 *
	 * @return
	 * 		the top object in the GUI's view heirarchy
	 */
	public View getTopView() {
		return myActivity.findViewById(R.id.top_layout);
	}

	public void setSurfaceView(Hex_SurfaceView surfaceView) {
		if (surfaceView == null) {
			throw new IllegalArgumentException("SurfaceView cannot be null.");
		}
		this.mySurfaceView = surfaceView;
	}



	/**
	 * sets the counter value in the text view
	 */
	protected void updateDisplay() {
		if (mySurfaceView == null) {
			Log.e("HexHumanPlayer", "surfaceView is not initialized.");

			return;
		}

		if (gameState == null) {
			Log.e("HexHumanPlayer", "gameState is not initialized.");
			return;
		}

		//Tell the user whose turn it is
		String turnText = "Blue's turn";
		if (this.gameState.player1_turn) {
			turnText = "Red's turn";
		}
		this.turnTV.setText(turnText);

		//Update the surface view
		this.mySurfaceView.setHexState(this.gameState);


	}

	/**
	 * callback method when we get a message (e.g., from the game)
	 *
	 * @param info
	 * 		the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		// ignore the message if it's not a CounterState message
		if (!(info instanceof HexState)) return;

		// update our state; then update the display
		this.gameState = (HexState)info;
		updateDisplay();
	}

	/**
	 * callback method--our game has been chosen/rechosen to be the GUI,
	 * called from the GUI thread
	 *
	 * @param activity
	 * 		the activity under which we are running
	 */
	public void setAsGui(GameMainActivity activity) {

		// remember the activity
		this.myActivity = activity;

		// loads the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);

		//this.turnTV = activity.findViewById(R.id.turnView);

	}
}// class CounterHumanPlayer