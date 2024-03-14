package edu.up.cs301.hex;

public class Player {
    private String name;
    private String color;

    public Player(String name, String color){
        this.name = name;
        this.color = color;
    }
    //getter methods
    public String getColor(){
        return color;
    }
    public String getName(){
        return name;
    }

}
