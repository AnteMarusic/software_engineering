package org.polimi.messages;

import java.io.Serializable;
import java.util.Arrays;

public class StartGameMessage extends Message implements Serializable {
    private String[] players;

    public StartGameMessage(String username, String[] players){
        super(username, MessageType.START_GAME_MESSAGE);
        this.players=players;
    }
    @Override
    public String toString() {
        return super.toString() + "Game Start" + "the other players are:" + Arrays.toString(players);
    }
}
