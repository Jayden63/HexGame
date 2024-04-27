package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.util.Log;

import java.io.Serializable;

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
public class HexHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, Serializable {
	// serial ID
	public static final long serialVersionUID = 202442385148L;

	/* instance variables */

	//Whose turn is it / winner notification text view
	private TextView turnTV;
	private TextView playerOneText;
	private TextView playerTwoText;

	// The most recent game state, as given to us by the HexLocalGame
	private HexState gameState;

	// The android activity that we are running
	private GameMainActivity myActivity;
	private Hex_SurfaceView mySurfaceView;

	// Variable to keep track if names have been set
	private boolean namesInitialized = false;

	// Settings buttons
	Button newGameButton;
    // Exit game button
	Button exitGameButton;

	@SuppressLint("UseSwitchCompatOrMaterialCode")
	Switch musicSwitch;

	/**
	 * HexHumanPlayer constructor
	 *
	 * @param name // The human player name
	 * @param gameState // Reference to the state of the game
	 */
	public HexHumanPlayer(String name, HexState gameState) {
		super(name);
		this.gameState = gameState;

	}


	/**
	 * Returns the GUI's top view object
	 *
	 * @return
	 * 		the top object in the GUI's view hierarchy
	 */
	public View getTopView() {
		return myActivity.findViewById(R.id.top_layout);
	}


	/**
	 * updateDisplay()
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

		// Declares the turnTV as turnView
		this.turnTV = (TextView) myActivity.findViewById(R.id.turnView);

		// Declares player name text
		this.playerOneText = myActivity.findViewById(R.id.PLAYER1_textView);
		this.playerTwoText = myActivity.findViewById(R.id.PLAYER2_textView);


		// To display player names
		String playerOneName = " You";
		String playerTwoName = " Opponent";

		// Sets the player names based on which player goes first
		if (!namesInitialized) {
			if (this.gameState.getPlayerTurnID() == 1) {
				String temp = playerOneName;
				playerOneName = playerTwoName;
				playerTwoName = temp;
			}
			playerOneText.setText(playerOneName);
			playerTwoText.setText(playerTwoName);
			namesInitialized = true;
		}


		// Updates the turn view based on the last player's move
		// Red always goes first regardless if it's AI or human player's turn
		String turnText = "Red's Turn";
		turnTV.setTextColor(Color.RED);

		if (this.gameState.getPlayerColor() == Color.BLUE) {
			turnText = "Blue's Turn";
			turnTV.setTextColor(Color.BLUE);
		}

		// Update the surface view
		this.mySurfaceView.setHexState(this.gameState);

		// If player has won, change text to game over
		if (gameState.blueWins() || gameState.redWins()) {
			turnText = "GAME OVER";
			turnTV.setTextColor(Color.BLACK);
		}

		// Updates the turnTV textView
		this.turnTV.setText(turnText);


	}//updateDisplay


	/**
	 * callback method when we get a message (e.g., from the game)
	 *
	 * @param info
	 * 		the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		// Ignore the message if it's not a HexState message
		if (!(info instanceof HexState)) return;

		// Update our state; then update the display
		this.gameState = (HexState)info;
		updateDisplay();
	}


	/**
	 * callback method--our game has been chosen to be the GUI,
	 * called from the GUI thread
	 *
	 * @param activity
	 * 		the activity under which we are running
	 */
	public void setAsGui(GameMainActivity activity) {

		// Remember the activity
		this.myActivity = (HexMainActivity) activity;

		// Loads the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);

		this.playerOneText = activity.findViewById(R.id.PLAYER1_textView);
		this.playerTwoText = activity.findViewById(R.id.PLAYER2_textView);
		this.turnTV = activity.findViewById(R.id.turnView);

		mySurfaceView = myActivity.findViewById(R.id.hex_grid);

		ImageButton btn1 = (ImageButton) activity.findViewById(R.id.settings_button);
		ImageButton btn2 = (ImageButton) activity.findViewById(R.id.rules_button);
		ImageButton btn3 = (ImageButton) activity.findViewById(R.id.skip_button);

		btn1.setOnClickListener(v -> ShowSettings());
		btn2.setOnClickListener(v -> ShowRuleBook());
		btn3.setOnClickListener(view -> ((HexMainActivity) myActivity).playNextRandomSong());

		mySurfaceView.setOnTouchListener(this);

	}//setAsGui


	/**
	 * ShowSettings pops up the layout dialog box
	 */
	public void ShowSettings() {
		AlertDialog.Builder popDialog = new AlertDialog.Builder(myActivity);
		LayoutInflater inflater = (LayoutInflater) myActivity.getLayoutInflater();

		View viewLayout = inflater.inflate(R.layout.activity_dialog,
				(ViewGroup) myActivity.findViewById(R.id.layout_dialog));

		this.newGameButton =(Button) viewLayout.findViewById(R.id.newgame_button);
		this.exitGameButton = (Button) viewLayout.findViewById(R.id.exitgame_button);
		this.musicSwitch = (Switch) viewLayout.findViewById(R.id.switch2);

		// TetOnClickListener to each button
		newGameButton.setOnClickListener(v -> myActivity.restartGame());
		exitGameButton.setOnClickListener(view -> {
			myActivity.finish();
			System.exit(0);
			});

		// Title for the settings popup
		popDialog.setTitle("Settings");
		popDialog.setView(viewLayout);

		// Creates the music switch
		createMusicSwitch();

		// Ok button for closing the popup
		popDialog.setPositiveButton("OK",
				(dialogInterface, i) -> dialogInterface.dismiss());

		// Create the popup
		popDialog.create();
		popDialog.show();

	}//ShowSettings

	/**
	 * ShowRuleBook()
	 * Method for rules pop up
	 * Contains the Text Views for the rules of the game
	 */
	public void ShowRuleBook() {
		AlertDialog.Builder popDialog = new AlertDialog.Builder(myActivity);
		LayoutInflater inflater = (LayoutInflater) myActivity.getLayoutInflater();

		View viewLayout = inflater.inflate(R.layout.activity_rule,
				(ViewGroup) myActivity.findViewById(R.id.layout_rule));

		// Sets the title of the popup page
		popDialog.setTitle("How To Play");
		popDialog.setView(viewLayout);

		// Ok button for closing the popup
		popDialog.setPositiveButton("OK",
				(dialogInterface, i) -> dialogInterface.dismiss());

		// Create the popup
		popDialog.create();
		popDialog.show();

	}//showRuleBook

	/**
	 * createMusicSwitch()
	 * switch for turning on or off the background music
	 *
	 */
	public void createMusicSwitch() {

		musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
			if (!isChecked) {
				((HexMainActivity) myActivity).pauseMusic();
			} else {
				((HexMainActivity) myActivity).playMusic();
			}
		});
	}//createMusicSwitch

	/**
	 * onTouch function is in charge of
	 *
	 * @param view The view the touch event has been dispatched to.
	 * @param event The MotionEvent object containing full information about
	 *        the event.
	 * @return true // if a touch has been performed
	 */
	@Override
	public boolean onTouch(View view, MotionEvent event) {

		// The coordinates registered by the touch
		float x = event.getX();
		float y = event.getY();


		// If the action is a tap
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// Check if touch is inside any hex tile in the grid
			for (int i = 0; i < gameState.grid.length; i++) {
				for (int j = 0; j < gameState.grid.length; j++) {

					// Checks if the tapped location is within a hexTile
					if (gameState.grid[i][j].isTouched(x, y)) {

						//sends a HexMoveAction
						game.sendAction(new HexMoveAction(this, i, j));
						((HexMainActivity) myActivity).soundEffect();

						return true;
					}

				}
			}
		}
		return false;
	}//onTouch
}