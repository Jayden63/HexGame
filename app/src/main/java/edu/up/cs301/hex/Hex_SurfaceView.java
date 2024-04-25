package edu.up.cs301.hex;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Color;
import androidx.annotation.NonNull;
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
    // serial ID
    public static final long serialVersionUID = 202442385352L;


    // Size and shape variables
    private float center_Height;
    private float center_Width;
    private float width_SurfaceView, height_SurfaceView;
    private HexState hexState;
    private final Paint gradPaint = new Paint();
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float gradientPosition = 0;

    private float scaleFactor = 1.0f;
    private final ScaleGestureDetector scaleGestureDetector;


    /**
     * When the game starts up
     * @param context // System
     * @param attrs // Attributes of the surface view
     */
    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        hexState = new HexState();
        //hexState.initializeGrid();

        // Starts the dynamic gradient background
        startAnimation();

        setWillNotDraw(false);

    }//Hex_SurfaceView



    /**
     * onTouchEvent listens for any pinch to zoom actions in the Surface View
     * In the Surface View
     * @param event // Our event object
     * @return true // if the pinch to zoom action is performed
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events
        scaleGestureDetector.onTouchEvent(event);

        return true;
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

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();

        // Scale the canvas from the center according to the zoom gesture
        canvas.scale(scaleFactor, scaleFactor,
                width_SurfaceView / 2, height_SurfaceView / 2);

        // The revolving colors of the gradient
        int startColor = Color.BLUE;
        int endColor = Color.RED;

        // Calculate the current color based on the gradient position
        int currentColor =
                (Integer) argbEvaluator.evaluate(gradientPosition, startColor, endColor);

        // Create a new gradient using the current color and a fixed end color
        LinearGradient gradient =
                // The style of the gradient
                new LinearGradient(0, 0, getWidth() * 10, 0,
                        currentColor, endColor, Shader.TileMode.CLAMP);


        // Sets the paint to the gradient object
        gradPaint.setShader(gradient);

        // Draws the gradient in the surface view
        canvas.drawPaint(gradPaint);


        // To draw the hexTile borders of the hex grid
        // Always in the center regardless of orientation
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


        // Draws each tile in the Hex Grid
        // We are using the grid directly from the hexState class
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {

                // Copied values from HexState but making x values centered
                // The x distance of different rows
                hexState.grid[i][j].setCenterX(center_Width + (i * 37)
                        // The x distance between individual tiles in the same row
                        + (float) (j * hexState.hexSize * 1.90));


                // The y distance between rows. Greater value = greater distance
                hexState.grid[i][j].setCenterY(center_Height
                        + (i * (hexState.hexSize * 1.65f)));

                // To draw the grid in the surface view
                HexTile tile = hexState.grid[i][j];

                if (tile != null) {  // Null check to avoid NullPointerException
                    // Drawing the grid in the canvas
                    tile.draw(canvas);
                }
            }
        }
        canvas.restore();
    }//onDraw


    /**
     * Updates the coordinates of the hex values
     */
    public void updateHexGrid() {
        for (int i = 0; i < hexState.gridSize; i++) {
            for (int j = 0; j < hexState.gridSize; j++) {
                HexTile tile = hexState.grid[i][j];
                if (tile != null) {
                    float newCenterX = (tile.getCenterX() * scaleFactor);
                    float newCenterY = (tile.getCenterY() * scaleFactor);
                    tile.setCenterX(newCenterX);
                    tile.setCenterY(newCenterY);

                    // Update the coordinates in the HexState class
                    hexState.grid[i][j].setCenterX(newCenterX);
                    hexState.grid[i][j].setCenterY(newCenterY);
                }
            }
        }
    }

    /**
     * startAnimation()
     * This method is used to start the dynamic gradient background animation
     * In the Surface View.
     */
    public void startAnimation() {
        // Animator object animates between two values, the two colors
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // 10 seconds is a good time to minimize distraction and seizures
        animator.setDuration(10000); // Duration can be changed

        // Animation will run in the anti direction when it ends
        animator.setRepeatMode(ValueAnimator.REVERSE);

        // Will always run
        animator.setRepeatCount(ValueAnimator.INFINITE);

        // Updates the value to 0 or 1, the red & blue colors
        // Always called to update the animation
        animator.addUpdateListener(animation -> {
            gradientPosition = (float) animation.getAnimatedValue();
            invalidate(); // Redraw the view
        });
        // Called in every animation frame
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

            // Gets the scale of the zoom
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 3.0f));

            updateHexGrid();

            invalidate();
            return true;
        }
    }//ScaleListener
}


