package edu.up.cs301.hex;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Color;
import java.io.Serializable;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;


/**
 * Hex_SurfaceView displays the hex grid
 *
 * @author Cody Gima
 * @author Jayden Zeng
 * @author Chengen Li
 * @author Eduardo Gonon
 *
 * @version April 2024
 */
public class Hex_SurfaceView extends SurfaceView implements Serializable {

    // serial ID, very important for network play
    public static final long serialVersionUID = 202442385352L;
    private HexState hexState;

    // Global variables used to fill the surface view
    private float center_Height;
    private float center_Width;
    public static float width_SurfaceView, height_SurfaceView;
    private final Paint gradPaint = new Paint();
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float gradientPosition = 0;
    public static float scaleFactor = 1.0f;
    private final ScaleGestureDetector scaleGestureDetector;


    /**
     * Hex_SurfaceView sets up the Hex Game in the surface view upon launch
     *
     * @param context // To launch the activity
     * @param attrs  // To read in any custom attributes for the view from XML
     */
    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        hexState = new HexState();

        startAnimation();
        setWillNotDraw(false);

    }//Hex_SurfaceView



    /**
     * onTouchEvent listens for any pinch to zoom actions in the Surface View
     *
     * @param event // Our event object
     * @return true // if the pinch to zoom action is performed, false otherwise
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events
        scaleGestureDetector.onTouchEvent(event);

        // Updating the hex grid after a pinch zoom
        updateHexGrid();
        return true;
    }



    /**
     * setHexState()
     * sets/updates the state of the hex game
     *
     * @param hexState // The reference to the HexState
     */
    public void setHexState(HexState hexState) {

        this.hexState = hexState;

        // Redraws the board
        invalidate();
    }



    /**
     * External Citation
     * Date: March 2024
     * Problem: Surface View orientation only works in one mode
     * Resource:
     * https://stackoverflow.com/questions/23608236/in-surfaceviews-onmeasure-how-can-i-calculate-the-size-of-current-view
     * Solution: I used the example code from this post to retrieve
     * The Surface View dimensions and use it to make the grid centered
     */

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

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width_SurfaceView = MeasureSpec.getSize(widthMeasureSpec);
        height_SurfaceView = MeasureSpec.getSize(heightMeasureSpec);

        // These values are dependent on the surface view dimensions
        // Useful to make the Hex Grid centered regardless of orientation
        center_Width = (width_SurfaceView/ 2f) - 555f;//555
        center_Height = (height_SurfaceView / 2f) - 322f;

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


    /**
     * onDraw draws our Hex Grid in the surface view
     *
     * @param canvas // Is the surface view
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        // For the zoom function, an extra feature
        canvas.scale(scaleFactor, scaleFactor,
                width_SurfaceView / 2, height_SurfaceView / 2);

        // Little methods to populate the surface view with beautiful things
        drawGradiant(canvas);
        drawBorders(canvas);
        drawGrid(canvas);

    }//onDraw



    /**
     * drawGradiant() animates the background of the SurfaceView
     * with a dynamic gradiant background
     *
     * @param canvas
     */
    public void drawGradiant(Canvas canvas) {

        // Red and blue reflects both player colors in the game, no one is left out
        int startColor = Color.BLUE;
        int endColor = Color.RED;

        // We chose to have it animate between both colors to improve the user experience
        int currentColor =
                (Integer) argbEvaluator.evaluate(gradientPosition, startColor, endColor);

        LinearGradient gradient =
                new LinearGradient(0, 0, getWidth() * 10, 0,
                        currentColor, endColor, Shader.TileMode.CLAMP);

        gradPaint.setShader(gradient);
        canvas.drawPaint(gradPaint);
    }


    /**
     * drawGrid method draws the white empty grid with all the HexTiles
     *
     * @param canvas
     */
    public void drawGrid(Canvas canvas) {

        // Draws each tile in the Hex Grid
        // We are using the grid directly from the hexState class
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {

                // Copied values from HexState but making x values centered
                // The i changes the x distance of different rows
                hexState.grid[i][j].setCenterX(center_Width + (i * 37)

                        // The j changes the x distance between individual tiles in the same row
                        + (float) (j * hexState.hexSize * 1.90));

                // The y changes the distance between rows. Greater value = greater distance
                hexState.grid[i][j].setCenterY(center_Height
                        + (i * (hexState.hexSize * 1.65f)));

                HexTile tile = hexState.grid[i][j];

                if (tile != null) {
                    tile.draw(canvas);
                }
            }
        }
        canvas.restore();
    }


    /**
     * drawBorders draws the red and blue borders of the hex grid
     * Lets the players know which sides they need to connect their tiles
     *
     * @param canvas
     */
    public void drawBorders(Canvas canvas) {

        for (int i = 0; i < hexState.gridSize; i++) {

            // The top border of the hex grid
            float x = center_Width + (i * (hexState.hexSize * 1.9f));
            HexTile topBorderTiles =
                    new HexTile(x, center_Height - 21, Color.BLUE);
            topBorderTiles.draw(canvas);

            // The right border of the hex grid
            float x4 = center_Width + 760 + (i * 37); //780
            float y2 = center_Height - 11 + (i * (hexState.hexSize * 1.65f)); //28
            HexTile rightBorderTiles = new HexTile(x4, y2, Color.RED);
            rightBorderTiles.draw(canvas);

            // The bottom border of the hex grid
            float x2 = center_Width + 370 + (i * (hexState.hexSize * 1.9f));
            HexTile bottomBorderTiles =
                    new HexTile(x2, center_Height + 666, Color.BLUE);
            bottomBorderTiles.draw(canvas);

            // The left border of the hex grid
            float x3 = center_Width - 20 + (i * 37);
            float y = center_Height + 11 + ((i * (hexState.hexSize) * 1.65f));
            HexTile leftBorderTiles = new HexTile(x3, y, Color.RED);
            leftBorderTiles.draw(canvas);
        }
    }//drawBorders



    /**
     * updateHexGrid updates the x and y coordinates of each hexTile
     * According to scaleFactor (how much zoom has been done)
     */
    public void updateHexGrid() {

        // Might raise some eyebrows but the radius updates
        HexTile.radius = HexTile.radius * (scaleFactor);

        // We chose to iteratively update each hexTile in the grid
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {
                    float newCenterX = (tile.getCenterX() - (width_SurfaceView / 2))
                            * scaleFactor + (width_SurfaceView / 2);
                    float newCenterY = (tile.getCenterY() - (height_SurfaceView / 2))
                            * scaleFactor + (height_SurfaceView / 2);

                    // Update coordinate of each Hex Tile
                    tile.setCenterX(newCenterX);
                    tile.setCenterY(newCenterY);

                    // Update the coordinates in the HexState class
                    // Useful for network play
                    hexState.grid[i][j].setCenterX(newCenterX);
                    hexState.grid[i][j].setCenterY(newCenterY);
                }
            }
        }
        invalidate();
    }//updateHexGrid



    /**
     * startAnimation()
     * This method is used to start the dynamic gradient background animation
     * In the Surface View.
     */
    public void startAnimation() {

        // Animator object animates between two values, the colors RED and BLUE
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // 10 seconds is a good time to minimize distraction and seizures
        animator.setDuration(10000);

        // Animation will run in the anti direction and vice versa indefinitely
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);

        // Always called to update the animation
        animator.addUpdateListener(animation -> {
            gradientPosition = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.start();

    }//startAnimation



    /**
     * This class is used for zooming in the surface view
     * The ScaleListener will listen for a pinch to zoom action
     * Performed by the user
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        /**
         * OnScale retrieves the scale of the zoom
         * How much the user zoomed in the surface view
         *
         * @param detector // Our detector object
         * @return true // if the user pinches to zoom
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            // scaleFactor is used to update the hex grid after each zoom
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 3.0f));

            invalidate();
            return true;
        }
    }//ScaleListener
}


