package edu.up.cs301.hex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Path;
import android.view.ViewGroup;

import android.view.View;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;

public class Hex_SurfaceView extends SurfaceView  {


    // Size and shape variables
    private float height_SurfaceView;
    private float width_SurfaceView;
    private HexState hexState;
    private HexTile hexTile;


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
     *
     * onMeasure() method will retrieve the surface view dimensions
     * Useful for drawing the grid centered in both landscape and portrait
     *
     * @param widthMeasureSpec
     * horizontal space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     * @param heightMeasureSpec
     * vertical space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     *
     *
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Calling the parent
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Assign width to the width of the surface view
        width_SurfaceView = MeasureSpec.getSize(widthMeasureSpec);

        // Assigns height to the height of surface view
        height_SurfaceView = MeasureSpec.getSize(heightMeasureSpec);

        // Fine tuning both values to make it centered
        width_SurfaceView = (width_SurfaceView / 2f) - 555f;
        height_SurfaceView = (height_SurfaceView / 2f) - 340;

    }//onMeasure

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

        // To draw the borders of the hex grid
        // Always in the center regardless of orientation
        for (int i = 0; i < hexState.gridSize; i++) {

            // The top border of the hex grid
            float x = width_SurfaceView + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
            HexTile topBorderTiles = new HexTile(x, height_SurfaceView - 20, Color.BLUE);
            topBorderTiles.draw(canvas);

            // The bottom border of the hex grid
            float x2 = 350 +
                    width_SurfaceView + (i * 35) + (i * (float) (hexState.hexSize * 1.02));
            HexTile bottomBorderTiles = new HexTile(x2, height_SurfaceView + 700, Color.BLUE);
            bottomBorderTiles.draw(canvas);

            // The left order of the hex grid
            float x3 = width_SurfaceView - 20 + (i * 35);
            float y = height_SurfaceView + (i * 28) + (i * (float) (hexState.hexSize));
            HexTile leftBorderTiles = new HexTile(x3, y, Color.RED);
            leftBorderTiles.draw(canvas);

            // The right border of the hex grid
            float x4 = width_SurfaceView + 780 + (i * 35);
            float y2 = height_SurfaceView + (i * 28) + (i * (float) (hexState.hexSize));
            HexTile rightBorderTiles = new HexTile(x4, y2, Color.RED);
            rightBorderTiles.draw(canvas);

        }


        // Draws each tile from the hexState
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {

               // Copied values from HexState but making it x values centered
                hexState.grid[i][j].setCenterX(width_SurfaceView + (i * 35)
                        + (float) (j * hexState.hexSize * 1.9));

                // Sets the y coordinates to be center
                hexState.grid[i][j].setCenterY(height_SurfaceView
                        + (float) (i * (hexState.hexSize * 1.7)));
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {  // Null check to avoid NullPointerException
                    tile.draw(canvas);
                }
            }
        }
    }

}//onDraw