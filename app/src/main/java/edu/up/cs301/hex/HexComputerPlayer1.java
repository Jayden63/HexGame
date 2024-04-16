package edu.up.cs301.hex;

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

    /**
     * Constructor for objects of class CounterComputerPlayer1
     * 
     * @param name
     * 		the player's name
     */
    public HexComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name);
        

    }
    
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (presumably containing the game's state)
     */
	@Override
	protected void receiveInfo(GameInfo info) {

		if (info instanceof HexState) {
			HexState hexState = (HexState) info;
			if (!hexState.getPlayerTurn()) {
				HexMoveAction a = new HexMoveAction(this, false);
				game.sendAction(a);
			}


			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
	}

}
