package edu.up.cs301.hex;

import android.graphics.Color;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second. 
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

public class HexComputerPlayer1 extends GameComputerPlayer {


    private Random random;
    /**
     * Constructor for objects of class CounterComputerPlayer1
     * 
     * @param name
     * 		the player's name
     */
    public HexComputerPlayer1(String name) {
        // invoke superclass constructor

        super(name);

        random = new Random();

    }
    
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (presumably containing the game's state)
     */
	@Override
	protected void receiveInfo(GameInfo info) {
        // Check if it's not the computer player's turn
        if (!(info instanceof HexState)) {
            return; // Ignore the message if it's not the computer player's turn or if the player is not active
        }

        HexState state = (HexState) info;

        // Check if it's the computer player's turn
        if (state.getPlayerTurnID() != playerNum) {
            return; // It's not this player's turn, so ignore
        }

        // Get the board size
        int gridSize = state.gridSize;

        // Loop until a valid move is made
        while (true) {
            // Generate random coordinates for the piece placement
            int randomRow = random.nextInt(gridSize);
            int randomCol = random.nextInt(gridSize);

            // Check if the selected position is empty
            if (state.grid[randomRow][randomCol].getColor() == Color.WHITE) {
                // Send a move action to the game
                game.sendAction(new HexMoveAction(this, randomRow, randomCol));
                break; // Exit the loop
            }
        }
    }
}
