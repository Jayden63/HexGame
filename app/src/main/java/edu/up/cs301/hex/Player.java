package edu.up.cs301.hex;
/**
 * @author Cody
 * @author Jayden
 * @author Chengen
 * @author Eduardo
 */
public class Player {
    private String name;
    private String color;

    public Player(String name, String color){
        this.name = name;
        this.color = color;
    }
    //copy constructor
    public Player(Player orig) {
        this.name = orig.name; //
        this.color = orig.color; //
    }








    //getter methods
    public String getColor(){
        return color;
    }
    public String getName(){
        return name;
    }
   //toString for player
    @Override
    public String toString() {
        return "Name: " + name + ", Color: " + color;
    }
}
