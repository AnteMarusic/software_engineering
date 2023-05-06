package org.polimi.messages;

import org.polimi.server.model.Coordinates;

import java.io.Serializable;

/**
 * this message will be received at the end of each turn by the players that didn't play the turn. Contains the updates
 * that the other player made in his turn
 */
public class ModelStatusUpdateMessage extends Message implements Serializable {
    private final Coordinates[] toRemove;
    private final int colToInsert;
    private final String[] usernames;

    public ModelStatusUpdateMessage(String username, Coordinates[] toRemove, int colToInsert, String[] usernames) {
        super(username, MessageType.MODEL_STATUS_UPDATE);
        this.toRemove = toRemove;
        this.colToInsert = colToInsert;
        this.usernames = usernames;
    }

    public Coordinates[] getToRemove() {
        return toRemove;
    }

    public int getColToInsert() {
        return colToInsert;
    }

    public String[] getUsernames() {
        return usernames;
    }
}
