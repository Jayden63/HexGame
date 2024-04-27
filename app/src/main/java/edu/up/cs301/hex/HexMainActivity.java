package edu.up.cs301.hex;

import java.io.Serializable;
import java.util.ArrayList;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.gameConfiguration.*;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import java.util.Random;


/**
 * this is the primary activity for Hex Game
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

/**
 * Main Activity Class for Hex Game
 *
 * Status Report:
 * - The game is built using the specified game framework, and sticking to the initial design and requirements outlined.
 * - Current implementation supports all defined rules of play. The game maintains the core gameplay mechanics of Hex.
 *
 * - Known Bugs:
 *    + Currently,none
 *    + We have fixed previous bugs like the CheckIfGameOver and our ComputerPlayer not working
 *
 * - Features/Enhancements:
 *    + Smart AI implemented, provides a harder experience for players than the Dumb AI.
 *    + The GUI is designed to be user-friendly and operates effectively across devices.
 *    + Game supports network play, allowing multiplayer sessions over network connections.
 *    + Code adheres to the CS301 coding standard and is well documented
 *
 * - Additional features not initially required but implemented to enhance gameplay:
 *    + Game supports both portrait and landscape modes
 *    + The game includes custom sound effects
 *    + Incorporates complex gestures like zoom in and out
 *    + A custom startup screen and “how to play” guide are implemented
 *    + Music for the game, and sound effects for each tile placement
 *    + music switch to turn off the music
 *    + skip music button
 *    + Pop up ads
 *    + How to play rulebook dialog
 *    + Color changing animation background
 * All features and issues are documented to ensure clarity and transparency in grading.
 */



public class HexMainActivity extends GameMainActivity implements Serializable {
	// serial ID
	public static final long serialVersionUID = 202442385127L;

	// the port number that this game will use when playing over the network
	private static final int PORT_NUMBER = 2234;

	// to play the music
	private MediaPlayer musicPlayer;

	// Array of songs
	public int[] songResources =
			{R.raw.lebronmysun, R.raw.home_depot_theme,R.raw.lift_urself,R.raw.jamal,R.raw.sza};
	public Random random = new Random();

	private int lastSongIndex = -1; // keeps track of last song played


	/**
	 * onCreate() plays the music and sets up the ad in the game
	 *
	 * @param savedInstanceState If the activity is being re-initialized after
	 *     previously being shut down then this Bundle contains the data it most
	 *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
	 *
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// initializes the MediaPlayer and plays the music
		setupMusicPlayer();
		countDownForAd();
	}//onCreate


	/**
	 * External Citation
	 * Date: April 2024
	 * Problem: Didn't know how to add music to the game
	 * Resource: https://www.youtube.com/watch?v=efY_P8mKUdA
	 * Solution: I used the YouTube video as a reference for adding music
	 * to the game
	 */

	/**
	 * setupMusicPlayer sets up the music player
	 * Which is used when the game starts
	 */
	private void setupMusicPlayer() {
		// Initialize MediaPlayer with a random song
		int randomIndex = random.nextInt(songResources.length);
		musicPlayer = MediaPlayer.create(this, songResources[randomIndex]);
		musicPlayer.setLooping(false); // Ensure the player does not loop songs

		// Set up listener to play a random song when one finishes
		musicPlayer.setOnCompletionListener(mp -> playNextRandomSong());

		musicPlayer.start();
	}//setupMusicPlayer


	/**
	 * playMusic method handles starting and resuming the music in the game
	 */
	public void playMusic() {
		if (musicPlayer == null) {
			int randomIndex = random.nextInt(songResources.length);
			musicPlayer = MediaPlayer.create(this, songResources[randomIndex]);
			musicPlayer.setLooping(true);
			musicPlayer.setVolume(0.5f, 0.5f);
			musicPlayer.setOnCompletionListener(mp -> {
				playNextRandomSong(); // Play next random song when current ends
			});
			musicPlayer.start();
		} else {
			musicPlayer.start(); // Resume playing if already initialized
		}
	}//playMusic


	/**
	 * 	Method to pause the music
	 */
	public void pauseMusic() {
		if (musicPlayer != null && musicPlayer.isPlaying()) {
			musicPlayer.pause();
		}
	}//pauseMusic


	/**
	 * playNextRandomSong skips the current song
	 * and plays a random song found in the music array
	 */
	public void playNextRandomSong() {
		// Release the current MediaPlayer
		if (musicPlayer != null) {
			musicPlayer.release();
		}

		int randomIndex;
		if (songResources.length > 1) {  // Ensure there is more than one song to avoid infinite loop
			do {
				randomIndex = random.nextInt(songResources.length);
			} while (randomIndex == lastSongIndex);  // Ensure not to repeat the last song
		} else {
			randomIndex = 0;  // Only one song available, no choice but to repeat
		}

		lastSongIndex = randomIndex;  // Update the last song index to the new song

		musicPlayer = MediaPlayer.create(this, songResources[randomIndex]);

		// Set up the MediaPlayer to listen for completion again
		musicPlayer.setOnCompletionListener(mp -> {
			// Recursive call to change to another random song when current ends
			playNextRandomSong();
		});

		musicPlayer.start();  // Start playing the new random song
	}//playNextRandomSong


	public void soundEffect() {
		// Assuming soundEffect is an int representing a resource ID
		MediaPlayer soundEffects = MediaPlayer.create(this, R.raw.pop); // Example using R.raw.sound_effect
		soundEffects.setVolume(1f,1f);
		soundEffects.setLooping(false); // Ensure the sound does not loop

		// Set a listener to release the MediaPlayer resources once the sound is complete
		// Release the MediaPlayer resources
		soundEffects.setOnCompletionListener(MediaPlayer::release);

		soundEffects.start(); // Start playing the sound effect
	}//soundEffects


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

		// Create a game configuration class for Hex:
		// - player types as given above
		// - from 1 to 2 players
		// - name of game is "Hex Game"
		// - port number as defined above
		GameConfig defaultConfig =
				new GameConfig(playerTypes, 1, 2,
						"Hex", PORT_NUMBER);

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


	/**
	 * create a local game
	 * 
	 * @return
	 * 		the local game, a hex game
	 */
	@Override
	public LocalGame createLocalGame(GameState state) {
		if (state == null)
			// Hex State parameters originally was zero since it took in int as a param but now we removed it
			state = new HexState();
		return new HexLocalGame(state);
	}//createLocalGame


	/**
	 External Citation
	 Date: 23 April 2024
	 Problem: Did not know how to add music to the game
	 Resource:
	 <a href="https://www.geeksforgeeks.org/how-to-add-audio-files-to-android-app-in-android-studio/">...</a>
	 Solution: I had to add a separate MediaPlayer class to play music
	 */
	protected void onDestroy() {
		super.onDestroy();
		// Release MediaPlayer resources
		if (musicPlayer != null) {
			musicPlayer.release();
			musicPlayer = null;
		}
	}//onDestroy


	/**
	 External Citation
	 Date: 25 April 2024
	 Problem: Didn't know how to add popup ads in the game
	 Resource:
	 https://www.youtube.com/watch?v=snuXdyPPmNg
	 Solution: Had to use an AlertDialog and a timer
	 *
	 * Method tells the pop up to show on the screen and how long until it shows up
	 */
	public void countDownForAd() {
		CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
			@Override
			public void onTick(long l) {
				// Do nothing on tick
			}

			@Override
			public void onFinish() {
				showAdPopup();
			}
		};
		countDownTimer.start();
	}//countDownForAd


	/**
	 External Citation:
	 Same as above
	 *
	 * This method displays the pop up window from the ad_popup xml
	 */
	public void showAdPopup() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View dialogView = getLayoutInflater().inflate(R.layout.ad_popup, null);
		builder.setView(dialogView);

		AlertDialog adDialog = builder.create();
		adDialog.show();

		// Find and set up the close button
		Button closeButton = dialogView.findViewById(R.id.close_ad);
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adDialog.dismiss(); // Dismiss the dialog when close button is clicked
			}
		});
	}//showAdPopup
}
