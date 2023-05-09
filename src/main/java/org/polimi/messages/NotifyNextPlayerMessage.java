package org.polimi.messages;

import java.io.Serializable;

public class NotifyNextPlayerMessage extends Message implements Serializable {
    private String nextPlayer;
    public NotifyNextPlayerMessage (String username, String nextPlayer) {
        super (username, MessageType.NOTIFY_NEXT_PLAYER);
        this.nextPlayer = nextPlayer;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }
}
