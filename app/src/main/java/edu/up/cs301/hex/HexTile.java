package edu.up.cs301.hex;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author Cody
 * @author Jayden
 * @author Chengen
 * @author Eduardo
 */

/** The public class HexTile will draw the Hex grid, receive user tapped information,
 * and display the correct changes to the user
 *
 */
public class HexTile {

    // The center x & y coordinates of a hexagon
    private float centerX, centerY;

    // Radius used for hexTile creation
    private float radius;

    // Color of the hexgrid
    private int color;

    // Constructor for HexTile
    public HexTile(float centerX, float centerY, int color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = 41; // Adjust radius as needed
        this.color = color;
    }
    public void draw(Canvas canvas) {

        // Paint Object for HexTile
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

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

        // Coloring the grid
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


    /** isTouched(float x, float y)
     *
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