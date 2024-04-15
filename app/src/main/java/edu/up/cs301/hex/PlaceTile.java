package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * @author Cody
 * @author Jayden
 * @author Chengen
 * @author Eduardo
 */

public class PlaceTile extends GameAction {
    private int x;
    private int y;

    public PlaceTile(GamePlayer player, int x, int y) {
        super(player);
        this.x = x;
        this.y = y;
    }

    // getter methods for x and y coordinates
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
