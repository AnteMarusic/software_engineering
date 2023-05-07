package org.polimi.messages;

import java.io.Serializable;

public class NewPlayerJoinedMessage extends Message implements Serializable {
    private String[] players;
    public NewPlayerJoinedMessage(String username, String[] players) {
        super(username, MessageType.NEW_PLAYER_JOINED);
        this.players = players;
    }

    public String[] getNewPlayer() {
        return this.players;
    }
}
