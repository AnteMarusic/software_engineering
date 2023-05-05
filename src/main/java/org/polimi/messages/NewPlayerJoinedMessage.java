package org.polimi.messages;

public class NewPlayerJoinedMessage extends Message{
    private String newPlayer;
    public NewPlayerJoinedMessage(String username, String newPlayer) {
        super(username, MessageType.NEW_PLAYER_JOINED);
        this.newPlayer = newPlayer;
    }

    public String getNewPlayer() {
        return this.newPlayer;
    }
}
