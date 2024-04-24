package edu.up.cs301.hex;

import java.io.Serializable;
import java.util.ArrayList;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.gameConfiguration.*;

import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * this is the primary activity for Counter game
 * 
 * @author Andrew M. Nuxoll
 * @author Steven R. Vegdahl
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 * @version March 2024
 */
public class HexMainActivity extends GameMainActivity implements Serializable {
	// serial ID
	public static final long serialVersionUID = 202442385127L;

	// the port number that this game will use when playing over the network
	private static final int PORT_NUMBER = 2234;

	// to play the music
	private MediaPlayer musicPlayer;

	MediaPlayer sfx;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// initializes the MediaPlayer and plays the music
		musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lebronmysun);
		musicPlayer.setLooping(true);
		musicPlayer.start();

		sfx = MediaPlayer.create(getApplicationContext(), R.raw.bubblepop);
	}

	/**
	 * Create the default configuration for this game:
	 * - one human player vs. one computer player
	 * - minimum of 1 player, maximum of 2
	 * - one kind of computer player and one kind of human player available
	 * 
	 * @return
	 * 		the new configuration object, representing the default configuration
	 */
	@Override
	public GameConfig createDefaultConfig() {
		
		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		
		// a human player player type (player type 0)
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new HexHumanPlayer(name, new HexState());
			}});
		
		// a computer player type (player type 1)
		playerTypes.add(new GamePlayerType("Dumb Computer Player") {
			public GamePlayer createPlayer(String name) {
				return new HexComputerPlayer1(name);
			}});
		
		// a computer player type (player type 2)
		playerTypes.add(new GamePlayerType("Smart Computer Player ") {
			public GamePlayer createPlayer(String name) {
				return new HexComputerPlayer2(name);
			}});

		// Create a game configuration class for Counter:
		// - player types as given above
		// - from 1 to 2 players
		// - name of game is "Counter Game"
		// - port number as defined above
		GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Hex",
				PORT_NUMBER);

		// Add the default players to the configuration
		defaultConfig.addPlayer("Human", 0); // player 1: a human player
		defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
		
		// Set the default remote-player setup:
		// - player name: "Remote Player"
		// - IP code: (empty string)
		// - default player type: human player
		defaultConfig.setRemoteData("Remote Player", "", 0);
		
		// return the configuration
		return defaultConfig;
	}//createDefaultConfig

	// BY Monday.
	/**
	 * create a local game
	 * 
	 * @return
	 * 		the local game, a counter game
	 */
	@Override
	public LocalGame createLocalGame(GameState state) {
		if (state == null)
			state = new HexState(); //hexstate parameters originally it was zero since it took in int as a param but now we removed it
		return new HexLocalGame(state);
	}

	/**
	 External Citation
	 Date: 23 April 2024
	 Problem: Did not know how to add music to the game
	 Resource:
	 https://www.geeksforgeeks.org/how-to-add-audio-files-to-android-app-in-android-studio/
	 Solution: I had to add a separate MediaPlayer class to play music
	 */
	protected void onDestroy() {
		super.onDestroy();
		// Release MediaPlayer resources
		if (musicPlayer != null) {
			musicPlayer.release();
			musicPlayer = null;
		}
	}

}
