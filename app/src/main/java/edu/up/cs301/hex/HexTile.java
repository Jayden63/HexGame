package edu.up.cs301.hex;

public class HexTile {
    private String color;
    public HexTile(){
        //tile would empty
        color = null;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
}
