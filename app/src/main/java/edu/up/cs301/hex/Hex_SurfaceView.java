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
import android.graphics.Path;
import androidx.annotation.NonNull;
import java.io.Serializable;


public class Hex_SurfaceView extends SurfaceView implements Serializable {
    // serial ID
    public static final long serialVersionUID = 202442385352L;


    // Size and shape variables
    private float center_Height;
    private float center_Width;
    private HexState hexState;
    private final Paint redBackground = new Paint();
    private final Paint blueBackground = new Paint();
    private final Paint gradPaint = new Paint();
    private final Path redRect = new Path();
    private int startColor = Color.BLUE; // For gradient
    private int endColor = Color.RED; // For gradient
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float gradientPosition = 0;




    public Hex_SurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);


        hexState = new HexState();
        //hexState.initializeGrid();

        // Starts the dynamic gradient background
        startAnimation();

        setWillNotDraw(false);

    }//Hex_SurfaceView

    private void setContentView(int activityMain) {
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

        //
        //float width, height;

        // Calling the parent
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Assign width to the width of the surface view
        center_Width = MeasureSpec.getSize(widthMeasureSpec);

        // Assigns height to the height of surface view
        center_Height = MeasureSpec.getSize(heightMeasureSpec);

        // Fine tuning both values to make it centered
        center_Width = (center_Width / 2f) - 555f;//555
        center_Height = (center_Height / 2f) - 340f;

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


        // Calculate the current color based on the gradient position
        int currentColor =
                (Integer) argbEvaluator.evaluate(gradientPosition, startColor, endColor);

        // Create a new gradient using the current color and a fixed end color
        LinearGradient gradient =
                // The style of the gradient
                new LinearGradient(00, 00, getWidth() * 10, 0,
                        currentColor, endColor, Shader.TileMode.CLAMP);


        // Sets the paint to the gradient object
        gradPaint.setShader(gradient);

        // Draws the gradient in the surface view
        canvas.drawPaint(gradPaint);


        // To draw the hexTile borders of the hex grid
        // Always in the center regardless of orientation
        for (int i = 0; i < hexState.gridSize; i++) {

            // The top border of the hex grid
            float x = center_Width + (i * (float) (hexState.hexSize * 1.9f));
            HexTile topBorderTiles =
                    new HexTile(x, center_Height - 21, Color.BLUE);
            topBorderTiles.draw(canvas);

            // The right border of the hex grid
            float x4 = center_Width + 760 + (i * 37); //780
            float y2 = center_Height - 11 + (i * (float) (hexState.hexSize * 1.65f)); //28
            HexTile rightBorderTiles = new HexTile(x4, y2, Color.RED);
            rightBorderTiles.draw(canvas);

            // The bottom border of the hex grid
            float x2 = center_Width + 370 + (i * (float) (hexState.hexSize * 1.9f));
            HexTile bottomBorderTiles =
                    new HexTile(x2, center_Height + 666, Color.BLUE);
            bottomBorderTiles.draw(canvas);

            // The left border of the hex grid
            float x3 = center_Width - 20 + (i * 37); //20
            float y = center_Height + 11 + ((i * (float) (hexState.hexSize) * 1.65f));
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
                        + (float) (i * (hexState.hexSize * 1.65f)));

                // To draw the grid in the surface view
                HexTile tile = hexState.grid[i][j];

                if (tile != null) {  // Null check to avoid NullPointerException
                    // Drawing the grid in the canvas
                    tile.draw(canvas);
                }
            }
        }
    }//onDraw


    /**
     * startAnimation()
     *
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
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            // Always called to update the animation
            public void onAnimationUpdate(ValueAnimator animation) {
                gradientPosition = (float) animation.getAnimatedValue();
                invalidate(); // Redraw the view
            }
        });
        // Called in every animation frame
        animator.start();

    }//startAnimation
}


