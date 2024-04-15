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
public class HexHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */
	
	// The TextView the displays the current counter value
	private TextView testResultsTextView;
	
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





	}

	/**
	 * this method gets called when the user clicks the '+' or '-' button. It
	 * creates a new CounterMoveAction to return to the parent activity.
	 * 
	 * @param button
	 * 		the button that was clicked
	 */
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		//if (game == null) return;

		//clears the EditText
		testResultsTextView.setText("");

		//new instance of game state is created using the default constructor
		HexState firstInstance = new HexState();

		//create the first copy
		HexState firstCopy = new HexState(firstInstance);

		//while the game is running
		//while (!firstInstance.isGameOver()) {

			//PlaceTile placeTile = new PlaceTile(this, firstInstance.getLastPlaceTileX(), firstInstance.getLastPlaceTileY());

			UndoMove undo = new UndoMove(this);

			NewGameMove newGameMove = new NewGameMove(this);

			//if a player places a tile it will show as a message
			//if (firstInstance.placeTile(placeTile)) {
				//int x = firstInstance.getLastPlaceTileX();
				//int y = firstInstance.getLastPlaceTileY();
				//testResultsTextView.append("Player " + getPlayerId() + "has placed a piece on " + "x: " + x +" y: "+ y);
				//not sure if we need this return;
				//return;
			//if the player undos their move
			//} else if (firstInstance.undoMove(undo)) {
				//testResultsTextView.append("Player " + getPlayerId() + " took back their move.");

				//return;
			//if the player starts a new game
			//} else if (firstInstance.newGameMove(newGameMove)) {
				//testResultsTextView.append("Player " + getPlayerId() + "started a new game. ");

				//return;
			//}

			//winner message
			//if (firstInstance.isGameOver()) {
				//testResultsTextView.append("Player " + getPlayerId() + " is the winner!");

				//return;
			//}
		//}

		//create 2nd new instance of hexState
		HexState secondInstance = new HexState();

		//create the 2nd copy
		HexState secondCopy = new HexState(secondInstance);

		//call toString() on firstCopy and second Copy
		String firstCopyStr = firstCopy.toString();
		String secondCopyStr = secondCopy.toString();
		if(firstCopyStr.equals(secondCopyStr)){
			testResultsTextView.append("The two game states are identical\n\n");
		}else{
			testResultsTextView.append("The two game states are not identical\n\n");
		}
		testResultsTextView.append("First copy state:\n"+firstCopyStr+"\n\n");
		testResultsTextView.append("Second copy state:\n"+secondCopyStr+"\n\n");

	}// onClick
	
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
		// initializes the testResultsTextView variable with a reference to the multi-line edit text
		// this.testResultsTextView = (EditText) activity.findViewById(R.id.editTextMultiLine);

		// registers the human player as a listener for the button
		// Button runTestButton = (Button) activity.findViewById(R.id.buttonRunTest);
		// runTestButton.setOnClickListener(this);
		}
}// class CounterHumanPlayer

