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
    // ArrayList to store all HexTiles
    //ArrayList<HexTile> tileList = new ArrayList<>();

    Paint hexRedSide = new Paint();
    Paint hexBlueSide = new Paint();
    private int playerTurn = 0;
    public HexState gameState;

    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        hexState = new HexState();
        hexState.initializeGrid();




        this.setOnTouchListener(this);
        setWillNotDraw(false);
    }
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {  // Null check to avoid NullPointerException
                    tile.draw(canvas);
                }
            }
        }
    }





















        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Check if touch is inside any hex tile
                for (int i = 0; i < hexState.grid.length; i++) {
                    for (int j = 0; j < hexState.grid.length; j++) {
                        if (hexState.grid[i][j].isTouched(x, y)) {
                            if (hexState.grid[i][j].getColor() == Color.GRAY) {
                                hexState.grid[i][j].setColor(hexState.playerColor);
                                invalidate();

                                hexState.Turn();

                            }
                        }
                    }
                }
            }
            return false;

        }
}