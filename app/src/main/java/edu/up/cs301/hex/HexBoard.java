package edu.up.cs301.hex;

import android.graphics.Color;

/**
 * This contains the Hex Board layout
 *
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 *  @version March 2024
 */
public class HexBoard {

    //instance variables
    private int size;
    private HexTile hexTile;
    private HexTile[][] grid;
    public HexBoard(HexBoard orig) {
        this.grid = new HexTile[orig.grid.length][orig.grid[0].length];
        for (int i = 0; i < orig.grid.length; i++) {
            for (int j = 0; j < orig.grid[0].length; j++) {
                this.grid[i][j] = orig.grid[i][j];
            }
        }
        this.size = orig.size;
    }


    //declare what the size of the board is
    public HexBoard(int size) {
        this.size = size;
        this.grid = new HexTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new HexTile();
            }
        }
    }



    //boolean method if the tile is occupied
    public boolean isTileOccupied(int row, int col) {
        return !(grid[row][col].equals(hexTile.EMPTY_COLOR));
    }

    //void method to set color of the tile to color if occupied
    /*public void occupyTile(int x, int y, int color) {
        if (!isTileOccupied(x, y)) {
            grid[x][y] = color;
        }
    }*/
    public HexTile[][] getGrid(){
        return this.grid;
    }
    //to string for HexBoard
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HexBoard Size: ").append(size).append("\nBoard:\n");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j]); // hexTile has a tostring method
                sb.append(" "); // space between tiles for readability
            }
            sb.append("\n"); // new line at the end of each row
        }

        return sb.toString();
    }




    public boolean isConnected(int row, int col, boolean[][] visited, int playerColor) {
        // Base cases: Check if we reached an opposite side
        if (playerColor==hexTile.RED_COLOR && row == grid.length - 1) {
            return true; // Blue player connected top and bottom
        }
        if (playerColor==hexTile.BLUE_COLOR && col == grid.length - 1) {
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
    public boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
    }
}
