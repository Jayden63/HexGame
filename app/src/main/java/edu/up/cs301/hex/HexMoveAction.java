package edu.up.cs301.hex;

import java.io.Serializable;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A hexMoveAction is an action that is a "move" the game:
 * a tap in the hex grid
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
	public class HexMoveAction extends GameAction implements Serializable {
	// serial ID
	public static final long serialVersionUID = 202442385301L;

		// The row where the player wants to place the hex tile
		private final int row;

		// The column where the player wants to place the hex tile
		private final int col;

		/**
		 * Constructor for the HexMoveAction class.
		 *
		 * @param player
		 *            the player making the move
		 * @param row
		 *            the row where the player wants to place the hex tile
		 * @param col
		 *            the column where the player wants to place the hex tile
		 */
		public HexMoveAction(GamePlayer player, int row, int col) {
			super(player);
			this.row = row;
			this.col = col;
		}



		/**
		 * Getter method to retrieve the row of the move.
		 *
		 * @return the row where the player wants to place the hex tile
		 */
		public int getRow() {
			return this.row;
		}

		/**
		 * Getter method to retrieve the column of the move.
		 *
		 * @return the column where the player wants to place the hex tile
		 */
		public int getCol() {
			return this.col;
		}
	}



