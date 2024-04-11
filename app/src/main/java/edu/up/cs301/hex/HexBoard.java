package edu.up.cs301.hex;

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
        /*for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new int[size][size];
            }
        }*/
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
}
