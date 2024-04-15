package edu.up.cs301.hex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Path;
import android.view.View;
import android.view.MotionEvent;
import java.util.ArrayList;

public class Hex_SurfaceView extends SurfaceView implements View.OnTouchListener {

private HexState hexState;
    // ArrayList to store all HexTiles
    //ArrayList<HexTile> tileList = new ArrayList<>();


    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // Sets the background of the surface view
        setBackgroundColor(0xFF808080);

        // Creates new instance of the HexState
        hexState = new HexState();

        // Sets up the hexState
        hexState.initializeGrid();

        // Listens for a touch in the game
        this.setOnTouchListener(this);

        // Draws the hexState
        setWillNotDraw(false);

    }// Hex_SurfaceView


    // Setter method for the HexState
    public void setHexState(HexState hexState) {
        this.hexState = hexState;
    }


    /**
     * External Citation
     * Date: 13 February 2024
     * Problem: Could not draw a hexagon on the surface view
     * Resource:
     * https://stackoverflow.com/questions/30217450/how-to-draw-hexagons-in-android
     * Solution: I used the example code from this post as the basis for the code below
     * Only change is the size and position of the hexagon
     */


    /** onDraw(Canvas canvas)
     *
     * This method overrides onDraw to draw the hexState size
     * The hexState grid is defined as an 11x11 grid
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // 11x11, gridSize is defined by 11
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {  // Null check to avoid NullPointerException
                    tile.draw(canvas);
                }
            }
        }
    }//onDraw


    /**
     *
     * onTouch(View view, MotionEvent event)
     *
     * onTouch method will listen to any taps the user does during the game
     * It will iterate through all the HexTiles in the grid and return true
     * if the touch is within bounds of a HexTile
     *
     * @param view The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *        the event.
     * @return boolean
     */
    public boolean onTouch(View view, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if touch is inside any hex tile in the 11x11 grid
            for (int i = 0; i < hexState.grid.length; i++) {
                for (int j = 0; j < hexState.grid.length; j++) {

                    // If the tap is inside a HexTile
                    if (hexState.grid[i][j].isTouched(x, y)) {

                        // If the HexTile is white, not yet tapped
                        if(hexState.grid[i][j].getColor() == Color.WHITE) {

                            // Update the color to their respective player
                            hexState.grid[i][j].setColor(hexState.playerColor);

                            // Update the hexTile color to reflect change
                            invalidate();

                            // Make it other player's turn
                            hexState.Turn();
                        }
                    }
                }
            }
        }

        // If the tap is not within a HexTile
        return false;
    }
}//onTouch