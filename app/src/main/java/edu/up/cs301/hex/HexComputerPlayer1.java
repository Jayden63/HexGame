package edu.up.cs301.hex;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

/**
 * A computer-version of a hex-player.
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

public class HexComputerPlayer1 extends GameComputerPlayer implements Serializable {

    // serial ID
    public static final long serialVersionUID = 202442385217L;
    private final Random random;


    /**
     * Constructor for objects of class hexComputerPlayer1
     * 
     * @param name
     * 		the player's name
     */
    public HexComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name);
        random = new Random();
    }//HexComputerPlayer1



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
            // Ignore message if it's not AI player's turn or if the player is inactive
            return;
        }

        HexState state = (HexState) info;

        // Check if it's the computer player's turn & ignore
        if (state.getPlayerTurnID() != playerNum) {
            return;
        }

        // Get the board size
        int gridSize = state.gridSize;

        // Loop until a valid move is made
        while (true) {
            // Generate random coordinates for the piece placement
            int randomRow = random.nextInt(gridSize);
            int randomCol = random.nextInt(gridSize);
            try {
                Thread.sleep(500); // Wait for .5 seconds before making a move
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                return;
            }

            // Check if the selected position is empty
            if (state.grid[randomRow][randomCol].getColor() == Color.WHITE) {
                // Send a move action to the game
                game.sendAction(new HexMoveAction(this, randomRow, randomCol));
                break; // Exit the loop
            }
        }
    }//receiveInfo
}
