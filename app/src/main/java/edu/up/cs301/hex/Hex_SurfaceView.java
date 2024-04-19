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

public class Hex_SurfaceView extends SurfaceView  {

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

    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setBackgroundColor(0xFF808080);
        hexState = new HexState();
        //hexState.initializeGrid();


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
            float x = 450 + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
            HexTile topBoarderTiles = new HexTile(x, 85 + 50, Color.BLUE);
            topBoarderTiles.draw(canvas);

            float x2 = 800 + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
            HexTile bottomBoarderTiles = new HexTile(x2, 795 + 50, Color.BLUE);
            bottomBoarderTiles.draw(canvas);

            float x3 = 440 + (i * 35);
            float y = 100 + 50 + (i * 28) + (i * (float) (hexState.hexSize));
            HexTile leftBoarderTiles = new HexTile(x3, y, Color.RED);
            leftBoarderTiles.draw(canvas);

            float x4 = 1225 + (i * 35);
            float y2 = 100 + 50 + (i * 28) + (i * (float) (hexState.hexSize));
            HexTile rightBoarderTiles = new HexTile(x4, y2, Color.RED);
            rightBoarderTiles.draw(canvas);

        }

        //Don't draw any tiles until there is a game state
        if (hexState == null) {
            return;
        }

        if (hexState.grid == null) {
            return;
        }

        //
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


}//onTouch