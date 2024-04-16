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

	// instance variables for our HexState
	private boolean hasWon; // To return true if a player has won
	private HexBoard board; // The hexboard grid
	private HexTile hexTile; // Each cell in the grid
	private String playerWinner; // To return the name of the player winner
	private Player player1; // First player, red
	private Player player2; // Second player, blue

	//For Undo:  save location of last move

	private int lastPlaceTileX;
	private int lastPlaceTileY;
	private HexState hexState;
	public int gridSize;

	HexTile[] boarderGrid;
	HexTile[][] grid;
	public float hexSize;
	private boolean player1_turn; //is it player1's turn? if so return true

	int playerColor;
	/**
	 * constructor, initializing the boolean values from the objects in the parameter
	 *
	 */
	public HexState() {

		this.hasWon = false;

		// Creates an 11x11 board
		//this.board = new HexBoard(11);
		this.gridSize = 11;
		// Two players, red & blue players
		this.player1 = new Player("player 1", "red");
		this.player2 = new Player("player2", "blue");

		this.hexSize = 40;
		this.player1_turn = true;
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
		// set the counter to that of the original
		// Deep copy
		this.hasWon = orig.hasWon;
		this.playerWinner = orig.playerWinner;

		this.player1 = new Player(orig.player1);
		this.player2 = new Player(orig.player2);

		this.hexSize = orig.hexSize;
		this.playerColor = Color.RED;

		orig.initializeGrid();
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
	public void Turn() {
		player1_turn = !player1_turn;  // toggle turn
		playerColor = player1_turn ? Color.RED : Color.BLUE;
	}

	/**
	 * gets the
	 *
	 * @return
	 */
	public boolean getPlayerTurn() {
		return player1_turn;
	}
}
