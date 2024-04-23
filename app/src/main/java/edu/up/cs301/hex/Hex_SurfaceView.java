package edu.up.cs301.hex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Path;

import androidx.annotation.NonNull;


public class Hex_SurfaceView extends SurfaceView  {


    // Size and shape variables
    private float height_SurfaceView;
    private float width_SurfaceView;
    private float width, height;
    private HexState hexState;
    private final Paint redBackground = new Paint();
    private final Paint blueBackground = new Paint();


    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // Sets the background to gray
        setBackgroundColor(0xFF808080);
        hexState = new HexState();
        //hexState.initializeGrid();

        // For background rectangles
        redBackground.setColor(0xFFFF2233);
        redBackground.setStyle(Paint.Style.FILL_AND_STROKE);
        blueBackground.setColor(0xFF2233FF);

        setWillNotDraw(false);

    }//Hex_SurfaceView


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
        width = MeasureSpec.getSize(widthMeasureSpec);

        // Assigns height to the height of surface view
        height = MeasureSpec.getSize(heightMeasureSpec);

        // Fine tuning both values to make it centered
        width_SurfaceView = (width / 2f) - 555f;//555
        height_SurfaceView = (height / 2f) - 340f;

    }//onMeasure

    /**
     * External Citation
     * Date: 13 February 2024
     * Problem: Could not draw a hexagon on the surface view
     * Resource:
     * <a href="https://stackoverflow.com/questions/30217450/how-to-draw-hexagons-in-android">...</a>
     * Solution: I used the example code from this post as the basis for the code below
     * Only change is the size and position of the hexagon
     */

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Shape of red rectangle
        Path redRect = new Path();
        redRect.moveTo(0, height / 2f - 200); // Top left
        redRect.lineTo(width, height / 2f - 200); // Top right
        redRect.lineTo(width, height / 2f + 200); // Bottom right
        redRect.lineTo(0, height / 2f + 200); // Bottom left
        redRect.close();
        canvas.drawPath(redRect, redBackground);

        // Shape of blue rectangle
        Path blueRect = new Path();
        blueRect.moveTo(width / 2f - 425, 0); // Top left
        blueRect.lineTo(width / 2f + 30, 0); // Top right
        blueRect.lineTo(width / 2f + 425, height); // Bottom right
        blueRect.lineTo(width / 2f - 30, height); // Bottom left
        blueRect.close();
        canvas.drawPath(blueRect, blueBackground);


        // To draw the hexTile borders of the hex grid
        // Always in the center regardless of orientation
        for (int i = 0; i < hexState.gridSize; i++) {

            // The top border of the hex grid
            float x = width_SurfaceView + (i * (float) (hexState.hexSize * 1.9f));
            HexTile topBorderTiles = new HexTile(x, height_SurfaceView - 21, Color.BLUE);
            topBorderTiles.draw(canvas);

            // The right border of the hex grid
            float x4 = width_SurfaceView + 760 + (i * 37); //780
            float y2 = height_SurfaceView - 11 + (i * (float) (hexState.hexSize * 1.65f)); //28
            HexTile rightBorderTiles = new HexTile(x4, y2, Color.RED);
            rightBorderTiles.draw(canvas);

            // The bottom border of the hex grid
            float x2 = width_SurfaceView + 370 + (i * (float) (hexState.hexSize * 1.9f));
            HexTile bottomBorderTiles = new HexTile(x2, height_SurfaceView + 666, Color.BLUE);
            bottomBorderTiles.draw(canvas);

            // The left border of the hex grid
            float x3 = width_SurfaceView - 20 + (i * 37); //20
            float y = height_SurfaceView + 11 + ((i * (float) (hexState.hexSize) * 1.65f));
            HexTile leftBorderTiles = new HexTile(x3, y, Color.RED);
            leftBorderTiles.draw(canvas);

        }//onDraw


        // Draws each tile in the Hex Grid
        // We are using the grid directly from the hexState class
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {

                // Copied values from HexState but making x values centered
                // The x distance of different rows
                hexState.grid[i][j].setCenterX(width_SurfaceView + (i * 37)
                        // The x distance between individual tiles in the same row
                        + (float) (j * hexState.hexSize * 1.90));

                // The y distance between rows. Greater value = greater distance
                hexState.grid[i][j].setCenterY(height_SurfaceView
                        + (float) (i * (hexState.hexSize * 1.65f)));

                // To draw the grid in the surface view
                HexTile tile = hexState.grid[i][j];

                if (tile != null) {  // Null check to avoid NullPointerException
                    // Drawing the grid in the canvas
                    tile.draw(canvas);
                }
            }
        }
    }
}//onDraw