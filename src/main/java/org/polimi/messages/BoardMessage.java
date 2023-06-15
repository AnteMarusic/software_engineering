package org.polimi.messages;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.Serializable;
import java.util.Map;

public class BoardMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;
    public BoardMessage (Map<Coordinates, Card> board){
        super("server", MessageType.BOARDMESSAGE);
        this.board = board;
    }

    public Map<Coordinates, Card> getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "BoardMessage";
    }
}
