package org.polimi.messages;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class StartGameMessage extends Message implements Serializable {
    private List<String> players;

    public StartGameMessage(String username, List<String> players){
        super(username, MessageType.START_GAME_MESSAGE);
        this.players=players;
    }
    @Override
    public String toString() {
        return super.toString() + "Game Start" + "the other players are:" + players;
    }
}
