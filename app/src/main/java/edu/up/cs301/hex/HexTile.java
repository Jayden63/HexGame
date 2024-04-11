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

    public static int EMPTY_COLOR = 0xFFAAAAAA;  // med gray
    public static int RED_COLOR = 0xFF0000; // red color
    public static int BLUE_COLOR = 0x0000FF; // blue color
    private int color;
    protected float x; // x-coord
    protected float y; // y-coord
    protected final int size = 20; // all spots are size 20
    protected Paint myPaint; // how the spot is drawn

    // instance variables to draw a hexagon
    private float width = 85;
    private float height = 85;
    private float radius = (height / 2);
    private Path hexTilePath;

    /** basic constructor*/
    public HexTile(){
        //tile would empty
        color = 0;
    }
    //copy constructor
    public HexTile(HexTile orig) {
        this.color = orig.color;
    }

    /** this ctor creates a HexTile at a specified location */
    public HexTile(float initX, float initY) {
        // place a spot in a random location
        x = initX;
        y = initY;
        setRandomPaint();
    }

    /** gives the spot a random colored paint */
    protected void setRandomPaint() {
        int color = Color.rgb((int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256));
        myPaint = new Paint();
        myPaint.setColor(color);
    }

    /** HexTile can draw itself on a given canvas */
    public void draw(Canvas canvas) {
        // canvas.drawPath(hexTilePath, myPaint);
        canvas.drawCircle(x, y, size, myPaint);
    }

    public int getColor(){
        return color;
    }
    public void setColor(int color){
        this.color = color;
    }

    //toString for HexTile
    /*@Override
    public String toString() {
        return color != null ? color.substring(0, 1) : "."; // returns first letter of color, or "." for empty
    }*/
}
