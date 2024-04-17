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
import android.widget.TextView;

import java.util.ArrayList;

public class Hex_SurfaceView extends SurfaceView implements View.OnTouchListener {

    private Paint brightPink = new Paint();
    private Paint forestGreen = new Paint();
    private Paint teal = new Paint();

    // Size and shape variables
    private Path hexagonPath;
    private Path hexagonBorderPath;
    private float radius;
    private float width, height;
    private float height_SurfaceView;
    private float width_SurfaceView;
    Paint hexPaint = new Paint();
    Paint hexBorderPaint = new Paint();

    private float xHexCenter; // The current x center of the hex
    private float yHexCenter; // The current y center of the hex

    private HexState hexState;

    TextView textView;

    // ArrayList to store all HexTiles
    //ArrayList<HexTile> tileList = new ArrayList<>();

    Paint hexRedSide = new Paint();
    Paint hexBlueSide = new Paint();
    private int playerTurn = 0;

    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setBackgroundColor(0xFF808080);
        hexState = new HexState();
        hexState.initializeGrid();



        this.setOnTouchListener(this);
        setWillNotDraw(false);
    }
    public void setHexState(HexState hexState) {

        this.hexState = hexState;

        invalidate();
        //TODO:  trigger a redraw event (mystery to solve)
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < hexState.gridSize; i++) {

                //draws the red and blue boards around the board
                // hard coded for now
                float x = 100 + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
                HexTile topBoarderTiles = new HexTile(x, 85, Color.BLUE);
                topBoarderTiles.draw(canvas);

                float x2 = 450 + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
                HexTile bottomBoarderTiles = new HexTile(x2 , 795, Color.BLUE);
                bottomBoarderTiles.draw(canvas);

                float x3 = 90 + (i * 35);
                float y = 100 + (i * 28) + (i * (float) (hexState.hexSize));
                HexTile leftBoarderTiles = new HexTile(x3, y, Color.RED);
                leftBoarderTiles.draw(canvas);


                float x4 = 875 + (i * 35);
                float y2 = 100 + (i * 28) + (i * (float) (hexState.hexSize));
                HexTile rightBoarderTiles = new HexTile(x4, y2, Color.RED);
                rightBoarderTiles.draw(canvas);
        }

        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {  // Null check to avoid NullPointerException
                    tile.draw(canvas);
                }
            }
        }
    }


    /**
     *
     *onTouch method to handle touch events in the SurfaceView
     *
     *@param view represents the view in which the touch event happened
     *@param event contains details (x,ycoord) of the touch
     *@return true if a touch has been performed and handled
     */

    public boolean onTouch(View view, MotionEvent event) {

        // The coordinates registered by the touch
        float x = event.getX();
        float y = event.getY();


        // If the action is a tap
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if touch is inside any hex tile in the grid
            for (int i = 0; i < hexState.grid.length; i++) {
                for (int j = 0; j < hexState.grid.length; j++) {


                    if (hexState.grid[i][j].isTouched(x, y)) {

                        // If the Hix Tile is color white (not yet touched)
                        if(hexState.grid[i][j].getColor() == Color.WHITE) {

                            // Set the color to the respective player's color
                            hexState.grid[i][j].setColor(hexState.getPlayerColor());

                            // Update the new color of the Hex Tile
                            invalidate();

                            hexState.Turn();
                        }

                    }
                }
            }
        }
        return false;

    }
}//onTouch