package edu.up.cs301.hex;

import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;


/**
 * This contains the state for our Hex game. The state consist of who won the game and whos turn it is
 *
 * @author Steven R. Vegdahl
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 * @version March 2024
 */
public class HexState extends GameState {

	private String playerWinner; // To return the name of the player winner
	//private Player player1; // First player, red
	//private Player player2; // Second player, blue

	//For Undo:  save location of last move

	private int lastPlaceTileX;
	private int lastPlaceTileY;
	private HexState hexState;
	public int gridSize;
	HexTile[][] grid;
	public float hexSize;
	private int playerTurnID;
	private int playerColor;// color of the current hexTile based on who's turn it is
	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 *
	 */
	public HexState() {


		this.gridSize = 11;
		this.hexSize = 40;
		this.playerTurnID = 0;
		this.playerColor = Color.RED;

		initializeGrid();
	}

	/**
	 * copy constructor; makes a copy of the original object
	 *
	 * Implementing a copy constructor that makes a deep copy of HexState
	 * A pointer to our original HexState
	 *
	 *
	 * @param orig the object from which the copy should be made
	 */
	public HexState(HexState orig) {

		this.gridSize = orig.gridSize;
		this.hexSize = orig.hexSize;
		this.playerColor = orig.playerColor;
		this.playerTurnID= orig.playerTurnID;

		//this doens't work
		copyGrid(orig.grid);
	}


	/**
	 * sets the coordinates for each new HexTile
	 */
	public void initializeGrid() {

		grid = new HexTile[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				float x = 100 + (i * 35) + (j * (float) (hexSize * 1.9));
				float y = 100 + ((float) (i * hexSize * 1.7));
				grid[i][j] = new HexTile(x, y, Color.WHITE);  // Ensuring no HexTile is null
			}
		}
	}

	/**
	 * sets the coordinates for each new HexTile
	 */
	public void copyGrid(HexTile[][] orig) {

		grid = new HexTile[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid[i][j] = new HexTile(orig[i][j].getCenterX(), orig[i][j].getCenterY(), orig[i][j].getColor());  // Ensuring no HexTile is null
			}
		}
	}
	/**
	 * Checks if blue wins
	 *
	 * @return
	 */
	public boolean blueWins() {
		//loops through up and down the grid board
		for (int col = 0; col < grid.length; col++) {
			//checks if there is a blue tile on each index of the colums and checks if they are connected
			if (grid[0][col].getColor() == Color.BLUE && isConnected(0, col, new boolean[grid.length][grid.length], Color.BLUE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if red wins
	 *
	 * @return
	 */
	public boolean redWins() {
		//loops through left and right of the grid board
		for (int row = 0; row < grid.length; row++) {

			//checks if there is red tile on each index of the rows and checks if they are connected
			if (grid[row][0].getColor() == Color.RED && isConnected(row, 0, new boolean[grid.length][grid.length], Color.RED)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if the tile being placed is within the bounds of hex board
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isValid(int row, int col) {
		return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
	}

	public boolean isLegalMove(int row, int col) {
		// Check if the specified row and column are within the bounds of the grid
		if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
			return false; // Move is out of bounds, so it's not legal
		}
		// Check if the corresponding hex tile is empty (white)
		return grid[row][col].getColor() == Color.WHITE;
	}




	public boolean placeTileAction(int row, int col) {

		// Check if the move is legal
		if (!isLegalMove(row, col)) {
			return false;
		}

		// Place the tile at the specified row and column
		grid[row][col].setColor(playerColor);

		// Update the player turn and color
		playerTurnID = 1 - playerTurnID; // Toggle between player 0 and player 1
		playerColor = (playerTurnID == 0) ? Color.RED : Color.BLUE;
		return true;
	}





	/**Checks if the matching color tiles are connected in any way
	 *
	 * @param row // row of the hex board
	 * @param col // col of the hex board
	 * @param visited
	 * 		checks if the tile has been visited, if so move on to the next one
	 * @param playerColor
	 *
	 * @return
	 */

	public boolean isConnected(int row, int col, boolean[][] visited, int playerColor) {
		// Base cases: Check if we reached an opposite side
		if (playerColor == Color.BLUE && row == grid.length - 1) {
			return true; // Blue player connected top and bottom
		}
		if (playerColor == Color.RED && col == grid.length - 1) {
			return true; // Red player connected left and right
		}

		//tile has been checked
		visited[row][col] = true;

		// Offsets for neighboring cells in a hexagonal grid
		int[] dx = {-1, 1, 0, 0, 1, -1};
		int[] dy = {0, -1, -1, 1, 0, 1};

		//loops through each case of offsets
		for (int i = 0; i < 6; i++) {
			int newRow = row + dx[i];
			int newCol = col + dy[i];

			// Check if the neighboring cell is within bounds and belongs to the same player
			if (isValid(newRow, newCol) && grid[newRow][newCol].getColor() == playerColor && !visited[newRow][newCol]) {
				if (isConnected(newRow, newCol, visited, playerColor)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * sets the color of the hexTiles based on which player's turn it is
	 */






	/**
	 * gets the
	 *
	 * @return
	 */
	public int getPlayerTurnID() {
		return playerTurnID;
	}

	public void setPlayerTurnID(int playerTurnID) {
		this.playerTurnID = playerTurnID;
	}

	public int getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}

	protected void switchTurns() {
		if (this.getPlayerTurnID() == 0) {
			this.setPlayerTurnID(1); // Switch to player 1's turn
		} else {
			this.setPlayerTurnID(0); // Switch to player 0's turn
		}
	}
}
