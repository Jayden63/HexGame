package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * An undoMove is an action that is a "move". The game undoes the
 * move of the current player and allows them tap a tile again
 *
 * @author Cody
 * @author Jayden
 * @author Chengen
 * @author Eduardo
 */
public class UndoMove extends GameAction {

    public UndoMove(GamePlayer player) {
        super(player);
    }
}
