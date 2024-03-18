package edu.up.cs301.hex;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class UndoMove extends GameAction {

    public UndoMove(GamePlayer player) {
        super(player);
    }
}
