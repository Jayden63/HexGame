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

public class HexTile {



    private float centerX, centerY;
    private float radius;
    private int color;

    public HexTile(float centerX, float centerY, int color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = 40; // Adjust radius as needed
        this.color = color;
    }
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        float angle_deg, x, y;

        // Start the path at the top vertex
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

        path.close();
        canvas.drawPath(path, paint);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public boolean isTouched(float x, float y) {
        // Check if touch coordinates are inside the hexagon
        double dx = x - centerX;
        double dy = y - centerY;
        return dx * dx + dy * dy < radius * radius;
    }
}