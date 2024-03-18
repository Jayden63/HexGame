package edu.up.cs301.hex;

public class HexTile {
    private String color;
    public HexTile(){
        //tile would empty
        color = null;
    }
    //copy constructor
    public HexTile(HexTile orig) {
        this.color = orig.color;
    }

    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
    //toString for HexTile
    @Override
    public String toString() {
        return color != null ? color.substring(0, 1) : "."; // returns first letter of color, or "." for empty
    }
}
