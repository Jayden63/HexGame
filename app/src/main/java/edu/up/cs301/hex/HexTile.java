package edu.up.cs301.hex;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;

/*
 * @author Cody
 * @author Jayden
 * @author Chengen
 * @author Eduardo
 */

/** The public class HexTile will draw the Hex grid, receive user tapped information,
 * and display the correct changes to the user
 *
 */
public class HexTile implements Serializable {
    // serial ID
    public static final long serialVersionUID = 202442391158L;

    // The center x & y coordinates of a hexagon
    private float centerX, centerY;

    // Radius used for hexTile creation
    public static float radius;

    // Color of the Hex Grid
    private int color;

    /**
     *
     * Constructor for HexTile
     * @param centerX // The x coordinate of the HexTile
     * @param centerY // The y coordinate of the HexTile
     * @param color // The color of the HexTile
     */
    public HexTile(float centerX, float centerY, int color) {

        // The starting position of the first hexTile
        this.centerX = centerX;
        this.centerY = centerY;
        radius = 42; // Adjust radius as needed
        this.color = color;
    }


    /** void draw(Canvas canvas)
     * draws a hexagon when HexTile is called
     *
     * @param canvas // the canvas to be drawn on
     */
    public void draw(Canvas canvas) {

        // Paint Object for HexTile
        Paint paint = new Paint();
        Paint borderPaint = new Paint();

        // Setting colors for hexTile
        paint.setColor(color);

        // The border color of the HexTile
        borderPaint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        // Outlines the perimeter of the hexTile
        borderPaint.setStyle(Paint.Style.STROKE);

        // For a solid visible border
        borderPaint.setStrokeWidth(1);

        // Path will determine the path of the HexTile
        Path path = new Path();
        float angle_deg, x, y;

        // Start the path at the top right vertex of the grid
        angle_deg = 30;
        x = centerX + radius * (float) Math.cos(Math.toRadians(angle_deg));
        y = centerY + radius * (float) Math.sin(Math.toRadians(angle_deg));
        path.moveTo(x, y);

        // Draw the other vertices of the hexagon
        for (int i = 1; i < 6; i++) {
            angle_deg += 60;
            x = centerX + radius * (float) Math.cos(Math.toRadians(angle_deg));
            y = centerY + radius * (float) Math.sin(Math.toRadians(angle_deg));
            path.lineTo(x, y);
        }

        // Closing the path
        path.close();

        // Colors the border HexTiles
        canvas.drawPath(path, borderPaint);

        // Colors the grid HexTiles
        canvas.drawPath(path, paint);

    }//draw



    // Setter method for setting HexTile color
    public void setColor(int color) {
        this.color = color;
    }

    // Getter method for returning HexTile color
    public int getColor() {
        return color;
    }

    // Getter method for getting the HexTile x coordinate
    public float getCenterX() {
        return centerX;
    }

    // For getting the HexTile y coordinate
    public float getCenterY() {
        return centerY ;
    }

    // For setting the HexTile x coordinate
    public void setCenterX(float centerX)    {
        this.centerX = centerX;
    }

    // For getting the HexTile y coordinate
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }


    /** isTouched(float x, float y)
     * The isTouched method will return true if the user tapped coordinates
     * Are inside a HexTile in the hex grid
     * Returns false is the tap is not inside a HexTile
     *
     * @param x // The tapped x coordinate
     * @param y // The tapped y coordinate
     * @return boolean
     */
    public boolean isTouched(float x, float y) {
        // Check if touch coordinates are inside the hexagon
        double dx = x - centerX;
        double dy = y - centerY;
        return dx * dx + dy * dy < radius * radius;
    }// isTouched
}