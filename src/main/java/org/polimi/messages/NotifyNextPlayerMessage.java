package org.polimi.messages;

import java.io.Serializable;

public class NotifyNextPlayerMessage extends Message implements Serializable {
    private String nextPlayer;
    private int nextPlayerInt;
    public NotifyNextPlayerMessage (String username, String nextPlayer, int nextPlayerInt) {
        super (username, MessageType.NOTIFY_NEXT_PLAYER);
        this.nextPlayer = nextPlayer;
        this.nextPlayerInt = nextPlayerInt;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public int getNextPlayerInt(){
        return nextPlayerInt;
    }
}
