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




    //declare what the size of the board is



    //void method to set color of the tile to color if occupied
    /*public void occupyTile(int x, int y, int color) {
        if (!isTileOccupied(x, y)) {
            grid[x][y] = color;
        }
    }*/

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


}
