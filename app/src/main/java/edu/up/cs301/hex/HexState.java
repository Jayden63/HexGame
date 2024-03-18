package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for our Hex game. The state consist of who won the game and whos turn it is
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class HexState extends GameState {

	// instance variables for our HexState
	private int playerTurn = 0;
	private boolean hasWon;

	
	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 * 
	 * @param initPlayerTurn, initHasWon
	 */
	public HexState(int initPlayerTurn, boolean initHasWon) {
		this.playerTurn = initPlayerTurn;
		this.hasWon = initHasWon;
	}
	
	/**
	 * copy constructor; makes a copy of the original object
	 * 
	 * @param orig
	 * 		the object from which the copy should be made
	 */
	public HexState(HexState orig) {
		// set the counter to that of the original
		this.playerTurn = orig.playerTurn;
		this.hasWon = orig.hasWon;
	}

	/**
	 * getter method for the counter
	 * 
	 * @return
	 * 		the value of the counter
	 */
	/*public int getCounter() {
		return counter;
	}*/

	
	/**
	 * setter method for the counter
	 * 
	 * @param counter
	 * 		the value to which the counter should be set
	 */
	/*public void setCounter(int counter) {
		this.counter = counter;
	}*/

	/**
	 * toString method to describe the state of the game
	 *
	 * @return a string representation of the game state
	 */
	@Override
	public String toString() {
		return "HexState{" +
				"playerTurn=" + playerTurn +
				", hasWon=" + hasWon +
				'}';
	}
}
