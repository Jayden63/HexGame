package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View.OnClickListener;
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
	TextView turnTV;
	TextView playerTurnID_View;

	// the most recent game state, as given to us by the CounterLocalGame
	private HexState gameState;

	private HexTile tile;

	// the android activity that we are running
	private GameMainActivity myActivity;
	private Hex_SurfaceView mySurfaceView;
//new game button
	Button newGameButton;
//exit game button
	Button exitGameButton;

	Switch musicSwitch;

	private MediaPlayer sfx;

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

		//declares the turnTV as turnView
		this.turnTV = (TextView) myActivity.findViewById(R.id.turnView);


		//Tell the user whose turn it is
		String turnIdText = "current player ID: " + Integer.toString(gameState.getPlayerTurnID());
		String turnText = "Red's turn";
		turnTV.setTextColor(Color.RED);

		if (this.gameState.getPlayerTurnID() == 1) {
			turnText = "Blue's turn";
			turnTV.setTextColor(Color.BLUE);

		}



		//this.playerTurnID_View.setText(turnIdText);
		//Update the surface view
		this.mySurfaceView.setHexState(this.gameState);


		//if player has won, change text to game over
		if (gameState.blueWins() || gameState.redWins()) {
			turnText = "GAME OVER";
			turnTV.setTextColor(Color.BLACK);
		}


		//updates the turnTV textView
		this.turnTV.setText(turnText);
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
	 * callback method--our game has been chosen to be the GUI,
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
		//this.playerTurnID_View = activity.findViewById(R.id.playerTurnIDView);

		mySurfaceView = myActivity.findViewById(R.id.hex_grid);

		ImageButton btn1 = (ImageButton) activity.findViewById(R.id.settings_button);
		ImageButton btn2 = (ImageButton) activity.findViewById(R.id.rules_button);

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowSettings();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowRuleBook();
			}
		});

		mySurfaceView.setOnTouchListener(this);

	}

	public void ShowSettings() {
		AlertDialog.Builder popDialog = new AlertDialog.Builder(myActivity);
		LayoutInflater inflater = (LayoutInflater) myActivity.getLayoutInflater();

		View viewLayout = inflater.inflate(R.layout.activity_dialog,
				(ViewGroup) myActivity.findViewById(R.id.layout_dialog));

		TextView item1 = (TextView) viewLayout.findViewById(R.id.textView);
		TextView item2 = (TextView) viewLayout.findViewById(R.id.textView2);
		this.newGameButton =(Button) viewLayout.findViewById(R.id.newgame_button);
		this.exitGameButton = (Button) viewLayout.findViewById(R.id.exitgame_button);
		this.musicSwitch = (Switch) viewLayout.findViewById(R.id.switch2);
				newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			myActivity.restartGame();
			}
		});
				exitGameButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						myActivity.finish();
						System.exit(0);
					}
				});



		popDialog.setTitle("Settings");
		popDialog.setView(viewLayout);

		musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					((HexMainActivity) myActivity).pauseMusic();
				} else {
					((HexMainActivity) myActivity).playMusic();
				}
			}
		});

		/*SeekBar seek1 = (SeekBar) viewLayout.findViewById(R.id.seekBar);

		seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				//tv.setText("Value of :" + progress);
				item1.setText("SFX Volume: " + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		SeekBar seek2 = (SeekBar) viewLayout.findViewById(R.id.seekBar2);
		seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				//tv.setText("Value of :" + progress);
				item2.setText("Music Volume: " + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});*/

		popDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
		popDialog.create();
		popDialog.show();
	}

	//code by Chengen
	//
	//method for the rules popup
	//contains the text views for the rules of the game
	//
	public void ShowRuleBook() {
		AlertDialog.Builder popDialog = new AlertDialog.Builder(myActivity);
		LayoutInflater inflater = (LayoutInflater) myActivity.getLayoutInflater();

		View viewLayout = inflater.inflate(R.layout.activity_rule,
				(ViewGroup) myActivity.findViewById(R.id.layout_rule));

		TextView item2 = (TextView) viewLayout.findViewById(R.id.rule_text);

		popDialog.setTitle("How To Play");
		popDialog.setView(viewLayout);


		popDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
		popDialog.create();
		popDialog.show();
	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {

		HexMainActivity activity = new HexMainActivity();
		// The coordinates registered by the touch
		float x = event.getX();
		float y = event.getY();


		// If the action is a tap
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Check if touch is inside any hex tile in the grid
			for (int i = 0; i < gameState.grid.length; i++) {
				for (int j = 0; j < gameState.grid.length; j++) {

					//checks if the tapped location is within a hexTile
					if (gameState.grid[i][j].isTouched(x, y)) {

						//sends a HexMoveAction
						game.sendAction(new HexMoveAction(this, i, j));

						return true;
					}

				}
			}
		}
		return false;

	}



}