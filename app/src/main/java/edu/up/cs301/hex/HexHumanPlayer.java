package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.util.Log;
import edu.up.cs301.hex.R;
import android.widget.EditText;
/**
 * A GUI of a hex-player.
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
public class HexHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

	/* instance variables */

	//Whose turn is it / winner notification text view
	TextView turnTV;
	TextView playerTurnID_View;

	// the most recent game state, as given to us by the CounterLocalGame
	private HexState gameState;

	// the android activity that we are running
	private GameMainActivity myActivity;
	private Hex_SurfaceView mySurfaceView;



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




	/**
	 * updates the gameState to know who's turn it is
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
		String turnIdText = "current player ID: " + Integer.toString(gameState.getPlayerTurnID());
		String turnText = "Red's turn";
		if (this.gameState.getPlayerTurnID() == 1) {
			turnText = "Blue's turn";
		}

		this.turnTV.setText(turnText);

		this.playerTurnID_View.setText(turnIdText);
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
		this.myActivity = (HexMainActivity) activity;

		// loads the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);

		this.turnTV = activity.findViewById(R.id.turnView);
		this.playerTurnID_View = activity.findViewById(R.id.playerTurnIDView);

		mySurfaceView = myActivity.findViewById(R.id.hex_grid);



		mySurfaceView.setOnTouchListener(this);



	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {

		// The coordinates registered by the touch
		float x = event.getX();
		float y = event.getY();


		// If the action is a tap
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Check if touch is inside any hex tile in the grid
			for (int i = 0; i < gameState.grid.length; i++) {
				for (int j = 0; j < gameState.grid.length; j++) {
					//Step 1:  figure row/col of corresponding HexTile that was touched (if any)

					//Step 2:  Create a HexMoveAction that indicates this row/col is being played

					//Step 3:  game.sendAction(pta)

					if (gameState.grid[i][j].isTouched(x, y)) {
						game.sendAction(new HexMoveAction(this, i, j));
						return true;
					}

				}
			}
		}
		return false;

	}


	@Override
	public void onClick(View v) {

	}
}